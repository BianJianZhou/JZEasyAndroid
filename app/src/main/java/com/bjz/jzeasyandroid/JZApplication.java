package com.bjz.jzeasyandroid;

import android.app.Application;

import io.github.prototypez.appjoint.core.AppSpec;


@AppSpec
public class JZApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /* router 的实现类 需要添加注解：@ServiceProvider */
    }
}
