package com.wpy.xh.module.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.R;
import com.wpy.xh.base.App;
import com.wpy.xh.base.BaseActivity;
import com.wpy.xh.config.NetConfig;
import com.wpy.xh.entity.UserInfo;
import com.wpy.xh.pojo.login.LoginResponse;
import com.wpy.xh.util.HttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.Inject;
import library.mlibrary.util.log.LogDebug;

public class LoginActivity extends BaseActivity {
    @Inject(R.id.et_account)
    private EditText et_account;
    @Inject(R.id.et_pwd)
    private EditText et_pwd;
    @Inject(R.id.tv_login)
    private TextView tv_login;

    @Override
    protected void onSetContentView() {

        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_login:
                checkLogin();
                break;
        }
    }

    private void checkLogin() {
        String account = et_account.getText().toString();
        if (CommonUtil.isEmpty(account)) {
            showToastShort("请输入账号或手机号");
            return;
        }
        String pwd = et_pwd.getText().toString();
        if (CommonUtil.isEmpty(pwd)) {
            showToastShort("请输入密码");
            return;
        }
        hideSoftInput();
        login(account, pwd);
    }

    private void login(String account, String pwd) {
        showProgress("正在登录");
        HttpUtils.RequestParam params = new HttpUtils.RequestParam();
        params.action(NetConfig.login);
        params.add("mobileno", account);
        params.add("password", pwd);
        HttpUtils.getInstance().interfaceapi(NetConfig.iumobileapi).params(params).executeGet(new HttpUtils.HttpListener() {
            @Override
            public void onHttpSuccess(String string) {
                super.onHttpSuccess(string);
                try {
                    Gson gson = new Gson();
                    TypeToken<LoginResponse> typeToken = new TypeToken<LoginResponse>() {
                    };
                    Type type = typeToken.getType();
                    LoginResponse loginResponse = gson.fromJson(string, type);
                    if (loginResponse.getCode().equals("200")) {
                        saveLoginInfo(loginResponse);
                        finish();
                    } else {
                        showToastShort("登录失败,错误码：" + loginResponse.getCode());
                    }
                    dismissProgress();
                } catch (Exception e) {
                    LogDebug.e(e);
                    showToastShort("登录失败，请稍候再试");
                }

            }

            @Override
            public void onException(Throwable e) {
                super.onException(e);
                showToastShort("网络异常");
                dismissProgress();
            }
        });
    }

    private boolean saveLoginInfo(LoginResponse loginResponse) {
        UserInfo userInfo = new UserInfo();
        try {
            userInfo.setToken(loginResponse.getToken());
            if (!CommonUtil.isEmpty(loginResponse.getUserid())) {
                userInfo.setUserId(loginResponse.getUserid());
            } else {
                userInfo.setUserId(loginResponse.getInfo().getUserid());
            }
            App.getApp().setUserInfo(userInfo);
            EventBus.getDefault().post(loginResponse);
            return true;
        } catch (Exception e) {
            LogDebug.e(e);
            return false;
        }
    }
}
