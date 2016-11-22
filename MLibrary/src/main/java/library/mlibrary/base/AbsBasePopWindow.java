package library.mlibrary.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import library.mlibrary.R;
import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.InjectUtil;

/**
 * Created by Harmy on 2016/4/7 0007.
 */
public abstract class AbsBasePopWindow {
    private PopupWindow mPopupWindow;
    private Context mContext;

    public AbsBasePopWindow(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    private View mLayoutView;
    private int mLayoutId = -1;


    public void create() {
        beforeSetContentView();
        onSetContentView();
        afterSetContentView();
        if (mLayoutId != -1) {
            mLayoutView = LayoutInflater.from(getContext()).inflate(mLayoutId, null);
        }
        onSetAttr();
        mPopupWindow = new PopupWindow(mLayoutView, mWidth, mHeight, mFocusable);
        mPopupWindow.setOutsideTouchable(mOutTouchable);
        mPopupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_transparent));
        injectView();
        onFindView(mLayoutView);
        initViews();
        afterOnCreate();
        onSetListener();
    }

    public PopupWindow getPopupWindow() {
        if (mPopupWindow == null) {
            create();
        }
        return mPopupWindow;
    }

    private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setWidth(float scale) {
        mWidth = (int) (CommonUtil.getDisplayMetrics(getContext()).widthPixels * scale);
    }

    private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setHeight(float scale) {
        mHeight = (int) (CommonUtil.getDisplayMetrics(getContext()).heightPixels * scale);
    }

    private boolean mFocusable = true;
    private boolean mOutTouchable = true;

    public void setOutTouchable(boolean outtouchable) {
        mOutTouchable = outtouchable;
    }

    public void setFocusable(boolean focusable) {
        mFocusable = focusable;
    }

    public void setOutTouchCancel(boolean outcancel) {
        if (!outcancel) {
            setOutTouchable(false);
            setFocusable(false);
        } else {
            if (!mFocusable && !mOutTouchable) {
                setOutTouchable(true);
            }
        }
    }

    public abstract void onSetAttr();

    public void setContentView(int resId) {
        mLayoutId = resId;
    }

    public void setContentView(View view) {
        mLayoutView = view;
    }

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView() {
        InjectUtil.injectObjectFields(this, mLayoutView);
    }

    protected void beforeSetContentView() {

    }

    protected abstract void onSetContentView();

    protected void afterSetContentView() {

    }


    protected void onFindView(View view) {
    }

    protected abstract void initViews();

    protected abstract void onSetListener();

    protected abstract void afterOnCreate();

    public void showAsDropDown(View view) {
        if (mPopupWindow == null) {
            create();
        }
        mPopupWindow.showAsDropDown(view);
    }

    public void showAsDropDown(View view, int offx, int offy) {
        if (mPopupWindow == null) {
            create();
        }
        mPopupWindow.showAsDropDown(view, offx, offy);
    }

    @SuppressLint("NewApi")
    public void showAsDropDown(View view, int offx, int offy, int gravity) {
        if (mPopupWindow == null) {
            create();
        }
        mPopupWindow.showAsDropDown(view, offx, offy, gravity);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow == null) {
            create();
        }
        mPopupWindow.showAtLocation(parent, gravity, x, y);
    }

    public void dismiss() {
        if (mPopupWindow == null) {
            create();
        }
        mPopupWindow.dismiss();
    }
}
