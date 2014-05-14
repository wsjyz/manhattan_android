package com.ivan.android.manhattanenglish.app.core.teacher;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.course.TeacherDetail;
import com.squareup.picasso.Picasso;

public class TeacherDetailInfoActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<TeacherDetail> {

    static final String TEACHER_ID_KEY = "TEACHER_ID";

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
    ListAdapter mAdapter;

    TextView mTeachMethod;
    TextView mRequiredLevel;
    TextView mCost;
    TextView mSelfIntroduce;

    TeacherDetail mData;

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

        mFocusCount = (TextView) findViewById(R.id.focus_count_text);
        mCommentCount = (TextView) findViewById(R.id.comment_count_text);
        mCollectCount = (TextView) findViewById(R.id.collect_count_text);

        mGrade = (TextView) findViewById(R.id.grade_text);
        mEvaluation = (TextView) findViewById(R.id.evaluate_text);
        mAvailableLocation = (TextView) findViewById(R.id.available_location_text);
        mTeachWay = (TextView) findViewById(R.id.teach_way_text);

        mAuditionBtn = (ImageView) findViewById(R.id.audition_image_btn);
        mAppointBtn = (ImageView) findViewById(R.id.appoint_btn);
        mCollectBtn = (ImageView) findViewById(R.id.collect_image_btn);

        mTeacherScheduleGrid = (GridView) findViewById(R.id.schedule_grid);
        //todo create grid adapter
        mTeacherScheduleGrid.setAdapter(mAdapter);

        mTeachMethod = (TextView) findViewById(R.id.teach_method);
        mRequiredLevel = (TextView) findViewById(R.id.required_level_text);
        mCost = (TextView) findViewById(R.id.cost);

        mSelfIntroduce = (TextView) findViewById(R.id.self_introduce);

    }

    public class TeacherDetailInfoLoader extends AsyncTaskLoader<TeacherDetail> {

        private String teacherId;

        public TeacherDetailInfoLoader(Context context, String teacherId) {
            super(context);
            this.teacherId = teacherId;
        }

        @Override
        public TeacherDetail loadInBackground() {
            //todo load teacher from remote server
            return null;
        }

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String teacherId = args.getString(TEACHER_ID_KEY);
        return new TeacherDetailInfoLoader(this, teacherId);
    }

    @Override
    public void onLoadFinished(Loader<TeacherDetail> loader, TeacherDetail data) {
        mData = data;
        refresh();
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mData = null;
    }

    private void refresh() {
        if (mData == null) return;
        Picasso.with(this)
                .load(mData.getAvatarUrl())
                .fit()
                .into(mAvatar);

        mTeacherName.setText(mData.getName());
        mCollege.setText(mData.getCollege());
        mSex.setImageResource(mData.getSexDrawableResource());
        mMainSubject.setText(getTextFromFormat(R.string.label_main_subject, mData.getMainSubject()));

        String focusCountText = getTextFromFormat(R.string.pattern_focus_count, String.valueOf(mData.getFocusCount()));
        String commentCountText = getTextFromFormat(R.string.pattern_comment_count, String.valueOf(mData.getCommentCount()));
        String collectCountText = getTextFromFormat(R.string.pattern_collect_count, String.valueOf(mData.getCollectCount()));
        mFocusCount.setText(focusCountText);
        mCommentCount.setText(commentCountText);
        mCollectCount.setText(collectCountText);

        String gradeText = getTextFromFormat(R.string.pattern_grade, String.valueOf(mData.getRating()));
        String evaluationText = getTextFromFormat(R.string.pattern_evaluate, mData.getEvaluation());
        String availableLocation = getTextFromFormat(R.string.pattern_available_location, mData.getAvailableLocation());
        String teachWayText = getTextFromFormat(R.string.pattern_teach_way, mData.getTeachWay());
        mGrade.setText(gradeText);
        mEvaluation.setText(evaluationText);
        mAvailableLocation.setText(availableLocation);
        mTeachWay.setText(teachWayText);

        mTeachMethod.setText(mData.getTeachMethod());
        mRequiredLevel.setText(mData.getRequiredLevel());
        mCost.setText(getTextFromFormat(R.string.pattern_rmb, String.valueOf(mData.getCost())));
    }
}
