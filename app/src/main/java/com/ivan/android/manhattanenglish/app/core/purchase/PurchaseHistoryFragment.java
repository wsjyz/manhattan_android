package com.ivan.android.manhattanenglish.app.core.purchase;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.purchase.PurchaseHistory;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-21
 * Time: AM11:19
 */
public class PurchaseHistoryFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<PurchaseHistory>> {

    PurchaseHistoryAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(false);
        setEmptyText(getText(R.string.history_empty_text));

        mAdapter = new PurchaseHistoryAdapter(getActivity());
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<PurchaseHistory>> onCreateLoader(int id, Bundle args) {
        return new PurchaseHistoryLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<PurchaseHistory>> loader, List<PurchaseHistory> data) {
        mAdapter.setData(data);

        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<PurchaseHistory>> loader) {
        mAdapter.clear();
    }


    public static class PurchaseHistoryLoader extends CommonDataLoader<List<PurchaseHistory>> {

        public PurchaseHistoryLoader(Context context) {
            super(context);
        }

        @Override
        public List<PurchaseHistory> loadInBackground() {
            //todo load from server
            return null;
        }
    }
}
