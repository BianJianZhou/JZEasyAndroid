package com.bjz.wystudytestlib.designMode.decorator._2;

/*
 这里我们定义一种形状 矩形
  */
public class Rectangle implements MyShape {
    @Override
    public void draw() {
        System.out.println("shape：rectangle");
    }
}
