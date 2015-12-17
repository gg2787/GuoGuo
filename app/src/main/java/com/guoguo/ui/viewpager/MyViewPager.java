package com.guoguo.ui.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.guoguo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MyViewPager extends Activity{
    private View mView1;
    private View mView2;
    private View mView3;

    private ViewPager mViewPager;

    private ArrayList<View> mViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager);

        initView();
    }

    private void initView() {
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        LayoutInflater inflater = getLayoutInflater();
        mView1 = inflater.inflate(R.layout.view_pager_layout1, null);
        mView2 = inflater.inflate(R.layout.view_pager_layout2, null);
        mView3 = inflater.inflate(R.layout.view_pager_layout3, null);

        mViewList = new ArrayList<View>();
        mViewList.add(mView1);
        mViewList.add(mView2);
        mViewList.add(mView3);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }



        };

        mViewPager.setAdapter(pagerAdapter);
    }


}
