package library.mlibrary.view.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

import library.mlibrary.R;
import library.mlibrary.view.banner.adapter.AbsBasePageAdapter;
import library.mlibrary.view.banner.listener.CBPageChangeListener;
import library.mlibrary.view.banner.listener.OnItemClickListener;
import library.mlibrary.view.banner.listener.TurningListener;
import library.mlibrary.view.banner.view.CBLoopViewPager;

/**
 * 页面翻转控件，极方便的广告栏
 * 支持无限循环，自动翻页，翻页特效
 *
 * @author Sai 支持自动翻页
 */
public class ConvenientBanner<E, T> extends LinearLayout {
    private int[] page_indicatorId;
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private CBPageChangeListener pageChangeListener;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private AbsBasePageAdapter pageAdapter;
    private CBLoopViewPager viewPager;
    private ViewPagerScroller scroller;
    private LinearLayout loPageTurningPoint;
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private boolean manualPageable = true;
    private boolean canLoop = false;

    public enum PageIndicatorAlign {
        LEFT, RIGHT, CENTER_VERTICAL, CENTER_HORIZONTAL, TOP, BOTTOM
    }

    public enum PageIndicatorOrientation {
        VERTICAL, HORIZONTAL
    }

    private AdSwitchTask adSwitchTask;

    public ConvenientBanner(Context context) {
        super(context);
        init(context);
    }

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private int mOrientation = HORIZONTAL;

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, false);
        mOrientation = a.getInt(R.styleable.ConvenientBanner_CB_orientation, HORIZONTAL);
        a.recycle();
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, false);
        mOrientation = a.getInt(R.styleable.ConvenientBanner_CB_orientation, HORIZONTAL);
        a.recycle();
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, false);
        mOrientation = a.getInt(R.styleable.ConvenientBanner_CB_orientation, HORIZONTAL);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(
                R.layout.include_viewpager, this, true);
        viewPager = (CBLoopViewPager) hView.findViewById(R.id.cbLoopViewPager);
        setViewPagerOrientation(mOrientation);
        loPageTurningPoint = (LinearLayout) hView
                .findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();

        adSwitchTask = new AdSwitchTask(this);
    }

    public void setViewPagerOrientation(int orientation) {
        viewPager.setOrientation(orientation);
    }

    static class AdSwitchTask implements Runnable {

        private final WeakReference<ConvenientBanner> reference;

        AdSwitchTask(ConvenientBanner convenientBanner) {
            this.reference = new WeakReference<ConvenientBanner>(convenientBanner);
        }

        @Override
        public void run() {
            ConvenientBanner convenientBanner = reference.get();

            if (convenientBanner != null) {
                if (!convenientBanner.canLoop && convenientBanner.mTurningListener != null && convenientBanner.getCurrentItem() == convenientBanner.getViewPager().getAdapter().getRealCount() - 1) {
                    convenientBanner.stopTurning();
                    convenientBanner.mTurningListener.onFinish();
                }
                if (convenientBanner.viewPager != null && convenientBanner.turning) {
                    int page = convenientBanner.viewPager.getCurrentItem() + 1;
                    convenientBanner.viewPager.setCurrentItem(page);
                    convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
                }
            }
        }
    }

    private TurningListener mTurningListener;

    public void setTurningListener(TurningListener listener) {
        mTurningListener = listener;
    }


    public ConvenientBanner setAdapter(AbsBasePageAdapter adapter) {
        pageAdapter = adapter;
        viewPager.setAdapter(pageAdapter, canLoop);
        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId, viewPager.getAdapter().getCount());
        return this;
    }

    public AbsBasePageAdapter getAdapter() {
        return pageAdapter;
    }

    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId, viewPager.getAdapter().getCount());
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public ConvenientBanner setPageIndicatorVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public ConvenientBanner setPageIndicator(int[] page_indicatorId, int size) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (size == 0) return this;
        for (int count = 0; count < size; count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 5, 5, 5);
            if (mPointViews.isEmpty())
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
        }
        pageChangeListener = new CBPageChangeListener(mPointViews,
                page_indicatorId);
        viewPager.setOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(viewPager.getRealItem());
        if (onPageChangeListener != null)
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);

        return this;
    }

    /**
     * 指示器的方向
     *
     * @return
     */
    public ConvenientBanner setPageIndicatorOrientation(PageIndicatorOrientation orientation) {
        if (orientation == PageIndicatorOrientation.HORIZONTAL) {
            loPageTurningPoint.setOrientation(LinearLayout.HORIZONTAL);
        }
        if (orientation == PageIndicatorOrientation.VERTICAL) {
            loPageTurningPoint.setOrientation(LinearLayout.VERTICAL);
        }
        return this;
    }

    /**
     * 指示器的背景
     *
     * @return
     */
    public ConvenientBanner setPageIndicatorBacground(@DrawableRes int id) {
        loPageTurningPoint.setBackgroundResource(id);
        return this;
    }

    public LinearLayout getPageIndicator() {
        return loPageTurningPoint;
    }

    /**
     * 指示器的位置
     *
     * @return
     */
    public ConvenientBanner setPageIndicatorAlign(PageIndicatorAlign... aligns) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) loPageTurningPoint.getLayoutParams();
        int alignsl = aligns.length;
        for (int i = 0; i < alignsl; i++) {
            PageIndicatorAlign align = aligns[i];
            if (align == PageIndicatorAlign.BOTTOM) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            }
            if (align == PageIndicatorAlign.TOP) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }
            if (align == PageIndicatorAlign.LEFT) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            }
            if (align == PageIndicatorAlign.RIGHT) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            }
            if (align == PageIndicatorAlign.CENTER_HORIZONTAL) {
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            }
            if (align == PageIndicatorAlign.CENTER_VERTICAL) {
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            }
        }
        loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     *
     * @return
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     *
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public ConvenientBanner startTurning(long autoTurningTime) {
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }


    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public ConvenientBanner setPageTransformer(PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }


    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return viewPager.isCanScroll();
    }

    public void setCanScroll(boolean manualPageable) {
        viewPager.setCanScroll(manualPageable);
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) startTurning(autoTurningTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    //获取当前的页面index
    public int getCurrentItem() {
        if (viewPager != null) {
            return viewPager.getRealItem();
        }
        return -1;
    }

    //设置当前的页面index
    public void setCurrentItem(int index) {
        if (viewPager != null) {
            int current = viewPager.getCurrentItem();
            int realcurrent = getCurrentItem();
            int off = index - realcurrent;
            index = off + current;
            viewPager.setCurrentItem(index);
        }
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     *
     * @param onPageChangeListener
     * @return
     */
    public ConvenientBanner setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (pageChangeListener != null)
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        else viewPager.setOnPageChangeListener(onPageChangeListener);
        return this;
    }

    public boolean isCanLoop() {
        return viewPager.isCanLoop();
    }

//    /**
//     * 监听item点击
//     *
//     * @param onItemClickListener
//     */
//    public ConvenientBanner setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        if (onItemClickListener == null) {
//            viewPager.setOnItemClickListener(null);
//            return this;
//        }
//        viewPager.setOnItemClickListener(onItemClickListener);
//        return this;
//    }

    /**
     * 设置ViewPager的滚动速度
     *
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration) {
        scroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return scroller.getScrollDuration();
    }

    public CBLoopViewPager getViewPager() {
        return viewPager;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        viewPager.setCanLoop(canLoop);
    }
}
