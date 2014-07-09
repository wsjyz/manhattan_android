package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.course.Appointment;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

/**
 * 教师详情 ---点击预约之后的界面
 */
public class AppointTeacherActivity extends BaseActivity {

    public final static String SUBJECT_ARRAY_KEY = "SUBJECT_ARRAY";

    public final static String LOCATION_ARRAY_KEY = "LOCATION_ARRAY";

    public final static String STUDY_METHOD_KEY = "STUDY_METHOD";

    public static final String ACTION_TYPE_KEY = "ACTION_TYPE";
    public static final String RESOURCE_ID_KEY = "RESOURCE_ID";
    public static final int ACTION_TYPE_APPOINT = 1;
    public static final int ACTION_TYPE_AUDITION = 2;

    AppointContactFragment mFragment;

    Spinner mSubjectSpinner;

    Spinner mLocationSpinner;

    Button mSubmit;

    int actionType;

    String teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_teacher);

        actionType = getIntent().getIntExtra(ACTION_TYPE_KEY, ACTION_TYPE_APPOINT);
        teacherId = getIntent().getStringExtra(RESOURCE_ID_KEY);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setTitleText(actionType == ACTION_TYPE_APPOINT ? getText(R.string.title_activity_appoint_course) : getText(R.string.title_activity_audition_course));
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFragment = (AppointContactFragment) getSupportFragmentManager().findFragmentById(R.id.appoint_form);

        //科目Spinner
        mSubjectSpinner = (Spinner) findViewById(R.id.study_subject_spinner);
        String[] subjects = getIntent().getStringArrayExtra(SUBJECT_ARRAY_KEY);
        ArrayAdapter<String> subjectAdapter = createAdapterFromSource(subjects);
        mSubjectSpinner.setAdapter(subjectAdapter);

        mLocationSpinner = (Spinner) findViewById(R.id.location_spinner);
        String[] locations = getIntent().getStringArrayExtra(LOCATION_ARRAY_KEY);
        ArrayAdapter<String> locationAdapter = createAdapterFromSource(locations);
        mLocationSpinner.setAdapter(locationAdapter);

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setText(actionType == ACTION_TYPE_APPOINT ? getText(R.string.action_appoint) : getText(R.string.action_audition));
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSubmit(Appointment.PAYMENT_ONLINE, new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppointTeacherActivity.this, R.string.appoint_success, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });
    }


    private void attemptSubmit(String payment, Runnable runnable) {
        if (mFragment.checkForm()) {
            Appointment appointment = new Appointment();
            appointment.setUserId(UserCache.getUserId());
            appointment.setMobile(mFragment.getPhone());
            appointment.setUserName(mFragment.getUserName());
            appointment.setAddress(mFragment.getAddress());
            appointment.setResourceId(teacherId);
            appointment.setResourceType(actionType == ACTION_TYPE_APPOINT ? Appointment.RESOURCE_TYPE_APPOINTMENT_TEACHER : Appointment.RESOURCE_TYPE_LISTEN_TEACHER);
            appointment.setPayment(payment);

            new AppointTask(this, runnable).execute(appointment);
        }
    }


    private ArrayAdapter<String> createAdapterFromSource(String[] arr) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }


}
