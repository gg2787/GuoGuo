package com.guoguo.ui.title;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.guoguo.R;

/**
 * Created by HP on 2015/10/30.
 */
public class BackTabTitle extends RelativeLayout{
    private Context mContext = null;

    public BackTabTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.back_tab_title, this);
        initView();
    }

    private void initView() {
        ImageView backBtn = (ImageView)findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }
}
