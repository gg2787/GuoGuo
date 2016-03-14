package com.guoguo.ui.view.customProgressView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CustomProgress extends View{
    private float mfProgress = 0;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute();
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttribute() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制外框
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeWidth((float) 0.5);

        RectF rect = new RectF(0, 0, 500, 10);
        canvas.drawRect(rect, paint);

        //绘制内容


        //绘制text
    }

    public void setProgress(float fProgress) {
        mfProgress = fProgress;
        postInvalidate();
    }
}
