package com.guoguo.ui.view.customListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoguo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/30.
 * 在getItem中设置view
 */
public class CustomListViewAdapter extends ArrayAdapter<CustomItemData> {
    private ArrayList<CustomItemData> mData = null;

//    public CustomListViewAdapter(Context context,  int resource,  ArrayList<CustomItemData> objects) {
//        super(context, resource, objects);
//    }

    /**
     * 使用 CustomListViewAdapter(Context context,  int resource,  ArrayList<CustomItemData> objects) 或者
     * 使用CustomListViewAdapter(Context context,  int resource) 和setData (ArrayList<CustomItemData> data)
     * 注意super.addAll(data);
     * @param context
     * @param resource
     */
    public CustomListViewAdapter(Context context,  int resource) {
        super(context, resource);
    }

    public void setData (ArrayList<CustomItemData> data) {
        super.addAll(data);
        mData = data;
    }

    @Override
    public CustomItemData getItem(int position) {
        return mData.get(position);
    }

    /**
     * 创造出一个子项的view，并更新view中的数据
     * 注意LayoutInflater.from(getContext()).inflate和View.inflate都可以
     * 使用convertView进行缓存，避免重复inflater加载view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if (null == convertView) {
            //  view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, null);
            view = View.inflate(getContext(), R.layout.custom_list_item, null);
            viewHolder = new ViewHolder();
            ImageView imageView = (ImageView)view.findViewById(R.id.list_image);
            viewHolder.imageView = imageView;
            TextView textView = (TextView)view.findViewById(R.id.list_text);
            viewHolder.textView = textView;
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        if (null == view || null == viewHolder) {
            return null;
        }
        CustomItemData data = getItem(position);
        viewHolder.imageView.setImageDrawable(data.getIcon());
        viewHolder.textView.setText(data.getText());
        return view;
    }

    /**
     * 使用viewholder进行缓存，避免重复findidbyview
     */
    private class ViewHolder{
        private ImageView imageView;
        private TextView textView;
    }
}
