package com.guoguo.ui.view.customProgressView;

import android.app.Activity;
import android.os.Bundle;

import com.guoguo.R;

/**
 * Created by Administrator on 2016/3/7.
 */
public class ProgressActivity extends Activity{
    private CustomProgress mcustomProgress = null;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_fragment);
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

    void initView() {
        mcustomProgress = (CustomProgress)findViewById(R.id.progress);
        mcustomProgress.setProgress(15);
    }
}
