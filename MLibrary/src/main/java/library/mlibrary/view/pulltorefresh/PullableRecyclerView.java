package library.mlibrary.view.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import library.mlibrary.view.recyclerview.RecyclerView;

/**
 * Created by Harmy on 2015/8/27 0027.
 */
public class PullableRecyclerView extends RecyclerView implements Pullable {
    public PullableRecyclerView(Context context) {
        super(context);
    }

    public PullableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getAdapter().getItemCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (isTopShowed(getLayoutManager())
                && getChildAt(0).getTop() >= 0) {
            // 滑到顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getAdapter().getItemCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getAdapter().getItemCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }

    private boolean isTopShowed(RecyclerView.LayoutManager manager) {
        if (manager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (manager instanceof GridLayoutManager) {
            if (((GridLayoutManager) manager).findFirstCompletelyVisibleItemPosition() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (manager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) manager).findFirstCompletelyVisibleItemPositions(new int[100])[0] == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
