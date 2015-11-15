package com.guoguo.ui.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;


public class TouchView extends View{
    Scroller mScroller = null;

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //检测滑动速度
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        //这里的速度是指一段时间内手指所滑过的像素数
        //速度可以为负数，水平方向从右往左，水平方向速度即为负值
        //速度计算公式：速度=（终点位置-起点位置）/时间段
        velocityTracker.computeCurrentVelocity(1000);//获取速度前先计算速度，1000为时间单元，单位毫秒
        int xVelocity = (int)velocityTracker.getXVelocity();
        int yVelocity = (int)velocityTracker.getYVelocity();

        //不需要使用时，clear并回收
        velocityTracker.clear();
        velocityTracker.recycle();

        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - destY;
        mScroller.startScroll(scrollX, 0, delta, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


}
