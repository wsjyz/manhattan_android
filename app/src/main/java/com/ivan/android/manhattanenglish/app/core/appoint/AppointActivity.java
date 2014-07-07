package com.ivan.android.manhattanenglish.app.core.appoint;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.course.NiceCourseActivity;
import com.ivan.android.manhattanenglish.app.customviews.MultiPickerDialog;
import com.ivan.android.manhattanenglish.app.customviews.SinglePickerDialog;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.course.QueryParam;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppointActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    ListView searchListView;

    List<SearchCondition> conditions;

    Button searchButton;

    int[] icons = {
            R.drawable.course_category,
            R.drawable.teach_location,
            R.drawable.teach_method,
            R.drawable.teacher_sex,
            R.drawable.appoint_date
    };

    MultiPickerDialog pickLocationDialog;

    MultiPickerDialog pickCategoryDialog;

    SinglePickerDialog pickSexDialog;

    MultiPickerDialog pickTeachMethodDialog;

    DatePickerDialog datePickerDialog;

    Set<String> selectedLocations;

    Set<String> selectedCategories;

    Set<String> selectedMethods;

    String selectedSex;

    Date selectedDate;

    SearchListAdapter mAdapter;

    Map<String, String> mTeacherMethodMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);

        mTeacherMethodMap = new HashMap<String, String>();
        mTeacherMethodMap.put(getString(R.string.teach_method_for_student), TeacherDetail.WAY_STUDENT_VISIT);
        mTeacherMethodMap.put(getString(R.string.teach_method_for_teacher), TeacherDetail.WAY_TEACHER_VISIT);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String[] searchTexts = getResources().getStringArray(R.array.appoint_search_text);
        conditions = SearchCondition.createFromArray(searchTexts, icons, getResources().getString(R.string.search_no_limit));

        searchListView = (ListView) findViewById(R.id.search_condition_list);
        mAdapter = new SearchListAdapter(this, conditions);
        searchListView.setAdapter(mAdapter);
        searchListView.setOnItemClickListener(this);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(AppointActivity.this, NiceCourseActivity.class);
                search.putExtra(NiceCourseActivity.QUERY_PARAM_KEY, JSON.toJSONString(collectParam()));
                startActivity(search);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://course category
                getPickCategoryDialog().show();
                break;
            case 1: //teach location
                getPickLocationDialog().show();
                break;
            case 2: //teach_method
                getPickTeachMethodDialog().show();
                break;
            case 3: //teacher_sex
                getPickSexDialog().show();
                break;
            default: //appoint_date
                getDatePickDialog().show();
                break;
        }
    }

    private MultiPickerDialog getPickLocationDialog() {
        if (pickLocationDialog == null) {
            pickLocationDialog = new MultiPickerDialog(this, getString(R.string.course_category), R.array.locations);
            pickLocationDialog.setOnItemsCheckedListener(new MultiPickerDialog.OnItemsCheckedListener() {
                @Override
                public void onItemsChecked(Set<String> items, String itemsForString) {
                    selectedLocations = items;
                    conditions.get(1).conditionText = itemsForString;
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        pickLocationDialog.setSelectedItems(selectedLocations);
        return pickLocationDialog;
    }


    private MultiPickerDialog getPickCategoryDialog() {
        if (pickCategoryDialog == null) {
            pickCategoryDialog = new MultiPickerDialog(this, getString(R.string.course_category), R.array.course_categories);
            pickCategoryDialog.setOnItemsCheckedListener(new MultiPickerDialog.OnItemsCheckedListener() {
                @Override
                public void onItemsChecked(Set<String> items, String itemsForString) {
                    selectedCategories = items;
                    conditions.get(0).conditionText = itemsForString;
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        pickCategoryDialog.setSelectedItems(selectedCategories);
        return pickCategoryDialog;
    }

    private SinglePickerDialog getPickSexDialog() {
        if (pickSexDialog == null) {
            pickSexDialog = new SinglePickerDialog(this, getString(R.string.title_teach_sex), R.array.sex_select_arr);
            pickSexDialog.setOnItemPickedListener(new SinglePickerDialog.OnItemPickedListener() {
                @Override
                public void onItemPicked(String item) {
                    selectedSex = item;
                    conditions.get(3).conditionText = item;
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        pickSexDialog.setSelectedItem(selectedSex);
        return pickSexDialog;
    }

    private DatePickerDialog getDatePickDialog() {
        Calendar calendar = Calendar.getInstance();
        Date initialDate = selectedDate == null ? new Date() : selectedDate;
        calendar.setTime(initialDate);
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                    conditions.get(4).conditionText = DateFormatUtils.format(selectedDate);
                    mAdapter.notifyDataSetChanged();

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getText(R.string.positive_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    datePickerDialog.dismiss();
                }
            });
        }

        return datePickerDialog;
    }

    private MultiPickerDialog getPickTeachMethodDialog() {
        if (pickTeachMethodDialog == null) {
            pickTeachMethodDialog = new MultiPickerDialog(this, getString(R.string.teach_method), R.array.teach_method_arr);
            pickTeachMethodDialog.setGridColumn(2);
            pickTeachMethodDialog.setOnItemsCheckedListener(
                    new MultiPickerDialog.OnItemsCheckedListener() {
                        @Override
                        public void onItemsChecked(Set<String> items, String itemsForString) {
                            selectedMethods = items;
                            conditions.get(2).conditionText = itemsForString;
                            mAdapter.notifyDataSetChanged();
                        }
                    }
            );
        }

        pickTeachMethodDialog.setSelectedItems(selectedMethods);
        return pickTeachMethodDialog;
    }

    private QueryParam collectParam() {
        QueryParam queryParam = new QueryParam();

        queryParam.setAppointmentTime(selectedDate);

        String locations = conditions.get(1).conditionText;
        if (!"不限".equals(locations)) {
            queryParam.setPlace(locations);
        }

        String sex = null;
        if ("男".equals(selectedSex)) {
            sex = User.SEX_MALE;
        } else if ("女".equals(selectedSex)) {
            sex = User.SEX_FEMALE;
        }
        queryParam.setSex(sex);

        String techerMethod = null;
        if (selectedMethods != null) {
            StringBuilder sb = new StringBuilder();
            for (String selectedMethod : selectedMethods) {
                sb.append(mTeacherMethodMap.get(selectedMethod)).append(",");
            }
            techerMethod = sb.toString();
        }
        queryParam.setTutoringWay(techerMethod);

        String categories = conditions.get(0).conditionText;
        if (!"不限".equals(categories)) {
            queryParam.setCourseCategory(categories);
        }

        Log.i("AppointActivity", "query param :" + JSON.toJSONString(queryParam));
        return queryParam;
    }
}
