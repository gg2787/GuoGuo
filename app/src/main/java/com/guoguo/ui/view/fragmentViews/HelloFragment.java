package com.guoguo.ui.view.fragmentViews;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoguo.R;

/**
 * Created by HP on 2015/11/6.
 */
public class HelloFragment extends Fragment{
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.hello_fragment, container, false);
        View view = inflater.inflate(R.layout.hello_fragment, null);
        return view;
    }

}
