package library.mlibrary.view.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import library.mlibrary.R;

/**
 * 拦截子控件所有触摸事件
 */
public class InterceptContainerView extends LinearLayout {

    private boolean Intercept;

    public InterceptContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InterceptContainerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InterceptContainerView, defStyle, 0);
        Intercept = a.getBoolean(R.styleable.InterceptContainerView_ISC_intercept, true);
    }

    public InterceptContainerView(Context context) {
        this(context, null);
    }

    public void setIntercept(boolean b) {
        Intercept = b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return Intercept;
    }

}
