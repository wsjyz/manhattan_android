package com.ivan.android.manhattanenglish.app.core.collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-5
 * Time: PM5:10
 */
public class StudentListAdapter extends BaseAdapter {

    List<User> data;

    LayoutInflater mInflater;

    Context context;

    public StudentListAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<User>();
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        data.clear();
    }

    public void setData(List<User> newData) {
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
        User user = data.get(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.student_list_item, null);
            holder.avatar = (RoundedImageView) convertView.findViewById(R.id.student_avatar);
            holder.name = (TextView) convertView.findViewById(R.id.student_name);
            holder.phone = (TextView) convertView.findViewById(R.id.phone_text);
            holder.address = (TextView) convertView.findViewById(R.id.address_text);
            holder.sex = (ImageView) convertView.findViewById(R.id.student_sex);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load(user.getAvatar())
                .fit()
                .into(holder.avatar);

        holder.name.setText(getText(R.string.label_class_num, user.getUserName()));
        holder.phone.setText(user.getMobile());
        holder.address.setText(getText(R.string.pattern_address, user.getAddress()));
        holder.sex.setImageResource(user.getSexDrawableResource());

        return convertView;
    }

    public String getText(int stringResId, String... params) {
        return String.format(context.getResources().getString(stringResId), params);
    }

    class ViewHolder {
        RoundedImageView avatar;
        TextView name;
        TextView phone;
        TextView address;
        ImageView sex;
    }
}
