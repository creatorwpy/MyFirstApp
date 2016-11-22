package library.mlibrary.view.fixwh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Harmy on 2016/10/4 0004.
 */

public class FixWidthImageView extends ImageView {
    public FixWidthImageView(Context context) {
        super(context);
    }

    public FixWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixWidthImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
