package com.ivan.android.manhattanenglish.app.core.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.appoint.StudentListActivity;
import com.ivan.android.manhattanenglish.app.core.collect.StudentListFragment;
import com.ivan.android.manhattanenglish.app.core.course.NiceCourseActivity;
import com.ivan.android.manhattanenglish.app.core.course.PublishCourseActivity;
import com.ivan.android.manhattanenglish.app.core.homework.TeacherHomeworkActivity;
import com.ivan.android.manhattanenglish.app.core.info.InfomationActivity;
import com.ivan.android.manhattanenglish.app.core.more.MoreInfoActivity;
import com.ivan.android.manhattanenglish.app.core.question.QuestionForTeacherActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherActivity;
import com.ivan.android.manhattanenglish.app.core.userinfo.TeacherInfoActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class TeacherHomeActivity extends BaseActivity {
    //发布课程
    private Button mPublishCourse;
    //精品课程
    private Button mCourseBtn;
    //名师教学
    private Button mTeacherBtn;
    //我的作业
    private Button mHomeworkBtn;
    //资讯
    private Button mInfoBtn;
    //我要回答
    private Button mAnswerQuestion;
    //我的学生
    private Button mStudent;
    //我的预约
    private Button mAppointQueryBtn;
    //我的试听
    private Button mAuditionBtn;
    //更多
    private Button mMoreInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(TeacherInfoActivity.class);
            }
        });

        mPublishCourse = (Button) findViewById(R.id.publish_course);
        mPublishCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(PublishCourseActivity.class);
            }
        });

        mCourseBtn = (Button) findViewById(R.id.course_button);
        mCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(NiceCourseActivity.class);
            }
        });

        mTeacherBtn = (Button) findViewById(R.id.teacher_button);
        mTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(TeacherActivity.class);
            }
        });

        mHomeworkBtn = (Button) findViewById(R.id.homework_button);
        mHomeworkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(TeacherHomeworkActivity.class);
            }
        });

        mInfoBtn = (Button) findViewById(R.id.infomation_button);
        mInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(InfomationActivity.class);
            }
        });

        mAnswerQuestion = (Button) findViewById(R.id.answer_question);
        mAnswerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(QuestionForTeacherActivity.class);
            }
        });

        mStudent = (Button) findViewById(R.id.student_btn);
        mStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToStudentList(R.string.title_my_student, StudentListFragment.TYPE_ALL);
            }
        });

        mAppointQueryBtn = (Button) findViewById(R.id.appoint_query_btn);
        mAppointQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToStudentList(R.string.title_my_appoint_student, StudentListFragment.TYPE_APPOINT);
            }
        });

        mAuditionBtn = (Button) findViewById(R.id.audition_btn);
        mAuditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToStudentList(R.string.title_my_audition_student, StudentListFragment.TYPE_AUDITION);
            }
        });

        mMoreInfoBtn = (Button) findViewById(R.id.more_info_btn);
        mMoreInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(MoreInfoActivity.class);
            }
        });
    }


    private void navigateToStudentList(int titleResId, String type) {
        Intent intent = new Intent(TeacherHomeActivity.this, StudentListActivity.class);
        intent.putExtra(StudentListActivity.TITLE_KEY, getResources().getString(titleResId));
        intent.putExtra(StudentListActivity.LOAD_TYPE, type);
        startActivity(intent);
    }

}
