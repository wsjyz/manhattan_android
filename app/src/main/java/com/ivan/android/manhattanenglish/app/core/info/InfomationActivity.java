package com.ivan.android.manhattanenglish.app.core.info;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.info.Infomation;
import com.ivan.android.manhattanenglish.app.remote.info.InfomationService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;

public class InfomationActivity extends ActionBarActivity {

    TitleBar titleBar;

    PullToRefreshListView infoListView;

    OpenPage<Infomation> page;

    InfomationService infomationService;

    InfomationListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);

        page = new OpenPage<Infomation>();
        infomationService = ServiceFactory.getService(InfomationService.class);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null);

        infoListView = (PullToRefreshListView) findViewById(R.id.info_list);
        mAdapter = new InfomationListAdapter(this, new ArrayList<Infomation>());
        infoListView.setAdapter(mAdapter);
        infoListView.setEmptyView(emptyView);

        infoListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                mAdapter.clear();
                new InfomationLoadTask().execute(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (page.getPageNo() >= page.getPageSize()) {
                    refreshView.onRefreshComplete();
                    Toast.makeText(InfomationActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                } else {
                    page.setPageNo(page.getPageNo() + 1);
                    new InfomationLoadTask().execute(page);
                }
            }
        });

    }

    class InfomationLoadTask extends AsyncTask<OpenPage<Infomation>, Void, OpenPage<Infomation>> {
        @Override
        protected OpenPage<Infomation> doInBackground(OpenPage<Infomation>... params) {
            OpenPage<Infomation> param = params[0];
            param.setRows(null);//empty data
            try {
                return infomationService.loadLatestInfomation(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(OpenPage<Infomation> infomationOpenPage) {
            if (infomationOpenPage != null) {
                page = infomationOpenPage;
                mAdapter.addAll(page.getRows());
                mAdapter.notifyDataSetChanged();
            }
            infoListView.onRefreshComplete();
        }
    }
}
