package com.ivan.android.manhattanenglish.app.core.more;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class MoreInfoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    TitleBar titleBar;

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
            default:
                break;
        }
    }

    private void navigate(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
