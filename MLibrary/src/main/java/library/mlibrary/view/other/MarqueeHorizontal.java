package library.mlibrary.view.other;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by Harmy on 2015/12/3 0003.
 */
public class MarqueeHorizontal extends HorizontalScrollView implements Runnable {
    private int currentScrollX; // 当前滚动的位置
    private boolean isStop = false;
    private int childwidth;
    private boolean isMeasure = false;

    public MarqueeHorizontal(Context context) {
        super(context);
    }

    public MarqueeHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeHorizontal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasure) {
            getChildWidth();
            isMeasure = true;
        }
    }

    private void getChildWidth() {
        childwidth = getChildAt(0).getWidth();
    }

    public static final int LEFT_RIGHT = 0;
    public static final int RIGHT_LEFT = 1;
    public static final int GO_BACK = 2;

    private int mDirection = LEFT_RIGHT;

    /**
     * 设置滚动方向
     *
     * @param direction MarqueeHorizontal.LEFT_RIGHT;MarqueeHorizontal.RIGHT_LEFT;MarqueeHorizontal.GO_BACK
     */
    public void setDirection(int direction) {
        mDirection = direction;
    }

    private int mSpeed = 1;
    private int mTime = 10;

    /**
     * 值越小速度越快,默认10
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        mTime = speed;
    }

    private int mDelayTime = 1000;

    /**
     * 设置到头部和到尾部时停留的时间，毫秒
     */
    public void setDelayTime(int time) {
        mDelayTime = time;
    }

    private void scrollTo() {
        scrollTo(currentScrollX, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        currentScrollX = l;
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private boolean isGo = true;

    @Override
    public void run() {
        if (isStop) {
            return;
        }
        switch (mDirection) {
            case LEFT_RIGHT:
                currentScrollX += mSpeed;
                if (getScrollX() >= childwidth - this.getWidth()) {
                    scrollTo();
                    postDelayed(this, mDelayTime);
                    currentScrollX = 0;
                } else {
                    scrollTo();
                    postDelayed(this, mTime);
                }
                break;
            case RIGHT_LEFT:
                currentScrollX -= mSpeed;
                if (getScrollX() <= 0) {
                    scrollTo();
                    postDelayed(this, mDelayTime);
                    currentScrollX = childwidth - this.getWidth();
                } else {
                    scrollTo();
                    postDelayed(this, mTime);
                }
                break;
            case GO_BACK:
                if (isGo) {
                    currentScrollX += mSpeed;
                    if (getScrollX() >= childwidth - this.getWidth()) {
                        scrollTo();
                        postDelayed(this, mDelayTime);
                        isGo = false;
                    } else {
                        scrollTo();
                        postDelayed(this, mTime);
                    }
                } else {
                    currentScrollX -= mSpeed;
                    if (getScrollX() <= 0) {
                        scrollTo();
                        postDelayed(this, mDelayTime);
                        isGo = true;
                    } else {
                        scrollTo();
                        postDelayed(this, mTime);
                    }
                }
                break;
        }
    }

    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }


    // 停止滚动
    public void stopScroll() {
        isStop = true;
    }


    // 从头开始滚动
    public void startFromHead() {
        if (isMeasure) {
            currentScrollX = 0;
            startScroll();
        } else {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startFromHead();
                }
            }, 5);
        }

    }

    // 从尾开始滚动
    public void startFromTail() {
        if (isMeasure) {
            currentScrollX = childwidth - this.getWidth();
            startScroll();
        } else {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startFromTail();
                }
            }, 5);
        }
    }

}
