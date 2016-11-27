package com.wpy.xh.module.home.xingxiu;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.R;
import com.wpy.xh.base.App;
import com.wpy.xh.base.BaseActivity;
import com.wpy.xh.base.adapter.BaseBannerAdapter;
import com.wpy.xh.config.Key;
import com.wpy.xh.config.NetConfig;
import com.wpy.xh.entity.UserInfo;
import com.wpy.xh.pojo.xingxiu.PicListResponse;
import com.wpy.xh.util.HttpUtils;
import com.wpy.xh.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.Inject;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.banner.ConvenientBanner;
import library.mlibrary.view.imageview.costumshape.XfermodeImageView;
import library.mlibrary.view.pulltorefresh.pullview.PullLayout;
import library.mlibrary.view.recyclerview.RecyclerView;

import static java.security.AccessController.getContext;

public class XingxiuInfoActivity extends BaseActivity {

    @Inject(R.id.iv_avatar)
    private ImageView iv_avatar;
    @Inject(R.id.cb_info)
    private ConvenientBanner cb_info;
    @Inject(R.id.iv_vote)
    private ImageView iv_vote;
    @Inject(R.id.iv_publish)
    private ImageView iv_publish;
    @Inject(R.id.backRL)
    private RelativeLayout backRL;
    @Inject(R.id.shareRL)
    private RelativeLayout shareRL;
    @Inject(R.id.pullView)
    private PullLayout pullView;
    @Inject(R.id.rv_photo)
    private RecyclerView rv_photo;
    private TextView mPiaoshuTextView;
    //艺人信息
    private UserInfo mUserInfo;
    private String mUserId;
    private String mXuanxiuId;
    private String mYirenPic;

    private String mCurrentPiaoshu;

    private HeadAdapter mHeadAdapter;
    private ArrayList<PicListResponse.YiRen> mHeadInfos;
    private PicListResponse.YiRen mYirenInfo;
    //请求数据分页信息
    private int pageNo = 1;
    private final static int MODE_REFRESH = 0;
    private final static int MODE_MORE = 1;
    private int currentMode = MODE_REFRESH;


    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_xingxiuinfo);
    }

    @Override
    protected void initViews() {
        pullView.setPullListener(new PullLayout.PullListener(){
            @Override
            public void onHeading(PullLayout pullToRefreshLayout) {
                super.onHeading(pullToRefreshLayout);
                pageNo=1;
                currentMode=MODE_REFRESH;
                getXuanxiuPic();
            }

            @Override
            public void onFooting(PullLayout pullToRefreshLayout) {
                super.onFooting(pullToRefreshLayout);
                pageNo++;
                currentMode=MODE_MORE;
                getXuanxiuPic();
            }
        });
    }

    @Override
    protected void onSetListener() {

    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        pullView.setDelayDist(150);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(Key.USERID);
        mXuanxiuId = intent.getStringExtra(Key.XUANXIUID);
        mYirenPic = intent.getStringExtra(Key.PICPATH);
        mUserInfo = App.getApp().getUserInfo();
        initHead();
//        initPicList();
        pullView.autoHead();
    }
    private void initHead() {
        if (App.getApp().getUserInfo() != null && App.getApp().getUserInfo().getUserId().equals(mUserId)) {
            iv_publish.setVisibility(View.VISIBLE);
            iv_vote.setVisibility(View.INVISIBLE);
        } else {
            iv_publish.setVisibility(View.INVISIBLE);
            iv_vote.setVisibility(View.VISIBLE);
        }
        mHeadInfos = new ArrayList<>();
        cb_info.setCanLoop(true);
    }
