package com.ivan.android.manhattanenglish.app.core.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.appoint.AppointActivity;
import com.ivan.android.manhattanenglish.app.core.appoint.StudentAppointActivity;
import com.ivan.android.manhattanenglish.app.core.audition.StudentAuditionActivity;
import com.ivan.android.manhattanenglish.app.core.collect.StudentCollectActivity;
import com.ivan.android.manhattanenglish.app.core.course.CourseActivity;
import com.ivan.android.manhattanenglish.app.core.homework.HomeworkActivity;
import com.ivan.android.manhattanenglish.app.core.info.InfomationActivity;
import com.ivan.android.manhattanenglish.app.core.more.MoreInfoActivity;
import com.ivan.android.manhattanenglish.app.core.question.QuestionActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherActivity;
import com.ivan.android.manhattanenglish.app.core.userinfo.StudentInfoActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class TeacherHomeActivity extends BaseActivity {
    //我要预约
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
                navigate(StudentInfoActivity.class);
            }
        });

        mPublishCourse = (Button) findViewById(R.id.publish_course);
        mPublishCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(AppointActivity.class);
            }
        });

        mCourseBtn = (Button) findViewById(R.id.course_button);
        mCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(CourseActivity.class);
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
                navigate(HomeworkActivity.class);
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
                navigate(QuestionActivity.class);
            }
        });

        mStudent = (Button) findViewById(R.id.student_btn);
        mStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(StudentCollectActivity.class);
            }
        });

        mAppointQueryBtn = (Button) findViewById(R.id.appoint_query_btn);
        mAppointQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(StudentAppointActivity.class);
            }
        });

        mAuditionBtn = (Button) findViewById(R.id.audition_btn);
        mAuditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(StudentAuditionActivity.class);
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


}