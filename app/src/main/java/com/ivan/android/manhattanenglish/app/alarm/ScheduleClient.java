package com.ivan.android.manhattanenglish.app.alarm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-23
 * Time: AM10:54
 */
public class ScheduleClient {

    private ScheduleService mBoundService;

    private Context mContext;

    private boolean mIsBound;

    private List<Calendar> mWorks = new ArrayList<Calendar>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (Calendar date : mWorks) {
                mBoundService.setAlarm(date);
            }
            mWorks.clear();
        }
    };

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((ScheduleService.ServiceBinder) service).getService();
            mHandler.sendEmptyMessage(1);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

    public ScheduleClient(Context mContext) {
        this.mContext = mContext;
    }

    public void doBindService() {
        mContext.bindService(new Intent(mContext, ScheduleService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void setAlarmNotification(Calendar date) {
        if (mBoundService != null) {
            mBoundService.setAlarm(date);
        } else { //waiting for service created
            mWorks.add(date);
        }
    }

    public void setAlarmNotification(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setAlarmNotification(calendar);
    }

    public void unBindService() {
        if (mIsBound) {
            mContext.unbindService(serviceConnection);
            mIsBound = false;
        }
    }


}
