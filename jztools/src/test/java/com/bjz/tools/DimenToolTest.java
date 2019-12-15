package com.bjz.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        dimenTool.init();
    }
}