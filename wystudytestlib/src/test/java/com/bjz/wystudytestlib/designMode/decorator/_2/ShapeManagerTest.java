package com.bjz.wystudytestlib.designMode.decorator._2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShapeManagerTest {

    ShapeManager shapeManager;

    @Before
    public void setUp() throws Exception {
        shapeManager = new ShapeManager();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doSomething() {
        shapeManager.doSomething();
    }
}