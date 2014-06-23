package com.ivan.android.manhattanenglish.app.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-19
 * Time: PM4:11
 */
public class SinglePickerGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] mData;
    private String selectedItem;
    private Context mContext;

    public SinglePickerGridAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    SinglePickerGridAdapter(Context context, String[] data) {
        this(context);
        this.mData = data;
    }

    SinglePickerGridAdapter(Context context, int dataResId) {
        this(context);
        String[] array = context.getResources().getStringArray(dataResId);
        this.mData = array;
    }

    public void setData(String[] newData) {
        if (newData != null) {
            mData = newData;
        }
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

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final String category = mData[position];
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.single_picker_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView;
            viewHolder.selected = category.equals(selectedItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(category);
        setTextSelected(viewHolder.text, category.equals(selectedItem));
        viewHolder.selected = category.equals(selectedItem);

        return convertView;
    }

    private void setTextSelected(TextView text, boolean selected) {
        if (selected) {
            text.setBackgroundResource(R.drawable.text_border_blue);
            text.setTextColor(mContext.getResources().getColor(android.R.color.white));
        } else {
            text.setBackgroundResource(mContext.getResources().getColor(android.R.color.transparent));
            text.setTextColor(mContext.getResources().getColor(R.color.common_text_color));
        }

    }

    class ViewHolder {
        TextView text;
        boolean selected;
    }
}

