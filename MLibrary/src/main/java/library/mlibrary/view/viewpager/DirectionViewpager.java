package library.mlibrary.view.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import library.mlibrary.R;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.viewpager.transform.DefaultTransformer;

/**
 * Created by Harmy on 2016/5/17 0017.
 */
public class DirectionViewpager extends ViewPager {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int mOrientation = HORIZONTAL;

    public DirectionViewpager(Context context) {
        super(context);
        init();
    }

    public DirectionViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DirectionViewpager);
        mOrientation = a.getInt(R.styleable.DirectionViewpager_DV_orientation, HORIZONTAL);
        init();
    }

    public DirectionViewpager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DirectionViewpager);
        mOrientation = a.getInt(R.styleable.DirectionViewpager_DV_orientation, HORIZONTAL);
        init();
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
        init();
    }

    private void init() {
        if (mOrientation == VERTICAL) {
            // The majority of the magic happens here
            if (!hasPageTransformer) {
                setPageTransformer(true, new DefaultTransformer(true));
            }
            // The easiest way to get rid of the overscroll drawing that happens on the left and right
            setOverScrollMode(OVER_SCROLL_NEVER);
        }
    }

    private boolean hasPageTransformer = false;

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        hasPageTransformer = true;
        super.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mOrientation == VERTICAL) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev); // return touch coordinates to original reference frame for any child views
            return intercepted;
        } else {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (Exception e) {
                LogDebug.e(e);
                return false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mOrientation == VERTICAL) {
            return super.onTouchEvent(swapXY(ev));
        } else {
            return super.onTouchEvent(ev);
        }
    }
}