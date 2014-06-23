package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
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
                registerForContextMenu(v);
                openContextMenu(v);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appoint_course, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.online:
                attemptSubmit("ONLINE", new Runnable() {
                    @Override
                    public void run() {
                        //todo 支付宝接口
                    }
                });
                return true;
            case R.id.offline:
                attemptSubmit("OFFLINE", new Runnable() {
                    @Override
                    public void run() {
                        //todo 打电话
                    }
                });
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void attemptSubmit(String payment, Runnable runnable) {
        if (mFragment.checkForm()) {
            Appointment appointment = new Appointment();
            appointment.setUserId(UserCache.getUserId());
            appointment.setMobile(mFragment.getPhone());
            appointment.setUserName(mFragment.getUserName());
            appointment.setAddress(mFragment.getAddress());
            appointment.setResourceId(courseId);
            appointment.setResourceType(actionType == ACTION_TYPE_APPOINT ? "APPOINTMENT_COURSE" : "LISTEN_COURSE");
            appointment.setPayment(payment);

            new AppointTask(this, runnable).execute(appointment);
        }
    }

}
