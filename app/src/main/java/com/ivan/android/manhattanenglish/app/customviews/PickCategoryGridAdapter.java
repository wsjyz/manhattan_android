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
public class PickCategoryGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] allCategory;
    private String selectedCategory;
    private Context mContext;

    PickCategoryGridAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.allCategory = context.getResources().getStringArray(R.array.course_categories);
    }

    @Override
    public int getCount() {
        return allCategory.length;
    }

    @Override
    public Object getItem(int position) {
        return allCategory[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final String category = allCategory[position];
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pick_category_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView;
            viewHolder.selected = category.equals(selectedCategory);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(category);
        setTextSelected(viewHolder.text, category.equals(selectedCategory));
        viewHolder.selected = category.equals(selectedCategory);

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

