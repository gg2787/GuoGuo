package com.guoguo.ui.view.customListView;

import android.app.Activity;
import android.os.Bundle;

import com.guoguo.R;

/**
 * Created by Administrator on 2015/10/30.
 */
public class CustomListActivity extends Activity{
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guoguo_simple_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initView();
    }


    /**
     * 把adapter设置给listview
     */
    private void initView() {

    }
}
