package com.ivan.android.manhattanenglish.app.core.question;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.question.Answer;
import com.ivan.android.manhattanenglish.app.remote.question.Question;
import com.makeramen.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class QuestionDetailActivity extends BaseActivity {

    TextView mQuestionContent;

    TextView mCreateTime;

    String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        questionId = getIntent().getStringExtra("questionId");

        String content = getIntent().getStringExtra("content");
        String createTime = getIntent().getStringExtra("creatTime");

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mQuestionContent = (TextView) findViewById(R.id.question_content_text);
        mQuestionContent.setText(content);

        mCreateTime = (TextView) findViewById(R.id.create_time_text);
        mCreateTime.setText(createTime);

        FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentById(R.id.fragment_container) == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            AnswerListFragment fragment = new AnswerListFragment();
            transaction.add(R.id.fragment_container, fragment).commit();
        }
    }


    public static class AnswerListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Answer>> {

        AnswerListAdapter mAdapter;

        public AnswerListFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListShown(false);

            mAdapter = new AnswerListAdapter(getActivity());

            setEmptyText(getText(R.string.answer_empty_text));

            setListAdapter(mAdapter);

            getLoaderManager().initLoader(0, null, this);
        }

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            return new AnswerListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Answer>> loader, List<Answer> data) {
            mAdapter.setData(data);
            // The list should now be shown.
            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        }

        @Override
        public void onLoaderReset(Loader loader) {
            mAdapter.clear();
        }
    }

    /**
     * ListAdapter for Answer List
     */
    public static class AnswerListAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        List<Answer> mData;

        public AnswerListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mData = new ArrayList<Answer>();

        }

        public void clear() {
            mData.clear();
        }

        public void setData(List<Answer> data) {
            clear();
            mData.addAll(data);
            notifyDataSetChanged();
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
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.answer_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.answerContent = (TextView) convertView.findViewById(R.id.answer_content_text);
                viewHolder.teacherName = (TextView) convertView.findViewById(R.id.teacher_name_text);
                viewHolder.avatar = (RoundedImageView) convertView.findViewById(R.id.avatar_image);
                viewHolder.answerTime = (TextView) convertView.findViewById(R.id.answer_time_text);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Answer answer = mData.get(position);
            viewHolder.answerContent.setText(answer.getAnswerContent());
            viewHolder.teacherName.setText(answer.getTeacherName());

            //todo load techer avatar

            viewHolder.answerTime.setText(answer.getAnswerTimeString());

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView answerContent;
        TextView teacherName;
        RoundedImageView avatar;
        TextView answerTime;
    }


    public static class AnswerListLoader extends CommonDataLoader<List<Answer>> {


        public AnswerListLoader(Context context) {
            super(context);
        }

        @Override
        public List<Answer> loadInBackground() {

            return null;
        }
    }

}
