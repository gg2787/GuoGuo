package com.guoguo.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/3/29.
 */
public class FileUtils {
    public static boolean isExistSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getExternalStoragePath() {
        if (isExistSdcard()) {
            File file = Environment.getExternalStorageDirectory();
            if(file != null && file.exists()){
                return file.getAbsolutePath();
            }
        }
        return "";
    }
}
