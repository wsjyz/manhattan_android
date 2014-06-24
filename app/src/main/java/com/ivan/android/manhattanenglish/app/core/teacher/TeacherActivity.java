package com.ivan.android.manhattanenglish.app.core.teacher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.userinfo.TeacherInfoActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;
import java.util.Date;

/**
 * 名师列表
 */
public class TeacherActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public final static String ACTION_PICK_TEACHER = "com.ivan.android.manhattanenglish.app.core.teacher.TeacherActivity.ACTION_PICK_TEACHER";

    PullToRefreshListView teacherList;

    OpenPage<TeacherDetail> page;

    EditText searchView;

    ImageButton searchBtn;

    TeacherListAdapter mAdapter;

    String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        page = new OpenPage<TeacherDetail>();
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchView = (EditText) findViewById(R.id.teacher_search);

        searchBtn = (ImageButton) findViewById(R.id.search_img_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywords = searchView.getText().toString();
                new TeacherLoadTask().execute(page);
            }
        });

        //init pull-to-refresh
        teacherList = (PullToRefreshListView) findViewById(R.id.teacher_detail_list);
        teacherList.setEmptyView(getEmptyView());
        mAdapter = new TeacherListAdapter(this, new ArrayList<TeacherDetail>());

        teacherList.setAdapter(mAdapter);

        teacherList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                if (refreshDate != null) {
                    CharSequence label = getRefreshTimeString();
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                }
                new TeacherLoadTask().execute(page);
            }
        });

        teacherList.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new TeacherLoadTask().execute(page);
                } else {
                    Toast.makeText(TeacherActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        teacherList.setOnItemClickListener(this);
        new TeacherLoadTask().execute(page);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TeacherDetail item = (TeacherDetail) mAdapter.getItem(position - 1);
        if (ACTION_PICK_TEACHER.equals(getIntent().getAction())) {
            Intent result = new Intent();
            result.putExtra("teacherId", item.getTeacherId());
            result.putExtra("teacherName", item.getName());
            setResult(0, result);
        } else {
            Intent detail = new Intent(this, TeacherDetailInfoActivity.class);
            detail.putExtra(TeacherDetailInfoActivity.TEACHER_ID_KEY, item.getTeacherId());
            startActivity(detail);
        }
    }


    class TeacherLoadTask extends AsyncTask<OpenPage<TeacherDetail>, Void, OpenPage<TeacherDetail>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected OpenPage<TeacherDetail> doInBackground(OpenPage<TeacherDetail>... params) {
            OpenPage<TeacherDetail> param = params[0];
            param.setRows(null);//empty data
            try {
                UserService userService = ServiceFactory.getService(UserService.class);
                return userService.search(param, keywords);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(OpenPage<TeacherDetail> openPage) {
            hideLoadingDialog();
            if (openPage != null) {
                if (openPage.getPageNo() == 1) {
                    mAdapter.clear();
                }
                mAdapter.addAll(openPage.getRows());
            }
            teacherList.onRefreshComplete();
            refreshDate = new Date();
        }
    }
}
