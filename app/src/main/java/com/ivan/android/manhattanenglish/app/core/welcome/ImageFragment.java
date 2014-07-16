package com.ivan.android.manhattanenglish.app.core.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ivan.android.manhattanenglish.app.R;


public class ImageFragment extends Fragment {

    OnEnterIconClickListener mListener;

    private static int[] imageRecourseIds = {R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3};

    public static ImageFragment newInstance(int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int position = getArguments().getInt("position");

        View view = inflater.inflate(R.layout.fragment_welcom, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.welcome_image);
        imageView.setImageResource(imageRecourseIds[position]);

        if (position == imageRecourseIds.length - 1) {
            ImageView enterBtn = (ImageView) view.findViewById(R.id.enter_icon);
            enterBtn.setVisibility(View.VISIBLE);
            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onEnterIconClick(v);
                    }
                }
            });
        }
        return view;
    }

    public OnEnterIconClickListener getmListener() {
        return mListener;
    }

    public void setmListener(OnEnterIconClickListener mListener) {
        this.mListener = mListener;
    }

    public static int[] getImageRecourseIds() {
        return imageRecourseIds;
    }

    public static void setImageRecourseIds(int[] imageRecourseIds) {
        ImageFragment.imageRecourseIds = imageRecourseIds;
    }

    public interface OnEnterIconClickListener {
        void onEnterIconClick(View view);
    }
}
