package com.ivan.android.manhattanenglish.app.core.question;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.question.Question;
import com.ivan.android.manhattanenglish.app.remote.question.QuestionService;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-28
 * Time: PM3:15
 */
public class QuestionListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Question>> {

    public static final String LOAD_TYPE = "TYPE";

    public static final int TYPE_ASSIGNED = 0;

    public static final int TYPE_ANSWERED = 1;

    public static final int TYPE_NOT_ANSWER = 2;

    QuestionListAdapter mAdapter;

    OnQuestionItemClickListener mListener;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new QuestionListAdapter(getActivity());
        setListAdapter(mAdapter);

        setEmptyText(getText(R.string.empty_text));
        setListShown(false);
        getLoaderManager().initLoader(0, getArguments(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnQuestionItemClickListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.getClass().getName() + " can't cast to OnQuestionItemClickListener.");
        }
    }

    public static QuestionListFragment newInstance(int type) {
        QuestionListFragment fragment = new QuestionListFragment();
        Bundle args = new Bundle();
        args.putInt(LOAD_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Loader<List<Question>> onCreateLoader(int id, Bundle args) {
        int type = args.getInt(LOAD_TYPE, TYPE_ASSIGNED);
        switch (type) {
            case TYPE_ANSWERED:
                return new AnsweredQuestionListLoader(getActivity());
            case TYPE_NOT_ANSWER:
                return new NotAnsweredQuestionListLoader(getActivity());
            default:
                return new AssignedQuestionListLoader(getActivity());
        }

    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        mAdapter.setData(data);
        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    public void setOnQuestionItemClickListener(OnQuestionItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mListener != null) {
            Question question = (Question) mAdapter.getItem(position);
            mListener.onQuestionItemClick(question);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {
        mAdapter.clear();
    }

    public interface OnQuestionItemClickListener {
        void onQuestionItemClick(Question question);
    }

    public static class AssignedQuestionListLoader extends CommonDataLoader<List<Question>> {

        public AssignedQuestionListLoader(Context context) {
            super(context);
        }

        @Override
        public List<Question> loadInBackground() {
            List<Question> result = null;
            QuestionService questionService = ServiceFactory.getService(QuestionService.class);
            try {
                result = questionService.loadQuestionByType(QuestionService.SEARCH_TYPE_ASSIGN);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public static class AnsweredQuestionListLoader extends CommonDataLoader<List<Question>> {

        public AnsweredQuestionListLoader(Context context) {
            super(context);
        }

        @Override
        public List<Question> loadInBackground() {
            List<Question> result = null;
            try {
                QuestionService questionService = ServiceFactory.getService(QuestionService.class);
                result = questionService.loadQuestionByType(QuestionService.SEARCH_TYPE_ANSWER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public static class NotAnsweredQuestionListLoader extends CommonDataLoader<List<Question>> {

        public NotAnsweredQuestionListLoader(Context context) {
            super(context);
        }

        @Override
        public List<Question> loadInBackground() {
            List<Question> result = null;
            try {
                QuestionService questionService = ServiceFactory.getService(QuestionService.class);
                result = questionService.loadQuestionByType(QuestionService.SEARCH_TYPE_UNANSWER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
