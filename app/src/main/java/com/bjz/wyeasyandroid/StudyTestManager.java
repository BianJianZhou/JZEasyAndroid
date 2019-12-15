package com.bjz.wyeasyandroid;

import android.content.Context;
import android.content.Intent;

public class StudyTestManager {

    public static class synchronizedTestClass {

        private static final String synchronizedActivityPackage = "com.bjz.wystudytestlib.thread.synchronizedTest.SynchronizedTestActivity";
        private static final String bitmapOperationActivity = "com.bjz.wystudytestlib.imgOperation.ui.BitmapOperationActivity";

        /* 跳转同步锁测试页面 */
        public static void skipSynchronizedTestActivity(Context context) {
            try {
                context.startActivity(new Intent(context, Class.forName(synchronizedActivityPackage)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void skipBitmapOperationActivity(Context context) {
            try {
                context.startActivity(new Intent(context, Class.forName(bitmapOperationActivity)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
