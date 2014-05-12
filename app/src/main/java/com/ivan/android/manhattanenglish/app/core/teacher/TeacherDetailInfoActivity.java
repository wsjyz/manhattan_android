package com.ivan.android.manhattanenglish.app.core.teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class TeacherDetailInfoActivity extends BaseActivity {

    ImageView mAvatar;
    TextView mTeacherName;
    ImageView mSex;
    TextView mCollege;
    TextView mMainSubject;

    TextView mGrade;
    TextView mEvaluation;
    TextView mAvailableLocation;
    TextView mTeachWay;

    ImageView mAuditionBtn;
    ImageView mAppointBtn;
    ImageView mCollectBtn;

    TextView mFocusCount;
    TextView mCommentCount;
    TextView mCollectCount;

    GridView mTeacherScheduleGrid;

    TextView mTeachMethod;
    TextView mRequiredLevel;
    TextView mCost;
    TextView mSelfIntroduce;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail_info);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAvatar = (ImageView) findViewById(R.id.teacher_avatar);
        mTeacherName = (TextView) findViewById(R.id.teacher_name);
        mSex = (ImageView) findViewById(R.id.teacher_sex);
        mCollege = (TextView) findViewById(R.id.college);
        mMainSubject = (TextView) findViewById(R.id.main_subject);

        mGrade = (TextView) findViewById(R.id.grade_text);
        mEvaluation = (TextView) findViewById(R.id.evaluate_text);
        mAvailableLocation = (TextView) findViewById(R.id.available_location_text);
        mTeachWay = (TextView) findViewById(R.id.teach_way_text);

        mAuditionBtn = (ImageView) findViewById(R.id.audition_image_btn);
        mAppointBtn = (ImageView) findViewById(R.id.appoint_btn);
        mCollectBtn = (ImageView) findViewById(R.id.collect_image_btn);

        mTeacherScheduleGrid = (GridView) findViewById(R.id.schedule_grid);
        mTeachMethod = (TextView) findViewById(R.id.teach_method);
        mRequiredLevel = (TextView) findViewById(R.id.required_level_text);
        mCost = (TextView) findViewById(R.id.cost);

        mSelfIntroduce = (TextView) findViewById(R.id.self_introduce);

    }


}
