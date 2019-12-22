package com.bjz.jzeasyandroid;

import android.widget.TextView;

import com.bjz.baselib.ui.page.JZBaseActivity;

public class MainActivity extends JZBaseActivity<MainPresenter> {

    StudyTestManager studyTestManager;

    TextView textView;

    TextView
            toBitmapTestText;

    @Override
    public int getResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        textView = findViewById(R.id.main_skip_synchronized_test_text);
        toBitmapTestText = findViewById(R.id.main_skip_bitmap_operation_test_text);
    }

    @Override
    public void initData() {
        studyTestManager = new StudyTestManager();
        setTitle("JZEasyAndroid");
    }

    private static final String bitmapOperationActivity = "com.bjz.wystudytestlib.imgOperation.ui.BitmapOperationActivity";

    @Override
    public void setListener() {
        textView.setOnClickListener(view -> {
            StudyTestManager.synchronizedTestClass.skipSynchronizedTestActivity(this);
        });
        /* 跳转bitmap测试页面 */
        toBitmapTestText.setOnClickListener(v -> {
//            StudyTestManager.synchronizedTestClass.skipBitmapOperationActivity(this);
            try {
                jumpNextPage(null, Class.forName(bitmapOperationActivity));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public MainPresenter getPresenter() {
        return new MainPresenter(this, this);
    }


}
