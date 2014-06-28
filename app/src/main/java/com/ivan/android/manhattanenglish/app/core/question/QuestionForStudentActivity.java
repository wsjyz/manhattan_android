package com.ivan.android.manhattanenglish.app.core.question;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.question.Question;
import com.ivan.android.manhattanenglish.app.remote.question.QuestionService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;

import java.util.List;

/**
 * 学生--- 我的问题列表
 */
public class QuestionForStudentActivity extends BaseActivity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<List<Question>> {

    ListView mQuestionList;

    TextView mAddQuestion;

    QuestionListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddQuestion = (TextView) findViewById(R.id.add_question_btn);
        mAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(AskQuestionActivity.class);
            }
        });

        mQuestionList = (ListView) findViewById(R.id.question_list);
        mQuestionList.setOnItemClickListener(this);
        mQuestionList.setEmptyView(getEmptyView());

        registerForContextMenu(mQuestionList);

        mAdapter = new QuestionListAdapter(this);
        mQuestionList.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
        showLoadingDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = (Question) mAdapter.getItem(position);

        Intent questionDetail = new Intent(this, QuestionDetailActivity.class);
        questionDetail.putExtra(QuestionDetailActivity.JSON_QUESTION_KEY, JSON.toJSONString(question));
        startActivity(questionDetail);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.question, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        new DeleteQuestionTask(this, menuInfo.position).execute();
        return true;
    }

    @Override
    public Loader<List<Question>> onCreateLoader(int id, Bundle args) {
        return new QuestionListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        hideLoadingDialog();
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {
        mAdapter.clear();
    }


    public static class QuestionListLoader extends CommonDataLoader<List<Question>> {

        public QuestionListLoader(Context context) {
            super(context);
        }

        @Override
        public List<Question> loadInBackground() {
            QuestionService questionService = ServiceFactory.getService(QuestionService.class);
            List<Question> result = null;
            try {
                result = questionService.loadMyQuestions();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public class DeleteQuestionTask extends CommonAsyncTask<Integer, Void, Void> {

        private int position;
        private Question question;

        protected DeleteQuestionTask(Context context, int position) {
            super(context);
            this.position = position;
            this.question = (Question) mAdapter.getItem(position);
        }

        @Override
        protected Void getResultInBackground(Integer... params) {
            QuestionService questionService = ServiceFactory.getService(QuestionService.class);
            questionService.deleteQuestion(question.getQuestionId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!hasError) {
                mAdapter.removeItem(position);
            }
        }
    }

}
