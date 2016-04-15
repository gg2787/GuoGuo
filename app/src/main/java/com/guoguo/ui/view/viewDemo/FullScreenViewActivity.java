package com.guoguo.ui.view.viewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.guoguo.R;

/**
 * Created by Administrator on 2016/4/14.
 */
public class FullScreenViewActivity extends Activity{
    private FullScreenView mScreenView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mScreenView = new FullScreenView(this);
        mScreenView.setLayoutParams(params);
        setContentView(mScreenView, params);
    }
}
