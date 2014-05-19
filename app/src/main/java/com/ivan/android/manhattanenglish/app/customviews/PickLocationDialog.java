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
public class PickLocationDialog extends AlertDialog {

    protected TextView mTitle;

    protected LayoutInflater mInflater;

    protected GridView mLocationGrid;

    protected PickLocationGridAdapter mAdapter;

    protected Button mPositiveButton;

    protected Context context;

    protected LocationPickEvent mListener;

    public PickLocationDialog(Context context) {
        super(context);
        this.context = context;
        mAdapter = new PickLocationGridAdapter(context);
    }

    public void setSelectedLocationSet(Set<String> selectedLocationSet) {
        if(selectedLocationSet == null){
            selectedLocationSet = new HashSet<String>();
        }
        mAdapter.setSelectedLocationSet(selectedLocationSet);
    }

    public void setOnLocationPicked(LocationPickEvent mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_location);
        mInflater = LayoutInflater.from(context);

        mTitle = (TextView) findViewById(R.id.dialog_title);

        mLocationGrid = (GridView) findViewById(R.id.location_grid);

        mLocationGrid.setAdapter(mAdapter);

        mPositiveButton = (Button) findViewById(R.id.positiveBtn);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onLocationPicked(mAdapter.getSelectedLocationSet());
                }

            }
        });
    }

    public interface LocationPickEvent {
        void onLocationPicked(Set<String> locations);
    }

}
