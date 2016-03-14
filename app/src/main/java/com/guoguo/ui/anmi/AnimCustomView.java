package com.guoguo.ui.anmi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.guoguo.R;

/**
 * Created by Administrator on 2016/3/14.
 * 派生自LinearLayout，其实就是派生自viewgroup
 */
public class AnimCustomView extends LinearLayout{

    private Context mContext = null;

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


    }

}
