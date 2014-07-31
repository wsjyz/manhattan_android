package com.ivan.android.manhattanenglish.app.core.collect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.appoint.AppointTeacherListLoader;
import com.ivan.android.manhattanenglish.app.core.audition.AuditionTeacherListLoader;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherListAdapter;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;

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
        setEmptyText(getText(R.string.empty_text));

        getLoaderManager().initLoader(1, getArguments(), this);

        String loadType = getArguments().getString(LOAD_TYPE);
        if (TYPE_COLLECT.equals(loadType)) {
            Log.i("TeacherListFragment", "registerForContextMenu.");
            registerForContextMenu(getListView());
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.question, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.i("TeacherListFragment", "onContextItemSelected.position = " + menuInfo.position);
        new CancelCollectTask(getActivity()).execute(menuInfo.position);
        return true;
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
            } else if (TYPE_AUDITION.equals(type)) {
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

    class CancelCollectTask extends CommonAsyncTask<Integer, Void, Void> {

        private int position;

        protected CancelCollectTask(Context context) {
            super(context);
        }

        @Override
        protected Void getResultInBackground(Integer... params) {
            position = params[0];
            TeacherDetail detail = (TeacherDetail) mAdapter.getItem(position);
            UserService userService = ServiceFactory.getService(UserService.class);
            userService.cancelCollect(detail.getTeacherId());
            return null;
        }

        @Override
        protected void onSuccess(Void aVoid) {
            super.onSuccess(aVoid);
            mAdapter.removeItemAt(position);
        }
    }


    public interface OnTeacherItemClickListener {
        void onTeacherItemClick(String teacherId);
    }
}
