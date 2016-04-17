package com.guoguo.ui.view.viewDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.guoguo.R;
import com.guoguo.ui.toast.ShowToast;
import com.guoguo.utils.UIutils;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by HP on 2015/12/3.
 */
public class FullScreenView extends ImageView {
    private Context mContext;
    private GestureDetector mGestrueDetector;
    private Bitmap mBitmap;
    private Scroller mScroller;

    private int mLastX;
    private int mLastY;

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
        mBitmap = UIutils.loadBitmap(context, R.drawable.wallbig1);
        setImageBitmap(mBitmap);

        //手势监听
        mGestrueDetector = new GestureDetector(mContext, new GestureDetectorListener());
        mGestrueDetector.setIsLongpressEnabled(false);

        //弹性滑动
        mScroller = new Scroller(mContext);

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
//            smoothScrollTo(200, 200);
            scrollUseScrollBy(100, 100);
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        VelocityTracker velocityTracker = VelocityTracker.obtain();
//        velocityTracker.addMovement(event);
//
//        //计算1000ms内的平均速度
//        velocityTracker.computeCurrentVelocity(1000);
//        int xVelocity = (int)velocityTracker.getXVelocity();
//        int yVelocity = (int)velocityTracker.getYVelocity();
//
////        ShowToast.showLongToast(mContext, "滑动速度:X方向" + String.valueOf(xVelocity) + "Y方向" + String.valueOf(yVelocity));
//
//        velocityTracker.clear();
//        velocityTracker.recycle();
//        return mGestrueDetector.onTouchEvent(event);
//    }


    /**
     * 系统所能识别出的被认为是滑动的最小距离
     * @return
     */
//    public int getTouchSlop(Context context) {
//        return ViewConfiguration.get(context).getScaledTouchSlop();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:{
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                int scrollX = getScrollX();
                int scrollY = getScrollY();
//                scrollUsePropertyAnim(deltaX, deltaY);
//                scrollUseScrollBy(deltaX, deltaY);
                smoothScrollTo(deltaX, deltaY);

                scrollX = getScrollX();
                scrollY = getScrollY();
                break;
            }
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true; //return true表示这里已经处理了，父view不要再处理了
    }

    /////////////滑动测试////////////////////////////////////////////////////

    /*
     *使用属性动画实现滑动
     * 如果是具有过渡效果的属性动画也不应该使用
     */
    private void scrollUsePropertyAnim(int deltaX, int deltaY) {
        int translationX = (int) ViewHelper.getTranslationX(this) + deltaX;
        int translationY = (int) ViewHelper.getTranslationY(this) + deltaY;
        ViewHelper.setTranslationX(this, translationX);
        ViewHelper.setTranslationY(this, translationY);

        ViewGroup
    }

    /*
     *使用scrollBy实现滑动,参考系不同，需要使用负值
     */
    private void scrollUseScrollBy(int offsetX, int offsetY) {
        scrollBy(-offsetX, -offsetY);
//        ((View)getParent()).scrollBy(-offsetX, -offsetY);
    }

    /*
    *弹性滑动,具有过渡效果,不应该连续调用
    */
    private void smoothScrollTo(int offsetX, int offsetY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        int deltaX = -offsetX - scrollX;
        int deltaY = -offsetY - scrollY;

        //1000ms内滑向destX
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /////////////end of 滑动测试////////////////////////////////////////////////////

    public boolean disp
}
