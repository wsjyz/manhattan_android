package com.ivan.android.manhattanenglish.app.core.appoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.collect.StudentListFragment;
import com.ivan.android.manhattanenglish.app.core.userinfo.StudentInfoActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.user.User;

public class StudentListActivity extends BaseActivity implements StudentListFragment.OnStudentListItemClickListener {

    public static final String TITLE_KEY = "TITLE";

    public static final String LOAD_TYPE = "TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        String title = getIntent().getStringExtra(TITLE_KEY);
        String loadType = getIntent().getStringExtra(LOAD_TYPE);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setTitleText(title);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.container) == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            StudentListFragment fragment = StudentListFragment.newInstance(loadType);
            transaction.add(R.id.container, fragment).commit();
        }

    }

    @Override
    public void onStudentListItemClick(User student) {
        Intent studentInfo = new Intent(this, StudentInfoActivity.class);
        studentInfo.putExtra(StudentInfoActivity.USER_ID_KEY, student.getUserId());
        startActivity(studentInfo);
    }
}
