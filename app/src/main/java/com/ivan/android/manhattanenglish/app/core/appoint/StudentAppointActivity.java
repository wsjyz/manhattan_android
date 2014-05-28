package com.ivan.android.manhattanenglish.app.core.appoint;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.collect.CourseListFragment;
import com.ivan.android.manhattanenglish.app.core.collect.TeacherListFragment;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

/**
 * 学生---我的预约界面
 *
 * @author: Ivan Vigoss
 * Date: 14-5-13
 * Time: PM10:17
 */
public class StudentAppointActivity extends BaseActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    TextView courseTab;

    TextView teacherTab;

    Drawable selectedPointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_appoint);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        courseTab = (TextView) findViewById(R.id.student_tab);
        courseTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(0);
            }
        });

        teacherTab = (TextView) findViewById(R.id.teacher_tab);
        teacherTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(1);
            }
        });

        selectedPointer = getResources().getDrawable(R.drawable.tab_selected_pointer);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                markTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void selectItem(int position) {
        int currentItem = mViewPager.getCurrentItem();
        if (position == currentItem) return;
        mViewPager.setCurrentItem(position, true);
        markTab(position);
    }

    private void markTab(int position) {
        if (position == 0) {
            if (!hasBottomDrawable(courseTab)) {
                teacherTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                courseTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, selectedPointer);
            }

        } else {
            if (!hasBottomDrawable(teacherTab)) {
                courseTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                teacherTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, selectedPointer);
            }
        }
    }

    private boolean hasBottomDrawable(TextView tab) {
        Drawable[] drawables = tab.getCompoundDrawables();
        return drawables != null && drawables[3] != null;
    }


    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? CourseListFragment.newInstance(CourseListFragment.TYPE_APPOINT) : TeacherListFragment.newInstance(TeacherListFragment.TYPE_APPOINT);
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
