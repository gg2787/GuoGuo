package com.guoguo.ui.anmi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/15.
 * 画一个圈，然后水波纹
 *
 */
public class AnimCircle extends View {
    private Context mContext = null;
    private int mnBkColor = 0xfff3f3f3;
    private int mnCircleColor = 0xff4fb5fc;
    private int mnStrokeWidth = 20;
    private int mnCircleRadius = 200;//半径
    private Paint mPaint;

    private long mTimeCircle = 3000;//5秒画完小圈
    private long mTimeWave = 1000;//圈圈展示2秒后消失
    private long mTimeTotal = 30000;
    private int mTimes = 3;//3次

    private long mTimeStart = 0;

    private final static int STAGE_DRAW_CIRCLE = 1;
    private final static int STAGE_WAVE_CIRCLE = 2;

    private int mStage = 0;

    private float mProcress = 0f;
    private boolean mShow = true;

    public AnimCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    public AnimCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mStage == STAGE_DRAW_CIRCLE) {
            drawCircle(canvas);
        } else {
            waveCircle(canvas);
        }
    }

    /*
    按进度画一个圈
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setStrokeWidth(mnStrokeWidth);
        mPaint.setColor(mnCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);

        RectF oval = new RectF(0, 0, mnCircleRadius, mnCircleRadius);
        canvas.drawArc(oval, 0, 360 * mProcress, false, mPaint);
    }

    /*
    扩大圈
     */
    private void waveCircle(Canvas canvas) {

    }

    public void startAnim() {
        mTimeStart = System.currentTimeMillis();
        drawCircleAnim();
    }

    private void drawCircleAnim() {
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                if (getProgressTime() >= mTimeTotal) {
                    mStage = STAGE_DRAW_CIRCLE;
                    mProcress = 1.0f;
                    return;
                }
                if (getProgressTime() < mTimeCircle) {
                    mStage = STAGE_DRAW_CIRCLE;
                    mProcress = (float) getProgressTime() / (float) mTimeCircle;
                }
                invalidate();
                drawCircleAnim();
                if (mProcress == 1.0f) {
                    drawCircleShowAnim();
                }
            }
        }, 100);
    }

    private void drawCircleShowAnim() {
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                mStage = STAGE_WAVE_CIRCLE;
                mShow = !mShow;
                invalidate();
                drawCircleShowAnim();

            }
        }, 500);
    }


    private long getProgressTime() {
        return System.currentTimeMillis() - mTimeStart;
    }
}