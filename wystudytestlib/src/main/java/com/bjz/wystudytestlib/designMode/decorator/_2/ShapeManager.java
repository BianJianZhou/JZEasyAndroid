package com.bjz.wystudytestlib.designMode.decorator._2;

public class ShapeManager {
    public void doSomething(){
        MyShape circle = new Circle();
        MyShape rectangle = new Rectangle();

        ShapeDecorator redCircle = new RedShapeDecorator(circle);
        ShapeDecorator redRectangle = new RedShapeDecorator(rectangle);

        System.out.println("+++++++++++++万恶分割线++++++++++++++");
        circle.draw();
        System.out.println("+++++++++++++万恶分割线++++++++++++++");
        rectangle.draw();
        System.out.println("+++++++++++++万恶分割线++++++++++++++");
        redCircle.draw();
        System.out.println("+++++++++++++万恶分割线++++++++++++++");
        redRectangle.draw();

    }
}
