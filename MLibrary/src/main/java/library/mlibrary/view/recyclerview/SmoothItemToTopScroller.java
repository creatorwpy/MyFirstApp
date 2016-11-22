package library.mlibrary.view.recyclerview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.DisplayMetrics;

/**
 * Created by Harmy on 2016/7/20 0020.
 */
public class SmoothItemToTopScroller extends LinearSmoothScroller {

    public SmoothItemToTopScroller(Context context) {
        super(context);
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        return ((LinearLayoutManager) getLayoutManager()).computeScrollVectorForPosition(targetPosition);
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
    }

    private final float MILLISECONDS_PER_INCH = 5f;
}
