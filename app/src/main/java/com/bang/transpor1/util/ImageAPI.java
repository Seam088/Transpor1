package com.bang.transpor1.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by 12457 on 2018/11/8.
 */

public class ImageAPI {

    public static Bitmap getImageByFilePath(String filePath, int scale) {
        Bitmap res = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        return res;
    }

    public static Bitmap getImageByFilePath(String filePath, int Towidth, int ToHeight) {
        Bitmap res = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (!new File(filePath).exists())
            return res;
        BitmapFactory.decodeFile(filePath, options);

        int origionalWidth = options.outHeight;
        int origionalHeight = options.outWidth;
        options.inJustDecodeBounds = false;
        int scale = Math.max(origionalWidth / Towidth, origionalHeight / ToHeight);
        options.inSampleSize = scale;
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        try {
            res = BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }

}