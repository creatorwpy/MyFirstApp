package com.wpy.xh.module.setting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpy.xh.R;
import com.wpy.xh.base.App;
import com.wpy.xh.base.BaseActivity;
import com.wpy.xh.config.DIR;
import com.wpy.xh.module.home.xingxiu.VoteOptionDialog;
import com.wpy.xh.pojo.login.LoginResponse;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.Inject;

public class SettingActivity extends BaseActivity {
    @Inject(R.id.cancelLL)
    LinearLayout cancelLL;
    @Inject(R.id.tv_cachesize)
    TextView tv_cachesize;
    @Inject(R.id.clearLL)
    LinearLayout clearLL;

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initViews() {
        if (App.getApp().getUserInfo() == null) {
            cancelLL.setVisibility(View.GONE);
        } else {
            cancelLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSetListener() {
        cancelLL.setOnClickListener(this);
        clearLL.setOnClickListener(this);
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        tv_cachesize.setText("正在计算缓存空间");
        new GetCacheSizeTask().execute();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.cancelLL:
                App.getApp().cancelAccount();
                EventBus.getDefault().post(new LoginResponse());
                finish();
                break;
            case R.id.clearLL:
                tv_cachesize.setText("正在清理缓存");
                new ClearCacheTask().execute();
                break;
        }
    }

    private class ClearCacheTask extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            return CommonUtil.deleteFile(DIR.CACHE);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            new GetCacheSizeTask().execute();
        }
    }
    private class GetCacheSizeTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            long size = CommonUtil.getFileSize(DIR.CACHE);
            long k = 1024;
            long m = 1024 * k;
            long g = 1024 * m;
            float sizef = 0;
            if (size > g) {
                sizef = (float) size / (float) g;
                return fixSize(sizef, "G");
            } else if (size > m) {
                sizef = (float) size / (float) m;
                return fixSize(sizef, "M");
            } else if (size > k) {
                sizef = (float) size / (float) k;
                return fixSize(sizef, "K");
            } else {
                sizef = (float) size / (float) k;
                return fixSize(sizef, "Byte");
            }
        }


        @Override
        protected void onPostExecute(String aVoid) {
            tv_cachesize.setText(aVoid);

        }
    }
    private String fixSize(float size, String dw) {
        String s = String.valueOf(size);
        String[] strings = s.split("\\.");
        if (strings.length != 2) {
            return s + dw;
        } else {
            String xiaoshu = strings[1];
            if (xiaoshu.length() > 2) {
                xiaoshu = xiaoshu.substring(0, 2);
            }
            return strings[0] + "." + xiaoshu + dw;
        }
    }
}
