package library.mlibrary.view.dialog;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import library.mlibrary.R;
import library.mlibrary.base.AbsBaseActivity;
import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.InjectUtil;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.dialog.effects.BaseEffects;

/**
 * Created by Harmy on 2016/5/30 0030
 */
public abstract class AbsBaseAnimateDialog extends Dialog {
    /**
     * 因为Dialog将构造方法内的context转成ContextThemeWrapper，
     * 之后会造成context无法再转成activity，所以此处自己将context赋值给activity
     * 并提供getActivity方法
     */
    private AbsBaseActivity mActivity;

    private AbsBaseAnimateDialog mThis;

    public AbsBaseAnimateDialog(Context context) {
        super(context, R.style.CommonDialog);
        mActivity = (AbsBaseActivity) context;
        init();
    }

    public AbsBaseAnimateDialog(Context context, int themeResId) {
        super(context, themeResId);
        mActivity = (AbsBaseActivity) context;
        init();
    }

    protected AbsBaseAnimateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mActivity = (AbsBaseActivity) context;
        init();
    }

    public AbsBaseActivity getActivity() {
        return mActivity;
    }

    public AbsBaseAnimateDialog getThis() {
        return mThis;
    }

    private LinearLayout mMainLL;
    private RelativeLayout mBaseRL;

    private void init() {
        mThis = this;
        super.setContentView(R.layout.dialog_basedialog);
        mMainLL = (LinearLayout) findViewById(R.id.mainLL);
        mBaseRL = (RelativeLayout) findViewById(R.id.baseRL);
        mBaseRL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mOnOutSideTouchListener != null) {
                    mOnOutSideTouchListener.onTouch(v, event);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isCancelable && isOutSideCancelable) {
                        dismiss();
                    }
                }
                return false;
            }
        });
        super.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (mOnShowListener != null) {
                    mOnShowListener.onShow(dialog);
                }
                if (mEnableEffect) {
                    if (mShowEffect == null) {
                        mShowEffect = Effectstype.SlideBottom;
                    }
                    BaseEffects animator = mShowEffect.getAnimator();
                    animator.setDuration(Math.abs(mDuration));
                    animator.start(mMainLL);
                }
            }
        });
    }

    private boolean isCancelable = true;
    private boolean isOutSideCancelable = true;

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        isOutSideCancelable = cancel;
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        isCancelable = cancelable;
    }

    @Override
    public void setContentView(int layoutResID) {
        mMainLL.addView(LayoutInflater.from(getContext()).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        mMainLL.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mMainLL.addView(view, params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        onSetContentView();
        afterSetContentView();
        onFindView();
        injectView();
        initWindow();
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

    private OnShowListener mOnShowListener;

    @Override
    public void setOnShowListener(OnShowListener listener) {
        mOnShowListener = listener;
    }

    public void setGravity(int... gravitys) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mMainLL.getLayoutParams();
        for (int i = 0; i < gravitys.length; i++) {
            int align = gravitys[i];
            if (align == RelativeLayout.ALIGN_PARENT_BOTTOM) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.ALIGN_PARENT_TOP) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.ALIGN_PARENT_LEFT) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.ALIGN_PARENT_RIGHT) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.CENTER_HORIZONTAL) {
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.CENTER_VERTICAL) {
                lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.CENTER_IN_PARENT) {
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            }
        }
    }

    /**
     * @param widthscale  宽度占屏幕宽度比例
     * @param heightscale 高度占屏幕宽度比例
     * @param gravity
     */
    public void initWindow(float widthscale, float heightscale, int... gravity) {
        initWindow(widthscale, heightscale, true, gravity);
    }


    /**
     * @param widthscale  宽度占屏幕宽度比例
     * @param heightscale 高度占屏幕宽度比例
     */
    public void initWindow(float widthscale, float heightscale) {
        initWindow(widthscale, heightscale, true, RelativeLayout.CENTER_IN_PARENT);
    }

    /**
     * @param widthscale  宽度占屏幕宽度比例
     * @param heightscale 高度占屏幕宽度比例
     * @param outcancel   是否点击外部消失
     * @param gravitys    RelativeLayout.ALIGN_PARENT_BOTTOM....
     */
    public void initWindow(float widthscale, float heightscale, boolean outcancel, int... gravitys) {

        setCanceledOnTouchOutside(outcancel);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mMainLL.getLayoutParams();
        if (widthscale > 0) {
            int width = CommonUtil.getDisplayMetrics(getContext()).widthPixels;
            lp.width = (int) (widthscale * width);
        }
        if (heightscale > 0) {
            int height = CommonUtil.getDisplayMetrics(getContext()).heightPixels;
            lp.height = (int) (heightscale * height);
        }
        for (int i = 0; i < gravitys.length; i++) {
            int align = gravitys[i];
            if (align == RelativeLayout.ALIGN_PARENT_BOTTOM) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.ALIGN_PARENT_TOP) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.ALIGN_PARENT_LEFT) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.ALIGN_PARENT_RIGHT) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.CENTER_HORIZONTAL) {
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.CENTER_VERTICAL) {
                lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            }
            if (align == RelativeLayout.CENTER_IN_PARENT) {
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            }
        }
    }

    private void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.drawable.shape_transparent);
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    private Effectstype mShowEffect = Effectstype.SlideBottom;
    private Effectstype mDismissEffect = Effectstype.SlideBottomDismiss;

    @Override
    public void show() {
        super.show();
    }

    public void show(Effectstype type, long duration) {
        showeffect(type);
        duration(duration);
        show();
    }

    private long mDuration = 300;

    public void showeffect(Effectstype type) {
        this.mShowEffect = type;
    }

    public void dismisseffect(Effectstype type) {
        this.mDismissEffect = type;
    }

    public void duration(long duration) {
        mDuration = duration;
    }

    public void dismiss(Effectstype type, long duration) {
        dismisseffect(type);
        duration(duration);
        dismiss();
    }

    @Override
    public void dismiss() {
        if (mEnableEffect) {
            if (mDismissEffect == null) {
                mDismissEffect = Effectstype.SlideBottomDismiss;
            }
            BaseEffects animator = mDismissEffect.getAnimator();
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    try {
                        AbsBaseAnimateDialog.super.dismiss();
                    } catch (Exception e) {
                        LogDebug.e(e);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setDuration(Math.abs(mDuration));
            animator.start(mMainLL);
        } else {
            super.dismiss();
        }
    }

    private boolean mEnableEffect = true;

    /**
     * 禁用启用动画
     *
     * @param enable
     */
    public void enableEffect(boolean enable) {
        mEnableEffect = enable;
    }

    private View.OnTouchListener mOnOutSideTouchListener;

    public void setOnOutSideTouchListener(View.OnTouchListener mOnOutSideTouchListener) {
        this.mOnOutSideTouchListener = mOnOutSideTouchListener;
    }
}
