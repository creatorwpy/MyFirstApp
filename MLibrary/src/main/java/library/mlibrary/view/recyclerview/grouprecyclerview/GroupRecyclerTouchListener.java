package library.mlibrary.view.recyclerview.grouprecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

public class GroupRecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private final GestureDetector mTapDetector;
    private final RecyclerView mRecyclerView;
    private final GroupRecyclerDecoration mDecor;
    private OnHeaderClickListener mOnHeaderClickListener;

    public interface OnHeaderClickListener {
        void onHeaderClick(View header, int position, long headerId);
    }

    public GroupRecyclerTouchListener(final RecyclerView recyclerView,
                                      final GroupRecyclerDecoration decor) {
        mTapDetector = new GestureDetector(recyclerView.getContext(), new SingleTapDetector());
        mRecyclerView = recyclerView;
        mDecor = decor;
    }

    public GroupRecyclerImp getAdapter() {
        if (mRecyclerView.getAdapter() instanceof GroupRecyclerImp) {
            return (GroupRecyclerImp) mRecyclerView.getAdapter();
        } else {
            throw new IllegalStateException("A RecyclerView with " +
                    GroupRecyclerTouchListener.class.getSimpleName() +
                    " requires a " + GroupRecyclerImp.class.getSimpleName());
        }
    }


    public void setOnHeaderClickListener(OnHeaderClickListener listener) {
        mOnHeaderClickListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        if (this.mOnHeaderClickListener != null) {
            boolean tapDetectorResponse = this.mTapDetector.onTouchEvent(e);
            if (tapDetectorResponse) {
                // Don't return false if a single tap is detected
                return true;
            }
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
                return position != -1;
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent e) { /* do nothing? */ }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // do nothing
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
            if (position != -1) {
                View headerView = mDecor.getHeaderView(mRecyclerView, position);
                long headerId = getAdapter().getHeaderId(position);
                mOnHeaderClickListener.onHeaderClick(headerView, position, headerId);
                mRecyclerView.playSoundEffect(SoundEffectConstants.CLICK);
                headerView.onTouchEvent(e);
                return true;
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
}
