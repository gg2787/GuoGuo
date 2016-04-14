package com.guoguo.ui.view.viewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.guoguo.R;

/**
 * Created by Administrator on 2016/4/14.
 */
public class FullScreenViewActivity extends Activity{
    private FullScreenView mScreenView;

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
    }

    private void initView() {
        mScreenView = new FullScreenView(this);
    }
}
