package com.ivan.android.manhattanenglish.app.core.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.question.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-20
 * Time: PM3:31
 */
public class QuestionListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    private List<Question> mData = new ArrayList<Question>();


    public QuestionListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void addAll(List<Question> questions) {
        mData.addAll(questions);
    }

    public void clear() {
        mData.clear();
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
        ViewHolder holder;
        Question question = mData.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.question_list_item, null);
            holder = new ViewHolder();
            holder.content = (TextView) convertView;
            convertView.setTag(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.content.setText(question.getContent());
        return convertView;
    }

    private class ViewHolder {
        TextView content;
    }
}
