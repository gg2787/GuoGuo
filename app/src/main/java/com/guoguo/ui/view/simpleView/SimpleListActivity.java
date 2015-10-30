package com.guoguo.ui.view.simpleView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.guoguo.R;
import com.guoguo.logic.phone.AppsManager;
import com.guoguo.ui.title.BackTabTitle;

import java.util.ArrayList;

/**
 * Created by HP on 2015/10/30.
 */
public class SimpleListActivity extends Activity{
    private ArrayAdapter<String> mArrayAdapter = null;
    private ListView mListView = null;
    private BackTabTitle mBackTitle = null;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guoguo_simple_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        initListView();
    }

    private void initData() {
        ArrayList<String> arrList = AppsManager.getInstalledPkgs();
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrList);
        //android.R.layout.simple_list_item_1 是安卓提供的listitem样式
    }

    private void initListView() {
        if (null == mArrayAdapter) {
            return;
        }
        mListView = (ListView)findViewById(R.id.simple_list_view);
        mListView.setAdapter(mArrayAdapter);

        mBackTitle = (BackTabTitle)findViewById(R.id.back_title);
        mBackTitle.SetTabClassName(getClass().getSimpleName());
    }
}
