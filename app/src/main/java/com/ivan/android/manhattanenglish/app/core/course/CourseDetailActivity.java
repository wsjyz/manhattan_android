package com.ivan.android.manhattanenglish.app.core.course;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

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
    TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        //todo load course info by courseId
        String courseId = getIntent().getStringExtra(COURSE_ID_KEY);

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

        mAppointBtn = (ImageView) findViewById(R.id.appoint_btn);

        mTeacherList = (ListView) findViewById(R.id.teacher_list);
        //todo teacherlist

        mDescription = (TextView) findViewById(R.id.description);

    }


    class CourseDetailLoadTask extends AsyncTask<String, Void, Course> {


        @Override
        protected Course doInBackground(String... params) {
            //todo load course from server
            String courseId = params[0];

            return null;
        }

        @Override
        protected void onPostExecute(Course course) {
            Picasso.with(CourseDetailActivity.this)
                    .load(course.getImageUrl())
                    .fit()
                    .into(mCourseImage);

            mClassNum.setText(getTextFromFormat(R.string.label_class_num, course.getClassNum()));
            mCost.setText(getTextFromFormat(R.string.label_fee, String.valueOf(course.getMoneyCost())));
            mLocation.setText(getTextFromFormat(R.string.label_teach_location, course.getLocation()));

            mStartTime.setText(getTextFromFormat(R.string.label_course_start_time, course.getStartTimeString()));
            mEndTime.setText(getTextFromFormat(R.string.label_course_end_time, course.getEndTimeString()));

            mPeriod.setText(getTextFromFormat(R.string.label_course_peroid, String.valueOf(course.getPeroid())));
            mDescription.setText(course.getDescription());

            super.onPostExecute(course);
        }
    }

}
