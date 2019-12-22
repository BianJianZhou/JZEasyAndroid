package com.bjz.wystudytestlib.imgOperation.ui;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjz.baselib.ui.page.JZBaseActivity;
import com.bjz.wystudytestlib.R;
import com.bjz.wystudytestlib.imgOperation.presenter.BitmapOperationPresenter;
import com.bjz.wystudytestlib.imgOperation.utils.BitmapUtil;

public class BitmapOperationActivity extends JZBaseActivity<BitmapOperationPresenter> {

    /* 两个本地测试图片的路径和 名称 */
    /* 123.jpg native_bmp_test_img.jpg */

    String savePath = "/sdcard/0JzEasyAndroid";
    String imgName1 = "123.jpg";
    String imgName2 = "native_bmp_test_img.jpg";

    /* 本地图片转bitmap */
    TextView
            nativePicToBmpText;

    ImageView
            img;

    @Override
    public int getResId() {
        return R.layout.activity_bitmap_operation;
    }

    @Override
    public void initView() {
        nativePicToBmpText = findViewById(R.id.bitmap_test_native_pic_to_bitmap_text);
        img = findViewById(R.id.bitmap_test_img);
    }

    @Override
    public void initData() {
        setTitle("Bitmap 测试页面");
    }

    @Override
    public void setListener() {
        /* 本地图片转bitmap */
        nativePicToBmpText.setOnClickListener(view -> {
            Bitmap bmp = BitmapUtil.fileToBitmap(savePath + "/" + imgName2);
            Bitmap bmp2 = BitmapUtil.openImage(savePath + "/" + imgName2);
            img.setImageBitmap(bmp);
        });
    }

    @Override
    public BitmapOperationPresenter getPresenter() {
        return new BitmapOperationPresenter(this, this);
    }

}
