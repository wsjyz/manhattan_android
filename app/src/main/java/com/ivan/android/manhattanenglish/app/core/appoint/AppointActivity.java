package com.ivan.android.manhattanenglish.app.core.appoint;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.PickCategoryDialog;
import com.ivan.android.manhattanenglish.app.customviews.PickLocationDialog;
import com.ivan.android.manhattanenglish.app.customviews.PickSexDialog;
import com.ivan.android.manhattanenglish.app.customviews.PickTeachMethodDialog;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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

    PickLocationDialog pickLocationDialog;

    PickCategoryDialog pickCategoryDialog;

    PickSexDialog pickSexDialog;

    PickTeachMethodDialog pickTeachMethodDialog;

    DatePickerDialog datePickerDialog;

    Set<String> selectedLocations;

    String selectedCategory;

    String selectedSex;

    String selecteMethod;

    Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);

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
        searchListView.setAdapter(new SearchListAdapter(this, conditions));
        searchListView.setOnItemClickListener(this);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
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

    private PickLocationDialog getPickLocationDialog() {
        if (pickLocationDialog == null) {
            pickLocationDialog = new PickLocationDialog(this);
            pickLocationDialog.setOnLocationPicked(new PickLocationDialog.LocationPickEvent() {
                @Override
                public void onLocationPicked(Set<String> locations) {
                    selectedLocations = locations;
                }
            });
        }
        pickLocationDialog.setSelectedLocationSet(selectedLocations);
        return pickLocationDialog;
    }


    private PickCategoryDialog getPickCategoryDialog() {
        if (pickCategoryDialog == null) {
            pickCategoryDialog = new PickCategoryDialog(this);
            pickCategoryDialog.setOnCategoryPicked(new PickCategoryDialog.CategoryPickEvent() {
                @Override
                public void onCategoryPicked(String category) {
                    selectedCategory = category;
                }
            });
        }
        pickCategoryDialog.setSelectedCategory(selectedCategory);
        return pickCategoryDialog;
    }

    private PickSexDialog getPickSexDialog() {
        if (pickSexDialog == null) {
            pickSexDialog = new PickSexDialog(this);
            pickSexDialog.setPickSexEvent(new PickSexDialog.PickSexEvent() {
                @Override
                public void onSexPicked(String sex) {
                    selectedSex = sex;
                }
            });
        }

        pickSexDialog.setSelectedSex(selectedSex);
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

    private PickTeachMethodDialog getPickTeachMethodDialog() {
        if (pickTeachMethodDialog == null) {
            pickTeachMethodDialog = new PickTeachMethodDialog(this);
            pickTeachMethodDialog.setPickMethodEvent(new PickTeachMethodDialog.PickMethodEvent() {
                @Override
                public void onMethodPicked(String method) {
                    selecteMethod = method;
                }
            });
        }

        pickTeachMethodDialog.setSelectedMethod(selecteMethod);
        return pickTeachMethodDialog;
    }
}
