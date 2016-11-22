package library.mlibrary.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import library.mlibrary.util.log.LogDebug;

public class AutoMeasureLinearLayoutManager extends LinearLayoutManager {
    public AutoMeasureLinearLayoutManager(Context context) {
        super(context);
        setAutoMeasureEnabled(true);
    }

    public AutoMeasureLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        setAutoMeasureEnabled(true);
    }

    public AutoMeasureLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAutoMeasureEnabled(true);
    }

//    private static final int CHILD_WIDTH = 0;
//    private static final int CHILD_HEIGHT = 1;
//    private static final int DEFAULT_CHILD_SIZE = 100;
//
//    private final int[] childDimensions = new int[2];
//
//    private int childSize = DEFAULT_CHILD_SIZE;
//    private boolean hasChildSize;
//
//    public MaxHeightLinearLayoutManager(Context context) {
//        super(context);
//    }
//
//    public MaxHeightLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
//        super(context, orientation, reverseLayout);
//    }
//
//    private int[] mMeasuredDimension = new int[2];
//
//
//    @Override
//    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//        final int widthMode = View.MeasureSpec.getMode(widthSpec);
//        final int heightMode = View.MeasureSpec.getMode(heightSpec);
//        final int widthSize = View.MeasureSpec.getSize(widthSpec);
//        final int heightSize = View.MeasureSpec.getSize(heightSpec);
//        int width = 0;
//        int height = 0;
//
//        for (int i = 0; i < getItemCount(); i++) {
//            try {
//                measureScrapChild(recycler, i,
//                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
//                        mMeasuredDimension);
//            } catch (IndexOutOfBoundsException e) {
////                LogDebug.e(e);
//            }
//
//            if (getOrientation() == HORIZONTAL) {
//                width = width + mMeasuredDimension[0];
//                if (i == 0) {
//                    height = mMeasuredDimension[1];
//                }
//            } else {
//                height = height + mMeasuredDimension[1];
//                if (i == 0) {
//                    width = mMeasuredDimension[0];
//                }
//            }
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
//
//    }
//
//    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
//        View view = recycler.getViewForPosition(position);
//
//        // For adding Item Decor Insets to view
////        super.measureChildWithMargins(view, 0, 0);
//
//        if (view != null) {
//            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
//            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
//                    getPaddingTop() + getPaddingBottom(), p.height);
//            view.measure(widthSpec, childHeightSpec);
//            measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
//            measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
//            recycler.recycleView(view);
//        }
//    }
//
//    @Override
//    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
////        Logger.e("SyLinearLayoutManager state:" + state.toString());
//        super.onLayoutChildren(recycler, state);
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