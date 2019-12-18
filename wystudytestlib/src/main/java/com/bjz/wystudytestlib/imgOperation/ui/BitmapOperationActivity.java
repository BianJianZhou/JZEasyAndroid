package com.bjz.wystudytestlib.imgOperation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjz.wystudytestlib.R;
import com.bjz.wystudytestlib.imgOperation.utils.BitmapUtil;

public class BitmapOperationActivity extends Activity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_operation);

        nativePicToBmpText = findViewById(R.id.bitmap_test_native_pic_to_bitmap_text);
        img = findViewById(R.id.bitmap_test_img);
        /* 本地图片转bitmap */
        nativePicToBmpText.setOnClickListener(view -> {
            Bitmap bmp = BitmapUtil.fileToBitmap(savePath + "/" + imgName2);
            Bitmap bmp2 = BitmapUtil.openImage(savePath + "/" + imgName2);
            img.setImageBitmap(bmp);
        });
    }
}
