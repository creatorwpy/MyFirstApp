package library.mlibrary.view.expandlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Harmy on 2015/12/31 0031.
 */
public class MaxHeightExpandableListView extends ExpandableListView {
    public MaxHeightExpandableListView(Context context) {
        super(context);
    }

    public MaxHeightExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
