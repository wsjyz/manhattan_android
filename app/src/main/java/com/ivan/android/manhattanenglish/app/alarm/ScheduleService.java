package com.ivan.android.manhattanenglish.app.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-23
 * Time: AM10:49
 */
public class ScheduleService extends Service {

    private final IBinder mBinder = new ServiceBinder();

    public class ServiceBinder extends Binder {
        ScheduleService getService() {
            return ScheduleService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ScheduleService", "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    public void setAlarm(Calendar date) {
        Log.i("ScheduleService", "setAlarm at date :" + date);
        new Thread(new AlarmTask(this,date)).run();
    }
}
