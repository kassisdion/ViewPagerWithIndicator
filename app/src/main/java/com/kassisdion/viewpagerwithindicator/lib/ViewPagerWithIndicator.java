package com.kassisdion.viewpagerwithindicator.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kassisdion.viewpagerwithindicator.R;

import org.w3c.dom.Text;

public class ViewPagerWithIndicator extends LinearLayout {

    private final Boolean mArrowEnable;
    private int mArrowWidth;
    private int mArrowHeight;
    private int mRightArrowDrawable = R.drawable.ic_action_arrow_right;
    private int mLeftArrowDrawable = R.drawable.ic_action_arrow_left;

    private final Boolean mRoundEnable;
    private int mRoundSize;
    private int mRoundDrawable = R.drawable.background_rounded;
    private int mRoundDefaultColor;
    private int mRoundSelectedColor;

    private ViewPager mViewPager = null;

    private ImageView mRightArrow = null;
    private ImageView mLeftArrow = null;

    private LinearLayout roundedIndicatorContainer = null;
    private LinearLayout arrowContainer = null;

    /*
    ** Constructor
     */
    public ViewPagerWithIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerWithIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewPagerWithIndicator, 0, 0);
        try {
            mArrowEnable = a.getBoolean(R.styleable.ViewPagerWithIndicator_arrow_enable,  true);
            if (mArrowEnable) {
                mArrowWidth = a.getLayoutDimension(R.styleable.ViewPagerWithIndicator_arrow_width,  ViewGroup.LayoutParams.WRAP_CONTENT);
                mArrowHeight = a.getLayoutDimension(R.styleable.ViewPagerWithIndicator_arrow_height, ViewGroup.LayoutParams.WRAP_CONTENT);
                mRightArrowDrawable = a.getResourceId(R.styleable.ViewPagerWithIndicator_right_arrow_drawable,  R.drawable.ic_action_arrow_right);
                mLeftArrowDrawable = a.getResourceId(R.styleable.ViewPagerWithIndicator_left_arrow_drawable,  R.drawable.ic_action_arrow_left);
            }

            mRoundEnable = a.getBoolean(R.styleable.ViewPagerWithIndicator_round_enable, true);
            if (mRoundEnable) {
                mRoundSize = a.getLayoutDimension(R.styleable.ViewPagerWithIndicator_round_size,  ViewGroup.LayoutParams.WRAP_CONTENT);
                mRoundDrawable = a.getResourceId(R.styleable.ViewPagerWithIndicator_round_drawable,  R.drawable.background_rounded);
                mRoundDefaultColor = a.getColor(R.styleable.ViewPagerWithIndicator_round_color_default, Color.TRANSPARENT);
                mRoundSelectedColor = a.getColor(R.styleable.ViewPagerWithIndicator_round_color_selected, Color.BLUE);
            }

        } finally {
            a.recycle();
        }

        init();
    }

    /*
    ** Public method
     */
    public void setViewPager(@NonNull final ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        mViewPager = viewPager;

        populateView();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mArrowEnable) {
                    updateArrowVisibility();
                }
                if (mRoundEnable) {
                    updateRoundIndicator();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mLeftArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnFirstPage()) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            }
        });
        mRightArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnLastPage()) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            }
        });
    }

    /*
    ** Private method
     */
    private void init() {
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);

        if (mArrowEnable) {
            //Init arrow
            mLeftArrow = new ImageView(getContext());
            mRightArrow = new ImageView(getContext());

            mLeftArrow.setImageResource(mLeftArrowDrawable);
            mRightArrow.setImageResource(mRightArrowDrawable);

            mRightArrow.setLayoutParams(new LinearLayout.LayoutParams(mArrowWidth, mArrowHeight));
            mLeftArrow.setLayoutParams(new LinearLayout.LayoutParams(mArrowWidth, mArrowHeight));

            //Init arrow+viewPager container
            arrowContainer = new LinearLayout(getContext());
            arrowContainer.setOrientation(LinearLayout.HORIZONTAL);
            arrowContainer.setGravity(Gravity.CENTER);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            arrowContainer.setLayoutParams(params);

            this.addView(arrowContainer);
        }

        if (mRoundEnable) {
            //Init roundedIndicator container
            roundedIndicatorContainer = new LinearLayout(getContext());
            roundedIndicatorContainer.setOrientation(LinearLayout.HORIZONTAL);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            roundedIndicatorContainer.setLayoutParams(params);

            this.addView(roundedIndicatorContainer);
        }
    }

    private void populateView() {
        if (mArrowEnable) {
            //Populate arrow+viewPager container
            ((ViewGroup)mViewPager.getParent()).removeView(mViewPager);
            ((LayoutParams) mViewPager.getLayoutParams()).weight = 1;

            arrowContainer.addView(mLeftArrow);
            arrowContainer.addView(mViewPager);
            arrowContainer.addView(mRightArrow);

            updateArrowVisibility();
        }

        if (mRoundEnable) {
            //populate roundedIndicator container
            LayoutParams params = new LayoutParams(mRoundSize, mRoundSize);

            for (int i=0; i < mViewPager.getAdapter().getCount(); i++) {
                ImageView roundIndicator = new ImageView(getContext());
                roundIndicator.setBackgroundResource(mRoundDrawable);
                roundIndicator.setLayoutParams(params);

                roundedIndicatorContainer.addView(roundIndicator);
            }
            updateRoundIndicator();
        }
    }

    /*
    ** Utils
     */
    private void updateArrowVisibility() {
        if (isOnFirstPage()) {
            mLeftArrow.setVisibility(View.INVISIBLE);
        } else {
            mLeftArrow.setVisibility(View.VISIBLE);
        }

        if (isOnLastPage()) {
            mRightArrow.setVisibility(View.INVISIBLE);
        } else {
            mRightArrow.setVisibility(View.VISIBLE);
        }
    }

    private void updateRoundIndicator() {
        //TODO avoid loop over every element (store last updated)
        for (int i=0; i < mViewPager.getAdapter().getCount(); i++) {
            View view = roundedIndicatorContainer.getChildAt(i);
            GradientDrawable background = (GradientDrawable) view.getBackground();
            background.setColor(i == mViewPager.getCurrentItem() ? mRoundSelectedColor : mRoundDefaultColor);
        }
    }

    private boolean isOnLastPage() {
        return mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1;
    }

    private boolean isOnFirstPage() {
        return mViewPager.getCurrentItem() == 0;
    }
}
