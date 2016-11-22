package library.mlibrary.view.banner.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import library.mlibrary.view.banner.adapter.AbsBasePageAdapter;
import library.mlibrary.view.viewpager.DirectionViewpager;


public class CBLoopViewPager extends DirectionViewpager {
    ViewPager.OnPageChangeListener mOuterPageChangeListener;
    //    private OnItemClickListener onItemClickListener;
    private AbsBasePageAdapter mPageAdapter;

    private boolean isCanScroll = true;
    private boolean canLoop = false;

    public void setAdapter(AbsBasePageAdapter adapter, boolean canLoop) {
        mPageAdapter = adapter;
        setCanLoop(canLoop);
        mPageAdapter.setViewPager(this);
        super.setAdapter(mPageAdapter);

        setCurrentItem(getFristItem(), false);
    }

    public int getFristItem() {
        return canLoop ? mPageAdapter.getRealCount() * mPageAdapter.MULTIPLE_FIRST : 0;
    }

    public int getLastItem() {
        return mPageAdapter.getRealCount() - 1;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

//    private float oldX = 0, newX = 0, oldY = 0, newY = 0;
//    private static final float sens = 5;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
//            if (onItemClickListener != null) {
//                switch (ev.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        oldX = ev.getX();
//                        oldY = ev.getY();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        newX = ev.getX();
//                        newY = ev.getY();
//                        if (onItemClickListener != null) {
//                            if (Math.abs(oldX - newX) < sens && Math.abs(oldY - newY) < sens) {
//                                onItemClickListener.onItemClick((getRealItem()));
//                            }
//                        }
//                        oldX = 0;
//                        newX = 0;
//                        oldY = 0;
//                        newY = 0;
//                        break;
//                }
//            }
            return super.onInterceptTouchEvent(ev);
        } else
            return false;
    }


    public AbsBasePageAdapter getAdapter() {
        return mPageAdapter;
    }

    public int getRealItem() {
        return mPageAdapter != null ? mPageAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }


    public CBLoopViewPager(Context context) {
        super(context);
        init();
    }

    public CBLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(onPageChangeListener);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mPageAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;

            if (mOuterPageChangeListener != null) {
                if (realPosition != mPageAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (canLoop == false) {
            setCurrentItem(getRealItem(), false);
        }
        if (mPageAdapter == null) return;
        mPageAdapter.setCanLoop(canLoop);
        mPageAdapter.notifyDataSetChanged();
    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
}
