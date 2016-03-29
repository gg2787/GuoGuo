package com.guoguo.wallpaperAndLock;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.guoguo.R;
import com.guoguo.utils.FileUtils;
import com.guoguo.utils.UIutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/25.
 */
public class Wallpaper {
    private static final int CEIL_TO_DECIMALS = 100;
    private static final int SAVE_JPG_QUALITY = 100;

    public static final int SAVE_RESULT_EXIST = 1;
    public static final int SAVE_RESULT_SUCCESS = 2;
    public static final int SAVE_RESULT_FAIL = 3;

    public void changeWall(final Activity activity) {

        final Bitmap  bitmap = UIutils.loadBitmap(activity.getApplicationContext(), R.drawable.small2);

        final Bitmap bitmap2 = imageScaleAndCrop(activity, bitmap);

        new Thread(new Runnable() {
            public void run() {
                //execute the task
                //do something
                setWallpaper(activity.getApplicationContext(), bitmap2);
                bitmap2.recycle();
            }
        }).start();

//        setWallpaper(context, bitmap);
    }

    private void setWallpaper (Context context, Bitmap bitmap) {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

        try {
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Bitmap imageScaleAndCrop(Activity activity, Bitmap bitmapSrc) {
        if (null == activity || null == bitmapSrc) {
            return null;
        }
        getWallpaper(activity.getApplicationContext());
        //计算屏幕的宽高比
        int[] nScreenSize = UIutils.getScreenSize(activity);
        if (nScreenSize.length < 2) {
            return null;
        }
        int nScreenWidth = nScreenSize[0];
        int nScreenHeight = nScreenSize[1];

        int nSrcWidth = bitmapSrc.getWidth();
        int nSrcHeight = bitmapSrc.getHeight();

        //计算出缩放,先裁剪 后缩放
        float fScale = Math.max((float) nScreenWidth / (float) nSrcWidth, (float) nScreenHeight / (float) nSrcHeight);
        Matrix matrix = new Matrix();
        float fScaleMatrix = (float)Math.ceil(fScale * 100) / 100;
        matrix.setScale(fScaleMatrix, fScaleMatrix);

        int nDstX = (int)(Math.min(nSrcWidth, Math.ceil(nScreenWidth / fScale)));
        int nDstY = (int)(Math.min(nSrcHeight, Math.ceil(nScreenHeight / fScale)));
        int nStartX = (int)Math.floor((nSrcWidth - nDstX) / 2);
        int nStartY = (int)Math.floor((nSrcHeight - nDstY) / 2);

        //裁剪图片
        Bitmap bitmapDstScale = null;
        try {
            bitmapDstScale = Bitmap.createBitmap(bitmapSrc, nStartX, nStartY,
                    nDstX, nDstY, matrix, false);
        } catch (IllegalArgumentException e) {
            return null;
        }

        Bitmap bitmapDstAdjust = Bitmap.createBitmap(bitmapDstScale, 0, 0,
                nScreenWidth, nScreenHeight);
        bitmapDstScale.recycle();

        saveWallpaper(activity.getApplicationContext(), bitmapDstAdjust, "wall1", "GuoguoWall", null);

        return bitmapDstAdjust;
    }

    private void getWallpaper (Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        int nWidth3 = wallpaperManager.getDesiredMinimumWidth();
        int nHeight3 = wallpaperManager.getDesiredMinimumHeight();

        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        int nWidth = wallpaperDrawable.getIntrinsicWidth();
        int nHeight = wallpaperDrawable.getIntrinsicHeight();

        int nWidth1 = wallpaperDrawable.getMinimumWidth();
        int nHeight2 = wallpaperDrawable.getMinimumHeight();
    }

    public static void saveWallpaper(Context context, Bitmap bitmap, String strFileName, String strDirName, WallpaperSaveListener listener) {
        if (null == context || null == bitmap || TextUtils.isEmpty(strFileName) || TextUtils.isEmpty(strDirName)) {
            return;
        }
        int nResult = SAVE_RESULT_FAIL;
        if (!FileUtils.isExistSdcard()) {
            return;
        }
        String strPath;
        strPath = FileUtils.getExternalStoragePath() + "/" + strDirName + "/";

        File dir = new File(strPath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String strRegularFileName = regularFileName(strFileName);

        File imageFile = new File(dir, strRegularFileName + ".jpg");
        if (imageFile.exists()) {
            if (null != listener) {
                listener.onWallpaperSaveFinish(SAVE_RESULT_EXIST);
            }
            return;
        }

        FileOutputStream fos = null;
        try {
            imageFile.createNewFile();
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, SAVE_JPG_QUALITY, fos);
            fos.flush();
            nResult = SAVE_RESULT_SUCCESS;

            //通知扫描
            StringBuilder sbFileName = new StringBuilder(strPath).append("/").append(strRegularFileName).append(".jpg");
            MediaScannerConnection.scanFile(context, new String[]{sbFileName.toString()}, null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != listener) {
                listener.onWallpaperSaveFinish(nResult);
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public interface WallpaperSaveListener {
        void onWallpaperSaveFinish(final int nSaveResult);
    }

    private static String regularFileName(String strFileName) {
        strFileName = "/123.5";
        StringBuilder sbName = new StringBuilder(strFileName);
        int nIndexSlash = strFileName.lastIndexOf('/');
        if (nIndexSlash >= 0 && nIndexSlash < strFileName.length()) {
            sbName.delete(0, nIndexSlash + 1);
        }

        int nIndexDot = sbName.toString().lastIndexOf('.'); //从0开始
        if (nIndexDot >= 0 && nIndexDot < strFileName.length()) {
            sbName.delete(nIndexDot, sbName.length());
        }

        return sbName.toString();
    }
}
