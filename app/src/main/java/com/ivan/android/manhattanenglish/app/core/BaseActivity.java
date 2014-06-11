package com.ivan.android.manhattanenglish.app.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.course.CourseActivity;
import com.ivan.android.manhattanenglish.app.core.course.CourseDetailActivity;
import com.ivan.android.manhattanenglish.app.core.home.StudentHomeActivity;
import com.ivan.android.manhattanenglish.app.core.info.InfomationActivity;
import com.ivan.android.manhattanenglish.app.core.login.FindPasswordActivity;
import com.ivan.android.manhattanenglish.app.core.login.LoginActivity;
import com.ivan.android.manhattanenglish.app.core.login.RegisterActivity;
import com.ivan.android.manhattanenglish.app.core.more.AboutUsActivity;
import com.ivan.android.manhattanenglish.app.core.more.ContactUsActivity;
import com.ivan.android.manhattanenglish.app.core.more.MoreInfoActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherDetailInfoActivity;
import com.ivan.android.manhattanenglish.app.core.welcome.WelcomeActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-7
 * Time: PM2:50
 */
public class BaseActivity extends FragmentActivity {

    protected TitleBar titleBar;

    private ProgressDialog progressDialog;

    private Set<Class<? extends Activity>> openActivitySet;

    private Set<Class<? extends Activity>> getOpenActivitySet() {
        if (openActivitySet == null) {
            openActivitySet = new HashSet<Class<? extends Activity>>();

            openActivitySet.add(WelcomeActivity.class);
            openActivitySet.add(LoginActivity.class);
            openActivitySet.add(RegisterActivity.class);
            openActivitySet.add(FindPasswordActivity.class);

            openActivitySet.add(StudentHomeActivity.class);
            openActivitySet.add(InfomationActivity.class);

            openActivitySet.add(AboutUsActivity.class);
            openActivitySet.add(ContactUsActivity.class);
            openActivitySet.add(MoreInfoActivity.class);
            openActivitySet.add(WelcomeActivity.class);

            openActivitySet.add(CourseActivity.class);
            openActivitySet.add(CourseDetailActivity.class);
            openActivitySet.add(TeacherActivity.class);
            openActivitySet.add(TeacherDetailInfoActivity.class);

        }
        return openActivitySet;
    }

    protected boolean needLogin(Class<? extends Activity> activity) {
        if (getOpenActivitySet().contains(activity)) {
            return false;
        } else if (UserCache.getCurrentUser() != null) {
            return false;
        }

        return true;
    }


    protected void navigate(Class<? extends Activity> activity) {
        Intent intent;
        if (needLogin(activity)) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, activity);
        }
        startActivity(intent);
    }

    protected View getEmptyView() {
        return LayoutInflater.from(this).inflate(R.layout.empty_view, null);
    }

    protected String getTextFromFormat(int resId, String... params) {
        return String.format(getText(resId).toString(), params);
    }


    protected void showLoadingDialog() {
        CharSequence message = getText(R.string.loading_text);
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, null, message, true, true, null);
        } else if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (progressDialog == null) return;
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog = null;
    }
}
