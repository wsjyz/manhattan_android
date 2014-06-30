package com.ivan.android.manhattanenglish.app.core.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.core.appoint.AppointCourseActivity;
import com.ivan.android.manhattanenglish.app.core.appoint.AppointTeacherActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.squareup.picasso.Picasso;

public class TeacherDetailInfoActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<TeacherDetail> {

    public static final String TEACHER_ID_KEY = "TEACHER_ID";

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
    ScheduleGridAdapter mAdapter;

    TextView mTeachMethod;
    TextView mRequiredLevel;
    TextView mCost;
    TextView mSelfIntroduce;

    String teacherId;
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

        teacherId = getIntent().getStringExtra(TEACHER_ID_KEY);

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
        mAuditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAppoint(AppointTeacherActivity.ACTION_TYPE_AUDITION);
            }
        });

        mAppointBtn = (ImageView) findViewById(R.id.appoint_btn);
        mAppointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAppoint(AppointTeacherActivity.ACTION_TYPE_APPOINT);
            }
        });

        mCollectBtn = (ImageView) findViewById(R.id.collect_image_btn);
        mCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CollectTeacherTask(TeacherDetailInfoActivity.this).execute();
            }
        });

        mTeacherScheduleGrid = (GridView) findViewById(R.id.schedule_grid);
        mAdapter = new ScheduleGridAdapter(this, false);
        mTeacherScheduleGrid.setAdapter(mAdapter);

        mTeachMethod = (TextView) findViewById(R.id.teach_method);
        mRequiredLevel = (TextView) findViewById(R.id.required_level_text);
        mCost = (TextView) findViewById(R.id.cost);

        mSelfIntroduce = (TextView) findViewById(R.id.self_introduce);

        showLoadingDialog();
        getSupportLoaderManager().initLoader(0, getIntent().getExtras(), this);

    }

    private void navigateToAppoint(int actionType) {
        if (mData == null) return;
        Intent intent = new Intent(this, AppointTeacherActivity.class);
        intent.putExtra(AppointTeacherActivity.ACTION_TYPE_KEY, actionType);
        intent.putExtra(AppointTeacherActivity.RESOURCE_ID_KEY, teacherId);
        intent.putExtra(AppointTeacherActivity.SUBJECT_ARRAY_KEY,mData.getSubjectArray());
        intent.putExtra(AppointTeacherActivity.LOCATION_ARRAY_KEY,mData.getLocations());
        intent.putExtra(AppointTeacherActivity.STUDY_METHOD_KEY,mData.getTeachWayArray());
        startActivity(intent);
    }

    class CollectTeacherTask extends CommonAsyncTask<Void, Void, Void> {

        protected CollectTeacherTask(Context context) {
            super(context);
        }

        @Override
        protected Void getResultInBackground(Void... params) {
            UserService userService = ServiceFactory.getService(UserService.class);
            userService.collect(teacherId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!hasError) {
                onCollect();
            }
        }
    }

    public static class TeacherDetailInfoLoader extends CommonDataLoader<TeacherDetail> {

        private String teacherId;

        public TeacherDetailInfoLoader(Context context, String teacherId) {
            super(context);
            this.teacherId = teacherId;
        }

        @Override
        public TeacherDetail loadInBackground() {
            UserService userService = ServiceFactory.getService(UserService.class);
            try {
                return userService.loadTeacherDetail(teacherId);
            } catch (Exception e) {
                Log.e("TeacherDetailInfoLoader", "load TeacherDetail error.", e);
            }
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
        hideLoadingDialog();
        if (data != null) {
            mData = data;
            refresh();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mData = null;
    }

    private void refresh() {
        if (mData == null) return;
        if (!TextUtils.isEmpty(mData.getAvatarUrl())) {
            Picasso.with(this)
                    .load(mData.getAvatarUrl())
                    .placeholder(R.drawable.avatar)
                    .fit()
                    .into(mAvatar);
        }

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

        mTeachMethod.setText(mData.getTeachWay());
        mRequiredLevel.setText(mData.getRequiredLevel());
        mCost.setText(getTextFromFormat(R.string.pattern_rmb, String.valueOf(mData.getCost())));

        //授课时间GridView
        mAdapter.setTeachingTime(mData.getTeachingTime());
    }

    private void onCollect() {
        String collectCountText = getTextFromFormat(R.string.pattern_collect_count, String.valueOf(mData.getCollectCount() + 1));
        mCollectCount.setText(collectCountText);
        Toast.makeText(this, R.string.info_collect_success, Toast.LENGTH_SHORT).show();
    }
}
