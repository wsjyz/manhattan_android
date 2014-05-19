package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.PickLocationDialog;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

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

    Set<String> selectedLocations;

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
                Toast.makeText(this, "course_category pressed", Toast.LENGTH_SHORT).show();
                break;
            case 1: //teach location
                getPickLocationDialog().show();
                break;
            case 2: //teach_method
                break;
            case 3: //teacher_sex
                break;
            default: //appoint_date
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

}
