package com.ivan.android.manhattanenglish.app.core.homework;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

import java.util.ArrayList;

public class HomeworkActivity extends ActionBarActivity {

    TitleBar titleBar;

    ListView homeworkListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homeworkListView = (ListView) findViewById(R.id.homework_list);
        homeworkListView.setAdapter(new HomeworkListAdapter(this, new ArrayList<String>()));

    }


}