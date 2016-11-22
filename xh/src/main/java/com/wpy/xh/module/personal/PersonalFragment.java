package com.wpy.xh.module.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.R;
import com.wpy.xh.base.App;
import com.wpy.xh.base.BaseFragment;
import com.wpy.xh.config.DIR;
import com.wpy.xh.config.Key;
import com.wpy.xh.config.NetConfig;
import com.wpy.xh.entity.UserInfo;
import com.wpy.xh.module.login.LoginActivity;
import com.wpy.xh.module.setting.SettingActivity;
import com.wpy.xh.module.userlist.FollowActivity;
import com.wpy.xh.pojo.login.LoginResponse;
import com.wpy.xh.pojo.login.UserInfoResponse;
import com.wpy.xh.util.HttpUtils;
import com.wpy.xh.util.Utils;
import com.wpy.xh.widget.ImageCropActivity;
import com.wpy.xh.widget.dialog.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Type;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.common.SysUtil;
import library.mlibrary.util.inject.Inject;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.imageview.costumshape.XfermodeImageView;
import library.mlibrary.view.pulltorefresh.pullview.PullLayout;

/**
 * Created by harmy on 2016/8/4 0004.
 */
public class PersonalFragment extends BaseFragment {
    @Inject(R.id.fanLL)
    private LinearLayout fanLL;
    @Inject(R.id.favLL)
    private LinearLayout favLL;
    @Inject(R.id.chargeLL)
    private LinearLayout chargeLL;
    @Inject(R.id.yirenLL)
    private LinearLayout yirenLL;
    @Inject(R.id.settingLL)
    private LinearLayout settingLL;
    @Inject(R.id.pullView)
    private PullLayout pullView;
    @Inject(R.id.personLL)
    private LinearLayout personLL;
    @Inject(R.id.detailLL)
    private LinearLayout detailLL;
    @Inject(R.id.iv_edit)
    private ImageView iv_edit;
    @Inject(R.id.iv_avatar)
    private XfermodeImageView iv_avatar;
    @Inject(R.id.tv_name)
    private TextView tv_name;
    @Inject(R.id.tv_xingzuan)
    private TextView tv_xingzuan;
    @Inject(R.id.tv_xingbi)
    private TextView tv_xingbi;
    @Inject(R.id.tv_level)
    private TextView tv_level;
    @Inject(R.id.iv_gender)
    private ImageView iv_gender;
    @Inject(R.id.nickRightLL)
    private LinearLayout nickRightLL;

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.fragment_personal);
    }

    @Override
    protected void initViews() {
        LogDebug.v("v", "initViews");
    }

    @Override
    protected void onSetListener() {
        pullView.setPullListener(new PullLayout.PullListener() {
            @Override
            public void onHeading(PullLayout pullToRefreshLayout) {
                super.onHeading(pullToRefreshLayout);
                checkUser();
            }
        });
        personLL.setOnClickListener(this);
        settingLL.setOnClickListener(this);
        fanLL.setOnClickListener(this);
        favLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.personLL:
                UserInfo userInfo = App.getApp().getUserInfo();
                if (userInfo == null) {
                    showToastShort("去登录");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    showToastShort("更新头像");
                    showUpLoadAvatorDialog();
                }
                break;
            case R.id.settingLL:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.fanLL://我的粉丝
                if (checkUserLogin()) {
                    Intent intent = new Intent(getActivity(), FollowActivity.class);
                    intent.putExtra(Key.TAG, "fan");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.favLL://我的关注
                if (checkUserLogin()) {
                    Intent intent = new Intent(getActivity(), FollowActivity.class);
                    intent.putExtra(Key.TAG, "fav");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        pullView.setCanPullFoot(false);
        pullView.autoHead();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void checkUser() {
        UserInfo userInfo = App.getApp().getUserInfo();
        if (userInfo == null) {
            noUserMode();
        } else {
            hasUserMode();
            getUserinfo(userInfo);
        }

    }

    private boolean checkUserLogin() {
        UserInfo userInfo = App.getApp().getUserInfo();
        if (userInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    //没有登录时调用
    private void noUserMode() {
        App.getApp().setUserInfo(null);
        pullView.headFinish();
        detailLL.setVisibility(View.GONE);
        iv_edit.setVisibility(View.INVISIBLE);
        Utils.showImage(this, iv_avatar, R.drawable.avatar_unlogin);
        tv_name.setText("登录");
        yirenLL.setVisibility(View.GONE);
        nickRightLL.setVisibility(View.GONE);
    }

    private void hasUserMode() {
        pullView.headFinish();
        UserInfo userInfo = App.getApp().getUserInfo();
        tv_name.setText(userInfo.getNickName());
        iv_gender.setVisibility(View.VISIBLE);
        if (userInfo.isMan()) {
            iv_gender.setImageResource(R.drawable.gender_boy);
        } else {
            iv_gender.setImageResource(R.drawable.gender_girl);
        }
        tv_level.setText(userInfo.getRichLevel());
        Utils.showImage(this, iv_avatar, NetConfig.getAvatar(userInfo.getUserId(), userInfo.getAvatarUpdate()));
    }

    private void getUserinfo(UserInfo userInfo) {
        HttpUtils.RequestParam param = new HttpUtils.RequestParam();
        param.action(NetConfig.getuserinfo);
        param.add("token", userInfo.getToken());
        param.add("userid", userInfo.getUserId());
        HttpUtils.getInstance().interfaceapi(NetConfig.iumobileapi).params(param).executeGet(new HttpUtils.HttpListener() {
            @Override
            public void onHttpSuccess(String string) {
                super.onHttpSuccess(string);
                try {
                    Gson gson = new Gson();
                    TypeToken<UserInfoResponse> typeToken = new TypeToken<UserInfoResponse>() {
                    };
                    Type type = typeToken.getType();
                    UserInfoResponse userInfoResponse = gson.fromJson(string, type);
                    if (userInfoResponse.getCode().equals("200")) {
                        if (!saveUserInfo(userInfoResponse)) {
                            showToastShort("获取用户信息失败，请稍候再试");
                        }
                    } else {
                        showToastShort("获取用户信息失败,错误码:" + userInfoResponse.getCode());
                    }
                } catch (Exception e) {
                    LogDebug.e(e);
                    showToastShort("获取用户信息失败，请稍候再试");
                }
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onException(Throwable e) {
                super.onException(e);
                showToastShort("获取用户信息失败");
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    hasUserMode();
                    break;
                case 1:
                    noUserMode();
                    break;
            }
        }
    };
    private CommonDialog commonDialog;
    private String mPicTakeFromCamera;

    private void showUpLoadAvatorDialog() {
        if (commonDialog == null) {
            commonDialog = new CommonDialog(getActivity());
            commonDialog.setTitle("设置头像");
            commonDialog.setLeft("相册");
            commonDialog.setRight("拍照");
            commonDialog.setListener(new CommonDialog.Listener() {
                @Override
                public void onLeftClick() {//相册
                    super.onLeftClick();
                    SysUtil.choosePhoto(getThis(), 1001);
                }

                @Override
                public void onRightClick() {
                    super.onRightClick();
                    // /storage/emulated/0/Android/data/.com.wpy.xh/cache/picture/1479571200000.jpg
                    mPicTakeFromCamera = DIR.CACHE_PICTURE + CommonUtil.getCurrentMS() + ".jpg";
                    LogDebug.v("Dir", mPicTakeFromCamera);
                    SysUtil.startCamera(getThis(), new File(mPicTakeFromCamera), 1002);
                }
            });
        }
        commonDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogDebug.v("onActivityResult:", requestCode + "");
        LogDebug.v("resultCode:", resultCode + "");
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1001://相册
                    String picPath = SysUtil.getRealpathFromUri(getActivity(), data.getData());
                    cropImage(picPath);
                    break;
                case 1002:
                    cropImage(mPicTakeFromCamera);
                    break;
                case 200:
                    ///storage/emulated/0/Android/data/.com.wpy.xh/cache/picture/1479619933355.jpg
                    String cropedpath = data.getStringExtra(Key.PICPATH);
                    upLoadAvatar(cropedpath);
                    LogDebug.v("cropedpath:", cropedpath);
                    break;
            }
        }
    }

    private void cropImage(String path) {
        Intent intent = new Intent(getActivity(), ImageCropActivity.class);
        intent.putExtra(Key.PICPATH, path);
        startActivityForResult(intent, 200);

    }

    private void upLoadAvatar(String path) {
        if (CommonUtil.isEmpty(path)) {
            return;
        }
        showProgress("正在上传头像");

        HttpUtils.RequestParam params = new HttpUtils.RequestParam();
        params.action(NetConfig.upavatar);
        params.add("userid", App.getApp().getUserInfo().getUserId());
//        params.add("token", App.getApp().getUserInfo().getToken());
        params.add("avatar", new File(path));
        HttpUtils.getInstance().interfaceapi(NetConfig.iumobileapiindex + "token=" + App.getApp().getUserInfo().getToken()).params(params).executePost(new HttpUtils.HttpListener() {
            @Override
            public void onException(Throwable e) {
                super.onException(e);
                dismissProgress();
                showToastShort("网络异常");
            }

            @Override
            public void onRequestProgress(long progress, long total, boolean done) {
                float per = (float) progress / (float) total * (float) 100;
                String pers = String.valueOf(per);
                if (pers.length() > 5) {
                    pers = pers.substring(0, 5);
                }
                showProgress("正在上传头像" + " " + pers + "%");
            }

            @Override
            public void onHttpSuccess(String string) {
                dismissProgress();
                if (HttpUtils.isSuccess(string)) {
                    showToastShort("头像上传成功");
                    getUserinfo(App.getApp().getUserInfo());
                } else {
                    showToastShort("头像上传失败");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void callBackRefresh(LoginResponse loginResponse) {
        checkUser();
    }
}
