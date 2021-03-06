package library.mlibrary.view.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Harmy on 2015/9/22 0022.
 */
public class PullableLinearLayout extends LinearLayout implements Pullable {
    public PullableLinearLayout(Context context) {
        super(context);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
    }
}
