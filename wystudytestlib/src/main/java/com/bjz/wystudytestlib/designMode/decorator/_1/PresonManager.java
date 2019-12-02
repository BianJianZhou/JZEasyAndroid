package com.bjz.wystudytestlib.designMode.decorator._1;

/* 这个类我们用懒汉式单例来实现一下 */
/* 作用是用来主动控制这个苦逼的程序员开始一天的生活 */
/* 然后我们使用单元测试看下控制台信息.. */
public class PresonManager {

//    private static volatile PresonManager manager = null;
    private static PresonManager manager = new PresonManager();

    private PresonManager() {

    }

    public static PresonManager getInstance() {
        if (manager == null) {
            synchronized (PresonManager.class) {
                if (manager == null) {
                    manager = new PresonManager();
                }
            }
        }
        return manager;
    }

    /* 我们来操纵这个 preson 来做事情 */
    public void presonDoSomething() {
        /* 慢着，程序员为什么是 new 出来的 呐呐.. */

        /* 正常是这么弄的 */
        /* 1 */
        Preson preson = new Programmer();
        Decorator_1 decorator_1 = new Decorator_1(preson);
        Decorator_2 decorator_2 = new Decorator_2(decorator_1);
        Decorator_3 decorator_3 = new Decorator_3(decorator_2);
        Decorator decorator = decorator_3;

        /* 或者2 */
        Decorator decorator2 = new Decorator_3(new Decorator_2(new Decorator_1(preson)));

        decorator.wearClothes();
        decorator.walk();
        System.out.println("+++++++++++++万恶分割线++++++++++++++");
        decorator2.wearClothes();
        decorator2.walk();

        System.out.println("+++++++++++++万恶分割线++++++++++++++");

        preson = new Decorator_1(preson);
        preson = new Decorator_2(preson);
        preson = new Decorator_3(preson);
        preson.wearClothes();
        preson.walk();


        /* 呐呐，这么写挺恶心的，写个方法改下形式吧.. */
//        Decorator decoratorx = decoratorSomething();

    }

    public void decoratorSomething(Decorator... decorator){
        if (decorator != null){

        }
    }

}
