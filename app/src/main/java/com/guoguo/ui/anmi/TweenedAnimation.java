package com.guoguo.ui.anmi;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.guoguo.R;

/**
 * Created by Administrator on 2016/1/4.
 * 用于实现基本的动画效果：控件的透明度、旋转、大小、位移
 */
public class TweenedAnimation extends Activity{
    private Animation mAnimationSet = null;
    private ImageView mImageView = null;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_image);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnim2();
    }

    private void initView() {
        mImageView = (ImageView)findViewById(R.id.image_png);
        mAnimationSet = AnimationUtils.loadAnimation(this, R.anim.tweened);
    }

    private void startAnim() {
        mImageView.startAnimation(mAnimationSet);
    }

    private void startAnim2() {
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,

                Animation.RELATIVE_TO_SELF,0.5f,

                Animation.RELATIVE_TO_SELF,0.5f);

        rotateAnimation.setDuration(1000);

        animationSet.addAnimation(rotateAnimation);

        animationSet.addAnimation(alphaAnimation);

        mImageView.startAnimation(animationSet);
    }
}
