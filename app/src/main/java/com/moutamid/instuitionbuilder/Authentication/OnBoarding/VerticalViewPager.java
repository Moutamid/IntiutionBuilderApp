package com.moutamid.instuitionbuilder.Authentication.OnBoarding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(false, new HorizontalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);

        // Adjust the velocity and touch parameters as needed
        try {
            Class cls = this.getClass().getSuperclass();
            Field distanceField = cls.getDeclaredField("mFlingDistance");
            distanceField.setAccessible(true);
            distanceField.setInt(this, distanceField.getInt(this) / 40);

            // Other parameter adjustments...
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float x = ev.getX();
        float y = ev.getY();

        float newX = (x / width) * width;
        float newY = (y / height) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

    private class HorizontalPageTransformer implements PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            if (position <= -1) {
                view.setAlpha(0);
            } else if (position <= 0) {
                view.setAlpha(1);
                ViewCompat.setElevation(view, 1);
                view.setTranslationX(view.getWidth() * -position);
                view.setTranslationY(0);

                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else if (position <= 1) {
                view.setAlpha(1);
                ViewCompat.setElevation(view, 2);
                view.setTranslationX(position * view.getWidth());
                view.setTranslationY(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else {
                view.setAlpha(0);
            }
        }
    }
}
