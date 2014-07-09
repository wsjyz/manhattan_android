package com.ivan.android.manhattanenglish.app.core.homework;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.homework.Homework;
import com.ivan.android.manhattanenglish.app.remote.homework.HomeworkService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.Date;

public class StudentHomeworkActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    PullToRefreshListView homeworkListView;

    OpenPage<Homework> page;

    HomeworkService homeworkService;

    HomeworkListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        page = new OpenPage<Homework>();
        homeworkService = ServiceFactory.getService(HomeworkService.class);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter = new HomeworkListAdapter(this);

        homeworkListView = (PullToRefreshListView) findViewById(R.id.homework_list);
        homeworkListView.setEmptyView(getEmptyView());
        homeworkListView.setAdapter(mAdapter);

        homeworkListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                if (refreshDate != null) {
                    CharSequence label = getRefreshTimeString();
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                }
                new HomeworkLoadTask(StudentHomeworkActivity.this).execute(page);
            }
        });

        homeworkListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new HomeworkLoadTask(StudentHomeworkActivity.this).execute(page);
                } else {
                    Toast.makeText(StudentHomeworkActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeworkListView.setOnItemClickListener(this);

        new HomeworkLoadTask(StudentHomeworkActivity.this).execute(page);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Homework homework = (Homework) mAdapter.getItem(position - 1);
        if (homework != null) {
            Intent submitHomework = new Intent(this, SubmitHomeworkActivity.class);
            submitHomework.putExtra(SubmitHomeworkActivity.KEY_HOMEWORK, JSON.toJSONString(homework));
            startActivity(submitHomework);
        }
    }


    class HomeworkLoadTask extends CommonAsyncTask<OpenPage<Homework>, Void, OpenPage<Homework>> {

        protected HomeworkLoadTask(Context context) {
            super(context);
        }

        @Override
        protected OpenPage<Homework> getResultInBackground(OpenPage<Homework>... params) {
            OpenPage<Homework> param = params[0];
            param.setRows(null);//empty data
            return homeworkService.loadUserHomework(param);
        }

        @Override
        protected void onSuccess(OpenPage<Homework> openPage) {
            super.onSuccess(openPage);
            if (openPage != null) {
                if (openPage.getPageNo() == 1) {
                    mAdapter.clear();
                }
                mAdapter.addAll(openPage.getRows());
            }
            homeworkListView.onRefreshComplete();
            refreshDate = new Date();
        }

    }


}