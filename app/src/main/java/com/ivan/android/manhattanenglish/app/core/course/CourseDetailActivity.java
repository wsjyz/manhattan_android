package com.ivan.android.manhattanenglish.app.core.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.appoint.AppointCourseActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherListAdapter;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.ivan.android.manhattanenglish.app.remote.course.CourseService;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseDetailActivity extends BaseActivity {

    public final static String COURSE_ID_KEY = "course_id";

    RoundedImageView mCourseImage;
    TextView mClassNum;
    TextView mCost;
    TextView mLocation;
    TextView mStartTime;
    TextView mEndTime;
    TextView mPeriod;
    ImageView mAuditionBtn;
    ImageView mAppointBtn;

    ListView mTeacherList;
    TeacherListAdapter mTeacherListAdapter;

    TextView mDescription;
    String courseId;
    Course mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        courseId = getIntent().getStringExtra(COURSE_ID_KEY);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCourseImage = (RoundedImageView) findViewById(R.id.course_image);

        mClassNum = (TextView) findViewById(R.id.class_num);

        mCost = (TextView) findViewById(R.id.cost);

        mLocation = (TextView) findViewById(R.id.location);

        mStartTime = (TextView) findViewById(R.id.start_time);

        mEndTime = (TextView) findViewById(R.id.end_time);

        mPeriod = (TextView) findViewById(R.id.course_peroid);

        mAuditionBtn = (ImageView) findViewById(R.id.audition_image_btn);
        mAuditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAppoint(AppointCourseActivity.ACTION_TYPE_AUDITION);
            }
        });

        mAppointBtn = (ImageView) findViewById(R.id.appoint_btn);
        mAppointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAppoint(AppointCourseActivity.ACTION_TYPE_APPOINT);
            }
        });

        mTeacherList = (ListView) findViewById(R.id.teacher_list);
        mTeacherListAdapter = new TeacherListAdapter(this, new ArrayList<TeacherDetail>());
        mTeacherList.setEmptyView(getEmptyView());

        mDescription = (TextView) findViewById(R.id.description);

        new CourseDetailLoadTask(this).execute();
    }


    private void navigateToAppoint(int actionType) {
        Intent intent = new Intent(this, AppointCourseActivity.class);
        intent.putExtra(AppointCourseActivity.ACTION_TYPE_KEY, actionType);
        intent.putExtra(AppointCourseActivity.RESOURCE_ID_KEY, courseId);
        startActivity(intent);
    }

    /**
     * 加载课程详情的任务
     */
    class CourseDetailLoadTask extends CommonAsyncTask<String, Void, Course> {

        protected CourseDetailLoadTask(Context context) {
            super(context);
        }

        @Override
        protected Course getResultInBackground(String... params) {
            CourseService courseService = ServiceFactory.getService(CourseService.class);
            return courseService.loadCourse(courseId);
        }

        @Override
        protected void onPostExecute(Course course) {
            super.onPostExecute(course);
            mCourse = course;
            refresh();
        }
    }

    public void refresh() {
        if (!TextUtils.isEmpty(mCourse.getImageUrl())) {
            Picasso.with(CourseDetailActivity.this)
                    .load(mCourse.getImageUrl())
                    .fit()
                    .into(mCourseImage);
        }

        mClassNum.setText(getTextFromFormat(R.string.label_class_num, mCourse.getClassNum()));
        mCost.setText(getTextFromFormat(R.string.label_fee, String.valueOf(mCourse.getMoneyCost())));
        mLocation.setText(getTextFromFormat(R.string.label_teach_location, mCourse.getLocation()));

        mStartTime.setText(getTextFromFormat(R.string.label_course_start_time, mCourse.getStartTimeString()));
        mEndTime.setText(getTextFromFormat(R.string.label_course_end_time, mCourse.getEndTimeString()));

        mPeriod.setText(getTextFromFormat(R.string.label_course_peroid, String.valueOf(mCourse.getPeriod())));
        mDescription.setText(mCourse.getDescription());

        mTeacherListAdapter.setData(mCourse.getTeacherDetailList());
    }

}
