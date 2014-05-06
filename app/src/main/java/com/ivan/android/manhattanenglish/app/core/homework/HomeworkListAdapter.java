package com.ivan.android.manhattanenglish.app.core.homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-5
 * Time: PM3:18
 */
public class HomeworkListAdapter extends BaseAdapter {

    List<String> contentList;

    LayoutInflater mInflater;

    public HomeworkListAdapter(Context context, List<String> contentList) {
        this.contentList = contentList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public Object getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.homework_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(contentList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView text;
    }
}
