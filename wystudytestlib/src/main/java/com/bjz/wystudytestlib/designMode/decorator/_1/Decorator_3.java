package com.bjz.wystudytestlib.designMode.decorator._1;

/*
我们丰富 preson 的行为
*/
public class Decorator_3 extends Decorator{
    public Decorator_3(Preson preson) {
        super(preson);
    }

    public void getDressed(){
        System.out.println("嗯嗯..找到了..终于穿好衣服了..");
    }

    public void goHomeDoTheCooking(){
        System.out.println("算了..没钱..还是回家吃泡面吧..");
    }

    @Override
    public void wearClothes() {
        super.wearClothes();
        getDressed();
    }

    @Override
    public void walk() {
        super.walk();
        goHomeDoTheCooking();
    }
}
