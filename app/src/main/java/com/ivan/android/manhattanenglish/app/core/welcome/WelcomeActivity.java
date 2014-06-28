package com.ivan.android.manhattanenglish.app.core.welcome;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

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

    private static int[] imageRecourseIds = {R.drawable.girl, R.drawable.girl, R.drawable.girl};

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (PageIndicator) findViewById(R.id.indicator);

        adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);

        mIndicator.setViewPager(mPager);

        final Runnable toLoginJob = new Runnable() {
            @Override
            public void run() {
                toFirstPage();
            }
        };

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == imageRecourseIds.length - 1 && positionOffsetPixels == 0) {
                    //try to drag the last one
                    toFirstPage();
                    handler.removeCallbacks(toLoginJob);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageRecourseIds.length - 1) {
                    //enter next activity after 1500 ms
                    handler.postDelayed(toLoginJob, 1500);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void toFirstPage() {
        finish();
        if (UserCache.getCurrentUser() != null && User.USER_TYPE_TEACHER.equals(UserCache.getCurrentUser().getType())) {
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
            return ImageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return imageRecourseIds.length;
        }
    }

    public static class ImageFragment extends Fragment {

        public static ImageFragment newInstance(int position) {
            ImageFragment fragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            int position = getArguments().getInt("position");
            imageView.setImageResource(imageRecourseIds[position]);

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            layout.setGravity(Gravity.CENTER);
            layout.addView(imageView);
            return layout;

        }
    }


}
