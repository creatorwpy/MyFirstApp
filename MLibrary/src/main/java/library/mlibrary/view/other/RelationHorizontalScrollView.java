package library.mlibrary.view.other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;

import library.mlibrary.util.common.CommonUtil;

/**
 * Created by Harmy on 2015/11/9 0009.
 */
public class RelationHorizontalScrollView extends HorizontalScrollView {
    private ArrayList<RelationHorizontalScrollView> mRelationViews;

    public RelationHorizontalScrollView(Context context) {
        super(context);
    }

    public RelationHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelationHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addRelatiedView(RelationHorizontalScrollView view) {
        if (mRelationViews == null) {
            mRelationViews = new ArrayList<>();
        }
        if (!mRelationViews.contains(view)) {
            mRelationViews.add(view);
        }
    }

//    public void onScroll(int l, int t) {
//        super.scrollTo(l, t);
//    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (!CommonUtil.isEmpty(mRelationViews)) {
            for (RelationHorizontalScrollView view : mRelationViews) {
                view.scrollTo(l, t);
            }
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
