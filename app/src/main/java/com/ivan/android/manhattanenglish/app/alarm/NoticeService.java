package com.ivan.android.manhattanenglish.app.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-23
 * Time: AM10:30
 */
public class NoticeService extends Service {

    private final static int NOTIFICATION_ID = 0;

    public final static String INTENT_NOTIFY = "com.ivan.android.manhattanenglish.app.alarm.NoticeService.INTENT_NOTIFY";

    private NotificationManager nm;

    private IBinder mBinder = new ServiceBinder();

    public class ServiceBinder extends Binder {
        NoticeService getService() {
            return NoticeService.this;
        }
    }

    @Override
    public void onCreate() {
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
            showNotification();
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Hello,World!").setContentText("This is just a test")
                .setSmallIcon(R.drawable.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL )
                .setAutoCancel(true);
        //todo pending Intent

        nm.notify(NOTIFICATION_ID, builder.build());
        stopSelf();
    }
}
