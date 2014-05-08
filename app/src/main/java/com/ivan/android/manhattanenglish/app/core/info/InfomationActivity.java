package com.ivan.android.manhattanenglish.app.core.info;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.info.Infomation;
import com.ivan.android.manhattanenglish.app.remote.info.InfomationService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;
import java.util.Date;

public class InfomationActivity extends BaseActivity {

    PullToRefreshListView infoListView;

    OpenPage<Infomation> page;

    InfomationService infomationService;

    InfomationListAdapter mAdapter;

    Date refreshDate;

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


        infoListView = (PullToRefreshListView) findViewById(R.id.info_list);
        mAdapter = new InfomationListAdapter(this, new ArrayList<Infomation>());
        infoListView.setAdapter(mAdapter);
        infoListView.setEmptyView(getEmptyView());

        infoListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                mAdapter.clear();
                if (refreshDate != null) {
                    CharSequence label = DateUtils.getRelativeTimeSpanString(refreshDate.getTime());
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                }
                new InfomationLoadTask().execute(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new InfomationLoadTask().execute(page);
                } else {
                    infoListView.onRefreshComplete();
                    Toast.makeText(InfomationActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        new InfomationLoadTask().execute(page);

    }

    class InfomationLoadTask extends AsyncTask<OpenPage<Infomation>, Void, OpenPage<Infomation>> {
        @Override
        protected OpenPage<Infomation> doInBackground(OpenPage<Infomation>... params) {
            OpenPage<Infomation> param = params[0];
            param.setRows(null);//empty data
            try {
                Thread.sleep(2000);
                return infomationService.loadLatestInfomation(param);
            } catch (Exception e) {
                Log.e(getClass().getName(), "load latestinfomation error", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(OpenPage<Infomation> infomationOpenPage) {
            if (infomationOpenPage != null) {
                Log.i(getClass().getName(), "load latestinfomation finished. load rows " + page.getRows().size());
                page = infomationOpenPage;
                mAdapter.addAll(page.getRows());
                mAdapter.notifyDataSetChanged();
            }
            infoListView.onRefreshComplete();
            refreshDate = new Date();
        }
    }
}
