package com.kassisdion.library;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ViewPagerWrapContent extends ViewPager {
    /*
    ** Constructor
     */
    public ViewPagerWrapContent(Context context) {
        super(context);
    }

    public ViewPagerWrapContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    ** Parent method
     */
    //Overrode onMeasure() for returning the child size
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
