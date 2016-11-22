package library.mlibrary.view.fixwh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Harmy on 2016/10/4 0004.
 */

public class FixWidthTextView extends TextView {
    public FixWidthTextView(Context context) {
        super(context);
    }

    public FixWidthTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixWidthTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
