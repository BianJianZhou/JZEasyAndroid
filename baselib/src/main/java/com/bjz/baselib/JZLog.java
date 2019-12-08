package com.bjz.baselib;

import android.util.Log;

public class JZLog {
    private static String TAG = "JZBaseLib: ";

    public static void d(String tag, String msgStr) {
//        Log.i(TAG + tag, msgStr);
        System.out.println(TAG + tag + msgStr);
    }

    public static void e(String tag, String msgStr) {
//        Log.e(TAG + tag, msgStr);
        System.out.println(TAG + tag + msgStr);
    }
}
