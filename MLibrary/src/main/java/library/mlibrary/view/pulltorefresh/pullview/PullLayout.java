package library.mlibrary.view.pulltorefresh.pullview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import library.mlibrary.R;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.recyclerview.RecyclerView;

public class PullLayout extends RelativeLayout {
    // 初始状态
    public static final int INIT = 0;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;
    // 释放加载
    public static final int RELEASE_TO_LOAD = 3;
    // 正在加载
    public static final int LOADING = 4;
    // 操作完毕
    public static final int DONE = 5;
    // 当前状态
    private int state = INIT;
    // 刷新回调接口
    private PullListener mListener;
    // 刷新成功
    public static final int SUCCEED = 0;
    // 刷新失败
    public static final int FAIL = 1;
    private boolean isVertical = true;
    // 按下Y坐标，上一个事件点Y坐标
    private float downXorY, lastFooterDist, lastHeaderDist;

    public float headerOffDist = 0;
    private float footerOffDist = 0;

    /**
     * 延迟的距离，为了防止误触
     */
    private float delayDist = 0;

    private float headerDist = 200 + delayDist;
    private float footerDist = 200 + delayDist;

    private MyTimer timer;
    // 回滚速度
    public float MOVE_SPEED = 10;
    // 第一次执行布局
    private boolean isLayout = false;
    // 在刷新过程中滑动操作
    private boolean isTouch = false;
    // 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
    private float radio = 2;

    // 实现了Pullable接口的View
    private View pullableView;
    // 过滤多点触碰
    private int mEvents;
    // 这两个变量用来控制pull的方向，如果不加控制，当情况满足可上拉又可下拉时没法下拉
    private boolean canPullDown = true;
    private boolean canPullUp = true;

