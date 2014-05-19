package com.ivan.android.manhattanenglish.app.core.teacher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.ivan.android.manhattanenglish.app.remote.course.TeacherDetail;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;

public class TeacherActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    PullToRefreshListView teacherList;

    OpenPage<TeacherDetail> page;

    EditText searchView;

    ImageView searchBtn;

    TeacherListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);



        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //init pull-to-refresh
        teacherList = (PullToRefreshListView) findViewById(R.id.teacher_detail_list);
        teacherList.setEmptyView(getEmptyView());
        mAdapter = new TeacherListAdapter(this, new ArrayList<TeacherDetail>());

        teacherList.setAdapter(mAdapter);

        teacherList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                mAdapter.clear();
                new TeacherLoadTask().execute(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new TeacherLoadTask().execute(page);
                } else {
                    teacherList.onRefreshComplete();
                    Toast.makeText(TeacherActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        teacherList.setOnItemClickListener(this);
        new TeacherLoadTask().execute(page);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TeacherDetail item = (TeacherDetail) mAdapter.getItem(position);
        if(Intent.ACTION_PICK.equals(getIntent().getAction())){
            Intent result = new Intent();
            result.putExtra("teacherId",item.getTeacherId());
            result.putExtra("teacherName",item.getName());
            setResult(0,result);
        }else{
            //todo navigation to detail page

        }
    }



    class TeacherLoadTask extends AsyncTask<OpenPage<TeacherDetail>, Void, OpenPage<TeacherDetail>> {
        @Override
        protected OpenPage<TeacherDetail> doInBackground(OpenPage<TeacherDetail>... params) {
            OpenPage<TeacherDetail> param = params[0];
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
        protected void onPostExecute(OpenPage<TeacherDetail> teacherPage) {
            if (teacherPage != null) {
                page = teacherPage;
                mAdapter.addAll(page.getRows());
                mAdapter.notifyDataSetChanged();
            }
            teacherList.onRefreshComplete();
        }
    }
}
