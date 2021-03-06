package com.ivan.android.manhattanenglish.app.core.info;

import android.content.Context;
import android.content.Intent;
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
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.info.Infomation;
import com.ivan.android.manhattanenglish.app.remote.info.InfomationService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;
import java.util.Date;

public class InfomationActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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


        infoListView = (PullToRefreshListView) findViewById(R.id.info_list);

        infoListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page.setPageNo(1);
                if (refreshDate != null) {
                    CharSequence label = getRefreshTimeString();
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                }
                new InfomationLoadTask(InfomationActivity.this).execute(page);
            }

        });

        infoListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page.hasNext()) {
                    page.setPageNo(page.getPageNo() + 1);
                    new InfomationLoadTask(InfomationActivity.this).execute(page);
                } else {
                    Toast.makeText(InfomationActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAdapter = new InfomationListAdapter(this, new ArrayList<Infomation>());
        infoListView.setAdapter(mAdapter);
        infoListView.setEmptyView(getEmptyView());
        infoListView.setOnItemClickListener(this);

        new InfomationLoadTask(this).execute(page);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Infomation info = (Infomation) mAdapter.getItem(position - 1);
        if (info != null) {
            Intent detail = new Intent(this, InfomationDetailActivity.class);
            detail.putExtra(InfomationDetailActivity.INFO_ID_KEY, info.getId());
            startActivity(detail);
        }
    }


    class InfomationLoadTask extends CommonAsyncTask<OpenPage<Infomation>, Void, OpenPage<Infomation>> {
        protected InfomationLoadTask(Context context) {
            super(context);
        }

        @Override
        protected OpenPage<Infomation> getResultInBackground(OpenPage<Infomation>... params) {
            OpenPage<Infomation> param = params[0];
            param.setRows(null);//empty data
            return infomationService.loadLatestInfomation(param);
        }

        @Override
        protected void onPostExecute(OpenPage<Infomation> openPage) {
            super.onPostExecute(openPage);
            if (openPage != null) {
                if (openPage.getPageNo() == 1) {
                    mAdapter.clear();
                }
                mAdapter.addAll(openPage.getRows());
            }
            infoListView.onRefreshComplete();
            refreshDate = new Date();
        }
    }
}
