package com.bjz.wystudytestlib.designMode.decorator._1;

/*
 那么这个装饰着用来干什么呢？
 我们帮助处理 “人” preson 的行为
 我们来对这个装饰着进行扩展，丰富人的行为/人的行为过程进行诠释/丰富行为细节
  */
public class Decorator implements Preson {

    private Preson preson;

    public Decorator(Preson preson) {
        this.preson = preson;
    }

    @Override
    public void wearClothes() {
        preson.wearClothes();
    }

    @Override
    public void walk() {
        preson.walk();
    }
}
