package library.mlibrary.view.wheelview;

import android.view.MotionEvent;


public class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

   private LoopView loopView;

    public LoopViewGestureListener(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
