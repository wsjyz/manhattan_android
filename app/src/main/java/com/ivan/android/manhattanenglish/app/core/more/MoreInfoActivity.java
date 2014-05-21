package com.ivan.android.manhattanenglish.app.core.more;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class MoreInfoActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    ListView menuListView;

    String[] menuArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        menuArray = getResources().getStringArray(R.array.menu);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menuListView = (ListView) findViewById(R.id.menu_list);
        menuListView.setAdapter(new MenuListAdapter(this, menuArray));
        menuListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                navigate(AboutUsActivity.class);
                break;
            case 1:

                break;
            case 2:
                navigate(ContactUsActivity.class);
                break;
            case 3://我的钱包

                break;
            default:
                break;
        }
    }
}
