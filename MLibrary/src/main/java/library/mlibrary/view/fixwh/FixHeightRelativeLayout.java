package library.mlibrary.view.fixwh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Harmy on 2016/10/4 0004.
 */

public class FixHeightRelativeLayout extends RelativeLayout {
    public FixHeightRelativeLayout(Context context) {
        super(context);
    }

    public FixHeightRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixHeightRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
