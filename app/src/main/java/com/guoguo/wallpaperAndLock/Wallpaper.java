package com.guoguo.wallpaperAndLock;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.FaceDetector;
import android.media.MediaScannerConnection;
import android.text.TextUtils;

import com.guoguo.R;
import com.guoguo.ui.toast.ShowToast;
import com.guoguo.utils.FileUtils;
import com.guoguo.utils.UIutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 */
public class Wallpaper {
    private static final int CEIL_TO_DECIMALS = 100;
    private static final int SAVE_JPG_QUALITY = 100;

    public static final int SAVE_RESULT_EXIST = 1;
    public static final int SAVE_RESULT_SUCCESS = 2;
    public static final int SAVE_RESULT_FAIL = 3;

    private static final int MAX_FACES = 3;

    public void changeWall(final Activity activity) {

        final Bitmap  bitmap = UIutils.loadBitmap(activity.getApplicationContext(), R.drawable.wall_face);

//        faceDetect(bitmap);

        final Bitmap bitmap2 = imageScaleAndCrop(activity, bitmap);

        new Thread(new Runnable() {
            public void run() {

                setWallpaper(activity.getApplicationContext(), bitmap2);
                bitmap2.recycle();
            }
        }).start();
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

        int[] nScreenSize = UIutils.getScreenSize(activity);
        if (nScreenSize.length < 2) {
            return null;
        }
        int nScreenWidth = nScreenSize[0];
        int nScreenHeight = nScreenSize[1];

//        int[] minSize = getMinWallpaperAttr(activity);
//        if (null != minSize) {
//            nScreenWidth = minSize[0];
//            nScreenHeight = minSize[1];
//        }

        int nSrcWidth = bitmapSrc.getWidth();
        int nSrcHeight = bitmapSrc.getHeight();

        if (nSrcWidth == nScreenWidth && nSrcHeight == nScreenHeight) {
            return bitmapSrc;
        }

        int nCenterX = nSrcWidth / 2; //可能为0
        PointF face = faceDetect(bitmapSrc);
        if (face != null) {
            nCenterX = (int)face.x;
        }

        //计算出缩放,先裁剪 后缩放
        float fScale = Math.max((float) nScreenWidth / (float) nSrcWidth, (float) nScreenHeight / (float) nSrcHeight);
        float fScaleMatrix = (float)Math.ceil(fScale * 100) / 100;

        int nSrcX = (int)(Math.min(nSrcWidth, nScreenWidth / fScale));
        int nSrcY = (int)(Math.min(nSrcHeight, nScreenHeight / fScale));
//        int nSrcStartX = (int)Math.floor((nSrcWidth - nSrcX) / 2);
        int nSrcStartX = getStartX(nCenterX, nSrcWidth, nSrcX);
        int nSrcStartY = (int)Math.floor((nSrcHeight - nSrcY) / 2);

        Bitmap bitmapDst = bitmapScaleAndCrop(bitmapSrc, nSrcX, nSrcY,
                nSrcStartX, nSrcStartY, nScreenWidth, nScreenHeight, Bitmap.Config.ARGB_4444);

//        saveWallpaper(activity.getApplicationContext(), bitmapDstAdjust, "wall1", "GuoguoWall", null);

        return bitmapDst;
    }

    private int getStartX(int nCenterX, int nSrcX, int nDstX) {
        int nStart = nCenterX - nDstX / 2 - 1;
        int nEnd = nCenterX + nDstX / 2 + 1;
        if (nEnd > nSrcX) {
            nStart = nSrcX - nDstX;
        }
        if (nStart < 0) {
            nStart = 0;
        }
        return nStart;
    }

    public static int[] getMinWallpaperAttr(Context context) {
        if (null == context) {
            return null;
        }
        int nWallWidth, nWallHeight;

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        if (wallpaperDrawable != null) {
            nWallWidth = wallpaperDrawable.getMinimumWidth();
            nWallHeight = wallpaperDrawable.getMinimumHeight();
        } else {
            nWallWidth = wallpaperManager.getDesiredMinimumWidth();
            nWallHeight = wallpaperManager.getDesiredMinimumHeight();
        }

        int[] wallSize = new int[2];
        wallSize[0] = nWallWidth;
        wallSize[1] = nWallHeight;

        WallpaperInfo info = wallpaperManager.getWallpaperInfo();
        return wallSize;
    }

    private void getWallpaper (Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        int nWidth3 = wallpaperManager.getDesiredMinimumWidth();
        int nHeight3 = wallpaperManager.getDesiredMinimumHeight();

        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        if (wallpaperDrawable == null) {
            return;
        }
        int nWidth = wallpaperDrawable.getIntrinsicWidth();
        int nHeight = wallpaperDrawable.getIntrinsicHeight();

        int nWidth1 = wallpaperDrawable.getMinimumWidth();
        int nHeight2 = wallpaperDrawable.getMinimumHeight();

        ShowToast.showLongToast(context,
                "DesiredMinimumWidth_" + String.valueOf(nWidth3) + "DesiredMinimumHeight_" + String.valueOf(nHeight3)
                        + "getIntrinsicWidth" + String.valueOf(nWidth) + "getIntrinsicHeight" + String.valueOf(nHeight)
                        + "getMinimumWidth" + String.valueOf(nWidth1) + "getMinimumHeight" + String.valueOf(nHeight2));
    }

