package com.ivan.android.manhattanenglish.app.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-20
 * Time: AM9:40
 */
public class PickSexDialog extends AlertDialog {

    protected Button mPositiveButton;

    protected Context context;

    protected TextView mMaleText;

    protected TextView mFemaleText;

    protected String selectedSex;

    protected PickSexEvent mListener;

    protected String female;

    protected String male;


    public PickSexDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setSelectedSex(String selectedSex) {
        this.selectedSex = selectedSex;
    }

    public void refresh() {
        if (TextUtils.isEmpty(selectedSex)) {
            setTextViewSelected(mMaleText, false);
            setTextViewSelected(mFemaleText, false);
        } else if (male.equals(selectedSex)) {
            setTextViewSelected(mMaleText, true);
            setTextViewSelected(mFemaleText, false);
        } else {
            setTextViewSelected(mMaleText, false);
            setTextViewSelected(mFemaleText, true);
        }
    }

    public void setPickSexEvent(PickSexEvent mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_sex);

        female = context.getResources().getString(R.string.sex_female);
        male = context.getResources().getString(R.string.sex_male);

        mMaleText = (TextView) findViewById(R.id.male_text);
        mMaleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedSex(mMaleText.getText().toString());
                refresh();
            }
        });

        mFemaleText = (TextView) findViewById(R.id.female_text);
        mFemaleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedSex(mFemaleText.getText().toString());
                refresh();
            }
        });

        mPositiveButton = (Button) findViewById(R.id.positiveBtn);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onSexPicked(selectedSex);
                }
            }
        });

        refresh();

    }

    private void setTextViewSelected(TextView text, boolean selected) {
        if (selected) {
            text.setBackgroundResource(R.drawable.text_border_blue);
            text.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            text.setBackgroundResource(R.drawable.login_form_border);
            text.setTextColor(context.getResources().getColor(R.color.common_text_color));
        }

    }

    public interface PickSexEvent {
        void onSexPicked(String sex);
    }

}
