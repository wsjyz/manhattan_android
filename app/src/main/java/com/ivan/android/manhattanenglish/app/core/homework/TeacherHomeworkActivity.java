package com.ivan.android.manhattanenglish.app.core.homework;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ivan.android.manhattanenglish.app.PublishHomeworkActivity;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.homework.Homework;
import com.ivan.android.manhattanenglish.app.remote.homework.HomeworkService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.Date;

public class TeacherHomeworkActivity extends BaseActivity {

    PullToRefreshListView homeworkListView;

    OpenPage<Homework> page;

    HomeworkService homeworkService;

    HomeworkListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_for_teacher);

        page = new OpenPage<Homework>();
        homeworkService = ServiceFactory.getService(HomeworkService.class);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(PublishHomeworkActivity.class);
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
                new HomeworkLoadTask().execute(page);
            }
        });

        homeworkListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new HomeworkLoadTask().execute(page);
                } else {
                    Toast.makeText(TeacherHomeworkActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        new HomeworkLoadTask().execute(page);
    }


    class HomeworkLoadTask extends AsyncTask<OpenPage<Homework>, Void, OpenPage<Homework>> {

        @Override
        protected OpenPage<Homework> doInBackground(OpenPage<Homework>... params) {
            OpenPage<Homework> param = params[0];
            param.setRows(null);//empty data
            try {

                return homeworkService.loadTeacherHomework(param);
            } catch (Exception e) {
                Log.e(getClass().getName(), "load homework list error", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(OpenPage<Homework> openPage) {
            super.onPostExecute(openPage);
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