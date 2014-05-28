package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

/**
 * 课程详情----点击预约之后的界面
 */
public class AppointCourseActivity extends BaseActivity {

    AppointContactFragment mFragment;

    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_course);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFragment = (AppointContactFragment) getSupportFragmentManager().findFragmentById(R.id.appoint_form);

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo submit
            }
        });
    }

}
