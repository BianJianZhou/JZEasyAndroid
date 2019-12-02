package com.bjz.wystudytestlib.designMode.decorator._1;

/*
我们丰富 preson 的行为
*/

public class Decorator_1 extends Decorator {
    public Decorator_1(Preson preson) {
        super(preson);
    }

    public void goHome(){
        System.out.println("现在光着屁股呢，丁丁好凉啊..");
    }

    public void goShopping(){
        System.out.println("去超市吧..");
    }

    @Override
    public void wearClothes() {
        super.wearClothes();
        goHome();
    }

    @Override
    public void walk() {
        super.walk();
        goShopping();
    }
}
