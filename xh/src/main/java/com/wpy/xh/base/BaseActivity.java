package com.wpy.xh.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.wpy.xh.entity.UserInfo;
import com.wpy.xh.pojo.login.UserInfoResponse;

import library.mlibrary.base.AbsBaseActivity;
import library.mlibrary.util.common.SysUtil;
import library.mlibrary.util.log.LogDebug;

/**
 * Created by harmy on 2016/8/3 0003.
 */
public abstract class BaseActivity extends AbsBaseActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setOverridePending(true);
        super.onCreate(savedInstanceState);
    }

    private boolean isFirst = true;

    @Override
    protected void onResume() {
        if (isFirst) {
            overridePendingTransition(library.mlibrary.R.anim.in_activity_btt, library.mlibrary.R.anim.out_activity_ttb);
        } else {
            overridePendingTransition(library.mlibrary.R.anim.in_back_activity_btt, library.mlibrary.R.anim.out_back_activity_ttb);
        }
        isFirst = false;
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void finish() {
        hideSoftInput();
        super.finish();
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            SysUtil.hideSoftInput(view);
        }
    }


    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
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
                try{
                    getProgressDialog(msg).show();
                }catch (Exception e){

                }
            }
        });
    }

    public void dismissProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }catch (Exception e){

                }
            }
        });
    }


    public void showSoftInput(View view) {
        SysUtil.showSoftInput(view);
    }

    protected boolean saveUserInfo(UserInfoResponse userInfoResponse) {
        try {
            UserInfo userInfo = App.getApp().getUserInfo();
            if (userInfo == null) {
                userInfo = new UserInfo();
            }
            userInfo.setUserName(userInfoResponse.getInfo().getUsername());
            userInfo.setUserNumber(userInfoResponse.getInfo().getUsernumber());
            userInfo.setXingzuan(userInfoResponse.getInfo().getPoint());
            userInfo.setXingbi(userInfoResponse.getInfo().getBalance());
            userInfo.setNickName(userInfoResponse.getInfo().getNickname());
            userInfo.setMan("1".equals(userInfoResponse.getInfo().getGender()));
            userInfo.setAvatarUpdate(userInfoResponse.getInfo().getUpdate_avatar_time());
            userInfo.setProvince(userInfoResponse.getInfo().getProvince());
            userInfo.setCity(userInfoResponse.getInfo().getCity());
            userInfo.setBirthday(userInfoResponse.getInfo().getBirthday());
            userInfo.setQianmin(userInfoResponse.getInfo().getGxqm());
            userInfo.setRichLevel(String.valueOf(userInfoResponse.getInfo().getRichlevel()));
            userInfo.setFansNum(String.valueOf(userInfoResponse.getInfo().getFavmy_num()));
            //主播
            userInfo.setShower(!"0".equals(userInfoResponse.getIsshower()));
            if (userInfo.isShower()) {
                userInfo.setShowId(userInfoResponse.getIsshower());
            }
            //艺人信息
            userInfo.setYiren("1".equals(userInfoResponse.getIsyiren()));
            if (userInfo.isYiren()) {
                userInfo.setXuanxiuId(userInfoResponse.getXuanxiu_id());
                userInfo.setYirenPic(userInfoResponse.getYiren().getYirenpic());
                userInfo.setShenggao(userInfoResponse.getYiren().getShengao());
                userInfo.setTizhong(userInfoResponse.getYiren().getTizhong());
                userInfo.setDaibiaozuo(userInfoResponse.getYiren().getDaibiaozuo());
            }
            App.getApp().setUserInfo(userInfo);
            return true;
        } catch (Exception e) {
            LogDebug.e(e);
            return false;
        }
    }
}
