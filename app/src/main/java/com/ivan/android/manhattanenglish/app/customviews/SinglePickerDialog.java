package com.ivan.android.manhattanenglish.app.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-19
 * Time: PM2:19
 */
public class SinglePickerDialog extends AlertDialog implements AdapterView.OnItemClickListener {

    protected TextView mTitle;

    protected LayoutInflater mInflater;

    protected GridView mGrid;

    protected SinglePickerGridAdapter mAdapter;

    protected Button mPositiveButton;

    protected Context context;

    protected OnItemPickedListener mListener;

    protected String title;


    public SinglePickerDialog(Context context, String title, String[] data) {
        super(context);
        this.context = context;
        this.title = title;
        mAdapter = new SinglePickerGridAdapter(context, data);
    }

    public SinglePickerDialog(Context context, String title, int dataResId) {
        super(context);
        this.context = context;
        this.title = title;
        mAdapter = new SinglePickerGridAdapter(context, dataResId);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String category = (String) mAdapter.getItem(position);
        if (!category.equals(mAdapter.getSelectedItem())) {
            setSelectedItem(category);
            refreshView();
        }
    }

    public void setSelectedItem(String value) {
        if (TextUtils.isEmpty(value)) return;
        mAdapter.setSelectedItem(value);
    }

    public void refreshView() {
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemPickedListener(OnItemPickedListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_location);
        mInflater = LayoutInflater.from(context);

        mTitle = (TextView) findViewById(R.id.dialog_title);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }

        mGrid = (GridView) findViewById(R.id.location_grid);
        mGrid.setAdapter(mAdapter);
        mGrid.setOnItemClickListener(this);

        mPositiveButton = (Button) findViewById(R.id.positiveBtn);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onItemPicked(mAdapter.getSelectedItem());
                }
            }
        });
    }

    public interface OnItemPickedListener {
        void onItemPicked(String item);
    }

}
