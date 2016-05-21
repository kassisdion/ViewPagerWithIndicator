package com.kassisdion.viewpagerwithindicator.demo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kassisdion.viewpagerwithindicator.R;
import com.kassisdion.viewpagerwithindicator.lib.ViewPagerWithIndicator;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerWithIndicator mViewPagerWithIndicator;

    /*
    ** Life cycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPagerWithIndicator = (ViewPagerWithIndicator) findViewById(R.id.viewPagerWithIndicator);

        init();
    }

    /*
    ** Private method
     */
    private void init() {
        //Init viewPager
        List<Integer> objects = Arrays.asList(Color.BLUE, Color.RED, Color.GREEN);
        mViewPager.setAdapter(new CustomViewPagerAdapter(this, objects));
        mViewPagerWithIndicator.setViewPager(mViewPager);
    }

    /*
    ** Simple viewPager implementation
     */
    private static class CustomViewPagerAdapter extends PagerAdapter {

        @NonNull final List<Integer> mObjects;
        @NonNull final Context mContext;

        public CustomViewPagerAdapter(@NonNull final Context context, @NonNull final List<Integer> objects) {
            mContext = context;
            mObjects = objects;
        }

        @Override
        public int getCount() {
            return mObjects.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TextView textView = new TextView(mContext);
            textView.setText("Page: " + String.valueOf(position));
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(mObjects.get(position));

            container.addView(textView);

            return textView;
        }
    }
}
