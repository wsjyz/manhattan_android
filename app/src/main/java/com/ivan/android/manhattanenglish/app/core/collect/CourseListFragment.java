package com.ivan.android.manhattanenglish.app.core.collect;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.appoint.AppointCourseLoader;
import com.ivan.android.manhattanenglish.app.core.audition.AuditionCourseLoader;
import com.ivan.android.manhattanenglish.app.core.collect.CollectCourseLoader;
import com.ivan.android.manhattanenglish.app.core.course.CourseListAdapter;
import com.ivan.android.manhattanenglish.app.remote.course.Course;

import java.util.ArrayList;
import java.util.List;


public class CourseListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Course>> {

    public static final String LOAD_TYPE = "TYPE";

    public static final String TYPE_COLLECT = "COLLECT";
    public static final String TYPE_APPOINT = "APPOINT";
    public static final String TYPE_AUDITION = "AUDITION";

    private OnCourseItemClickListener mListener;

    private CourseListAdapter mAdapter;

    public static CourseListFragment newInstance(String type) {
        CourseListFragment fragment = new CourseListFragment();
        Bundle args = new Bundle();
        args.putString(LOAD_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public CourseListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(getText(R.string.empty_text));
        mAdapter = new CourseListAdapter(getActivity(), new ArrayList<Course>());

        setListAdapter(mAdapter);
        setListShown(false);

        getLoaderManager().initLoader(0, getArguments(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCourseItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            Course course = (Course) mAdapter.getItem(position);
            mListener.onCourseItemClick(course.getCourseId());
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (args != null) {
            String type = args.getString(LOAD_TYPE);
            if (TYPE_APPOINT.equals(type)) {
                return new AppointCourseLoader(getActivity());
            } else if (TYPE_AUDITION.equals(type)) {
                return new AuditionCourseLoader(getActivity());
            }
        }
        return new CollectCourseLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Course>> loader, List<Course> data) {
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.clear();
    }

    public interface OnCourseItemClickListener {

        public void onCourseItemClick(String courseId);
    }

}
