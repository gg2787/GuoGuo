package com.guoguo.ui.view.fragmentViews;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.guoguo.R;
import com.guoguo.ui.title.BackTabTitle;

/**
 * Created by HP on 2015/11/6.
 */
public class BaseFragmentActivity extends Activity{
    private BackTabTitle mBackTitle = null;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.base_fragments);
        //setContentView(R.layout.simple_list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }


    private void initView() {
        mBackTitle = (BackTabTitle)findViewById(R.id.back_title);
        mBackTitle.SetTabClassName(getClass().getSimpleName());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HelloFragment helloFragment = new HelloFragment();
        fragmentTransaction.replace(R.id.fragment_content, helloFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
