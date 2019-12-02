package com.bjz.wystudytestlib.designMode.decorator._1;

/* 那么多的类 我们终于将 人 简单的行为定义完了，那么我们找谁来测试呢，呐呐拿拿，苦逼的程序员吧.. */
public class Programmer implements Preson {
    @Override
    public void wearClothes() {
        System.out.println("上班要迟到了，完蛋了，出来忘了穿衣服..");
    }

    @Override
    public void walk() {
        System.out.println("好累啊，我想请一天假，想想去那呢..");
    }
}
