package com.guoguo.ui.anmi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.guoguo.R;
import com.guoguo.utils.UIutils;

import java.nio.charset.MalformedInputException;

/**
 * Created by Administrator on 2016/1/4.
 * 补间动画：Animation 用于实现基本的动画效果：控件的透明度、旋转、缩放、位移
 * 属性动画：Animator, 可以设置AnimatorListenerAdapter
 */
public class SimpleAnimation extends Activity{
    private ImageView mImageView = null;
    private int mnHeight = 0;
    private LinearLayout mCustomView = null;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_test);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startAnimationAnim1();
        startObjectAnimator();
    }

    private void initView() {
        mImageView = (ImageView)findViewById(R.id.image_png);
        mCustomView =(LinearLayout)findViewById(R.id.custom_view);
        mnHeight = UIutils.dip2px(this, 100);
    }

    /*
    xml补间动画
     */
    private void startAnimationAnim1() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tweened);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                return;
            }

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {

            }
        });

        mImageView.startAnimation(animation);
    }

    /*
    代码定义补间动画
     */
    private void startAnimationAnim2() {
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1000);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        translateAnimation.setDuration(1000);

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);

        mImageView.startAnimation(animationSet);
    }

    /*
    代码定义属性动画,缩小
     */
    private void startObjectAnimator() {
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImageView, "translationX", 0f, 200f);
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageView, "translationY", 0f,200f);
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageView, "rotation", 0f,360f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mCustomView, "scaleY", 1.0f,0.0f);

        animator3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float nValue = (float)animation.getAnimatedValue("scaleY");
                if (nValue <= 0.0 || nValue >= 1.0) {
                    return;
                }
                int nCurHeight = (int)(mnHeight * (nValue));
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, nCurHeight);
                mCustomView.setLayoutParams(param);

//                mCustomView.set
                return;
            }
        });

        AnimatorSet set = new AnimatorSet();
//        set.play(animator1).with(animator2);
//        set.play(animator3).after(animator2);
        set.play(animator3);
        set.setDuration(30000).start();
    }
}
