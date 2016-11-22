package library.mlibrary.view.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 可以对内部item拖动排序
 * Created by Harmy on 2015/11/30 0030.
 */
public class DragRecyclerView extends RecyclerView {
    private static final int DRAG_IMG_SHOW = 1;
    private static final int DRAG_IMG_NOT_SHOW = 0;
    private static final float AMP_FACTOR = 0.75f;
    private static final int INVALID_POSITION = -1;

    /**
     * 被拖动的视图
     */
    private ImageView dragImageView;
    private WindowManager.LayoutParams dragImageViewParams;
    private WindowManager windowManager;
    private boolean isViewOnDrag = false;

    /**
     * 前一次拖动的位置
     */
    private int preDraggedOverPositon = INVALID_POSITION;
    private int downRawX;
    private int downRawY;

    private DragListener mDragListener;

    public interface DragListener {
        public void onStart();

        public void onMoved(int from, int to);

        public void onFinish();
    }

    public DragRecyclerView(Context context) {
        this(context, null);
    }

    public DragRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        dragImageView = new ImageView(getContext());
        dragImageView.setTag(DRAG_IMG_NOT_SHOW);
        dragImageViewParams = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public void setDragListener(DragListener listener) {
        setNeedDrag(true);
        mDragListener = listener;
    }

    private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public void onItemLongClick(View view, int position) {
            if (mDragListener != null) {
                mDragListener.onStart();
            }
            //获取被长按item的drawing cache
            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(true);
            //通过被长按item，获取拖动item的bitmap
            Bitmap dragBitmap = Bitmap.createBitmap(view.getDrawingCache());

            //设置拖动item的参数
            dragImageViewParams.gravity = Gravity.TOP | Gravity.LEFT;
            dragImageViewParams.width = (int) (AMP_FACTOR * dragBitmap.getWidth());
            dragImageViewParams.height = (int) (AMP_FACTOR * dragBitmap.getHeight());
            //设置触摸点为绘制拖动item的中心
            dragImageViewParams.x = (downRawX - dragImageViewParams.width / 2);
            dragImageViewParams.y = (downRawY - dragImageViewParams.height / 2);
            dragImageViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            dragImageViewParams.format = PixelFormat.TRANSLUCENT;
            dragImageViewParams.windowAnimations = 0;

            //dragImageView为被拖动item的容器，清空上一次的显示
            if ((int) dragImageView.getTag() == DRAG_IMG_SHOW) {
                windowManager.removeView(dragImageView);
                dragImageView.setTag(DRAG_IMG_NOT_SHOW);
            }

            //设置本次被长按的item
            dragImageView.setImageBitmap(dragBitmap);

            //添加拖动item到屏幕
            windowManager.addView(dragImageView, dragImageViewParams);
            dragImageView.setTag(DRAG_IMG_SHOW);
            isViewOnDrag = true;
        }
    };

    /**
     * 设置是否开启拖动
     *
     * @param b
     */
    public void setNeedDrag(boolean b) {
        if (b) {
            setOnItemLongClickListener(mOnItemLongClickListener);
        } else {
            setOnItemLongClickListener(null);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            downRawX = (int) e.getRawX();
            downRawY = (int) e.getRawY();
            preDraggedOverPositon = pointToPosition(e.getX(), e.getY());
        }//释放dragImageView
        else if ((e.getAction() == MotionEvent.ACTION_UP) && (isViewOnDrag == true)) {
            if (mDragListener != null) {
                mDragListener.onFinish();
            }
            if ((int) dragImageView.getTag() == DRAG_IMG_SHOW) {
                windowManager.removeView(dragImageView);
                dragImageView.setTag(DRAG_IMG_NOT_SHOW);
            }
            isViewOnDrag = false;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //dragImageView处于被拖动时，更新dragImageView位置
        if ((ev.getAction() == MotionEvent.ACTION_MOVE) && (isViewOnDrag == true)) {
            //设置触摸点为dragImageView中心
            dragImageViewParams.x = (int) (ev.getRawX() - dragImageView.getWidth() / 2);
            dragImageViewParams.y = (int) (ev.getRawY() - dragImageView.getHeight() / 2);
            //更新窗口显示
            windowManager.updateViewLayout(dragImageView, dragImageViewParams);
            //获取当前触摸点的item position
            int currentPosition = pointToPosition(ev.getX(), ev.getY());
            //如果当前停留位置item不等于上次停留位置的item，交换本次和上次停留的item
            if (currentPosition != preDraggedOverPositon && currentPosition != INVALID_POSITION) {
                getAdapter().notifyItemMoved(preDraggedOverPositon, currentPosition);
                if (mDragListener != null) {
                    mDragListener.onMoved(preDraggedOverPositon, currentPosition);
                }
                preDraggedOverPositon = currentPosition;
            }
        } else if ((ev.getAction() == MotionEvent.ACTION_UP) && (isViewOnDrag == true)) {
            if ((int) dragImageView.getTag() == DRAG_IMG_SHOW) {
                windowManager.removeView(dragImageView);
                dragImageView.setTag(DRAG_IMG_NOT_SHOW);
            }
            isViewOnDrag = false;
            if (mDragListener != null) {
                mDragListener.onFinish();
            }
        }

        return super.onTouchEvent(ev);
    }
}
