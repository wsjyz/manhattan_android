package com.ivan.android.manhattanenglish.app.core;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-14
 * Time: PM11:17
 */
public abstract class CommonDataLoader<T> extends AsyncTaskLoader<T> {

    protected T mData;

    public CommonDataLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(T data) {
        mData = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }
}
