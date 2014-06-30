package com.ivan.android.manhattanenglish.app.core.collect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;

import java.util.List;

/**
 * 我的学生列表
 *
 * @author: Ivan Vigoss
 * Date: 14-5-28
 * Time: AM10:01
 */
public class StudentListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {
    public static final String LOAD_TYPE = "TYPE";
    public static final String TYPE_ALL = "ALL";
    public static final String TYPE_APPOINT = "APPOINT";
    public static final String TYPE_AUDITION = "AUDITION";

    StudentListAdapter mAdapter;

    OnStudentListItemClickListener mListener;

    public static StudentListFragment newInstance(String type) {
        StudentListFragment fragment = new StudentListFragment();
        Bundle args = new Bundle();
        args.putString(LOAD_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new StudentListAdapter(getActivity());
        setListAdapter(mAdapter);

        setListShown(false);
        getLoaderManager().initLoader(0, getArguments(), this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnStudentListItemClickListener) activity;
        } catch (Exception e) {
            throw new ClassCastException("you must impl OnStudentListItemClickListener");
        }
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            String type = args.getString(LOAD_TYPE);
            if (TYPE_APPOINT.equals(type)) {
                return new AppointStudentListLoader(getActivity());
            } else if (TYPE_AUDITION.equals(type)) {
                return new AuditionStudentListLoader(getActivity());
            }
        }
        return new AllStudentListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        mAdapter.setData(data);
        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        User user = (User) mAdapter.getItem(position);
        if (mListener != null) {
            mListener.onStudentListItemClick(user);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        mAdapter.clear();
    }

    public static class AppointStudentListLoader extends CommonDataLoader<List<User>> {

        public AppointStudentListLoader(Context context) {
            super(context);
        }

        @Override
        public List<User> loadInBackground() {
            UserService userService = ServiceFactory.getService(UserService.class);
            return userService.loadAppointStudentList();
        }
    }

    public static class AuditionStudentListLoader extends CommonDataLoader<List<User>> {

        public AuditionStudentListLoader(Context context) {
            super(context);
        }

        @Override
        public List<User> loadInBackground() {
            UserService userService = ServiceFactory.getService(UserService.class);
            return userService.loadAuditionStudentList();
        }
    }

    public static class AllStudentListLoader extends CommonDataLoader<List<User>> {

        public AllStudentListLoader(Context context) {
            super(context);
        }

        @Override
        public List<User> loadInBackground() {
            UserService userService = ServiceFactory.getService(UserService.class);
            return userService.loadStudentList();
        }
    }

    public interface OnStudentListItemClickListener {
        void onStudentListItemClick(User student);
    }

}
