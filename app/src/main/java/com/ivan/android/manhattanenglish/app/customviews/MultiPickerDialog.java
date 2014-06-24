package com.ivan.android.manhattanenglish.app.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-19
 * Time: PM2:19
 */
public class MultiPickerDialog extends AlertDialog {

    protected TextView mTitle;

    protected LayoutInflater mInflater;

    protected GridView mGrid;

    protected MultiPickerGridAdapter mAdapter;

    protected Button mPositiveButton;

    protected Context context;

    protected String title;

    protected int mGridColumn = 3;

    private OnItemsCheckedListener mListener;


    public MultiPickerDialog(Context context, String title, int dataResId) {
        super(context);
        this.context = context;
        this.title = title;
        mAdapter = new MultiPickerGridAdapter(context, dataResId);
    }

    public void setSelectedItems(Set<String> selectedLocationSet) {
        if (selectedLocationSet == null) {
            selectedLocationSet = new HashSet<String>();
        }
        mAdapter.setSelectedItems(selectedLocationSet);
    }

    public void setOnItemsCheckedListener(OnItemsCheckedListener mListener) {
        this.mListener = mListener;
    }

    public void setGridColumn(int count) {
        if (count <= 0) return;
        this.mGridColumn = count;
    }


    public Set<String> getSelectedItems() {
        return mAdapter.getSelectedItems();
    }

    public String getSelectedItemsForString() {
        Set<String> items = getSelectedItems();
        String result = context.getString(R.string.search_no_limit);
        if (items != null && items.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String item : items) {
                sb.append(item).append(",");
            }

            if (sb.length() > 0) {
                result = sb.substring(0, sb.length() - 1);
            }
        }

        return result;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_location);
        mInflater = LayoutInflater.from(context);

        mTitle = (TextView) findViewById(R.id.dialog_title);
        mTitle.setText(title);

        mGrid = (GridView) findViewById(R.id.location_grid);
        mGrid.setNumColumns(mGridColumn);

        mGrid.setAdapter(mAdapter);

        mPositiveButton = (Button) findViewById(R.id.positiveBtn);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onItemsChecked(getSelectedItems(), getSelectedItemsForString());
                }
            }
        });
    }

    public interface OnItemsCheckedListener {
        void onItemsChecked(Set<String> items, String itemsForString);
    }
}
