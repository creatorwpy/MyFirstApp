package library.mlibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import library.mlibrary.R;
import library.mlibrary.base.AbsBaseActivity;
import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.InjectUtil;

/**
 * Created by Harmy on 2016/4/7 0007.
 */
public abstract class AbsBaseDialog extends Dialog {
    /**
     * 因为Dialog将构造方法内的context转成ContextThemeWrapper，
     * 之后会造成context无法再转成activity，所以此处自己将context赋值给activity
     * 并提供getActivity方法
     */
    private AbsBaseActivity mActivity;

    private AbsBaseDialog mThis;

    public AbsBaseDialog(Context context) {
        super(context, R.style.CommonDialog);
        mActivity = (AbsBaseActivity) context;
    }

    public AbsBaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mActivity = (AbsBaseActivity) context;
    }

    protected AbsBaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mActivity = (AbsBaseActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        onSetContentView();
        afterSetContentView();
        onFindView();
        injectView();
        initViews();
        afterOnCreate(savedInstanceState);
        onSetListener();
    }

    @Override
    public void cancel() {
        beforeCancel();
        super.cancel();
    }

    protected void beforeCancel() {
    }

    public AbsBaseActivity getActivity() {
        return mActivity;
    }

    public AbsBaseDialog getThis() {
        return mThis;
    }

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView() {
        InjectUtil.injectDialogFields(this);
    }

    protected void beforeSetContentView() {

    }

    protected abstract void onSetContentView();

    protected void afterSetContentView() {

    }

    protected void onFindView() {
    }

    protected abstract void initViews();

    protected abstract void onSetListener();

    protected abstract void afterOnCreate(Bundle savedInstanceState);


    /**
     * @param widthscale  宽度占屏幕宽度比例
     * @param heightscale 宽度占屏幕宽度比例
     * @param gravity
     */
    public void initWindow(float widthscale, float heightscale, int gravity) {
        initWindow(widthscale, heightscale, gravity, true);
    }

    /**
     * @param widthscale  宽度占屏幕宽度比例
     * @param heightscale 宽度占屏幕宽度比例
     */
    public void initWindow(float widthscale, float heightscale) {
        initWindow(widthscale, heightscale, Gravity.CENTER, true);
    }

    /**
     * @param widthscale  宽度占屏幕宽度比例
     * @param heightscale 宽度占屏幕宽度比例
     * @param gravity
     * @param outcancel   是否点击外部消失
     */
    public void initWindow(float widthscale, float heightscale, int gravity, boolean outcancel) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (gravity == Gravity.NO_GRAVITY) {
            window.setGravity(Gravity.CENTER);
        } else {
            window.setGravity(gravity);
        }
        window.setBackgroundDrawableResource(R.drawable.shape_transparent);
        setCanceledOnTouchOutside(outcancel);
        if (widthscale > 0) {
            int width = CommonUtil.getDisplayMetrics(getContext()).widthPixels;
            lp.width = (int) (widthscale * width);
        }
        if (heightscale > 0) {
            int height = CommonUtil.getDisplayMetrics(getContext()).heightPixels;
            lp.height = (int) (heightscale * height);
        }
        window.setAttributes(lp);
    }
}
