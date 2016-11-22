package com.wpy.xh.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wpy.xh.R;
import com.wpy.xh.base.BaseDialog;

import library.mlibrary.util.inject.Inject;

/**
 * Created by harmy on 2016/8/11 0011.
 */
public class CommonDialog extends BaseDialog implements View.OnClickListener {
    public CommonDialog(Context context) {
        super(context);
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CommonDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onSetContentView() {
        setContentView(R.layout.dialog_common);
    }

    @Override
    protected void initViews() {
        initWindow(0.65f, 0);
        if (mTitle != null) {
            tv_title.setText(mTitle);
        }
        if (mLeft != null) {
            tv_left.setText(mLeft);
        }
        if (mRight != null) {
            tv_right.setText(mRight);
        }
        if (needRightColor) {
            tv_right.setTextColor(getContext().getResources().getColor(R.color.themegreen));
        } else {
            tv_right.setTextColor(getContext().getResources().getColor(R.color.subtitle));
        }
        if (needLeftColor) {
            tv_left.setTextColor(getContext().getResources().getColor(R.color.themegreen));
        } else {
            tv_left.setTextColor(getContext().getResources().getColor(R.color.subtitle));
        }
        if (isForce) {
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
    }

    @Override
    protected void onSetListener() {
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                if (mListener != null) {
                    mListener.onLeftClick();
                }
                break;
            case R.id.tv_right:
                if (mListener != null) {
                    mListener.onRightClick();
                }
                break;
        }
        cancel();
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    @Inject(R.id.tv_title)
    private TextView tv_title;
    @Inject(R.id.tv_left)
    private TextView tv_left;
    @Inject(R.id.tv_right)
    private TextView tv_right;

    private String mTitle;
    private String mLeft;
    private String mRight;

    private boolean needRightColor = false;
    private boolean needLeftColor = false;

    public void setTitle(String title) {
        mTitle = title;
        if (tv_title != null) {
            tv_title.setText(mTitle);
        }
    }

    public void setLeft(String left) {
        mLeft = left;
        if (tv_left != null) {
            tv_left.setText(mLeft);
        }
    }

    public void setRight(String right) {
        mRight = right;
        if (tv_right != null) {
            tv_right.setText(mRight);
        }
    }

    public void setNeedRightColor(boolean need) {
        needRightColor = need;
        if (need && tv_right != null) {
            tv_right.setTextColor(getContext().getResources().getColor(R.color.themegreen));
        } else if (!need && tv_right != null) {
            tv_right.setTextColor(getContext().getResources().getColor(R.color.subtitle));
        }
    }

    public void setNeedLeftColor(boolean need) {
        needLeftColor = need;
        if (need && tv_left != null) {
            tv_left.setTextColor(getContext().getResources().getColor(R.color.themegreen));
        } else if (!need && tv_left != null) {
            tv_left.setTextColor(getContext().getResources().getColor(R.color.subtitle));
        }
    }

    private boolean isForce;

    public void setForce(boolean isforce) {
        isForce = isforce;
    }

    private Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public static class Listener {
        public void onLeftClick() {
        }

        public void onRightClick() {
        }
    }
}
