package com.wpy.xh.base;


import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.config.Key;
import com.wpy.xh.entity.UserInfo;

import java.lang.reflect.Type;

import library.mlibrary.base.AbsBaseApplication;
import library.mlibrary.util.log.LogDebug;

/**
 * Created by harmy on 2016/8/3 0003.
 */
public class App extends AbsBaseApplication {
    private UserInfo mUnserInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        LogDebug.setDebugMode(true);
    }

    public static App getApp() {
        return (App) mAbsBaseApplication;
    }

    public void setUserInfo(UserInfo userInfo) {
        mUnserInfo = userInfo;
        saveUserInfoToPerfrence(userInfo);
    }

    public UserInfo getUserInfo() {
        if (mUnserInfo == null) {
            mUnserInfo = getUserInfoFromPrefrence();
        }
        return mUnserInfo;
    }

    //帐号退出
    public void cancelAccount() {
        setUserInfo(null);
    }

    private void saveUserInfoToPerfrence(UserInfo userInfo) {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (userInfo == null) {
            editor.putString(Key.USERINFO, "");
        } else {
            try {
                Gson gson = new Gson();
                String infoJson = gson.toJson(userInfo);
                editor.putString(Key.USERINFO, infoJson);
            } catch (Exception e) {
                LogDebug.e(e);
            }
        }
        editor.commit();
    }

    private UserInfo getUserInfoFromPrefrence() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String infoJson = sharedPreferences.getString(Key.USERINFO, "");
        try {
            Gson gson = new Gson();
            TypeToken<UserInfo> typeToken = new TypeToken<UserInfo>() {
            };
            Type type = typeToken.getType();
            UserInfo userInfo = gson.fromJson(infoJson, type);
            return userInfo;
        } catch (Exception e) {
            LogDebug.e(e);
            return null;
        }
    }

}

