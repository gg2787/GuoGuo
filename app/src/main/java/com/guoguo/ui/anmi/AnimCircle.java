package com.guoguo.ui.anmi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/15.
 */
public class AnimCircle extends View{
    private Context mContext = null;
    private int mnBkColor = 0xfff3f3f3;
    private int mnCircleColor = 0xff4fb5fc;
    private int mnStrokeWidth = 5;
    private int mnCircleRadius = 20;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画一个圈
        Paint paint = new Paint();
        paint.setStrokeWidth(mnStrokeWidth);
        paint.setColor(mnBkColor);

        RectF oval = new RectF(0, 0, mnCircleRadius, mnCircleRadius);
        canvas.drawOval(oval, paint);
        //
    }



}
