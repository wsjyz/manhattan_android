package com.ivan.android.manhattanenglish.app.core.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherDetailInfoActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

/**
 * 学生----我的收藏
 *
 * @author: Ivan Vigoss
 * Date: 14-5-13
 * Time: PM10:17
 */
public class MyCollectForStudentActivity extends BaseActivity implements TeacherListFragment.OnTeacherItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_collect);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            TeacherListFragment fragment = TeacherListFragment.newInstance(TeacherListFragment.TYPE_COLLECT);
            transaction.add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onTeacherItemClick(String teacherId) {
        Intent detail = new Intent(this, TeacherDetailInfoActivity.class);
        detail.putExtra(TeacherDetailInfoActivity.TEACHER_ID_KEY, teacherId);
        startActivity(detail);
    }
}
