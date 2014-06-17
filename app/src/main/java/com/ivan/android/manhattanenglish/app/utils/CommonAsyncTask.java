package com.ivan.android.manhattanenglish.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-10
 * Time: PM10:24
 */
public abstract class CommonAsyncTask<Params, Process, Result> extends AsyncTask<Params, Process, Result> {

    private Context context;

    private ProgressDialog progressDialog;

    protected CommonAsyncTask(Context context) {
        this.context = context;
    }

    private boolean hasError = false;

    private String errorMsg;

    protected void showLoadingDialog() {
        CharSequence message = context.getText(R.string.loading_text);
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(context, null, message, true, true, null);
        } else if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (progressDialog == null) return;
        progressDialog.dismiss();
    }

    protected abstract Result getResultInBackground(Params... params);

    @Override
    protected Result doInBackground(Params... params) {
        Result result = null;
        try {
            result = getResultInBackground(params);
        } catch (Exception e) {
            Log.e("CommonAsyncTask", "error when doing background job.", e);
            hasError = true;
            errorMsg = e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showLoadingDialog();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        hideLoadingDialog();
        if (hasError) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
