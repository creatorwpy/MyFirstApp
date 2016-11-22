package com.mycompany.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mycompany.util.common.SysUtil;


/**
 * Created by harmy on 2016/8/3 0003.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected BaseActivity mThis;

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThis = this;
    }

    @Override
    public void onClick(View view) {

    }

    private Toast mToast;

    public void showToastShort(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(getThis(), msg, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    protected BaseActivity getThis() {
        return mThis;
    }

    public void showToastLong(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(getThis(), msg, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }


    private ProgressDialog mProgressDialog;

    public void showProgress() {
        showProgress("处理中，请稍后！");
    }

    public ProgressDialog getProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getThis(), AlertDialog.THEME_HOLO_LIGHT);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(msg);
        return mProgressDialog;
    }

    public void showProgress(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getProgressDialog(msg).show();
            }
        });
    }

    public void dismissProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            SysUtil.hideSoftInput(view);
        }
    }

    public void showSoftInput(View view) {
        SysUtil.showSoftInput(view);
    }
}
