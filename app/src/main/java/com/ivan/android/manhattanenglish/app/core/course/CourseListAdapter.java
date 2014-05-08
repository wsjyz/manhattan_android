package com.ivan.android.manhattanenglish.app.core.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-5
 * Time: PM5:10
 */
public class CourseListAdapter extends BaseAdapter {

    List<Course> data;

    LayoutInflater mInflater;

    Context context;

    public CourseListAdapter(Context context, List<Course> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        data.clear();
    }

    public void addAll(List<Course> newData) {
        data.addAll(newData);
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
        Course course = data.get(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.course_list_item, null);
            holder.icon = (RoundedImageView) convertView.findViewById(R.id.course_image);
            holder.classNum = (TextView) convertView.findViewById(R.id.class_num_text);
            holder.cost = (TextView) convertView.findViewById(R.id.cost_text);
            holder.location = (TextView) convertView.findViewById(R.id.location_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load(course.getImageUrl())
                .fit()
                .into(holder.icon);
        holder.classNum.setText(course.getClassNum());
        holder.cost.setText(course.getMoneyCost() + "RMB");
        holder.location.setText(course.getLocation());

        return convertView;
    }

    class ViewHolder {
        RoundedImageView icon;
        TextView classNum;
        TextView cost;
        TextView location;
    }
}
