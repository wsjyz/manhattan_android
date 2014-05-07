package com.ivan.android.manhattanenglish.app.core.login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

public class RegisterActivity extends BaseActivity implements StudentRegisterFragment.RegisterListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    TextView studentTab;

    TextView teacherTab;

    Drawable selectedPointer;

    TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        studentTab = (TextView) findViewById(R.id.student_tab);
        studentTab.setOnClickListener(new View.OnClickListener() {
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
            if (!hasBottomDrawable(studentTab)) {
                teacherTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                studentTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, selectedPointer);
            }

        } else {
            if (!hasBottomDrawable(teacherTab)) {
                studentTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                teacherTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, selectedPointer);
            }
        }
    }

    private boolean hasBottomDrawable(TextView tab) {
        Drawable[] drawables = tab.getCompoundDrawables();
        return drawables != null && drawables[3] != null;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new StudentRegisterFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

    }


    @Override
    public void sendAuthCode(String tel) {
        //todo
    }

    @Override
    public void register(String tel, String password, String authCode) {
        //todo
    }

    @Override
    public void beVip(String tel, String password, String authCode) {
        //todo
    }
}
