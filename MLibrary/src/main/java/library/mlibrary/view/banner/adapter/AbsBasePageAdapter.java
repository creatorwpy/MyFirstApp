package library.mlibrary.view.banner.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.banner.view.CBLoopViewPager;

/**
 * Created by Harmy on 2016/4/25 0025.
 */
public abstract class AbsBasePageAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
    private Context mContent;
    private boolean canLoop = false;
    private CBLoopViewPager viewPager;
    private final int MULTIPLE_COUNT = 400;
    public static final int MULTIPLE_FIRST = 10;

    public AbsBasePageAdapter(Context Context, List<T> datas) {
        mContent = Context;
        mDatas = datas;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public Context getContext() {
        return mContent;
    }

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }

    public int getMULTIPLE_COUNT() {
        return MULTIPLE_COUNT;
    }

    @Override
    public int getCount() {
        return canLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = viewPager.getFristItem();
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
            LogDebug.e(e);
        }
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(CBLoopViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
