package com.ivan.android.manhattanenglish.app.core.course;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.core.teacher.ScheduleGridAdapter;
import com.ivan.android.manhattanenglish.app.customviews.MultiPickerDialog;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.course.CourseService;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import java.util.Set;

public class PublishCourseActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<TeacherDetail> {

    TextView mPickLocation;

    GridView mScheduleGrid;
    ScheduleGridAdapter mScheduleAdapter;

    Spinner mStudentSpinner;

    EditText mStudentCost;

    TextView mTeacherMethod;

    Button mSubmit;

    RadioGroup mTeachMethodRadio;

    MultiPickerDialog pickLocationDialog;

    Set<String> selectedLocations;

    TeacherDetail mData;


    String[] requiredLevelValue;

    String[] requiredLevel;

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

        mPickLocation = (TextView) findViewById(R.id.choose_location);
        mPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPickLocationDialog().show();
            }
        });

        mScheduleGrid = (GridView) findViewById(R.id.schedule_grid);
        mScheduleAdapter = new ScheduleGridAdapter(this, true);
        mScheduleGrid.setAdapter(mScheduleAdapter);

        mTeachMethodRadio = (RadioGroup) findViewById(R.id.teach_method_radio);

        requiredLevelValue = getResources().getStringArray(R.array.required_level_value);
        requiredLevel = getResources().getStringArray(R.array.required_level);

        mStudentSpinner = (Spinner) findViewById(R.id.required_level_spinner_for_student);
        ArrayAdapter<CharSequence> studentSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.required_level, android.R.layout.simple_spinner_item);
        studentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStudentSpinner.setAdapter(studentSpinnerAdapter);

        mTeacherMethod = (TextView) findViewById(R.id.teach_method_for_student);

        mStudentCost = (EditText) findViewById(R.id.cost_for_student);

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    if (mData == null) mData = new TeacherDetail();
                    mData.setAvailableLocation(mPickLocation.getText().toString());
                    mData.setTeachingTime(mScheduleAdapter.getTeachingTime());
                    mData.setTeacherId(UserCache.getUserId());

                    int position = mStudentSpinner.getSelectedItemPosition();
                    if (position != AdapterView.INVALID_POSITION) {
                        mData.setRequiredLevel(requiredLevelValue[position]);
                    }

                    mData.setCost(mStudentCost.getText().toString());

                    new PublishCourseTask(PublishCourseActivity.this).execute(mData);
                }
            }
        });

        showLoadingDialog();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private boolean checkForm() {
        boolean result = true;
        if (selectedLocations == null || selectedLocations.size() == 0) {
            result = false;
            Toast.makeText(this, R.string.error_location_required, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStudentCost.getText().toString())) {
            result = false;
            mStudentCost.setError(getText(R.string.error_student_cost_required));
            mStudentCost.requestFocus();
        }

        return result;
    }


    public void onTeachMethodClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.method_for_teacher:
                if (checked) {
                    checkTeachMethod(0);
                }
                break;
            case R.id.method_for_student:
                if (checked) {
                    checkTeachMethod(1);
                }
                break;
            default:
                break;
        }
    }

    private void checkTeachMethod(int type) {
        if (type == 0) {//check teacher
            mTeacherMethod.setText(R.string.teach_method_for_teacher);
            if (mData == null) mData = new TeacherDetail();
            mData.setTeachWay(TeacherDetail.WAY_TEACHER_VISIT);
        } else {
            mTeacherMethod.setText(R.string.teach_method_for_student);
            if (mData == null) mData = new TeacherDetail();
            mData.setTeachWay(TeacherDetail.WAY_STUDENT_VISIT);
        }
    }


    private MultiPickerDialog getPickLocationDialog() {
        if (pickLocationDialog == null) {
            pickLocationDialog = new MultiPickerDialog(this, getString(R.string.course_category), R.array.locations);
            pickLocationDialog.setDefaultEmptyText(R.string.action_choose_location);
            pickLocationDialog.setOnItemsCheckedListener(new MultiPickerDialog.OnItemsCheckedListener() {
                @Override
                public void onItemsChecked(Set<String> items, String itemsForString) {
                    selectedLocations = items;
                    mPickLocation.setText(itemsForString);
                }
            });
        }
        pickLocationDialog.setSelectedItems(selectedLocations);
        return pickLocationDialog;
    }

    class PublishCourseTask extends CommonAsyncTask<TeacherDetail, Void, Void> {

        protected PublishCourseTask(Context context) {
            super(context);
        }

        @Override
        protected Void getResultInBackground(TeacherDetail... params) {
            TeacherDetail teacherDetail = params[0];
            CourseService service = ServiceFactory.getService(CourseService.class);
            service.postCourse(teacherDetail);
            return null;
        }

        @Override
        protected void onSuccess(Void aVoid) {
            super.onSuccess(aVoid);
            Toast.makeText(PublishCourseActivity.this, R.string.info_submit_success, Toast.LENGTH_SHORT).show();
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
        return new TeacherDetailInfoLoader(this, UserCache.getUserId());
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

    public void refresh() {
        if (mData == null) return;
        if (!TextUtils.isEmpty(mData.getAvailableLocation())) {
            mPickLocation.setText(mData.getAvailableLocation());
        }
        //set selected items
        selectedLocations = mData.getLocationsForSet();

        if (TeacherDetail.WAY_STUDENT_VISIT.equals(mData.getTeachWay())) {
            mTeachMethodRadio.check(R.id.method_for_student);
            checkTeachMethod(1); //student
        } else {
            mTeachMethodRadio.check(R.id.method_for_teacher);
            checkTeachMethod(0);
        }

        mScheduleAdapter.setTeachingTime(mData.getTeachingTime());
        int levelPosition = 0;
        for (int i = 0; i < requiredLevelValue.length; i++) {
            if (requiredLevelValue[i].equals(mData.getRequiredLevel())) {
                levelPosition = i;
                break;
            }
        }

        mStudentSpinner.setSelection(levelPosition, true);
        mStudentCost.setText(mData.getCost());
    }
}
