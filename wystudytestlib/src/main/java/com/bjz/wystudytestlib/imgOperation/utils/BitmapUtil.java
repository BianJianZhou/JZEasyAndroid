package com.bjz.wystudytestlib.imgOperation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    /* 本地图片转bitmap */
    public static Bitmap fileToBitmap(String fileAbsPath) {
        if (!new File(fileAbsPath).exists()) {
            /* 图片不存在 */
            Log.i("BitmapUtil", "file not find");
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeFile(fileAbsPath, options);
        if (bmp != null) {
            Log.i("BitmapUtil", "fileToBitmap bmp size：" + bmp.getByteCount());
        }
        return bmp;
    }


    /**
     * 将本地图片转成Bitmap
     * @param path 已有图片的路径
     * @return
     */
    public static Bitmap openImage(String path){
        Bitmap bitmap = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null){
            Log.i("BitmapUtil", "openImage bmp size：" + bitmap.getByteCount());
        }
        return bitmap;
    }

    /* 资源文件转bitmap */
    public static Bitmap resToBitmap(Context context, int resId) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resId);
        return bmp;
    }

    /* bitmap存本地 */
    /* String savePath = "/sdcard"; */
    public static void bitSaveNative(Bitmap bmp, String savePath, String imgName) {
        /* 需要进行判断是否有读写存储卡权限 */
        /* 还没有实现 */
        /* 这里是干啥的不懂 */
        File pathFile = new File(savePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        Bitmap.CompressFormat bmpFormat = Bitmap.CompressFormat.JPEG;
        String imgNameTemp = imgName;
        String suffix = ".jpg";
        if (imgNameTemp.contains(".png")) {
            bmpFormat = Bitmap.CompressFormat.PNG;
            suffix = ".png";
        }
        if (imgNameTemp.contains(".")) {
            imgNameTemp = imgNameTemp.substring(0, imgNameTemp.indexOf("."));
        }
        imgNameTemp = imgNameTemp + "_" + System.currentTimeMillis() + suffix;
        String imgAbsPath = savePath + "/" + imgNameTemp;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(imgAbsPath));
            bmp.compress(bmpFormat, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            /* 输出保存文件的路径 */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
