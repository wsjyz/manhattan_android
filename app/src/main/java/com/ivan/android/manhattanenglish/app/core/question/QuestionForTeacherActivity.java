package com.ivan.android.manhattanenglish.app.core.question;

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
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.question.Question;

public class QuestionForTeacherActivity extends BaseActivity implements QuestionListFragment.OnQuestionItemClickListener {


    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    TextView mAssignedTab;

    TextView mAnsweredTab;

    TextView mNotAnsweredTab;

    Drawable selectedPointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_for_teacher);

        int currentItem = 0;
        if (savedInstanceState != null) {
            currentItem = savedInstanceState.getInt("currentItem", 0);
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selectedPointer = getResources().getDrawable(R.drawable.tab_selected_pointer);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mAssignedTab = (TextView) findViewById(R.id.assigned_tab);
        mAssignedTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(0);
            }
        });

        mAnsweredTab = (TextView) findViewById(R.id.answered_tab);
        mAnsweredTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(1);
            }
        });

        mNotAnsweredTab = (TextView) findViewById(R.id.not_answered_tab);
        mNotAnsweredTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(2);
            }
        });

        selectItem(currentItem);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentItem", mViewPager.getCurrentItem());
    }

    private void selectItem(int position) {
        int currentItem = mViewPager.getCurrentItem();
        if (position == currentItem) return;
        mViewPager.setCurrentItem(position, true);
        markTab(position);
    }

    private void markTab(int position) {
        //deselect all
        deselect(mAssignedTab);
        deselect(mAnsweredTab);
        deselect(mNotAnsweredTab);
        //make a select
        switch (position) {
            case 1:
                select(mAnsweredTab);
                break;
            case 2:
                select(mNotAnsweredTab);
                break;
            default:
                select(mAssignedTab);
                break;
        }

    }

    private void select(TextView view) {
        view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, selectedPointer);
    }

    private void deselect(TextView view) {
        view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
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
            QuestionListFragment fragment = QuestionListFragment.newInstance(position);
            fragment.setOnQuestionItemClickListener(QuestionForTeacherActivity.this);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onQuestionItemClick(Question question) {
        int position = mViewPager.getCurrentItem();
        switch (position) {
            case 1: //已回答

                break;
            default: //指定回答、未回答

                break;
        }
    }
}
