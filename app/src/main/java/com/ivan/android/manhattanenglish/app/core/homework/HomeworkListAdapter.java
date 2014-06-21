package com.ivan.android.manhattanenglish.app.core.homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.homework.Homework;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-5
 * Time: PM3:18
 */
public class HomeworkListAdapter extends BaseAdapter {

    List<Homework> mData;

    LayoutInflater mInflater;

    public HomeworkListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<Homework>();
    }

    public void clear() {
        mData.clear();
    }

    public void addAll(List<Homework> newData) {
        if (newData != null && newData.size() > 0) {
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Homework homework = mData.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.homework_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(homework.getHomeworkTitle());
        return convertView;
    }

    class ViewHolder {
        TextView text;
    }
}
