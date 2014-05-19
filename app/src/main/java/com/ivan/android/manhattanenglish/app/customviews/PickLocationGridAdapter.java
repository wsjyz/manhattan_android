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
public class PickLocationGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] allLocations;
    private Set<String> selectedLocationSet;

    PickLocationGridAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.allLocations = context.getResources().getStringArray(R.array.locations);
        this.selectedLocationSet = new HashSet<String>();
    }

    @Override
    public int getCount() {
        return allLocations.length;
    }

    @Override
    public Object getItem(int position) {
        return allLocations[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Set<String> getSelectedLocationSet() {
        return selectedLocationSet;
    }

    public void setSelectedLocationSet(Set<String> selectedLocationSet) {
        this.selectedLocationSet = selectedLocationSet;
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

        final String location = allLocations[position];
        viewHolder.checkBox.setText(location);
        viewHolder.checkBox.setChecked(containsLocation(location));
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedLocationSet.add(location);
                } else {
                    selectedLocationSet.remove(location);
                }
            }
        });
        return convertView;
    }

    private boolean containsLocation(String location) {
        return selectedLocationSet != null && selectedLocationSet.contains(location);
    }


    class ViewHolder {
        CheckBox checkBox;
    }
}

