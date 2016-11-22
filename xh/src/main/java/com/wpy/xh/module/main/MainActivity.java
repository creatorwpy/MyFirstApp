package com.wpy.xh.module.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.R;
import com.wpy.xh.base.App;
import com.wpy.xh.base.BaseActivity;
import com.wpy.xh.config.DIR;
import com.wpy.xh.config.NetConfig;
import com.wpy.xh.module.home.xingxiu.XingxiuFragment;
import com.wpy.xh.module.personal.PersonalFragment;
import com.wpy.xh.module.test.TestXiangceFragment;
import com.wpy.xh.pojo.AppUpdate;
import com.wpy.xh.util.HttpUtils;
import com.wpy.xh.util.Utils;
import com.wpy.xh.widget.dialog.CommonDialog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.common.FragmentManager;
import library.mlibrary.util.common.SysUtil;
import library.mlibrary.util.inject.Inject;
import library.mlibrary.util.log.LogDebug;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    @Inject(R.id.liveRL)
    private RelativeLayout liveRL;
    @Inject(R.id.homeRL)
    private RelativeLayout homeRL;
    @Inject(R.id.personalRL)
    private RelativeLayout personalRL;
    @Inject(R.id.iv_home)
    private ImageView iv_home;
    @Inject(R.id.iv_personal)
    private ImageView iv_personal;

    private FragmentManager mFragmentManager;
    private XingxiuFragment xingxiuFragment;
    private PersonalFragment personalFragment;
    private TestXiangceFragment testXiangceFragment;

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {
        liveRL.setOnClickListener(this);
        homeRL.setOnClickListener(this);
        personalRL.setOnClickListener(this);
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        mFragmentManager = initFragmentManager(R.id.mainFL);
        showHome();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.homeRL:
                showToastShort("点击了首页");
                showHome();
                break;
            case R.id.liveRL:
                showToastShort("点击了开播");
                showLive();
                break;
            case R.id.personalRL:
                showToastShort("点击了个人中心");
                showPerson();
                break;
        }
    }

    private void showHome() {
        if (xingxiuFragment == null) {
            xingxiuFragment = new XingxiuFragment();
        }
        mFragmentManager.showFragment(xingxiuFragment);
        Utils.showImage(this, iv_home, R.drawable.home_on);
        Utils.showImage(this, iv_personal, R.drawable.personal_no);
    }

    private void showPerson() {
        if (personalFragment == null) {
            personalFragment = new PersonalFragment();
        }
        mFragmentManager.showFragment(personalFragment);
        Utils.showImage(this, iv_home, R.drawable.home_no);
        Utils.showImage(this, iv_personal, R.drawable.personal_no);
    }

    private void showLive() {
        if (testXiangceFragment == null) {
            testXiangceFragment = new TestXiangceFragment();
        }
        LogDebug.d("showLive");
        mFragmentManager.showFragment(testXiangceFragment);
    }

    //2秒内按两次返回退出程序
    @Override
    public void onBackPressed() {
        exitApp();
    }

    private long msOnBackPressed = 0;
    private long exitDuration = 2000;

    private void exitApp() {
        long off = CommonUtil.getCurrentMS() - msOnBackPressed;
        if (off > exitDuration) {
            msOnBackPressed = CommonUtil.getCurrentMS();
            showToastShort("再按一次返回退出程序");
        } else {
            App.getApp().exitApplication();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUpDate();
    }

    //检查版本更新
    private String mCurrentVersion;
    private String mApkUrl;
    private String mUpdtaeVersion;
    private boolean isFroceUpdate = false;

    private void checkUpDate() {
        mCurrentVersion = CommonUtil.getVersionName(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = new File(DIR.CACHE);
                if (dir.exists()) {
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.getName().contains(".apk")) {
                            String ver = file.getName().replace(".apk", "");
                            int hh = CommonUtil.compareVersionName(mCurrentVersion, ver);
                            if (hh == 1 || hh == 0) {
                                CommonUtil.deleteFile(file);
                            }
                        }
                    }
                }
            }
        }).start();
        HttpUtils.RequestParam params = new HttpUtils.RequestParam();
        params.action(NetConfig.app_update);
        params.add("platform", "android");
        params.add("version", mCurrentVersion);
        HttpUtils.getInstance().interfaceapi(NetConfig.iumobileapiindex).params(params).executeGet(new HttpUtils.HttpListener() {
            @Override
            public void onHttpSuccess(String string) {
                super.onHttpSuccess(string);
                try {
                    Gson gson = new Gson();
                    TypeToken<AppUpdate> typeToken = new TypeToken<AppUpdate>() {
                    };
                    Type type = typeToken.getType();
                    AppUpdate res = gson.fromJson(string, type);
                    if (res.getCode().equals("200")) {
                        mApkUrl = res.getUrl();
                        mUpdtaeVersion = res.getVersion();
                        if (res.getVersion_must().equals("1")) {
                            isFroceUpdate = true;
                        }
                        mHandler.sendEmptyMessage(0);
                    }

                } catch (Exception e) {
                    LogDebug.e(e);
                }
            }
        });
    }

    //显示更新提示弹框
    private CommonDialog mUpdateTip;

    private void showUpdateTip() {
        LogDebug.d("显示更新弹框");
        if (mUpdateTip == null) {
            mUpdateTip = new CommonDialog(this);
            mUpdateTip.setTitle("发现新版本，是否更新");
            mUpdateTip.setLeft("否");
            mUpdateTip.setRight("是");
            mUpdateTip.setForce(true);
            mUpdateTip.setNeedRightColor(true);
            mUpdateTip.setListener(new CommonDialog.Listener() {
                @Override
                public void onLeftClick() {
                    super.onLeftClick();
                    if (isFroceUpdate) {
                        App.getApp().exitApplication();
                    }
                }

                @Override
                public void onRightClick() {
                    super.onRightClick();
                    downLoadApk();
                }
            });
            mUpdateTip.show();
        }
    }

    //下载apk
    private void downLoadApk() {
        String savepath = DIR.CACHE_APK + "/" + mUpdtaeVersion + ".apk";
        File file = new File(savepath);
        if (file.exists()) {
            SysUtil.installApk(MainActivity.this, file.getPath());
            return;
        }
        HttpUtils.getInstance().url(mApkUrl).executeGet(new HttpUtils.HttpListener() {
            @Override
            public void onRequestProgress(long progress, long total, boolean done) {
                super.onRequestProgress(progress, total, done);
                float per = (float) progress / (float) total * (float) 100;
                String pers = String.valueOf(per);
                if (pers.length() > 5) {
                    pers = pers.substring(0, 5);
                }
                showProgress("正在下载" + " " + pers + "%");
            }

            @Override
            public void onHttpSuccess(byte[] bytes) {
                super.onHttpSuccess(bytes);
                showProgress("准备安装，请稍后");
                String savepath = DIR.CACHE_APK + "/" + mUpdtaeVersion + ".apk";
                File file = new File(savepath);
                try {
                    CommonUtil.byte2File(bytes, file.getParent(), file.getName());
                    dismissProgress();
                    SysUtil.installApk(MainActivity.this, file.getPath());
                } catch (IOException e) {
                    LogDebug.e(e);
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showUpdateTip();
                    break;
            }
        }
    };
}
















