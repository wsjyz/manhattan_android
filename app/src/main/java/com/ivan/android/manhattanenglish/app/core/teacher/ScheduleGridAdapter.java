package com.ivan.android.manhattanenglish.app.core.teacher;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ivan.android.manhattanenglish.app.R;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-27
 * Time: AM10:44
 */
public class ScheduleGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private boolean clickable;

    private Set<Integer> selectedItems;

    public ScheduleGridAdapter(Context context, boolean clickable) {
        mInflater = LayoutInflater.from(context);
        this.clickable = clickable;
        this.selectedItems = new HashSet<Integer>();
    }

    public Set<Integer> getSelectedItems() {
        return selectedItems;
    }

    public void addSelectedItems(Set<Integer> data) {
        if (data != null && data.size() > 0) {
            selectedItems.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return 21;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.schedule_grid_item, null);
        }

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        if (selectedItems.contains(position)) {
            checkBox.setSelected(true);
        }
        checkBox.setClickable(clickable);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(position);
                } else {
                    selectedItems.remove(position);
                }
            }
        });

        Log.i("ScheduleGridAdapter", "convertView height :" + convertView.getHeight() + " width : " + convertView.getWidth());

        return convertView;
    }
}
