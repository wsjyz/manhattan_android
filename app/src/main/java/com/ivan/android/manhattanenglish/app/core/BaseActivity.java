package com.ivan.android.manhattanenglish.app.core;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-7
 * Time: PM2:50
 */
public class BaseActivity extends ActionBarActivity {

    protected TitleBar titleBar;

    protected void navigate(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected View getEmptyView() {
        return LayoutInflater.from(this).inflate(R.layout.empty_view, null);
    }

    protected String getTextFromFormat(int resId, String... params) {
        return String.format(getText(resId).toString(), params);
    }

}
