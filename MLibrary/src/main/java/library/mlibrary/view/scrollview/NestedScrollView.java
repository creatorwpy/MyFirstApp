package library.mlibrary.view.scrollview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Harmy on 2016/10/16 0016.
 */

public class NestedScrollView extends android.support.v4.widget.NestedScrollView {
    public NestedScrollView(Context context) {
        super(context);
    }

    public NestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 检查ScrollView的最终状态
    private static final int CHECK_STATE = 0;
    // 外部设置的监听方法
    private OnScrollListener onScrollListener;
    // 是否在触摸状态
    private boolean inTouch = false;
    // 上次滑动的最后位置
    private int lastT = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                inTouch = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                inTouch = false;

                lastT = getScrollY();
                checkStateHandler.removeMessages(CHECK_STATE);// 确保只在最后一次做这个check
                checkStateHandler.sendEmptyMessageDelayed(CHECK_STATE, 5);// 5毫秒检查一下

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener == null) {
            return;
        }
        if (inTouch) {
            if (t != oldt) {
                // 有手指触摸，并且位置有滚动
                onScrollListener.onScrollStateChanged(this,
                        OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }
        } else {
            if (t != oldt) {
                // 没有手指触摸，并且位置有滚动，就可以简单的认为是在fling
                onScrollListener.onScrollStateChanged(this,
                        OnScrollListener.SCROLL_STATE_FLING);
                // 记住上次滑动的最后位置
                lastT = t;
                checkStateHandler.removeMessages(CHECK_STATE);// 确保只在最后一次做这个check
                checkStateHandler.sendEmptyMessageDelayed(CHECK_STATE, 5);// 5毫秒检查一下
            }
        }
        onScrollListener.onScrollChanged(l, t, oldl, oldt);
    }

    private Handler checkStateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(onScrollListener!=null){
                if (lastT == getScrollY()) {
                    // 如果上次的位置和当前的位置相同，可认为是在空闲状态
                    onScrollListener.onScrollStateChanged(NestedScrollView.this,
                            OnScrollListener.SCROLL_STATE_IDLE);
                    if (getScrollY() + getHeight() >= computeVerticalScrollRange()) {
                        onScrollListener.onBottomArrived();
                    } else {
                    }
                }
            }
        }
    };

    /**
     * 设置滚动监听事件
     *
     * @param onScrollListener {@link OnScrollListener} 滚动监听事件（注意类的不同，虽然名字相同）
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 滚动监听事件
     *
     * @author 拉风的道长
     */
    public static class OnScrollListener {
        /**
         * The view is not scrolling. Note navigating the list using the
         * trackball counts as being in the idle state since these transitions
         * are not animated.
         */
        public static int SCROLL_STATE_IDLE = 0;

        /**
         * The user is scrolling using touch, and their finger is still on the
         * screen
         */
        public static int SCROLL_STATE_TOUCH_SCROLL = 1;

        /**
         * The user had previously been scrolling using touch and had performed
         * a fling. The animation is now coasting to a stop
         */
        public static int SCROLL_STATE_FLING = 2;

        /**
         * 滑动到底部回调
         */
        public void onBottomArrived() {
        }

        /**
         * 滑动状态回调
         *
         * @param view        当前的scrollView
         * @param scrollState 当前的状态
         */
        public void onScrollStateChanged(NestedScrollView view,
                                         int scrollState) {
        }

        /**
         * 滑动位置回调
         *
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
        }
    }
}
