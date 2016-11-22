package library.mlibrary.view.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Harmy on 2015/11/12 0012.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public static int HORIZONTAL_LIST = 0;
    public static int VERTICAL_LIST = 1;
    public static int GRID_LIST = 2;

    private Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation, Drawable divider) {
        mDivider = divider;
        setOrientation(orientation);
    }

    public DividerItemDecoration(int orientation, Drawable divider) {
        mDivider = divider;
        setOrientation(orientation);
    }

    private boolean isLastShow = false;

    public DividerItemDecoration(int orientation, Drawable divider, boolean lastshow) {
        mDivider = divider;
        setOrientation(orientation);
        isLastShow = lastshow;
    }

    private void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else if (mOrientation == HORIZONTAL_LIST) {
            drawHorizontal(c, parent);
        } else {
            drawGrid(c, parent);
        }

    }

    private void drawGrid(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (!isLastShow) {
                if (isLastRaw(parent, i, getSpanCount(parent), childCount)) {
                    continue;
                }
            }
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
        for (int i = 0; i < childCount; i++) {
            if (!isLastShow) {
                if (isLastColum(parent, i, getSpanCount(parent), childCount)) {
                    continue;
                }
            }
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }


    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                return (pos + 1) % spanCount == 0;
//                if ((pos + 1) % spanCount == 0) {
//                    return true;
//                }
            } else {
                int yushu = childCount % spanCount;
                if (yushu == 0) {
                    childCount = childCount - spanCount;
                } else {
                    childCount = childCount - yushu;
                }
                return pos > childCount - 1;
//                if (pos >= childCount - 1){
//                    return true;
//                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return (pos + 1) % spanCount == 0;
//                if ((pos + 1) % spanCount == 0) {
//                    return true;
//                }
            } else {
                int yushu = childCount % spanCount;
                if (yushu == 0) {
                    childCount = childCount - spanCount;
                } else {
                    childCount = childCount - yushu;
                }
                return pos > childCount - 1;
//                if (pos >= childCount - 1){
//                    return true;
//                }
            }
        } else {
            return true;
        }
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == GridLayoutManager.VERTICAL) {
                int yushu = childCount % spanCount;
                if (yushu == 0) {
                    childCount = childCount - spanCount;
                } else {
                    childCount = childCount - yushu;
                }
                return pos > childCount - 1;
//                if (pos >= childCount - 1){
//                    return true;
//                }
            } else {
                return (pos + 1) % spanCount == 0;
//                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
//                {
//                    return true;
//                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                int yushu = childCount % spanCount;
                if (yushu == 0) {
                    childCount = childCount - spanCount;
                } else {
                    childCount = childCount - yushu;
                }
                return pos > childCount - 1;
//                if (pos >= childCount - 1){
//                    return true;
//                }
            } else
                return (pos + 1) % spanCount == 0;
            // StaggeredGridLayoutManager 且横向滚动
//            {
//                // 如果是最后一行，则不需要绘制底部
//                if ((pos + 1) % spanCount == 0) {
//                    return true;
//                }
//            }
        } else {
            return true;
        }
    }


    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        if (!isLastShow) {
            childCount = childCount - 1;
        }
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight();

        int childCount = parent.getChildCount();
        if (!isLastShow) {
            childCount = childCount - 1;
        }
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();
        if (mOrientation == VERTICAL_LIST) {
            if (itemPosition == childCount - 1 && !isLastShow) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        } else if (mOrientation == HORIZONTAL_LIST) {
            if (itemPosition == childCount - 1 && !isLastShow) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        } else {
            int spanCount = getSpanCount(parent);
            int right = 0;
            int bottom = 0;
            if (!isLastShow) {
                if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                    bottom = 0;
                }
            } else {
                bottom = mDivider.getIntrinsicHeight();
            }
            if (!isLastShow) {
                if (isLastColum(parent, itemPosition, spanCount, childCount)) {
                    right = 0;
                }
            } else {
                right = mDivider.getIntrinsicWidth();
            }
            outRect.set(0, 0, right, bottom);
        }
    }
}
