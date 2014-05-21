package com.ivan.android.manhattanenglish.app.core.purchase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;

import java.math.BigDecimal;

public class StudentPurchaseActivity extends BaseActivity {

    TextView mRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_purchase);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 充值
            }
        });

        mRecharge = (TextView) findViewById(R.id.recharge_text);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.container) == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            PurchaseHistoryFragment fragment = new PurchaseHistoryFragment();
            transaction.add(R.id.container, fragment).commit();
        }

        new RechargeLoadTask().execute();
    }


    private class RechargeLoadTask extends AsyncTask<Void, Void, BigDecimal> {

        @Override
        protected BigDecimal doInBackground(Void... params) {
            //todo load from server
            return null;
        }

        @Override
        protected void onPostExecute(BigDecimal bigDecimal) {
            if (bigDecimal == null) {
                Toast.makeText(StudentPurchaseActivity.this, R.string.load_failed, Toast.LENGTH_SHORT).show();
            } else {
                String rechargeText = String.format(getResources().getString(R.string.pattern_balance), bigDecimal);
                mRecharge.setText(rechargeText);
            }
        }
    }

}
