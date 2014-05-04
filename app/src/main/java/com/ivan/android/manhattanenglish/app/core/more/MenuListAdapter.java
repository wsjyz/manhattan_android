package com.ivan.android.manhattanenglish.app.core.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM2:19
 */
public class MenuListAdapter extends BaseAdapter {

    private String[] menus;

    private LayoutInflater inflater;

    public MenuListAdapter(Context context, String[] menus) {
        this.menus = menus;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menus.length;
    }

    @Override
    public Object getItem(int position) {
        return menus[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.more_info_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.menu_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(menus[position]);
        return convertView;
    }

    class ViewHolder {
        TextView text;
    }
}
