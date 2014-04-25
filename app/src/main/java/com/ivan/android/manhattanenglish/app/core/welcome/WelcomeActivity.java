package com.ivan.android.manhattanenglish.app.core.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.ivan.android.manhattanenglish.app.core.login.LoginActivity;
import com.viewpagerindicator.PageIndicator;


public class WelcomeActivity extends FragmentActivity {

    private ViewPager mPager;

    private PageIndicator mIndicator;

    private PagerAdapter adapter;

    private int[] imageRecourseIds = {R.drawable.girl, R.drawable.girl, R.drawable.girl};

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (PageIndicator) findViewById(R.id.indicator);

        adapter = new WelcomePagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(adapter);

        mIndicator.setViewPager(mPager);

        final Runnable toLoginJob = new Runnable() {
            @Override
            public void run() {
                toLogin();
            }
        };

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == imageRecourseIds.length - 1 && positionOffsetPixels == 0) {
                    //try to drag the last one
                    toLogin();
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


    public void toLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }


    class WelcomePagerAdapter extends FragmentPagerAdapter {
        private Context mContext;

        public WelcomePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return new ImageFragment(mContext, position);
        }

        @Override
        public int getCount() {
            return imageRecourseIds.length;
        }
    }

    class ImageFragment extends Fragment {
        private Context context;
        private int position;

        ImageFragment(Context context, int position) {
            this.context = context;
            this.position = position;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageRecourseIds[position]);

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            layout.setGravity(Gravity.CENTER);
            layout.addView(imageView);
            return layout;

        }
    }


}
