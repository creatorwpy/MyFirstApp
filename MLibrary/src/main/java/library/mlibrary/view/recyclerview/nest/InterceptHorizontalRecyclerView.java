package library.mlibrary.view.recyclerview.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import library.mlibrary.view.recyclerview.RecyclerView;

/**
 * 拦截父view的横向触摸事件
 * Created by harmy on 2016/9/18 0018.
 */
public class InterceptHorizontalRecyclerView extends RecyclerView {
    public InterceptHorizontalRecyclerView(Context context) {
        super(context);
    }

    public InterceptHorizontalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptHorizontalRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float downX = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowX = e.getX();
                if (Math.abs(nowX - downX) > 5) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }
}
