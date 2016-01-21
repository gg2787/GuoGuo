package com.guoguo.ui.title;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoguo.R;
import com.guoguo.ui.toast.ShowToast;

/**
 * Created by HP on 2015/10/30.
 */
public class BackTabTitle extends RelativeLayout{
    private Context mContext = null;
    private String mstrClassName = null;
    private GestureDetector mGestureDetector;

    public BackTabTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.tab_title_back, this);
        initView();
    }

    private void initView() {
        TextView backBtn = (TextView)findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

        TextView showClassBtn = (TextView)findViewById(R.id.show_class);
        showClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mstrClassName)) {
                    ShowToast.showShortToast(v.getContext(), mstrClassName);
                }
            }
        });

        mGestureDetector = new GestureDetector(mContext, new GestureListener());
    }

    public void SetTabClassName(String strClassName) {
        mstrClassName = strClassName;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class GestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
        public boolean onDown(MotionEvent e) {
            return true;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return true;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return true;
        }
    }
}
