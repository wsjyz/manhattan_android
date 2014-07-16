package com.ivan.android.manhattanenglish.app.core.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.home.StudentHomeActivity;
import com.ivan.android.manhattanenglish.app.core.home.TeacherHomeActivity;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.UserCache;
import com.viewpagerindicator.PageIndicator;


public class WelcomeActivity extends BaseActivity {

    private ViewPager mPager;

    private PageIndicator mIndicator;

    private PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (PageIndicator) findViewById(R.id.indicator);

        adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager);
    }


    public void toFirstPage() {
        finish();
        User currentUser = UserCache.getCurrentUser();
        Log.i("WelcomeActivity", "Current User:" + JSON.toJSONString(currentUser));
        if (currentUser != null && User.USER_TYPE_TEACHER.equals(currentUser.getType())) {
            navigate(TeacherHomeActivity.class);
        } else {
            navigate(StudentHomeActivity.class);
        }
    }


    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ImageFragment fragment = ImageFragment.newInstance(position);
            fragment.setmListener(new ImageFragment.OnEnterIconClickListener() {
                @Override
                public void onEnterIconClick(View view) {
                    toFirstPage();
                }
            });
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
