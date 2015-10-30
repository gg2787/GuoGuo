package com.guoguo.ui.view.customListView;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2015/10/30.
 */
public class CustomItemData {
    private Drawable mIcon = null;
    private String mStrText = null;

    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setText(String strText) {
        mStrText = strText;
    }

    public String getText() {
        return mStrText;
    }
}
