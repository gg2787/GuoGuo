package com.guoguo.ui.anmi;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoguo.R;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MyView extends View{
    private TextView mTextView = null;
    private ImageView mImageView = null;

    /*
    在代码中直接创建控件对象（直接new）的时候调用
    例：MyView myView = new MyView(mContext);
     */
    public MyView(Context context) {
        this(context, null);
    }

    /*
    在xml中 ，使用inflate方法创建控件对象时调用
    MyView myView = View.inflate(context, R.layout.my_view, this);
     */
    public MyView (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /*
    和style和theme有关
     */
    public MyView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
      //  mTextView.setTextColor(typedArray.getInteger(R.styleable.MyView_text_color, R.color.White));
//        mImageView.setBackgroundResource(typedArray.getResourceId(R.styleable.MyView_img_bkg, R.drawable.logo));
        boolean bAnim = typedArray.getBoolean(R.styleable. MyView_anim, false);
        typedArray.recycle();
    }
}

