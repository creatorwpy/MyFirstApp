package library.mlibrary.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import library.mlibrary.util.log.LogDebug;

/**
 * Created by harmy on 2016/9/6 0006.
 */
public class AutoMeasureGrideLayoutManager extends GridLayoutManager {
    public AutoMeasureGrideLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAutoMeasureEnabled(true);
    }

    public AutoMeasureGrideLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        setAutoMeasureEnabled(true);
    }

    public AutoMeasureGrideLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        setAutoMeasureEnabled(true);
    }

    //    public MaxHeightGrideLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
//
//    public MaxHeightGrideLayoutManager(Context context, int spanCount) {
//        super(context, spanCount);
//        this.mChildPerLines = spanCount;
//    }
//
//    public MaxHeightGrideLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
//        super(context, spanCount, orientation, reverseLayout);
//        this.mChildPerLines = spanCount;
//    }
//
//    private int mChildPerLines;
//    private int[] mMeasuredDimension = new int[2];
//
//    @Override
//    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//        final int widthMode = View.MeasureSpec.getMode(widthSpec);
//        final int heightMode = View.MeasureSpec.getMode(heightSpec);
//        final int widthSize = View.MeasureSpec.getSize(widthSpec);
//        final int heightSize = View.MeasureSpec.getSize(heightSpec);
//        int width = 0;
//        int height = 0;
//        for (int i = 0; i < getItemCount(); ) {
//            try {
//                measureScrapChild(recycler, i,
//                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
//                        mMeasuredDimension);
//            } catch (IndexOutOfBoundsException e) {
////                LogDebug.e(e);
//            }
//            if (getOrientation() == HORIZONTAL) {
//                width = width + mMeasuredDimension[0];
//            } else {
//                height = height + mMeasuredDimension[1];
//            }
//            i = i + mChildPerLines;
//        }
//
//        switch (widthMode) {
//            case View.MeasureSpec.EXACTLY:
//                    width = widthSize;
//            case View.MeasureSpec.AT_MOST:
//            case View.MeasureSpec.UNSPECIFIED:
//        }
//
//        switch (heightMode) {
//            case View.MeasureSpec.EXACTLY:
//                height = heightSize;
//            case View.MeasureSpec.AT_MOST:
//            case View.MeasureSpec.UNSPECIFIED:
//        }
//        setMeasuredDimension(width, height);
//    }
//
//    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
//                                   int heightSpec, int[] measuredDimension) {
//
//        View view = recycler.getViewForPosition(position);
//
//        // For adding Item Decor Insets to view
//        super.measureChildWithMargins(view, 0, 0);
//        if (view != null) {
//            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
//            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
//                    getPaddingLeft() + getPaddingRight() + getDecoratedLeft(view) + getDecoratedRight(view), p.width);
//            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
//                    getPaddingTop() + getPaddingBottom() + getPaddingBottom() + getDecoratedBottom(view), p.height);
//            view.measure(childWidthSpec, childHeightSpec);
//
//            // Get decorated measurements
//            measuredDimension[0] = getDecoratedMeasuredWidth(view) + p.leftMargin + p.rightMargin;
//            measuredDimension[1] = getDecoratedMeasuredHeight(view) + p.bottomMargin + p.topMargin;
//            recycler.recycleView(view);
//        }
//    }
    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
}
