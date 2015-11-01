package com.guoguo.ui.title;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoguo.R;
import com.guoguo.ui.toast.ShowToast;

/**
 * Created by HP on 2015/10/30.
 */
public class BackTabTitle extends RelativeLayout{
    private Context mContext = null;
    private String mstrClassName = null;

    public BackTabTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.tab_title_back, this);
        initView();
    }

    private void initView() {
        TextView backBtn = (TextView)findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

        TextView showClassBtn = (TextView)findViewById(R.id.show_class);
        showClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mstrClassName)) {
                    ShowToast.showShortToast(v.getContext(), mstrClassName);
                }
            }
        });
    }

    public void SetTabClassName(String strClassName) {
        mstrClassName = strClassName;
    }
}
