package com.ivan.android.manhattanenglish.app.core.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.info.Infomation;
import com.ivan.android.manhattanenglish.app.utils.DateFormatUtils;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-5
 * Time: PM5:10
 */
public class InfomationListAdapter extends BaseAdapter {

    List<Infomation> data;

    LayoutInflater mInflater;

    public InfomationListAdapter(Context context, List<Infomation> data) {
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        data.clear();
    }

    public void addAll(List<Infomation> newData) {
        if (newData != null && newData.size() > 0) {
            data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.info_list_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.infomation_title);
            holder.createTime = (TextView) convertView.findViewById(R.id.create_time);
            holder.content = (TextView) convertView.findViewById(R.id.infomation_content);
            holder.expandIcon = (ImageView) convertView.findViewById(R.id.more_expand);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Infomation infomation = data.get(position);
        holder.title.setText(infomation.getTitle());
        holder.createTime.setText(DateFormatUtils.format(infomation.getCreateTime()));
        holder.content.setText(infomation.getContent());

        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView createTime;
        TextView content;
        ImageView expandIcon;
    }
}
