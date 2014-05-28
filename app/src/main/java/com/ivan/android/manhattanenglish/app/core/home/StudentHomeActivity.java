package com.ivan.android.manhattanenglish.app.core.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.appoint.AppointActivity;
import com.ivan.android.manhattanenglish.app.core.appoint.MyAppointForStudentActivity;
import com.ivan.android.manhattanenglish.app.core.audition.MyAuditionForStudentActivity;
import com.ivan.android.manhattanenglish.app.core.collect.MyCollectForStudentActivity;
import com.ivan.android.manhattanenglish.app.core.course.CourseActivity;
import com.ivan.android.manhattanenglish.app.core.homework.HomeworkActivity;
import com.ivan.android.manhattanenglish.app.core.info.InfomationActivity;
import com.ivan.android.manhattanenglish.app.core.more.MoreInfoActivity;
import com.ivan.android.manhattanenglish.app.core.question.QuestionForStudentActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherActivity;
import com.ivan.android.manhattanenglish.app.core.userinfo.StudentInfoActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class StudentHomeActivity extends BaseActivity {
    //我要预约
    private Button mAppointBtn;
    //精品课程
    private Button mCourseBtn;
    //名师教学
    private Button mTeacherBtn;
    //我的作业
    private Button mHomeworkBtn;
    //资讯
    private Button mInfoBtn;
    //我要提问
    private Button mAskQuestionBtn;
    //收藏
    private Button mCollectBtn;
    //我的预约
    private Button mAppointQueryBtn;
    //我的试听
    private Button mAuditionBtn;
    //更多
    private Button mMoreInfoBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(StudentInfoActivity.class);
            }
        });

        mAppointBtn = (Button) findViewById(R.id.appoint_button);
        mAppointBtn.setOnClickListener(new View.OnClickListener() {
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

        mAskQuestionBtn = (Button) findViewById(R.id.ask_qustion_button);
        mAskQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(QuestionForStudentActivity.class);
            }
        });

        mCollectBtn = (Button) findViewById(R.id.collect_btn);
        mCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(MyCollectForStudentActivity.class);
            }
        });

        mAppointQueryBtn = (Button) findViewById(R.id.appoint_query_btn);
        mAppointQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(MyAppointForStudentActivity.class);
            }
        });

        mAuditionBtn = (Button) findViewById(R.id.audition_btn);
        mAuditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(MyAuditionForStudentActivity.class);
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
