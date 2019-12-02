package com.bjz.wystudytestlib.designMode.decorator._1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PresonManagerTest {

    PresonManager manager = null;

    @Before
    public void setUp() throws Exception {
        manager = PresonManager.getInstance();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void programmerDoSomeThing(){
        manager.presonDoSomething();
    }
}