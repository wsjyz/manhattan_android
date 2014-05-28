package com.ivan.android.manhattanenglish.app.core.collect;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.core.appoint.AppointTeacherListLoader;
import com.ivan.android.manhattanenglish.app.core.audition.AuditionTeacherListLoader;
import com.ivan.android.manhattanenglish.app.core.collect.CollectTeacherListLoader;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherListAdapter;
import com.ivan.android.manhattanenglish.app.remote.course.TeacherDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-13
 * Time: PM11:03
 */
public class TeacherListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<TeacherDetail>> {

    public static final String LOAD_TYPE = "TYPE";

    public static final String TYPE_COLLECT = "COLLECT";
    public static final String TYPE_APPOINT = "APPOINT";
    public static final String TYPE_AUDITION = "AUDITION";

    private OnTeacherItemClickListener mListener;

    private TeacherListAdapter mAdapter;

    public static TeacherListFragment newInstance(String type) {
        TeacherListFragment fragment = new TeacherListFragment();
        Bundle args = new Bundle();
        args.putString(LOAD_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTeacherItemClickListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTeacherItemClickListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListShown(false);
        mAdapter = new TeacherListAdapter(getActivity(), new ArrayList<TeacherDetail>());
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(1, getArguments(), this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (null != mListener) {
            TeacherDetail detail = (TeacherDetail) mAdapter.getItem(position);
            mListener.onTeacherItemClick(detail.getTeacherId());
        }
    }

    @Override
    public Loader<List<TeacherDetail>> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            String type = args.getString(LOAD_TYPE);
            if (TYPE_APPOINT.equals(type)) {
                return new AppointTeacherListLoader(getActivity());
            }else if(TYPE_AUDITION.equals(type)){
                return new AuditionTeacherListLoader(getActivity());
            }
        }
        return new CollectTeacherListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<TeacherDetail>> loader, List<TeacherDetail> data) {
        mAdapter.setData(data);

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<TeacherDetail>> loader) {
        mAdapter.clear();
    }


    public interface OnTeacherItemClickListener {
        void onTeacherItemClick(String teacherId);
    }
}
