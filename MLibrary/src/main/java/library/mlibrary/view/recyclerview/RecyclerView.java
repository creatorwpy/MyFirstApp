package library.mlibrary.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.pulltorefresh.pullview.PullLayout;
import library.mlibrary.view.recyclerview.grouprecyclerview.GroupRecyclerDecoration;
import library.mlibrary.view.recyclerview.grouprecyclerview.GroupRecyclerImp;
import library.mlibrary.view.recyclerview.grouprecyclerview.ItemVisibilityAdapter;

/**
 * Created by Harmy on 2015/12/1 0001.
 */
public class RecyclerView extends android.support.v7.widget.RecyclerView {
    public static final int INVALID_POSITION = -1;

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        ((AbsBaseAdapter) getAdapter()).setOnItemLongClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        ((AbsBaseAdapter) getAdapter()).setOnItemClickListener(listener);
    }

    public void setOnFinishInitListener(OnFinishInitListener listener) {
        ((AbsBaseAdapter) getAdapter()).setOnFinishInitListener(listener);
    }

    public void setItemLongClickable(boolean clickable) {
        ((AbsBaseAdapter) getAdapter()).setItemCanLongClick(clickable);
    }

    public void setItemClickable(boolean clickable) {
        ((AbsBaseAdapter) getAdapter()).setItemCanClick(clickable);
    }

    public void scrollToPositionWithOffset(int position, int offset) {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) manager).scrollToPositionWithOffset(position, offset);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) manager).scrollToPositionWithOffset(position, offset);
        } else {
            LogDebug.e(new Exception("layoutmanager must be instanceof LinearLayoutManager or StaggeredGridLayoutManager"));
        }
    }

    public void smoothScrollToPosition(int position) {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            SmoothItemToTopScroller scroller = new SmoothItemToTopScroller(getContext());
            scroller.setTargetPosition(position);
            manager.startSmoothScroll(scroller);
        } else {
            super.smoothScrollToPosition(position);
        }
    }


    public int getLastCompleteVisiblePosition() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
        } else if (manager instanceof GridLayoutManager) {
            return ((GridLayoutManager) manager).findLastCompletelyVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            int spanCount = ((StaggeredGridLayoutManager) manager).getSpanCount();
            int[] positions = new int[spanCount];
            for (int i = 0; i < spanCount; i++) {
                positions[i] = ((StaggeredGridLayoutManager) manager).findLastCompletelyVisibleItemPositions(new int[spanCount])[i];
            }
            for (int i = 0; i < spanCount; i++) {
                for (int j = i + 1; j < spanCount; j++) {
                    if (positions[i] < positions[j]) {
                        int temp = positions[i];
                        positions[i] = positions[j];
                        positions[j] = temp;
                    }
                }
            }
            return positions[0];
        } else {
            return 0;
        }
    }

    public int getLastVisiblePosition() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) manager).findLastVisibleItemPosition();
        } else if (manager instanceof GridLayoutManager) {
            return ((GridLayoutManager) manager).findLastVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            int spanCount = ((StaggeredGridLayoutManager) manager).getSpanCount();
            int[] positions = new int[spanCount];
            for (int i = 0; i < spanCount; i++) {
                positions[i] = ((StaggeredGridLayoutManager) manager).findLastVisibleItemPositions(new int[spanCount])[i];
            }
            for (int i = 0; i < spanCount; i++) {
                for (int j = i + 1; j < spanCount; j++) {
                    if (positions[i] < positions[j]) {
                        int temp = positions[i];
                        positions[i] = positions[j];
                        positions[j] = temp;
                    }
                }
            }
            return positions[0];
        } else {
            return 0;
        }
    }

    public int getFirstCompleteVisiblePosition() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition();
        } else if (manager instanceof GridLayoutManager) {
            return ((GridLayoutManager) manager).findFirstCompletelyVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) manager).findFirstCompletelyVisibleItemPositions(new int[((StaggeredGridLayoutManager) manager).getSpanCount()])[0];
        } else {
            return 0;
        }
    }

    public int getFirstVisiblePosition() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
        } else if (manager instanceof GridLayoutManager) {
            return ((GridLayoutManager) manager).findFirstVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) manager).findFirstVisibleItemPositions(new int[((StaggeredGridLayoutManager) manager).getSpanCount()])[0];
        } else {
            return 0;
        }
    }

    public int getPositionForView(View view) {
        View listItem = view;
        try {
            View v;
            while (!(v = (View) listItem.getParent()).equals(this)) {
                listItem = v;
            }
        } catch (ClassCastException e) {
            // We made it up to the window without find this list view
            return INVALID_POSITION;
        }

        // Search the children for the list item
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).equals(listItem)) {
                return i;
            }
        }

        // Child not found!
        return INVALID_POSITION;
    }

    /**
     * 坐标转换成对应的ItemPosition
     *
     * @param x
     * @param y
     * @return
     */
    public int pointToPosition(float x, float y) {
        int itemcount = getAdapter().getItemCount();
        for (int i = 0; i < itemcount; i++) {
            View child = getChildAt(i);
            int left = child.getLeft();
            int top = child.getTop();
            int right = child.getRight();
            int bottom = child.getBottom();
            if (left <= x && right >= x && top <= y && bottom >= y) {
                return i;
            }
        }
        return INVALID_POSITION;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof GroupRecyclerImp) {
            addItemDecoration(new GroupRecyclerDecoration((GroupRecyclerImp) adapter));
        }
    }

    private boolean mAutoLoadmore = false;
    private PullLayout mPullView;

    public void setAutoLoadmore(boolean auto, PullLayout pullview) {
        mAutoLoadmore = auto;
        mPullView = pullview;
        if (mAutoLoadmore && mPullView != null) {
            addOnScrollListener(mAutoLoadmoreListener);
        } else {
            removeOnScrollListener(mAutoLoadmoreListener);
        }
    }

    public void setAutoLoadmore(PullLayout pullview) {
        mPullView = pullview;
        if (mPullView != null) {
            setAutoLoadmore(true, pullview);
        } else {
            setAutoLoadmore(false, pullview);
        }
    }

    private OnScrollListener mAutoLoadmoreListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
            // 当不滚动时
            if (newState == SCROLL_STATE_IDLE) {
                // 判断是否滚动到底部
                if (((RecyclerView) recyclerView).getLastCompleteVisiblePosition() == recyclerView.getAdapter().getItemCount() - 1) {
                    mPullView.autoFootDelay(200);
                }
            }
        }
    };

    private OnInterceptTouchListener mOnInterceptTouchListener;
    private OnFilterTouchListener mOnFilterTouchListener;

    public void setOnInterceptTouchListener(OnInterceptTouchListener listener) {
        mOnInterceptTouchListener = listener;
    }

    public void setOnFilterTouchListener(OnFilterTouchListener listener) {
        mOnFilterTouchListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (mOnInterceptTouchListener != null) {
            if (mOnInterceptTouchListener.onInterceptTouch(e)) {
                return super.onInterceptTouchEvent(e);
            }
        } else {
            return super.onInterceptTouchEvent(e);
        }
        return false;
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent e) {
        if (mOnFilterTouchListener != null) {
            if (mOnFilterTouchListener.onFilterTouch(e)) {
                return super.onFilterTouchEventForSecurity(e);
            }
        } else {
            return super.onFilterTouchEventForSecurity(e);
        }
        return false;
    }

    public interface OnInterceptTouchListener {
        /**
         * @param e MotionEvent
         * @return 如果要交给父类分发事件，返回true.返回flase则拦截事件
         */
        boolean onInterceptTouch(MotionEvent e);
    }

    public interface OnFilterTouchListener {
        /**
         * @param e MotionEvent
         * @return 如果要交给父类分发事件，返回true.返回flase则拦截事件
         */
        boolean onFilterTouch(MotionEvent e);
    }
}
