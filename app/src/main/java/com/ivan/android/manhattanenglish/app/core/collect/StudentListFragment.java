package com.ivan.android.manhattanenglish.app.core.collect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.user.User;

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

        setListShown(false);
        mAdapter = new StudentListAdapter(getActivity());
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, getArguments(), this);

    }


    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            String type = args.getString(LOAD_TYPE);
            if (TYPE_APPOINT.equals(type)) {
                return new AppointStudentListLoader(getActivity());
            }else if(TYPE_AUDITION.equals(type)){
                return new AuditionStudentListLoader(getActivity());
            }
        }
        return new AllStudentListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        mAdapter.clear();
    }


    public static interface OnStudentItemClickListener {
        void onItemClick(String studentId);
    }

    public class AppointStudentListLoader extends CommonDataLoader<List<User>> {

        public AppointStudentListLoader(Context context) {
            super(context);
        }

        @Override
        public List<User> loadInBackground() {
            //todo
            return null;
        }
    }

    public class AuditionStudentListLoader extends CommonDataLoader<List<User>> {

        public AuditionStudentListLoader(Context context) {
            super(context);
        }

        @Override
        public List<User> loadInBackground() {
            //todo
            return null;
        }
    }

    public class AllStudentListLoader extends CommonDataLoader<List<User>> {

        public AllStudentListLoader(Context context) {
            super(context);
        }

        @Override
        public List<User> loadInBackground() {
            return null;
        }
    }

}
