package com.ivan.android.manhattanenglish.app.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ivan.android.manhattanenglish.app.R;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-19
 * Time: PM4:11
 */
public class MultiPickerGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] mData;
    private Set<String> mSelectedItems;

    public MultiPickerGridAdapter(Context context, int dataResId) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = context.getResources().getStringArray(dataResId);
        this.mSelectedItems = new HashSet<String>();
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Set<String> getSelectedItems() {
        return mSelectedItems;
    }

    public void setSelectedItems(Set<String> mSelectedItems) {
        this.mSelectedItems = mSelectedItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pick_location_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String location = mData[position];
        viewHolder.checkBox.setText(location);
        viewHolder.checkBox.setChecked(isItemChecked(location));
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSelectedItems.add(location);
                } else {
                    mSelectedItems.remove(location);
                }
            }
        });
        return convertView;
    }

    private boolean isItemChecked(String location) {
        return mSelectedItems != null && mSelectedItems.contains(location);
    }


    class ViewHolder {
        CheckBox checkBox;
    }
}

