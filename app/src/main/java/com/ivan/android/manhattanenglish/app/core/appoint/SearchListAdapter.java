package com.ivan.android.manhattanenglish.app.core.appoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM2:19
 */
public class SearchListAdapter extends BaseAdapter {

    private List<SearchCondition> conditionList;

    private LayoutInflater inflater;

    public SearchListAdapter(Context context, List<SearchCondition> conditionList) {
        this.conditionList = conditionList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return conditionList.size();
    }

    @Override
    public Object getItem(int position) {
        return conditionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return conditionList.get(position).icon;
    }

    public void setConditionList(List<SearchCondition> conditionList) {
        this.conditionList = conditionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        SearchCondition _condition = conditionList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.appoint_search_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.menu_text);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.menu_icon);
            viewHolder.conditionText = (TextView) convertView.findViewById(R.id.search_condition_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(_condition.text);
        viewHolder.icon.setImageResource(_condition.icon);
        viewHolder.conditionText.setText(_condition.conditionText);
        return convertView;
    }

    class ViewHolder {
        TextView text;
        ImageView icon;
        TextView conditionText;
    }
}
