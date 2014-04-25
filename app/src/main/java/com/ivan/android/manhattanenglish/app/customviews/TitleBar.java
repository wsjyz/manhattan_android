package com.ivan.android.manhattanenglish.app.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-24
 * Time: PM8:22
 */
public class TitleBar extends LinearLayout {

    private Button mLeftButton;
    private TextView mTitleText;
    private Button mRightButton;

    private CharSequence mTitle;

    private Drawable mRightButtonDrawable;

    private CharSequence mRightButtonText;

    private boolean mShowLeftButton = true;

    private boolean mShowRightButton = true;

    private int mRightButtonPaddingLeft;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.title_bar, null);
        mLeftButton = (Button) view.findViewById(R.id.left_button);
        mTitleText = (TextView) view.findViewById(R.id.title_text);
        mRightButton = (Button) view.findViewById(R.id.right_button);


        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, 0, 0);

        try {
            mTitle = array.getText(R.styleable.TitleBar_titleText);
            mShowLeftButton = array.getBoolean(R.styleable.TitleBar_showLeft, true);
            mShowRightButton = array.getBoolean(R.styleable.TitleBar_showRight, true);
            mRightButtonText = array.getText(R.styleable.TitleBar_rightButtonText);
            mRightButtonDrawable = array.getDrawable(R.styleable.TitleBar_rightButtonIcon);
            mRightButtonPaddingLeft = array.getDimensionPixelSize(R.styleable.TitleBar_rightButtonPaddingLeft, 0);
            init();
        } finally {
            array.recycle();
        }

        int height = context.getResources().getDimensionPixelSize(R.dimen.title_bar_height);
        addView(view, ViewGroup.LayoutParams.MATCH_PARENT, height);

    }

    private void init() {
        mTitleText.setText(mTitle);

        if (mRightButtonDrawable != null) {
            mRightButton.setCompoundDrawablesWithIntrinsicBounds(mRightButtonDrawable, null, null, null);
        } else {
            mRightButton.setText(mRightButtonText);
        }

        mRightButton.setPadding(mRightButtonPaddingLeft, 0, 0, 0);
        mLeftButton.setVisibility(mShowLeftButton ? VISIBLE : INVISIBLE);
        mRightButton.setVisibility(mShowRightButton ? VISIBLE : INVISIBLE);

    }


    public void setTitleText(CharSequence text) {
        if (text == mTitle) return;
        mTitle = text;
        mTitleText.setText(text);
        invalidate();
        requestLayout();
    }

    public void setRightButtonText(CharSequence text) {
        if (text == mRightButtonText) return;
        mRightButtonText = text;
        mRightButton.setText(text);
        invalidate();
        requestLayout();
    }

    public void setRightButtonIcon(Drawable rightButtonDrawable) {
        if (rightButtonDrawable == mRightButtonDrawable) return;
        mRightButtonDrawable = rightButtonDrawable;
        if (rightButtonDrawable != null) {
            mRightButton.setCompoundDrawablesWithIntrinsicBounds(rightButtonDrawable, null, null, null);
        }
        invalidate();
        requestLayout();
    }

    public void setShowLeftButton(boolean showLeftButton) {
        if (mShowLeftButton == showLeftButton) return;
        mShowLeftButton = showLeftButton;
        mLeftButton.setVisibility(showLeftButton ? VISIBLE : INVISIBLE);
        invalidate();
        requestLayout();
    }

    public void setShowRightButton(boolean showRightButton) {
        if (mShowRightButton == showRightButton) return;
        mShowRightButton = showRightButton;
        mRightButton.setVisibility(showRightButton ? VISIBLE : INVISIBLE);
        invalidate();
        requestLayout();
    }


    public void setLeftButtonOnClickListener(OnClickListener listener) {
        mLeftButton.setOnClickListener(listener);
    }

    public void setRightButtonOnClickListener(OnClickListener listener) {
        mRightButton.setOnClickListener(listener);
    }

}
