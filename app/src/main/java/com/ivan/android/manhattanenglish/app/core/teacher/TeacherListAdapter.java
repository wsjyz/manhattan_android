package com.ivan.android.manhattanenglish.app.core.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.course.TeacherDetail;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-5
 * Time: PM5:10
 */
public class TeacherListAdapter extends BaseAdapter {

    List<TeacherDetail> data;

    LayoutInflater mInflater;

    Context context;

    public TeacherListAdapter(Context context, List<TeacherDetail> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        data.clear();
    }

    public void addAll(List<TeacherDetail> newData) {
        if (newData != null) {
            data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public void setData(List<TeacherDetail> newData) {
        clear();
        if (newData != null) {
            data.addAll(newData);
        }
        notifyDataSetChanged();
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
        TeacherDetail teacher = data.get(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.teacher_list_item, null);
            holder.avatar = (RoundedImageView) convertView.findViewById(R.id.teacher_avatar);
            holder.name = (TextView) convertView.findViewById(R.id.teacher_name);
            holder.college = (TextView) convertView.findViewById(R.id.college);
            holder.mainSubject = (TextView) convertView.findViewById(R.id.main_subject);
            holder.sex = (ImageView) convertView.findViewById(R.id.teacher_sex);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load(teacher.getAvatarUrl())
                .fit()
                .into(holder.avatar);

        holder.name.setText(getText(R.string.label_class_num, teacher.getName()));
        holder.college.setText(getText(R.string.label_fee, String.valueOf(teacher.getCollege())));
        holder.mainSubject.setText(getText(R.string.label_teach_location, teacher.getMainSubject()));
        holder.sex.setImageResource(teacher.getSexDrawableResource());

        return convertView;
    }

    public String getText(int stringResId, String... params) {
        return String.format(context.getResources().getString(stringResId), params);
    }

    class ViewHolder {
        RoundedImageView avatar;
        TextView name;
        TextView college;
        TextView mainSubject;
        ImageView sex;
    }
}
