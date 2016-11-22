package library.mlibrary.view.fixwh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Harmy on 2016/10/4 0004.
 */

public class FixHeightImageView extends ImageView {
    public FixHeightImageView(Context context) {
        super(context);
    }

    public FixHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixHeightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
