package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

import java.util.List;

public class AppointActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    ListView searchListView;

    List<SearchCondition> conditions;

    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String[] searchTexts = getResources().getStringArray(R.array.appoint_search_text);
        int[] icons = getResources().getIntArray(R.array.appoint_search_icon);
        conditions = SearchCondition.createFromArray(searchTexts, icons, getResources().getString(R.string.search_no_limit));

        searchListView = (ListView) findViewById(R.id.search_condition_list);
        searchListView.setAdapter(new SearchListAdapter(this, conditions));
        searchListView.setOnItemClickListener(this);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //todo
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.drawable.course_category:
                Log.e("appointActivity", "course_category pressed");
                break;
        }
    }
}