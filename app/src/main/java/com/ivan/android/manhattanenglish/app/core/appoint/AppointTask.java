package com.ivan.android.manhattanenglish.app.core.appoint;

import android.content.Context;

import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.course.Appointment;
import com.ivan.android.manhattanenglish.app.remote.course.CourseService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-23
 * Time: PM1:38
 */
public class AppointTask extends CommonAsyncTask<Appointment, Void, Void> {

    Runnable onSuccessCallback;

    public AppointTask(Context context, Runnable onSuccessCallback) {
        super(context);
        this.onSuccessCallback = onSuccessCallback;
    }

    @Override
    protected Void getResultInBackground(Appointment... params) {
        Appointment appointment = params[0];
        CourseService courseService = ServiceFactory.getService(CourseService.class);
        courseService.submitAppointment(appointment);
        return null;
    }

    @Override
    protected void onSuccess(Void aVoid) {
        super.onSuccess(aVoid);
        if (onSuccessCallback != null) {
            onSuccessCallback.run();
        }
    }

    public Runnable getOnSuccessCallback() {
        return onSuccessCallback;
    }

    public void setOnSuccessCallback(Runnable onSuccessCallback) {
        this.onSuccessCallback = onSuccessCallback;
    }
}
