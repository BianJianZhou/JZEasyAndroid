package com.bjz.wystudytestlib.designMode.decorator._2;

/*
 这里我们定义一种 形状 圆
  */
public class Circle implements MyShape {
    @Override
    public void draw() {
        System.out.println("shape：circle");
    }
}
