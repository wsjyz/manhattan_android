package com.ivan.android.manhattanenglish.app.core.purchase;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.purchase.PurchaseHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-21
 * Time: AM11:43
 */
public class PurchaseHistoryAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    List<PurchaseHistory> mData;

    Context mContext;

    public PurchaseHistoryAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        mData = new ArrayList<PurchaseHistory>();
    }

    public void setData(List<PurchaseHistory> mData) {
        if (mData != null) {
            clear();
            mData.addAll(mData);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        mData.clear();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        PurchaseHistory history = mData.get(position);

        if (convertView == null) {
//            convertView = mInflater.inflate();
            viewHolder = new ViewHolder();
//            viewHolder.cost
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String costString = String.format(mContext.getResources().getString(R.string.pattern_resume_history_item), history.getCost().floatValue());
        String costText = Html.fromHtml(costString).toString();
        viewHolder.cost.setText(costText);
        viewHolder.consumeTime.setText(history.getConsumeTimeString());

        return convertView;
    }

    private class ViewHolder {
        TextView cost;
        TextView consumeTime;
    }
}
