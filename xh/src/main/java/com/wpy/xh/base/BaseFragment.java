package com.wpy.xh.base;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;


import com.wpy.xh.pojo.login.UserInfoResponse;

import library.mlibrary.base.AbsBaseFragment;

/**
 * Created by harmy on 2016/8/4 0004.
 */
public abstract class BaseFragment extends AbsBaseFragment implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void showProgress() {
        ((BaseActivity) getActivity()).showProgress();
    }

    public void showProgress(String msg) {
        ((BaseActivity) getActivity()).showProgress(msg);
    }

    public void dismissProgress() {
        ((BaseActivity) getActivity()).dismissProgress();
    }
    private Toast mToast;
    public void showToastShort(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
    public void showToastLong(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
    protected boolean saveUserInfo(UserInfoResponse userInfoResponse){
        return ((BaseActivity)getActivity()).saveUserInfo(userInfoResponse);

    }

}
