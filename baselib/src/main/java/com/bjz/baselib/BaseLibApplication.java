package com.bjz.baselib;

import android.app.Application;
import android.util.Log;

import io.github.prototypez.appjoint.core.ModuleSpec;

@ModuleSpec
public class BaseLibApplication extends Application {

    String TAG = "BaseLibApplication";

    Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = (Application)getApplicationContext();
        /* 本模块的初始化行为 */
        Log.d(TAG, "onCreate()");
    }
}
