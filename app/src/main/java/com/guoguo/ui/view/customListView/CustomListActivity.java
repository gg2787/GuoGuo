package com.guoguo.ui.view.customListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.guoguo.R;
import com.guoguo.logic.phone.AppsManager;
import com.guoguo.ui.title.BackTabTitle;
import com.guoguo.ui.toast.ShowToast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/30.
 */
public class CustomListActivity extends Activity{
    private ArrayList<CustomItemData> mCustomItemDataList = null;
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
        initView();
    }

    private void initData() {
        mCustomItemDataList = new ArrayList<CustomItemData>();
        ArrayList<String> pkgNameList = AppsManager.getInstalledPkgs();
        for (String strPkgName : pkgNameList) {
            CustomItemData data = new CustomItemData();
            data.setText(strPkgName);
            data.setIcon(AppsManager.getAppIcon(strPkgName));
            mCustomItemDataList.add(data);
        }
    }

    /**
     * 把adapter设置给listview
     * 给listview设置点击监听
     */
    private void initView() {
        mBackTitle = (BackTabTitle)findViewById(R.id.back_title);
        mBackTitle.SetTabClassName(getClass().getSimpleName());

        if (null == mCustomItemDataList) {
            return;
        }
       // CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.custom_list_item, mCustomItemDataList);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.custom_list_item);
        adapter.setData(mCustomItemDataList);
        ListView listView = (ListView)findViewById(R.id.simple_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowToast.showShortToast(view.getContext(), mCustomItemDataList.get(position).getText());
            }
        });
    }
}
