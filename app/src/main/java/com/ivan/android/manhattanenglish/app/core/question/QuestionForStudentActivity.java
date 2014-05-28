package com.ivan.android.manhattanenglish.app.core.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.question.Question;

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
        questionDetail.putExtra("questionId", question.getQuestionId());
        questionDetail.putExtra("content", question.getContent());
        questionDetail.putExtra("createTime", question.getCreateTimeString());
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
        mAdapter.removeItem(menuInfo.position);
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


    public static class QuestionListLoader extends AsyncTaskLoader<List<Question>> {

        public QuestionListLoader(Context context) {
            super(context);
        }

        @Override
        public List<Question> loadInBackground() {
            //todo load question list from server
            return null;
        }
    }


    //todo load questionList from server

}
