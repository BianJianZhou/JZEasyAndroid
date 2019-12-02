package com.bjz.wyeasyandroid;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DimenToolTest {

    DimenTool dimenTool;

    @Before
    public void setUp() throws Exception {
        dimenTool = new DimenTool();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void init() {
//        dimenTool.init();
        dimenTool.createOneDimensFile();
    }
}