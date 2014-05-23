package com.ivan.android.manhattanenglish.app.core.more;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.alarm.ScheduleClient;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.WeekdayArrayAdapter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends BaseActivity {


    ScheduleClient mScheduleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        CaldroidFragment.selectedBackgroundDrawable = R.drawable.drawable_light_yellow_circle_bg;
        WeekdayArrayAdapter.weekendTextColor = R.color.weekend_text_color;

        CaldroidCustomFragment caldroidFragment = new CaldroidCustomFragment();
        //设置周日、周六label的字体

        try {
            caldroidFragment.setSelectedDateStrings("2014-05-05", "2014-05-20", "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mScheduleClient = new ScheduleClient(this);
        mScheduleClient.doBindService();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 15);

        mScheduleClient.setAlarmNotification(calendar);

        transaction.replace(R.id.calendar_container, caldroidFragment).commit();
    }

    @Override
    protected void onStop() {
        if (mScheduleClient != null) {
            mScheduleClient.unBindService();
        }
        super.onStop();
    }
}
