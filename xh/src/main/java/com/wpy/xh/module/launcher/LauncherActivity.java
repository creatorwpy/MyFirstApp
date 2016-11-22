package com.wpy.xh.module.launcher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wpy.xh.R;
import com.wpy.xh.base.App;
import com.wpy.xh.base.BaseActivity;
import com.wpy.xh.module.main.MainActivity;


public class LauncherActivity extends BaseActivity {
    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {

    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        startMainDelay();
    }

    @Override
    public void onBackPressed() {
        mHandler.removeMessages(STARTMAINMSG);
        App.getApp().exitApplication(false);
    }
    private static final long DELAY_TIME = 20;
    private void startMainDelay() {
        mHandler.sendEmptyMessageDelayed(STARTMAINMSG, DELAY_TIME);
    }
    private static final int STARTMAINMSG = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STARTMAINMSG:
                    startActivity(MainActivity.class);
                    finish();
                    break;
            }
        }
    };
}
