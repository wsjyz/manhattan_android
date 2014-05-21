package com.ivan.android.manhattanenglish.app.core.purchase;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.ivan.android.manhattanenglish.app.remote.purchase.PurchaseHistory;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-21
 * Time: AM11:19
 */
public class PurchaseHistoryFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<PurchaseHistory>> {



    @Override
    public Loader<List<PurchaseHistory>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<PurchaseHistory>> loader, List<PurchaseHistory> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<PurchaseHistory>> loader) {

    }
}
