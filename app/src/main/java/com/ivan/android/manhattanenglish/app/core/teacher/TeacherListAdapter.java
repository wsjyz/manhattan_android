package com.ivan.android.manhattanenglish.app.core.teacher;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
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
        if (newData != null && newData.size() > 0) {
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

    public void removeItemAt(int index) {
        if (index < 0 || index >= data.size()) return;
        data.remove(index);
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

        if (!TextUtils.isEmpty(teacher.getAvatarUrl())) {
            Picasso.with(context)
                    .load(teacher.getAvatarUrl())
                    .placeholder(R.drawable.avatar)
                    .fit()
                    .into(holder.avatar);
        }

        holder.name.setText(teacher.getName());
        holder.college.setText(teacher.getCollege());
        holder.mainSubject.setText(getText(R.string.label_main_subject, teacher.getMainSubject()));
        holder.sex.setImageResource(teacher.getSexDrawableResource());

        return convertView;
    }

    public String getText(int stringResId, String... params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] == null) {
                    params[i] = "";
                }
            }
        }
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
