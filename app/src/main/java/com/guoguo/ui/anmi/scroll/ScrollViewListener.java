package com.guoguo.ui.anmi.scroll;

import com.guoguo.ui.anmi.scroll.ObservableScrollView;

/**
 * Created by Administrator on 2016/3/15.
 */
public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
