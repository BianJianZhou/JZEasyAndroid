package com.bjz.baselib;

import com.bjz.baselib.event.JZCallBack;
import com.bjz.baselib.event.JZCallBackDataVo;
import com.bjz.baselib.event.JZSingleCallBack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WYBasePresenterTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void asfddfasdfas() {
        WYBasePresenter.regist();
        System.out.print("1");
        System.out.print("\n");
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.print("2");
                    System.out.print("\n");
                    System.out.print("3");
                    System.out.print("\n");
                    JZCallBack.single("test").send("myasdfasd", new JZCallBackDataVo().setData("我是你爸爸"));
                } catch (Exception e) {
                    System.out.print("4");
                    System.out.print("\n");
                    e.printStackTrace();
                }
            }
        }.start();
    }
}