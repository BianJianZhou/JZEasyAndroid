package com.bjz.wystudytestlib.designMode.decorator._2;

/*
我们对于装饰的形状 进行具体实现
*/
public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(MyShape shapeDecorator) {
        super(shapeDecorator);
    }

    @Override
    public void draw() {
        shapeDecorator.draw();
        setRedBorder();
    }

    public void setRedBorder(){
        System.out.println("shape set broder：red");
    }
}
