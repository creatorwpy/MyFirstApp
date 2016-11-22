package com.wpy.xh.base;

import android.content.Context;
import android.content.DialogInterface;

import library.mlibrary.view.dialog.AbsBaseAnimateDialog;

/**
 * Created by harmy on 2016/8/11 0011.
 */
public abstract class BaseDialog extends AbsBaseAnimateDialog {
    public BaseDialog(Context context) {
        super(context, library.mlibrary.R.style.CommonDialog);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
