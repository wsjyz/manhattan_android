package com.ivan.android.manhattanenglish.app.core.more;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.DateFormatUtils;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.WeekdayArrayAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends BaseActivity {

    CaldroidCustomFragment caldroidFragment;

    TextView mNoticeMesssage;

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

        mNoticeMesssage = (TextView) findViewById(R.id.notice_message);

        CaldroidFragment.selectedBackgroundDrawable = R.drawable.drawable_light_yellow_circle_bg;
        WeekdayArrayAdapter.weekendTextColor = R.color.weekend_text_color;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        caldroidFragment = new CaldroidCustomFragment();
        transaction.replace(R.id.calendar_container, caldroidFragment).commit();

        new CourseScheduleLoadTask(this).execute();
    }

    public class CourseScheduleLoadTask extends CommonAsyncTask<Void, Void, List<Date>> {

        protected CourseScheduleLoadTask(Context context) {
            super(context);
        }

        @Override
        protected List<Date> getResultInBackground(Void... params) {
            UserService userService = ServiceFactory.getService(UserService.class);
            Pair<Date, Date> datePair = getDateRange();
            return userService.loadCourseSchedule(datePair.first, datePair.second);
        }

        @Override
        protected void onPostExecute(List<Date> dates) {
            super.onPostExecute(dates);
            if (dates == null || dates.isEmpty()) return;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dates.get(0));
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
            String weekdayText = DateFormatUtils.getWeekday(weekday);

            String dateText = DateFormatUtils.format(dates.get(0));

            mNoticeMesssage.setText(dateText+"(" + weekdayText + ") 有课！");

            caldroidFragment.setSelectedDates(dates);
            caldroidFragment.refreshView();
        }
    }


    private Pair<Date, Date> getDateRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date startTime = calendar.getTime();

        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        Date endTime = calendar.getTime();

        return Pair.create(startTime, endTime);
    }

}
