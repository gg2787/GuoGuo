package com.guoguo.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guoguo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/8.
 */
public class MainGrideAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> mItems = null;
    private Context mContext = null;

    public MainGrideAdapter(ArrayList<HashMap<String, Object>> items, Context context) {
        mItems = items;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) { //创建view
            convertView = View.inflate(mContext, R.layout.main_gridview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mNameTv = (TextView)convertView.findViewById(R.id.grid_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (null == convertView || null == viewHolder) {
            return null;
        }

        //刷新view
        Object tvObj = mItems.get(position).get("ItemText");
        if (null != tvObj && tvObj instanceof String) {
            viewHolder.mNameTv.setText((String)tvObj);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView mNameTv;
    }
}
