package com.ivan.android.manhattanenglish.app.core.course;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.ivan.android.manhattanenglish.app.remote.course.CourseService;
import com.ivan.android.manhattanenglish.app.remote.course.QueryParam;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;
import java.util.Date;

/**
 * 精品课程
 */
public class NiceCourseActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final String QUERY_PARAM_KEY = "QUERY_KEY";

    PullToRefreshListView courseListView;

    OpenPage<Course> page;

    CourseListAdapter mAdapter;

    QueryParam queryParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        page = new OpenPage<Course>();

        String paramJson = getIntent().getStringExtra(QUERY_PARAM_KEY);
        if (!TextUtils.isEmpty(paramJson)) {
            queryParam = JSON.parseObject(paramJson, QueryParam.class);
        }

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        courseListView = (PullToRefreshListView) findViewById(R.id.course_list);

        mAdapter = new CourseListAdapter(this, new ArrayList<Course>());
        courseListView.setAdapter(mAdapter);
        courseListView.setEmptyView(getEmptyView());

        courseListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                if (refreshDate != null) {
                    CharSequence label = getRefreshTimeString();
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                }
                new CourseLoadTask(NiceCourseActivity.this).execute(page);
            }
        });

        courseListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new CourseLoadTask(NiceCourseActivity.this).execute(page);
                } else {
                    Toast.makeText(NiceCourseActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        courseListView.setOnItemClickListener(this);
        new CourseLoadTask(this).execute(page);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Course item = (Course) mAdapter.getItem(position - 1);
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(CourseDetailActivity.COURSE_ID_KEY, item.getCourseId());
        startActivity(intent);

    }

    class CourseLoadTask extends CommonAsyncTask<OpenPage<Course>, Void, OpenPage<Course>> {

        protected CourseLoadTask(Context context) {
            super(context);
        }

        @Override
        protected OpenPage<Course> getResultInBackground(OpenPage<Course>... params) {
            OpenPage<Course> param = params[0];
            param.setRows(null);
            CourseService courseService = ServiceFactory.getService(CourseService.class);
            return queryParam == null ? courseService.loadNiceCourse(param) : courseService.search(param, queryParam);
        }

        @Override
        protected void onPostExecute(OpenPage<Course> openPage) {
            super.onPostExecute(openPage);
            if (openPage != null) {
                if (openPage.getPageNo() == 1) {
                    mAdapter.clear();
                }
                mAdapter.addAll(openPage.getRows());
            }
            courseListView.onRefreshComplete();
            refreshDate = new Date();
        }
    }

}
