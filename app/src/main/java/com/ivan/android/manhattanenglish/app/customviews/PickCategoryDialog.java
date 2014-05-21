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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-19
 * Time: PM2:19
 */
public class PickCategoryDialog extends AlertDialog implements AdapterView.OnItemClickListener {

    protected TextView mTitle;

    protected LayoutInflater mInflater;

    protected GridView mCategoryGrid;

    protected PickCategoryGridAdapter mAdapter;

    protected Button mPositiveButton;

    protected Context context;

    protected CategoryPickEvent mListener;

    public PickCategoryDialog(Context context) {
        super(context);
        this.context = context;
        mAdapter = new PickCategoryGridAdapter(context);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String category = (String) mAdapter.getItem(position);
        if (!category.equals(mAdapter.getSelectedCategory())) {
            mAdapter.setSelectedCategory(category);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setSelectedCategory(String category) {
        if (TextUtils.isEmpty(category)) return;
        mAdapter.setSelectedCategory(category);
    }

    public void setOnCategoryPicked(CategoryPickEvent mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_location);
        mInflater = LayoutInflater.from(context);

        mTitle = (TextView) findViewById(R.id.dialog_title);

        mCategoryGrid = (GridView) findViewById(R.id.location_grid);
        mCategoryGrid.setAdapter(mAdapter);
        mCategoryGrid.setOnItemClickListener(this);

        mPositiveButton = (Button) findViewById(R.id.positiveBtn);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onCategoryPicked(mAdapter.getSelectedCategory());
                }
            }
        });
    }

    public interface CategoryPickEvent {
        void onCategoryPicked(String category);
    }

}
