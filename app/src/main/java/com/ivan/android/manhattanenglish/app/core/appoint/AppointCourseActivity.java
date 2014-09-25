package com.ivan.android.manhattanenglish.app.core.appoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.purchase.AlipayActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.course.Appointment;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

/**
 * 课程详情----点击预约\试听之后的界面
 */
public class AppointCourseActivity extends BaseActivity {

    public static final String ACTION_TYPE_KEY = "ACTION_TYPE";
    public static final String RESOURCE_ID_KEY = "RESOURCE_ID";
    public static final int ACTION_TYPE_APPOINT = 1;
    public static final int ACTION_TYPE_AUDITION = 2;

    AppointContactFragment mFragment;

    Button mSubmit;

    int actionType;

    String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_course);

        actionType = getIntent().getIntExtra(ACTION_TYPE_KEY, ACTION_TYPE_APPOINT);
        courseId = getIntent().getStringExtra(RESOURCE_ID_KEY);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setTitleText(actionType == ACTION_TYPE_APPOINT ? getText(R.string.title_activity_appoint_course) : getText(R.string.title_activity_audition_course));
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFragment = (AppointContactFragment) getSupportFragmentManager().findFragmentById(R.id.appoint_form);

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setText(actionType == ACTION_TYPE_APPOINT ? getText(R.string.action_appoint) : getText(R.string.action_audition));
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragment.checkForm()) {
                    Appointment appointment = new Appointment();
                    appointment.setUserId(UserCache.getUserId());
                    appointment.setMobile(mFragment.getPhone());
                    appointment.setUserName(mFragment.getUserName());
                    appointment.setAddress(mFragment.getAddress());
                    appointment.setResourceId(courseId);
                    appointment.setResourceType(actionType == ACTION_TYPE_APPOINT ? Appointment.RESOURCE_TYPE_APPOINTMENT_COURSE : Appointment.RESOURCE_TYPE_LISTEN_COURSE);

                    Intent payment = new Intent(AppointCourseActivity.this, AlipayActivity.class);
                    payment.putExtra(AlipayActivity.KEY_APPOINTMENT, JSON.toJSONString(appointment));
                    payment.putExtra(AlipayActivity.KEY_SUBJECT, actionType == ACTION_TYPE_APPOINT ? "预约课程" : "试听课程");
                    startActivity(payment);
                }
            }
        });
    }

}
