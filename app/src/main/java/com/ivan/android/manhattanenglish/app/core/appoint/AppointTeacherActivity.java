package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

/**
 * 教师详情 ---点击预约之后的界面
 */
public class AppointTeacherActivity extends BaseActivity {

    public final static String SUBJECT_ARRAY_KEY = "SUBJECT_ARRAY";

    public final static String LOCATION_ARRAY_KEY = "LOCATION_ARRAY";

    public final static String STUDY_METHOD_KEY = "STUDY_METHOD";

    AppointContactFragment mFragment;

    Spinner mSubjectSpinner;

    Spinner mLocationSpinner;

    Spinner mStudyMethodSpinner;

    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_teacher);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
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

        mStudyMethodSpinner = (Spinner) findViewById(R.id.study_method_spinner);
        String[] methods = getIntent().getStringArrayExtra(STUDY_METHOD_KEY);
        ArrayAdapter<String> methodAdapter = createAdapterFromSource(methods);
        mStudyMethodSpinner.setAdapter(methodAdapter);


        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo submit
            }
        });
    }



    private ArrayAdapter<String> createAdapterFromSource(String[] arr) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }


}
