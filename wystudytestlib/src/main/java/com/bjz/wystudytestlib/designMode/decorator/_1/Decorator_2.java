package com.bjz.wystudytestlib.designMode.decorator._1;

/*
我们丰富 preson 的行为
*/
public class Decorator_2 extends Decorator{

    public Decorator_2(Preson preson) {
        super(preson);
    }

    public void LookingForClothes(){
        System.out.println("快点，快点，先回家...衣服在哪里..");
    }

    public void wantBuyFood(){
        System.out.println("哇，超市东西好多啊。。");
    }

    @Override
    public void wearClothes() {
        super.wearClothes();
        LookingForClothes();
    }

    @Override
    public void walk() {
        super.walk();
        wantBuyFood();
    }

}
