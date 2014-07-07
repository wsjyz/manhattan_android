package com.ivan.android.manhattanenglish.app.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

/**
 * Created by ivan on 14-7-4.
 */
public class CustomDialog extends AlertDialog {

    protected TextView mTitle;

    protected LayoutInflater mInflater;

    protected Button mPositiveButton;

    protected RelativeLayout mContainer;

    protected Context context;

    protected String title;

    protected CustomDialog(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        mTitle = (TextView) findViewById(R.id.dialog_title);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }

        mContainer = (RelativeLayout) findViewById(R.id.content_container);

        mPositiveButton = (Button) findViewById(R.id.positiveBtn);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static class Builder{


    }


}
