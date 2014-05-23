package com.ivan.android.manhattanenglish.app.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-23
 * Time: AM10:28
 */
public class AlarmTask implements Runnable {
    private Calendar date;

    private AlarmManager am;

    private Context context;

    public AlarmTask(Context context, Calendar date) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
    }

    @Override
    public void run() {
        Intent intent = new Intent(context, NoticeService.class);
        intent.putExtra(NoticeService.INTENT_NOTIFY, true);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("AlarmTask", "set Alarm Task fired at " + date.getTimeInMillis());
        am.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);

    }
}
