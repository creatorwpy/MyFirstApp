package library.mlibrary.view.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Harmy on 2015/11/9 0009.
 */
public class PullLinearWithChild extends LinearLayout implements Pullable {
    public PullLinearWithChild(Context context) {
        super(context);
    }

    public PullLinearWithChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullLinearWithChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        View view = null;
        for (int i = 0; i < getChildCount(); i++) {
            if ("tag_pullview".equals(getChildAt(i).getTag())) {
                view = getChildAt(i);
            }
        }
        if (view == null) {
            return true;
        } else
            return ((Pullable) view).canPullDown();
    }

    @Override
    public boolean canPullUp() {
        View view = null;
        for (int i = 0; i < getChildCount(); i++) {
            if ("tag_pullview".equals(getChildAt(i).getTag())) {
                view = getChildAt(i);
            }
        }
        if (view == null) {
            return true;
        } else
            return ((Pullable) view).canPullUp();
    }

}