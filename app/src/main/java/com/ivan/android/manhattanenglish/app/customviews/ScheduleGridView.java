package com.ivan.android.manhattanenglish.app.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.GridView;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-25
 * Time: PM1:59
 */
public class ScheduleGridView extends GridView {

    public ScheduleGridView(Context context) {
        super(context);
    }

    public ScheduleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScheduleGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();

        //定义从背景图片获取的size
        final float bgWidth = 682;
        final float bgHeight = 369;
        final float itemWidth = 84;
        final float paddingTop = bgHeight - itemWidth * 3;
        final float paddingLeft = bgWidth - itemWidth * 7;

        //获得GridView的实际宽度（px），并按照图片的宽高比计算出实际高度
        float actualWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, getMeasuredWidth(), dm);
        float fixedHeight = bgHeight * actualWidth / bgWidth;

        //等比计算出GridView的偏移量以及列宽
        float fixedPaddingTop = paddingTop * fixedHeight / bgHeight;
        float fixedPaddingLeft = paddingLeft * actualWidth / bgWidth;
        float fixedColumnWidth = itemWidth * actualWidth / bgWidth;

        //设置PaddingLeft的偏移量，因为小数点等问题，导致PaddingLeft计算偏差
        float offset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, dm);

        setPadding((int) (fixedPaddingLeft + offset), (int) fixedPaddingTop, 0, 0);
        setColumnWidth((int) fixedColumnWidth);

        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) fixedHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
