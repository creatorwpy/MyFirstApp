package library.mlibrary.view.other;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by Harmy on 2015/10/28 0028.
 */
public class DrawerListener implements DrawerLayout.DrawerListener {
    private int times = 0;
    private float firstOff;
    private float secondOff;

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (times == 0) {
            firstOff = slideOffset;
        }
        if (times == 1) {
            secondOff = slideOffset;
            if (secondOff > firstOff) {
                onDrawerStartOpen(drawerView);
            } else {
                onDrawerStartClose(drawerView);
            }
        }
        times++;
        onDrawerSliding(drawerView, slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {
        switch (newState) {
            case DrawerLayout.STATE_DRAGGING:
                break;
            case DrawerLayout.STATE_SETTLING:
                break;
            case DrawerLayout.STATE_IDLE:
                times = 0;
                break;
        }
    }

    public void onDrawerSliding(View drawerView, float slideOffset) {
//        if ("navigation_left".equals(drawerView.getTag())) {
//            float rightScale = 1 - slideOffset * 0.2f;
//            float offwidth = drawerView.getMeasuredWidth() * slideOffset;
//            float leftScale = 0.6f + 0.4f * slideOffset;
//            ViewHelper.setPivotX(drawerView, 0);
//            ViewHelper.setScaleX(drawerView, slideOffset);
//            ViewHelper.setScaleY(drawerView, leftScale);
//            ViewHelper.setAlpha(drawerView, 0.1f + 0.9f * slideOffset);
//            ViewHelper.setTranslationX(drawerView, drawerView.getMeasuredWidth() - offwidth);
//            ViewHelper.setTranslationX(contentLL, offwidth);
//            ViewHelper.setPivotX(contentLL, 0);
//            ViewHelper.setPivotY(contentLL, contentLL.getMeasuredHeight() / 2);
//            ViewHelper.setScaleX(contentLL, rightScale);
//            ViewHelper.setScaleY(contentLL, rightScale);
//        } else {
//            float leftScale = 1 - slideOffset * 0.2f;
//            float offwidth = drawerView.getMeasuredWidth() * slideOffset;
//            float rightScale = 0.6f + 0.4f * slideOffset;
//            ViewHelper.setPivotX(drawerView, drawerView.getMeasuredWidth());
//            ViewHelper.setScaleX(drawerView, slideOffset);
//            ViewHelper.setScaleY(drawerView, rightScale);
//            ViewHelper.setAlpha(drawerView, 0.1f + 0.9f * slideOffset);
//            ViewHelper.setTranslationX(drawerView, offwidth - drawerView.getMeasuredWidth());
//            ViewHelper.setTranslationX(contentLL, -offwidth);
//            ViewHelper.setPivotX(contentLL, contentLL.getMeasuredWidth());
//            ViewHelper.setPivotY(contentLL, contentLL.getMeasuredHeight() / 2);
//            ViewHelper.setScaleX(contentLL, leftScale);
//            ViewHelper.setScaleY(contentLL, leftScale);
//        }
    }

    public void onDrawerStartOpen(View drawerView) {

    }

    public void onDrawerStartClose(View drawerView) {

    }
}
