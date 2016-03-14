package com.guoguo.ui.anmi;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/1/4.
 */
public class ComplexAnimation {
//
//    public void startHeightChangeAnimation(final CompleteToDismissListener listener) {
//        Animation animation = new Animation() {
//            @Override
//            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                if (interpolatedTime <= 0.0 || interpolatedTime >= 1.0) {
//                    return;
//                }
//                calHeightChangeParam(interpolatedTime);
//                changeLayout();
//            }
//        };
//        animation.setDuration(COMPLETE_TO_DISMISS_DURATION);
//        animation.setInterpolator(new AccelerateInterpolator());
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            public void onAnimationEnd(Animation animation) {
//                dismissLayout();
//                if (null != listener) {
//                    listener.onCompleteToDismissAnimFinish();
//                }
//            }
//
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        startAnimation(animation);
//    }
//
//    private void changeLayout() {
//        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mCurHeight);
//        mRootView.setLayoutParams(param);
//    }
//
//
//    private void dismissLayout() {
//        this.setVisibility(GONE);
//    }
//
//    private void calHeightChangeParam( float fTime) {
//        mCurHeight = (int)((mSrcHeight - mDstHeight) * (1.0 - fTime));
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
//        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
//        final int minHeight = Math.min(height, mCurHeight);
//        setMeasuredDimension(width, minHeight);
//
//        int childCount = this.getChildCount();
//        int childWidthMeasureSpec = widthMeasureSpec;
//        int childHeightMeasureSpec = heightMeasureSpec;
//        for (int i = 0; i < childCount; i++) {
//            View childView = getChildAt(i);
//            childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(minHeight, View.MeasureSpec.EXACTLY);
//            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//        }
//    }
}


