package com.bjz.wyeasyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    StudyTestManager studyTestManager;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        setOnClick();

        studyTestManager = new StudyTestManager();

    }

    private void bindView(){
        textView = findViewById(R.id.main_skip_synchronized_test_text);
    }

    private void setOnClick(){
        textView.setOnClickListener(view -> {
            StudyTestManager.synchronizedTestClass.skipSynchronizedTestActivity(this);
        });
    }


}
