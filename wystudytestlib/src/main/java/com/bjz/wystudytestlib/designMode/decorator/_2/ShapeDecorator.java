package com.bjz.wystudytestlib.designMode.decorator._2;

/*
我们对于形状 进行装饰
*/
public abstract class ShapeDecorator implements MyShape {

    protected MyShape shapeDecorator;

    public ShapeDecorator(MyShape shapeDecorator) {
        this.shapeDecorator = shapeDecorator;
    }

    @Override
    public void draw() {
        shapeDecorator.draw();
    }
}
