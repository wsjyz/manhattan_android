package com.ivan.android.manhattanenglish.app.core.course;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.remote.course.Course;

import java.util.Collections;
import java.util.List;

/**
 * 课程的ListFragment，参数
 *
 * @author: Ivan Vigoss
 * Date: 14-6-16
 * Time: AM10:33
 */
public class CourseListFragment extends ListFragment {

    public static final String COURSE_LIST_KEY = "COURSE_LIST";

    CourseListAdapter mAdapter;

    CourseItemClickListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Course> courseList = Collections.emptyList();
        Bundle arg = getArguments();
        if (arg != null) {
            String courseListJson = arg.getString(COURSE_LIST_KEY);
            if (!TextUtils.isEmpty(courseListJson)) {
                courseList = JSON.parseArray(courseListJson, Course.class);
            }
        }

        mAdapter = new CourseListAdapter(getActivity(), courseList);
        setListAdapter(mAdapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CourseItemClickListener) activity;
        } catch (Exception e) {
            throw new ClassCastException("must impl CourseListItemClickListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mListener != null) {
            Course course = (Course) mAdapter.getItem(position);
            mListener.onCourseItemClick(course, position);
        }
    }

    public static interface CourseItemClickListener {
        void onCourseItemClick(Course course, int position);
    }

}
