package com.ivan.android.manhattanenglish.app.core.course;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.ScheduleGridAdapter;
import com.ivan.android.manhattanenglish.app.customviews.MultiPickerGridAdapter;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class PublishCourseActivity extends BaseActivity {

    GridView mLocationGrid;

    MultiPickerGridAdapter mLocationAdapter;

    GridView mScheduleGrid;
    ScheduleGridAdapter mScheduleAdapter;

    Spinner mStudentSpinner;

    Spinner mTeacherSpinner;

    EditText mStudentCost;

    EditText mTeacherCost;

    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_course);


        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLocationGrid = (GridView) findViewById(R.id.location_grid);
        mLocationAdapter = new MultiPickerGridAdapter(this, R.array.locations);
        mLocationGrid.setAdapter(mLocationAdapter);

        mScheduleGrid = (GridView) findViewById(R.id.schedule_grid);
        mScheduleAdapter = new ScheduleGridAdapter(this, true);
        mScheduleGrid.setAdapter(mScheduleAdapter);

        mStudentSpinner = (Spinner) findViewById(R.id.required_level_spinner_for_student);
        ArrayAdapter<CharSequence> studentSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.required_level, android.R.layout.simple_spinner_item);
        studentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStudentSpinner.setAdapter(studentSpinnerAdapter);

        mTeacherSpinner = (Spinner) findViewById(R.id.required_level_spinner_for_teaher);
        ArrayAdapter<CharSequence> teacherSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.required_level, android.R.layout.simple_spinner_item);
        teacherSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTeacherSpinner.setAdapter(teacherSpinnerAdapter);

        mStudentCost = (EditText) findViewById(R.id.cost_for_student);

        mTeacherCost = (EditText) findViewById(R.id.cost_for_teacher);

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo submit
            }
        });


    }


    public void onTeachMethodClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.method_for_all:

                break;
            case R.id.method_for_teacher:
                break;
            case R.id.method_for_student:
                break;
            default:
                break;
        }
    }


}
