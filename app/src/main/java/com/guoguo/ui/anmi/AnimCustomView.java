package com.guoguo.ui.anmi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoguo.R;
import com.guoguo.utils.UIutils;

/**
 * Created by Administrator on 2016/3/14.
 * 派生自LinearLayout，其实就是派生自viewgroup
 * 本类用于缩放问题的验证。
 * 1.缩放后，自身占用的空间不变。
 * 2.如缩放因子为Y 0.3 ，那么控件会将自己的显示部分缩小到自身高度的0.3倍，即使调整高度为0.3倍，控件又会将自己缩小到新高度的0.3倍。
 */
public class AnimCustomView extends RelativeLayout{

    private Context mContext = null;

    private static final int LAYOUT_INIT_HEIGHT_DP = 100;
    private static final int COMPLETE_TO_DISMISS_DURATION = 30000;
    private int mSrcHeight = LAYOUT_INIT_HEIGHT_DP;
    private int mDstHeight = 0;
    private int mCurHeight = LAYOUT_INIT_HEIGHT_DP;
    private RelativeLayout mRootView = null;
    private RelativeLayout mContentView = null;
    private TextView mTextView1 = null;
    private TextView mTextView2 = null;
    private float mScaleY = 1.0f;

    public AnimCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    public AnimCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.anim_custom, this); //加载对应的xml
        //        LayoutInflater.from(context).inflate(R.layout.anim_custom, this);
        mRootView = (RelativeLayout)findViewById(R.id.custom_view);
        mContentView = (RelativeLayout)findViewById(R.id.custom_content);
        mTextView1 = (TextView)findViewById(R.id.custom_text1);
        mTextView2 = (TextView)findViewById(R.id.custom_text2);
        mSrcHeight = UIutils.dip2px(context, LAYOUT_INIT_HEIGHT_DP);
        mCurHeight = mSrcHeight;
    }

    public void startHeightChangeAnimation() {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime <= 0.0 || interpolatedTime >= 1.0) {
                    return;
                }
                calHeightChangeParam(interpolatedTime);
                changeLayout();
            }
        };
        animation.setDuration(COMPLETE_TO_DISMISS_DURATION);
        animation.setInterpolator(new LinearInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
//                dismissLayout();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        startAnimation(animation);
    }

    private void changeLayout() {
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mCurHeight);
        mRootView.setLayoutParams(param);
//        mLastHeight = mCurHeight;
        mRootView.setPivotY(0.0f);
        mRootView.setScaleY(mScaleY);
    }

    private void dismissLayout() {
        this.setVisibility(GONE);
    }

    private void calHeightChangeParam( float fTime) {
        mCurHeight = (int)((mSrcHeight - mDstHeight) * (1.0f - fTime));
        mScaleY = 1.0f - fTime;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int minHeight = Math.min(height, mCurHeight);
        setMeasuredDimension(width, minHeight);

        int childCount = this.getChildCount();
        int childWidthMeasureSpec = widthMeasureSpec;
        int childHeightMeasureSpec = heightMeasureSpec;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
//            childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(minHeight, View.MeasureSpec.EXACTLY);
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }


    /*
        缩放问题验证
     */
    public void resetLayout(View view) {
        mContentView.setPivotY(0.0f);
        mContentView.setScaleY(1.0f);  //大小减小后的0.3f

        mTextView1.setPivotY(0.0f);
        mTextView2.setPivotY(0.0f);
        mTextView1.setScaleY(0.3f);
        mTextView2.setScaleY(0.3f);

        mCurHeight = (int)((mSrcHeight - mDstHeight) * (0.3f));

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mCurHeight);
        mContentView.setLayoutParams(param);


    }


}
