package com.bjz.wyeasyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    StudyTestManager studyTestManager;

    TextView textView;

    TextView
            toBitmapTestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        setOnClick();

        studyTestManager = new StudyTestManager();

    }

    private void bindView() {
        textView = findViewById(R.id.main_skip_synchronized_test_text);
        toBitmapTestText = findViewById(R.id.main_skip_bitmap_operation_test_text);
    }

    private void setOnClick() {
        textView.setOnClickListener(view -> {
            StudyTestManager.synchronizedTestClass.skipSynchronizedTestActivity(this);
        });
        /* 跳转bitmap测试页面 */
        toBitmapTestText.setOnClickListener(v -> {
            StudyTestManager.synchronizedTestClass.skipBitmapOperationActivity(this);
        });
    }


}