//取得数据艺人信息及相册
    private void getXuanxiuPic() {
        HttpUtils.RequestParam params = new HttpUtils.RequestParam();
        params.action(NetConfig.yiren_info);
        params.add("userid", mUserId);
        params.add("xuanxiu_id", mXuanxiuId);
        if (mUserInfo != null) {
            params.add("token", mUserInfo.getToken());
        }
        params.add("p", String.valueOf(pageNo));
        params.add("limit", "10");
        HttpUtils.getInstance().interfaceapi(NetConfig.iumobileapiindex).params(params).executeGet(new HttpUtils.HttpListener() {
            @Override
            public void onException(Throwable e) {
                onGetPicFailure();
            }

            @Override
            public void onHttpSuccess(String string) {
                try {
                    Gson gson = new Gson();
                    TypeToken<PicListResponse> typeToken = new TypeToken<PicListResponse>() {
                    };
                    Type type = typeToken.getType();
                    PicListResponse responseResult = gson.fromJson(string, type);
                    if (responseResult.getCode().equals(NetConfig.CODE_SUCCESS)) {
                        if (currentMode == MODE_REFRESH) {
                            notifyHead(responseResult.getYireninfo());
                        } else {
                        }
                        if (currentMode == MODE_REFRESH) {
                            stopRefresh();
                        } else {
                            stopLoadMore();
                        }
                    } else {
                        onGetPicFailure();
                    }
                } catch (Exception e) {
                    LogDebug.e(e);
                    onGetPicFailure();
                }
            }
        });
    }
    private void notifyHead(PicListResponse.YiRen yiRen) {
        if (yiRen == null) {
            return;
        }
        mYirenInfo = yiRen;
        mHeadInfos.clear();
        mHeadInfos.add(yiRen);
        mHeadInfos.add(yiRen);
        mHandler.sendEmptyMessage(2);
    }
    private void onGetPicFailure() {
        if (currentMode == MODE_REFRESH) {
            stopRefresh();
        } else {
            stopLoadMore();
            pageNo--;
        }
    }
    private void stopRefresh() {
        mHandler.sendEmptyMessage(0);
    }

    private void stopLoadMore() {
        mHandler.sendEmptyMessage(1);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pullView.headFinish();
                    break;
                case 1:
                    pullView.footFinish();
                    break;
                case 2:
                    Utils.showImageNoGif(XingxiuInfoActivity.this, iv_avatar, NetConfig.getAvatar(mYirenInfo.getUserid(), mYirenInfo.getUpdate_avatar_time()));
                    mHeadAdapter = new HeadAdapter(getThis(), mHeadInfos);
                    cb_info.setAdapter(mHeadAdapter);
                    cb_info.setPageIndicatorOrientation(ConvenientBanner.PageIndicatorOrientation.HORIZONTAL);
                    cb_info.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL, ConvenientBanner.PageIndicatorAlign.BOTTOM);
                    cb_info.setPageIndicatorVisible(true);
                    cb_info.setPageIndicator(new int[]{R.drawable.shape_indicator_no, R.drawable.shape_indicator_on}, mHeadInfos.size());
                    break;
                case 3:
                    mPiaoshuTextView.setText(mCurrentPiaoshu);
                    break;
            }
        }
    };
    private class HeadAdapter extends BaseBannerAdapter<HeadHolder, PicListResponse.YiRen> {
        public HeadAdapter(Context Context, List<PicListResponse.YiRen> datas) {
            super(Context, datas);
        }

        @Override
        public int getViewType(int position) {
            return position % 2;
        }

        @Override
        public View createItemView(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = LayoutInflater.from(getContext()).inflate(R.layout.item_xingxiuinfo_head_1, parent, false);
                    break;
                case 1:
                    view = LayoutInflater.from(getContext()).inflate(R.layout.item_xingxiuinfo_head_2, parent, false);
                    break;
            }
            return view;
        }

        @Override
        public HeadHolder createViewHolder(ViewGroup parent, View itemView, int viewType) {
            return new HeadHolder(itemView);
        }

        @Override
        public void onBindViewHolder(HeadHolder holder, int position) {
            PicListResponse.YiRen item = getItem(position);
            switch (getViewType(position)) {
                case 0:
                    Utils.showImage(getContext(), holder.iv_avatar, NetConfig.getAvatar(item.getUserid(), item.getUpdate_avatar_time()));
                    if (item.getGender().equals("1")) {
                        holder.iv_gender.setImageResource(R.drawable.gender_boy);
                    } else {
                        holder.iv_gender.setImageResource(R.drawable.gender_girl);
                    }
                    String name = item.getYiming();
                    if (CommonUtil.isEmpty(name)) {
                        name = item.getNickname();
                    }
                    holder.tv_nick.setText(name);
                    if (CommonUtil.isEmpty(item.getGxqm())) {
                        holder.tv_qianming.setVisibility(View.GONE);
                    } else {
                        holder.tv_qianming.setVisibility(View.VISIBLE);
                        holder.tv_qianming.setText(item.getGxqm());
                    }
                    holder.tv_renqi.setText(mCurrentPiaoshu == null ? item.getPiaoshu2() : mCurrentPiaoshu);
                    mPiaoshuTextView = holder.tv_renqi;
                    break;
                case 1:
                    if (CommonUtil.isEmpty(item.getShengao())) {
                        holder.tv_shenggao.setText("");
                    } else {
                        holder.tv_shenggao.setText(item.getShengao());
                    }
                    if (CommonUtil.isEmpty(item.getTizhong())) {
                        holder.tv_tizhong.setText("");
                    } else {
                        holder.tv_tizhong.setText(item.getTizhong());
                    }
                    if (CommonUtil.isEmpty(item.getBirthday())) {
                        holder.tv_shengri.setText("");
                    } else {
                        holder.tv_shengri.setText(item.getBirthday());
                    }
                    if (CommonUtil.isEmpty(item.getCity())) {
                        holder.tv_place.setText("");
                    } else {
                        holder.tv_place.setText(item.getCity());
                    }
                    if (CommonUtil.isEmpty(item.getDaibiaozuo())) {
                        holder.tv_zuoping.setText("");
                    } else {
                        holder.tv_zuoping.setText(item.getDaibiaozuo());
                    }
                    break;
            }
        }
    }

    private class HeadHolder extends RecyclerView.ViewHolder {
        @Inject(R.id.iv_avatar)
        public XfermodeImageView iv_avatar;
        @Inject(R.id.iv_gender)
        public ImageView iv_gender;
        @Inject(R.id.tv_nick)
        public TextView tv_nick;
        @Inject(R.id.tv_qianming)
        public TextView tv_qianming;
        @Inject(R.id.tv_renqi)
        public TextView tv_renqi;
        @Inject(R.id.tv_shenggao)
        public TextView tv_shenggao;
        @Inject(R.id.tv_zuoping)
        public TextView tv_zuoping;
        @Inject(R.id.tv_shengri)
        public TextView tv_shengri;
        @Inject(R.id.tv_tizhong)
        public TextView tv_tizhong;
        @Inject(R.id.tv_place)
        public TextView tv_place;

        public HeadHolder(View itemView) {
            super(itemView);
        }
    }
}
