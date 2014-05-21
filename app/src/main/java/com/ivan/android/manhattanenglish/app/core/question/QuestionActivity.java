package com.ivan.android.manhattanenglish.app.core.question;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.question.Question;

public class QuestionActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    ListView mQuestionList;

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

        mQuestionList = (ListView) findViewById(R.id.question_list);
        mQuestionList.setOnItemClickListener(this);
        mQuestionList.setEmptyView(getEmptyView());

        registerForContextMenu(mQuestionList);

        mAdapter = new QuestionListAdapter(this);
        mQuestionList.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = (Question) mAdapter.getItem(position);
        //todo show question answer detail

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.question, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //todo delete
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Toast.makeText(this,"item "+ menuInfo.position+" delete.",Toast.LENGTH_SHORT).show();
        return true;
    }
}
