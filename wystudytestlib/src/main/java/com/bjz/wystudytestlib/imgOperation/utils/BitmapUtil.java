package com.bjz.wystudytestlib.imgOperation.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.File;

public class BitmapUtil {

    /* 本地图片转bitmap */
    public static Bitmap fileToBitmap(String fileAbsPath) {
        if (!new File(fileAbsPath).exists()) {
            /* 图片不存在 */
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeFile(fileAbsPath, options);
        return bmp;
    }

    /* 资源文件转bitmap */
    public static Bitmap resToBitmap() {
        return null;
    }

    /* bitmap存本地 */
    public static void bitSaveNative(String bmp, String savePath, String imgName) {

    }

    /* 资源文件转drawable */
    public static Drawable resToDrawable(String resId) {
        return null;
    }

    /* bitmap转drawable */
    public static Drawable bmpToDra(Bitmap bmp) {
        return null;
    }

    /* drawable转bitmap */
    public static Bitmap draToBmp(Drawable dra) {
        return null;
    }

    /* raw 文件夹下，resest 文件夹下 */

}
