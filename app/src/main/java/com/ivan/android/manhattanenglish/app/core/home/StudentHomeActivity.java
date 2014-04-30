package com.ivan.android.manhattanenglish.app.core.home;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class StudentHomeActivity extends ActionBarActivity {

    //custom views
    private TitleBar mTitleBar;
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

        mTitleBar = (TitleBar) findViewById(R.id.title_bar);

        mAppointBtn = (Button) findViewById(R.id.appoint_button);

        mCourseBtn = (Button) findViewById(R.id.course_button);

        mTeacherBtn = (Button) findViewById(R.id.teacher_button);

        mHomeworkBtn = (Button) findViewById(R.id.homework_button);

        mInfoBtn = (Button) findViewById(R.id.infomation_button);

        mAskQuestionBtn = (Button) findViewById(R.id.ask_qustion_button);

        mCollectBtn = (Button) findViewById(R.id.collect_btn);

        mAppointQueryBtn = (Button) findViewById(R.id.appoint_query_btn);

        mAuditionBtn = (Button) findViewById(R.id.audition_btn);

        mMoreInfoBtn = (Button) findViewById(R.id.more_info_btn);
    }

}