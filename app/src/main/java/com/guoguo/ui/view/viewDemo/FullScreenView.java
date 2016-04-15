package com.guoguo.ui.view.viewDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Scroller;

import com.guoguo.R;
import com.guoguo.ui.toast.ShowToast;
import com.guoguo.utils.UIutils;

/**
 * Created by HP on 2015/12/3.
 */
public class FullScreenView extends ImageView {
    private Context mContext;
    private GestureDetector mGestrueDetector;
    private Bitmap mBitmap;
    private Scroller mScroller;

    public FullScreenView(Context context) {
        this(context, null);
    }

    public FullScreenView (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullScreenView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mBitmap = UIutils.loadBitmap(context, R.drawable.shuxing);
        setImageBitmap(mBitmap);

        //手势监听
        mGestrueDetector = new GestureDetector(mContext, new GestureDetectorListener());
        mGestrueDetector.setIsLongpressEnabled(false);

        //弹性滑动
        mScroller = new Scroller(mContext);

    }

    /*
     *弹性滑动
     */
    private void smoothScrollTo(int destX, int dextY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;

        //1000ms内滑向destX
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


    public class GestureDetectorListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
        public boolean onDown(MotionEvent e){
            return true;
        }

        public void onShowPress(MotionEvent e){
            return;
        }

        public boolean onSingleTapUp(MotionEvent e){
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
            return true;
        }

        public void onLongPress(MotionEvent e){
            return;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e){
            smoothScrollTo(200, 200);
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {

            ShowToast.showLongToast(mContext, "x:" + String.valueOf(getX())
                    + "y:" + String.valueOf(getY())
                    + "left:" + String.valueOf(getLeft())
                    + "top:" + String.valueOf(getTop())
                    + "TranslationX:" + String.valueOf(getTranslationX())
                    + "TranslationY:" + String.valueOf(getTranslationY()));


            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent e){
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        //计算1000ms内的平均速度
        velocityTracker.computeCurrentVelocity(1000);
        int xVelocity = (int)velocityTracker.getXVelocity();
        int yVelocity = (int)velocityTracker.getYVelocity();

        ShowToast.showLongToast(mContext, "滑动速度:X方向" + String.valueOf(xVelocity) + "Y方向" + String.valueOf(yVelocity));

        velocityTracker.clear();
        velocityTracker.recycle();


        return mGestrueDetector.onTouchEvent(event);

//        return super.onTouchEvent(event);
    }


    /**
     * 系统所能识别出的被认为是滑动的最小距离
     * @return
     */
//    public int getTouchSlop(Context context) {
//        return ViewConfiguration.get(context).getScaledTouchSlop();
//    }
}
