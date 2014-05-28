package com.ivan.android.manhattanenglish.app.core.course;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;

/**
 * 精品课程
 */
public class CourseActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    PullToRefreshListView courseListView;

    OpenPage<Course> page;

    CourseListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        page = new OpenPage<Course>();

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

        courseListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                mAdapter.clear();
                new CourseLoadTask().execute(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new CourseLoadTask().execute(page);
                } else {
                    courseListView.onRefreshComplete();
                    Toast.makeText(CourseActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
        courseListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Course item = (Course) mAdapter.getItem(position);
        //todo navigation to detail page
    }

    class CourseLoadTask extends AsyncTask<OpenPage<Course>, Void, OpenPage<Course>> {
        @Override
        protected OpenPage<Course> doInBackground(OpenPage<Course>... params) {
            OpenPage<Course> param = params[0];
            param.setRows(null);//empty data
            try {
                //todo
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(OpenPage<Course> coursePage) {
            if (coursePage != null) {
                page = coursePage;
                mAdapter.addAll(page.getRows());
                mAdapter.notifyDataSetChanged();
            }
            courseListView.onRefreshComplete();
        }
    }

}