    public static void saveWallpaper(Context context, Bitmap bitmap, String strFileName, String strDirName, WallpaperSaveListener listener) {
        if (null == context || null == bitmap || bitmap.isRecycled() || TextUtils.isEmpty(strFileName) || TextUtils.isEmpty(strDirName)) {
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
        StringBuilder sbName = new StringBuilder(strFileName);
        int nIndexSlash = strFileName.lastIndexOf('/');
        if (nIndexSlash >= 0 && nIndexSlash < strFileName.length()) {
            sbName.delete(0, nIndexSlash + 1);
        }

        int nIndexDot = sbName.toString().lastIndexOf('.');
        if (nIndexDot > 0 && nIndexDot < strFileName.length() - 1) {
            sbName.delete(nIndexDot, sbName.length());
        }

        if (sbName.length() == 0) {
            sbName.append(System.currentTimeMillis());
        }
        return sbName.toString();
    }

    private static PointF faceDetect(Bitmap bitmapSrc) {
        if (null == bitmapSrc || bitmapSrc.isRecycled()) {
            return null;
        }
        try {
//            Matrix matrix = new Matrix();
//            matrix.setScale(0.5f, 0.5f);
//            Bitmap bitmapSmall = Bitmap.createBitmap(bitmapSrc, 0, 0, bitmapSrc.getWidth(), bitmapSrc.getHeight(), matrix, false);
//
////            Bitmap.createBitmap()
//
//            Bitmap bitmapFace = bitmapSmall;
//            if (bitmapFace.getConfig() != Bitmap.Config.RGB_565) {
//                bitmapFace = bitmapSmall.copy(Bitmap.Config.RGB_565, true);
//                bitmapSmall.recycle();
//                bitmapSmall = null;
//            }

            Bitmap bitmapFace = bitmapScaleAndCrop(bitmapSrc, bitmapSrc.getWidth(), bitmapSrc.getHeight(), 0, 0, (int) (bitmapSrc.getWidth() * 0.5f), (int) (bitmapSrc.getHeight() * 0.5f), Bitmap.Config.RGB_565);
            if (null == bitmapFace) {
                return null;
            }

            FaceDetector faceDet = new FaceDetector(bitmapFace.getWidth(), bitmapFace.getHeight(), 1);
            FaceDetector.Face[] faceList = new FaceDetector.Face[1];
            faceDet.findFaces(bitmapFace, faceList);
            bitmapFace.recycle();

            FaceDetector.Face face = faceList[0];
            if (face != null) {
                PointF pf = new PointF();
                face.getMidPoint(pf);
                pf.set(pf.x * 2, pf.y * 2);
                return pf;
            }else {
                return null;
            }

        } catch (OutOfMemoryError e) {
            System.gc();
        }
        return null;
    }

    private static ArrayList<PointF> faceDetectList(Bitmap bitmapSrc) {
        Bitmap tmpBmp = bitmapSrc.copy(Bitmap.Config.RGB_565, true);

        FaceDetector faceDet = new FaceDetector(tmpBmp.getWidth(), tmpBmp.getHeight(), MAX_FACES);
        FaceDetector.Face[] faceList = new FaceDetector.Face[MAX_FACES];
        faceDet.findFaces(tmpBmp, faceList);

        ArrayList<PointF> arrFaceList = new ArrayList<>();

        for (int i = 0; i < faceList.length; i++) {
            FaceDetector.Face face = faceList[i];
            if (face != null) {
                PointF pf = new PointF();
                //getMidPoint(PointF point);
                //Sets the position of the mid-point between the eyes.
                face.getMidPoint(pf);
//                RectF r = new RectF();
//                r.left = pf.x - face.eyesDistance() / 2;
//                r.right = pf.x + face.eyesDistance() / 2;
//                r.top = pf.y - face.eyesDistance() / 2;
//                r.bottom = pf.y + face.eyesDistance() / 2;
//                faceRects[i] = r;
//                detectedFaces++
                arrFaceList.add(pf);
            }
        }
        return arrFaceList;
    }

    private static Bitmap bitmapScaleAndCrop(Bitmap bitmapSrc, int srcWidth, int srcHeight, int srcOffsetX, int srcOffsetY, int dstWidth, int dstHeight, Bitmap.Config config) {
        if (null == bitmapSrc || bitmapSrc.isRecycled()) {
            return null;
        }
        try {
            Bitmap bitmapDst = Bitmap.createBitmap(dstWidth, dstHeight, config);
            Rect rectDst = new Rect(0, 0, dstWidth, dstHeight);
            Rect rectSrc = new Rect(srcOffsetX, srcOffsetY, Math.min(srcOffsetX + srcWidth, bitmapSrc.getWidth()), Math.min(srcHeight + srcOffsetY, bitmapSrc.getHeight()));

            Canvas canvas = new Canvas(bitmapDst);
            Paint p = new Paint();
            p.setFilterBitmap(true);
            canvas.drawBitmap(bitmapSrc, rectSrc, rectDst, p);
            canvas = null;
            return bitmapDst;
        } catch (OutOfMemoryError e) {
            System.gc();
            return null;
        }
    }

}
