package com.bjz.wystudytestlib.thread.synchronizedTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.bjz.wystudytestlib.R;

public class SynchronizedTestActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronized_test);

        bindView();
        setOnclick();
    }

    private void bindView(){
        textView = findViewById(R.id.synchronized_test_text);
    }

    private void setOnclick(){
        textView.setOnClickListener(view -> {

        });
    }

}