    /**
     * 执行自动回滚的handler
     */
    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // 回弹速度随下拉距离moveDeltaY增大而增大
            MOVE_SPEED = (float) (10 + 10 * Math.tan(Math.PI / 2
                    / getMeasuredHeight() * (headerOffDist + Math.abs(footerOffDist))));
            if (!isTouch) {
                // 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
                if (state == REFRESHING && headerOffDist <= headerDist) {
                    headerOffDist = headerDist;
                    timer.cancel();
                } else if (state == LOADING && -footerOffDist <= footerDist) {
                    footerOffDist = -footerDist;
                    timer.cancel();
                }

            }
            if (headerOffDist > 0)
                headerOffDist -= MOVE_SPEED;
            else if (footerOffDist < 0)
                footerOffDist += MOVE_SPEED;
            if (headerOffDist < 0) {
                // 已完成回弹
                headerOffDist = 0;
                // 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                if (state != REFRESHING && state != LOADING)
                    changeState(INIT);
                timer.cancel();
                requestLayout();
            }
            if (footerOffDist > 0) {
                // 已完成回弹
                footerOffDist = 0;
                // 隐藏上拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                if (state != REFRESHING && state != LOADING)
                    changeState(INIT);
                timer.cancel();
                requestLayout();
            }
            // 刷新布局,会自动调用onLayout
            requestLayout();
            // 没有拖拉或者回弹完成
            if (headerOffDist + Math.abs(footerOffDist) == 0)
                timer.cancel();
        }

    };

    public void setPullListener(PullListener listener) {
        mListener = listener;
    }

    public PullLayout(Context context) {
        this(context, null);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullLayout, defStyle, 0);
        isVertical = a.getBoolean(R.styleable.PullLayout_PL_vertical, true);
        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        timer = new MyTimer(updateHandler);
    }

    private void hide() {
        timer.schedule(5);
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    private long mHeaderStayTime = 0;
    private long mFooterStayTime = 0;

    /**
     * 如果需要防止误触，设置delayDist，既设置灵敏度
     *
     * @param dist dist越大，灵敏度越差，防误触越好
     */
    public void setDelayDist(float dist) {
        delayDist = dist;
    }

    /**
     * 设置刷新完成后状态显示多长时间
     *
     * @param time
     */
    public void setStayTime(long time) {
        mFooterStayTime = time;
        mHeaderStayTime = time;
    }

    public void setHeaderStayTime(long time) {
        mHeaderStayTime = time;
    }

    public void setFooterStayTime(long time) {
        mFooterStayTime = time;
    }
    /**
     * 完成刷新操作，显示刷新结果。注意：刷新完成后一定要调用这个方法
     */
    /**
     * @param refreshResult PullToRefreshLayout.SUCCEED代表成功，PullToRefreshLayout.FAIL代表失败
     */
    public void headFinish(int refreshResult) {
        try {
            switch (refreshResult) {
                case SUCCEED:
                    // 刷新成功
                    mHeader.onSuccess();
                    break;
                case FAIL:
                    // 刷新失败
                    mHeader.onFailure();
                    break;
                default:
                    // 刷新成功
                    mHeader.onSuccess();
                    break;
            }
            if (headerOffDist > 0) {
                // 刷新结果停留1秒
                mDelayHandler.sendEmptyMessageDelayed(0, mHeaderStayTime);
            } else {
                changeState(DONE);
                hide();
            }
        } catch (Exception e) {
            LogDebug.e(e);
        }
    }

    private Handler mDelayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    changeState(DONE);
                    hide();
                    break;
                case 1:
                    autoFoot();
                    break;
                case 2:
                    autoHead();
                    break;
            }
        }
    };

    public void headFinish() {
        headFinish(SUCCEED);
    }

    /**
     * 加载完毕，显示加载结果。注意：加载完成后一定要调用这个方法
     *
     * @param refreshResult PullToRefreshLayout.SUCCEED代表成功，PullToRefreshLayout.FAIL代表失败
     */
    public void footFinish(int refreshResult) {
        try {
            switch (refreshResult) {
                case SUCCEED:
                    // 加载成功
                    mFooter.onSuccess();
                    break;
                case FAIL:
                    // 加载失败
                    mFooter.onFailure();
                    break;
                default:
                    // 加载成功
                    mFooter.onSuccess();
                    break;
            }
            if (footerOffDist < 0) {
                // 刷新结果停留1秒
                mDelayHandler.sendEmptyMessageDelayed(0, mFooterStayTime);
            } else {
                changeState(DONE);
                hide();
            }
        } catch (Exception e) {
            LogDebug.e(e);
        }
    }

    public void footFinish() {
        footFinish(SUCCEED);
    }

    private void changeState(int to) {
        state = to;
        switch (state) {
            case INIT:
                // 下拉布局初始状态
                mHeader.onInit();
                // 上拉布局初始状态
                mFooter.onInit();
                break;
            case RELEASE_TO_REFRESH:
                // 释放刷新状态
                mHeader.onRelease();
                break;
            case REFRESHING:
                // 正在刷新状态
                mHeader.onHeading();
                break;
            case RELEASE_TO_LOAD:
                // 释放加载状态
                mFooter.onRelease();
                break;
            case LOADING:
                // 正在加载状态
                mFooter.onFooting();
                break;
            case DONE:
                // 刷新或加载完毕，啥都不做
                mHeader.onDone();
                mFooter.onDone();
                break;
        }
    }

    /**
     * 不限制上拉或下拉
     */
    private void releasePull() {
        canPullDown = true;
        canPullUp = true;
    }

    private boolean mSelfCanPullDown = true;
    private boolean mSelfCanPullUp = true;

    /**
     * 设置是否可以下拉
     *
     * @param can
     */
    public void setCanPullHead(boolean can) {
        mSelfCanPullDown = can;
    }

    /**
     * 设置是否可以上拉
     *
     * @param can
     */
    public void setCanPullFoot(boolean can) {
        mSelfCanPullUp = can;
    }

    private boolean mNeedHeader = true;
    private boolean mNeedFooter = true;

    public void setNeedHeader(boolean need) {
        mNeedHeader = need;
    }

    public void setNeedFooter(boolean need) {
        mNeedFooter = need;
    }

    private float getXorY(MotionEvent e) {
        if (isVertical) {
            return e.getY();
        } else {
            return e.getX();
        }
    }

    /*
     * （非 Javadoc）由父控件决定是否分发事件，防止事件冲突
     *
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downXorY = getXorY(ev);
                lastFooterDist = downXorY - delayDist;
                lastHeaderDist = downXorY + delayDist;
                timer.cancel();
                mEvents = 0;
                releasePull();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 过滤多点触碰
                mEvents = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mEvents == 0) {
                    if ((getXorY(ev) - downXorY) >= delayDist) {
                        if (headerOffDist > 0
                                || (checkContentCanPullHead() && mSelfCanPullDown
                                && canPullDown && state != LOADING)) {

                            // 可以下拉，正在加载时不能下拉
                            // 对实际滑动距离做缩小，造成用力拉的感觉
                            headerOffDist = headerOffDist + (getXorY(ev) - lastHeaderDist) / radio;
                            if (headerOffDist < 0) {
                                headerOffDist = 0;
                                canPullDown = false;
                                canPullUp = true;
                            }
                            if (headerOffDist > getMeasuredHeight())
                                headerOffDist = getMeasuredHeight();
                            if (state == REFRESHING) {
                                // 正在刷新的时候触摸移动
                                isTouch = true;
                            }
                        } else
                            releasePull();
                    } else if ((getXorY(ev) - downXorY) <= -delayDist) {
                        if (footerOffDist < 0
                                || (checkContentCanPullFoot() && mSelfCanPullUp && canPullUp && state != REFRESHING)) {
                            // 可以上拉，正在刷新时不能上拉
                            footerOffDist = footerOffDist + (getXorY(ev) - lastFooterDist) / radio;
                            if (footerOffDist > 0) {
                                footerOffDist = 0;
                                canPullDown = true;
                                canPullUp = false;
                            }
                            if (footerOffDist < -getMeasuredHeight())
                                footerOffDist = -getMeasuredHeight();
                            if (state == LOADING) {
                                // 正在加载的时候触摸移动
                                isTouch = true;
                            }
                        } else
                            releasePull();
                    }
                } else
                    mEvents = 0;
                lastHeaderDist = getXorY(ev);
                lastFooterDist = getXorY(ev);
                // 根据下拉距离改变比例
                radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight()
                        * (headerOffDist + Math.abs(footerOffDist))));
                if (headerOffDist > 0 || footerOffDist < 0)
                    requestLayout();
                if (headerOffDist > 0) {
                    if (headerOffDist <= headerDist
                            && (state == RELEASE_TO_REFRESH || state == DONE)) {
                        // 如果下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新
                        changeState(INIT);
                    }
                    if (headerOffDist >= headerDist && state == INIT) {
                        // 如果下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新
                        changeState(RELEASE_TO_REFRESH);
                    }
                } else if (footerOffDist < 0) {
                    // 下面是判断上拉加载的，同上，注意pullUpY是负值
                    if (-footerOffDist <= footerDist
                            && (state == RELEASE_TO_LOAD || state == DONE)) {
                        changeState(INIT);
                    }
                    // 上拉操作
                    if (-footerOffDist >= footerDist && state == INIT) {
                        changeState(RELEASE_TO_LOAD);
                    }

                }
                if ((headerOffDist + Math.abs(footerOffDist)) > 8) {
                    // 防止下拉过程中误触发长按事件和点击事件
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (headerOffDist > headerDist || -footerOffDist > footerDist)
                // 正在刷新时往下拉（正在加载时往上拉），释放后下拉头（上拉头）不隐藏
                {
                    isTouch = false;
                }
                if (state == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);
                    // 刷新操作
                    if (mListener != null) {
                        mListener.onHeading(this);
                    } else headFinish();
                } else if (state == RELEASE_TO_LOAD) {
                    changeState(LOADING);
                    // 加载操作
                    if (mListener != null) {
                        mListener.onFooting(this);
                    } else
                        footFinish();
                }
                hide();
            default:
                break;
        }
        // 事件分发交给父类
        super.dispatchTouchEvent(ev);
        return true;
    }

    /**
     * @author chenjing 自动模拟手指滑动的task
     */
    private class AutoHeadTask extends
            AsyncTask<Integer, Float, String> {

        @Override
        protected String doInBackground(Integer... params) {
            while (!isLayout) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LogDebug.e(e);
                }
            }
            while (headerOffDist < 10 / 9 * headerDist) {
                headerOffDist += MOVE_SPEED * 2.5;
                publishProgress(headerOffDist);
                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            changeState(REFRESHING);
            // 刷新操作
            if (mListener != null)
                mListener.onHeading(PullLayout.this);
            hide();
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            if (headerOffDist > headerDist)
                changeState(RELEASE_TO_REFRESH);
            requestLayout();
        }

    }

    /**
     * @author chenjing 自动模拟手指滑动的task
     */
    private class AutoFootTask extends
            AsyncTask<Integer, Float, String> {

        @Override
        protected String doInBackground(Integer... params) {
            while (!isLayout) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LogDebug.e(e);
                }
            }
            while (-footerOffDist < 10 / 9 * footerDist) {
                footerOffDist -= MOVE_SPEED * 2.5;
                publishProgress(footerOffDist);
                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            changeState(LOADING);
            // 刷新操作
            if (mListener != null)
                mListener.onFooting(PullLayout.this);
            hide();
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            if (footerOffDist > footerDist)
                changeState(RELEASE_TO_LOAD);
            requestLayout();
        }

    }

    /**
     * 自动刷新
     */
    public void autoHead() {
        AutoHeadTask task = new AutoHeadTask();
        task.execute(1);
    }

    public void autoHeadDelay(long delay) {
        mDelayHandler.sendEmptyMessageDelayed(2, delay);
    }

    /**
     * 自动加载
     */
    public void autoFoot() {
        AutoFootTask task = new AutoFootTask();
        task.execute(1);
//        footerOffDist = -footerDist;
//        requestLayout();
//        changeState(LOADING);
//        // 加载操作
//        if (mListener != null)
//            mListener.onFooting(this);
    }

    public void autoFootDelay(long delay) {
        mDelayHandler.sendEmptyMessageDelayed(1, delay);
    }

    /**
     * 是否滑动到底部时自动加载
     */
    private boolean isNeedAutoFooter = true;

    public void setNeedAutoFooter(boolean need) {
        isNeedAutoFooter = need;
    }

    @SuppressLint("NewApi")
    private void initScrollListener() {
        if (isInEditMode()) {
            return;
        }
        if (pullableView instanceof LinearLayout || pullableView instanceof ImageView || pullableView instanceof TextView) {
        } else if (pullableView instanceof WebView) {
        } else if (pullableView instanceof library.mlibrary.view.scrollview.ScrollView) {
            ((library.mlibrary.view.scrollview.ScrollView) pullableView).setOnScrollListener(new library.mlibrary.view.scrollview.ScrollView.OnScrollListener() {
                @Override
                public void onBottomArrived() {
                    if (isNeedAutoFooter && mSelfCanPullUp) {
                        autoFootDelay(200);
                    }
                }
            });

        } else if (pullableView instanceof library.mlibrary.view.scrollview.NestedScrollView) {
            ((library.mlibrary.view.scrollview.NestedScrollView) pullableView).setOnScrollListener(new library.mlibrary.view.scrollview.NestedScrollView.OnScrollListener() {
                @Override
                public void onBottomArrived() {
                    if (isNeedAutoFooter && mSelfCanPullUp && pullableView.getScrollY() != 0) {
                        autoFootDelay(200);
                    }
                }
            });
        } else if (pullableView instanceof RecyclerView) {
            ((RecyclerView) pullableView).addOnScrollListener(new android.support.v7.widget.RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
                    // 当不滚动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && isNeedAutoFooter && mSelfCanPullUp) {
                        // 判断是否滚动到底部
                        if (((RecyclerView) recyclerView).getAdapter().getItemCount() != 0 && ((RecyclerView) recyclerView).getLastCompleteVisiblePosition() == recyclerView.getAdapter().getItemCount() - 1
                                && ((RecyclerView) recyclerView).getFirstCompleteVisiblePosition() != 0
                                ) {
                            autoFootDelay(200);
                        }

                    }
                }
            });
        } else if (pullableView instanceof ListView) {
            ((ListView) pullableView).setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    // 当不滚动时
                    if (scrollState == SCROLL_STATE_IDLE && isNeedAutoFooter && mSelfCanPullUp) {
                        // 判断是否滚动到底部
                        if (view.getLastVisiblePosition() == view.getAdapter().getCount() - 1) {
                            autoFootDelay(200);
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        } else if (pullableView instanceof GridView) {
            ((GridView) pullableView).setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    // 当不滚动时
                    if (scrollState == SCROLL_STATE_IDLE && isNeedAutoFooter && mSelfCanPullUp) {
                        // 判断是否滚动到底部
                        if (view.getLastVisiblePosition() == view.getAdapter().getCount() - 1) {
                            autoFootDelay(200);
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }
    }

    private AbsBaseHeader mHeader;
    private AbsBaseFooter mFooter;

    public void setHeader(AbsBaseHeader mHeader) {
        this.mHeader = mHeader;
    }

    public void setFooter(AbsBaseFooter mFooter) {
        this.mFooter = mFooter;
    }

    private View headView;
    private View footView;

    private void initView() {
        pullableView = getChildAt(0);
        if (isVertical) {
            if (mHeader == null) {
                mHeader = new SimpleVHeader();
            }
            headView = mHeader.onCreateHeaderView(getContext());
            if (mFooter == null) {
                mFooter = new SimpleVFooter();
            }
            footView = mFooter.onCreateFooterView(getContext());
        } else {
            if (mHeader == null) {
                mHeader = new SimpleHHeader();
            }
            headView = mHeader.onCreateHeaderView(getContext());
            if (mFooter == null) {
                mFooter = new SimpleHFooter();
            }
            footView = mFooter.onCreateFooterView(getContext());
        }
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(headView, lp);
        addView(footView, lp);
        if (mNeedHeader) {
            headView.setVisibility(VISIBLE);
        } else {
            headView.setVisibility(INVISIBLE);
        }
        if (mNeedFooter) {
            footView.setVisibility(VISIBLE);
        } else {
            footView.setVisibility(INVISIBLE);
        }
        if (isNeedAutoFooter) {
            initScrollListener();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isLayout) {
            initView();
            isLayout = true;
        }
        if (isVertical) {
            headerDist = mHeader.getHeaderView().getMeasuredHeight();
            footerDist = mFooter.getFooterView().getMeasuredHeight();
            headView.layout(0,
                    (int) (headerOffDist + footerOffDist) - headView.getMeasuredHeight(),
                    headView.getMeasuredWidth(), (int) (headerOffDist + footerOffDist));
            pullableView.layout(0, (int) (headerOffDist + footerOffDist),
                    pullableView.getMeasuredWidth(), (int) (headerOffDist + footerOffDist)
                            + pullableView.getMeasuredHeight());
            footView.layout(0,
                    (int) (headerOffDist + footerOffDist) + pullableView.getMeasuredHeight(),
                    footView.getMeasuredWidth(),
                    (int) (headerOffDist + footerOffDist) + pullableView.getMeasuredHeight()
                            + footView.getMeasuredHeight());
        } else {
            headerDist = mHeader.getHeaderView().getMeasuredWidth();
            footerDist = mFooter.getFooterView().getMeasuredWidth();
            headView.layout((int) (headerOffDist + footerOffDist) - headView.getMeasuredWidth(),
                    0,
                    (int) (headerOffDist + footerOffDist), headView.getMeasuredHeight());
            pullableView.layout((int) (headerOffDist + footerOffDist), 0,
                    (int) (headerOffDist + footerOffDist)
                            + pullableView.getMeasuredWidth(), pullableView.getMeasuredHeight());
            footView.layout((int) (headerOffDist + footerOffDist) + pullableView.getMeasuredWidth(),
                    0,
                    (int) (headerOffDist + footerOffDist) + pullableView.getMeasuredWidth()
                            + footView.getMeasuredWidth(), footView.getMeasuredHeight()
            );
        }
        if (mListener != null) {
            mListener.onPulling(headerOffDist + footerOffDist);
        }
        mHeader.onPulling(headerOffDist + footerOffDist);
        mFooter.onPulling(headerOffDist + footerOffDist);
    }

    class MyTimer {
        private Handler handler;
        private Timer timer;
        private MyTask mTask;

        public MyTimer(Handler handler) {
            this.handler = handler;
            timer = new Timer();
        }

        public void schedule(long period) {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
            mTask = new MyTask(handler);
            timer.schedule(mTask, 0, period);
        }

        public void cancel() {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
        }

        class MyTask extends TimerTask {
            private Handler handler;

            public MyTask(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }

        }
    }

    private boolean checkContentCanPullHead() {
        if (pullableView instanceof LinearLayout || pullableView instanceof ImageView || pullableView instanceof TextView) {
            return true;
        } else if (pullableView instanceof WebView) {
            if (pullableView.getScrollY() == 0)
                return true;
            else
                return false;
        } else if (pullableView instanceof ScrollView) {
            if (pullableView.getScrollY() == 0)
                return true;
            else
                return false;
        } else if (pullableView instanceof NestedScrollView) {
            if (pullableView.getScrollY() == 0)
                return true;
            else
                return false;
        } else if (pullableView instanceof RecyclerView) {
            if (((RecyclerView) pullableView).getAdapter() == null) {
                return true;
            } else {
                if (((RecyclerView) pullableView).getAdapter().getItemCount() == 0) {
                    // 没有item的时候也可以下拉刷新
                    return true;
                } else if (((RecyclerView) pullableView).getFirstCompleteVisiblePosition() == 0
                        && ((RecyclerView) pullableView).getChildAt(0).getTop() >= 0) {
                    // 滑到顶部了
                    return true;
                } else
                    return false;
            }
        } else if (pullableView instanceof ListView) {
            if (((ListView) pullableView).getCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            } else if (((ListView) pullableView).getFirstVisiblePosition() == 0
                    && ((ListView) pullableView).getChildAt(0).getTop() >= 0) {
                // 滑到ListView的顶部了
                return true;
            } else
                return false;

        } else if (pullableView instanceof GridView) {
            if (((GridView) pullableView).getCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            } else if (((GridView) pullableView).getFirstVisiblePosition() == 0
                    && ((GridView) pullableView).getChildAt(0).getTop() >= 0) {
                // 滑到顶部了
                return true;
            } else
                return false;
        }
        return true;
    }

    private boolean checkContentCanPullFoot() {
        if (pullableView instanceof LinearLayout || pullableView instanceof ImageView || pullableView instanceof TextView) {
            return true;
        } else if (pullableView instanceof WebView) {
            if (pullableView.getScrollY() >= ((WebView) pullableView).getContentHeight() * ((WebView) pullableView).getScale() - pullableView.getMeasuredHeight())
                return true;
            else
                return false;
        } else if (pullableView instanceof ScrollView) {
            if (pullableView.getScrollY() >= (((ScrollView) pullableView).getChildAt(0).getHeight() - pullableView.getMeasuredHeight()))
                return true;
            else
                return false;
        } else if (pullableView instanceof NestedScrollView) {
            if (pullableView.getScrollY() >= (((NestedScrollView) pullableView).getChildAt(0).getHeight() - pullableView.getMeasuredHeight()))
                return true;
            else
                return false;
        } else if (pullableView instanceof RecyclerView) {
            if (((RecyclerView) pullableView).getAdapter() == null) {
                return true;
            } else {
                if (((RecyclerView) pullableView).getAdapter().getItemCount() == 0) {
                    // 没有item的时候也可以上拉加载
                    return true;
                } else if (((RecyclerView) pullableView).getLastVisiblePosition() == (((RecyclerView) pullableView).getAdapter().getItemCount() - 1)) {
                    // 滑到底部了
                    if (((RecyclerView) pullableView).getChildAt(((RecyclerView) pullableView).getLastVisiblePosition() - ((RecyclerView) pullableView).getFirstVisiblePosition()) != null
                            && ((RecyclerView) pullableView).getChildAt(
                            ((RecyclerView) pullableView).getLastVisiblePosition()
                                    - ((RecyclerView) pullableView).getFirstVisiblePosition()).getBottom() <= pullableView.getMeasuredHeight())
                        return true;
                }
                return false;
            }
        } else if (pullableView instanceof ListView) {
            if (((ListView) pullableView).getCount() == 0) {
                // 没有item的时候也可以上拉加载
                return true;
            } else if (((ListView) pullableView).getLastVisiblePosition() == (((ListView) pullableView).getCount() - 1)) {
                // 滑到底部了
                if (((ListView) pullableView).getChildAt(((ListView) pullableView).getLastVisiblePosition() - ((ListView) pullableView).getFirstVisiblePosition()) != null
                        && ((ListView) pullableView).getChildAt(
                        ((ListView) pullableView).getLastVisiblePosition()
                                - ((ListView) pullableView).getFirstVisiblePosition()).getBottom() <= pullableView.getMeasuredHeight())
                    return true;
            }
            return false;
        } else if (pullableView instanceof GridView) {
            if (((GridView) pullableView).getCount() == 0) {
                // 没有item的时候也可以上拉加载
                return true;
            } else if (((GridView) pullableView).getLastVisiblePosition() == (((GridView) pullableView).getCount() - 1)) {
                // 滑到底部了
                if (((GridView) pullableView).getChildAt(((GridView) pullableView).getLastVisiblePosition() - ((GridView) pullableView).getFirstVisiblePosition()) != null
                        && ((GridView) pullableView).getChildAt(
                        ((GridView) pullableView).getLastVisiblePosition()
                                - ((GridView) pullableView).getFirstVisiblePosition()).getBottom() <= pullableView.getMeasuredHeight())
                    return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 刷新加载回调接口
     *
     * @author chenjing
     */
    public static class PullListener {
        /**
         * 刷新操作
         */
        public void onHeading(PullLayout pullToRefreshLayout) {
            pullToRefreshLayout.headFinish();
        }

        /**
         * 加载操作
         */
        public void onFooting(PullLayout pullToRefreshLayout) {
            pullToRefreshLayout.footFinish();
        }

        public void onPulling(float dist) {
        }
    }

}
