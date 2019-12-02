package com.bjz.wyeasyandroid;

import android.content.Context;
import android.content.Intent;

public class StudyTestManager {

    public static class synchronizedTestClass {

        private static final String synchronizedActivityPackage = "com.bjz.wystudytestlib.thread.synchronizedTest.SynchronizedTestActivity";

        /* 跳转同步锁测试页面 */
        public static void skipSynchronizedTestActivity(Context context) {
            try {
                Intent intent = new Intent(context,Class.forName(synchronizedActivityPackage));
                context.startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
