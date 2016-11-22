package com.wpy.xh.module.home.xingxiu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.R;
import com.wpy.xh.base.App;
import com.wpy.xh.base.BaseFragment;
import com.wpy.xh.base.adapter.BaseAdapter;
import com.wpy.xh.config.NetConfig;
import com.wpy.xh.module.test.TestXiangceFragment;
import com.wpy.xh.pojo.home.XingxiuResponse;
import com.wpy.xh.util.HttpUtils;
import com.wpy.xh.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.Inject;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.pulltorefresh.pullview.PullLayout;
import library.mlibrary.view.recyclerview.DividerItemDecoration;
import library.mlibrary.view.recyclerview.OnItemClickListener;
import library.mlibrary.view.recyclerview.RecyclerView;

/**
 * Created by harmy on 2016/8/4 0004.
 */
public class XingxiuFragment extends BaseFragment {
    @Override
    protected void onSetContentView() {
        setContentView(R.layout.fragment_xingxiu);
    }

    @Override
    protected void initViews() {

    }
    @Override
    protected void onSetListener() {
        pullView.setPullListener(new PullLayout.PullListener() {
            @Override
            public void onHeading(PullLayout pullToRefreshLayout) {
                LogDebug.v("XingxiuFragment:","public void onHeading");
                currentMode = MODE_REFRESH;
                pageNo = 1;
                getXingxiuList();
            }

            @Override
            public void onFooting(PullLayout pullToRefreshLayout) {
                LogDebug.v("XingxiuFragment:","public void onFooting");
                currentMode = MODE_MORE;
                pageNo++;
                getXingxiuList();
            }
        });
        rv_xingxiu.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToastShort("点击了一项"+position);
                XingxiuResponse.ItemInfo item = mDatas.get(position);

                /*XingxiuResponse.ItemInfo item = mDatas.get(position);
                Intent intent = new Intent(getActivity(), XingxiuInfoActivity.class);
                intent.putExtra(Key.USERID, item.getUserid());
                intent.putExtra(Key.XUANXIUID, item.getXuanxiu_id());
                intent.putExtra(Key.PICPATH, item.getYirenpic());
                startActivity(intent);*/
            }
        });
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        pullView.setDelayDist(150);
        initList();
        pullView.autoHead();
        showToastShort("来了");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Inject(R.id.pullView)
    private PullLayout pullView;
    @Inject(R.id.rv_xingxiu)
    private RecyclerView rv_xingxiu;
    private ArrayList<XingxiuResponse.ItemInfo> mDatas;
    private Adapter mAdapter;
    private void initList() {
        LogDebug.v("XingxiuFragment:","private void initList");
        mDatas = new ArrayList<>();
        mAdapter = new Adapter(getActivity(), mDatas);
        rv_xingxiu.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        rv_xingxiu.setAdapter(mAdapter);
        rv_xingxiu.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.GRID_LIST,getResources().getDrawable(R.drawable.shape_div_2)));
    }

    private String mXuanxiuId = "0";

    private int pageNo = 1;
    private final static int MODE_REFRESH = 0;
    private final static int MODE_MORE = 1;
    private int currentMode = MODE_REFRESH;

    private void getXingxiuList() {
        LogDebug.v("XingxiuFragment:","private void getXingxiuList");
        HttpUtils.RequestParam params = new HttpUtils.RequestParam();
        params.action(NetConfig.xingxiu_list);
        params.add("xuanxiu_id", mXuanxiuId);
        params.add("p", String.valueOf(pageNo));
        if (mXuanxiuId.equals("0")) {
            params.add("limit", "100");
        } else {
            params.add("limit", "20");
        }
        HttpUtils.getInstance().interfaceapi(NetConfig.iumobileapi).params(params).executeGet(new HttpUtils.HttpListener() {
            @Override
            public void onException(Throwable e) {
                super.onException(e);
                if (currentMode == MODE_REFRESH) {
                    stopRefresh();
                } else {
                    stopLoadMore();
                    pageNo--;
                }
            }

            @Override
            public void onHttpSuccess(String string) {
                LogDebug.v("XingxiuFragment:","onHttpSuccess");
                if (currentMode == MODE_REFRESH) {
                    mDatas.clear();
                } else {
                }
                try {
                    Gson gson = new Gson();
                    TypeToken<XingxiuResponse> typeToken = new TypeToken<XingxiuResponse>() {
                    };
                    Type type = typeToken.getType();
                    XingxiuResponse xingxiuResponse = gson.fromJson(string, type);
                    if (xingxiuResponse.getCode().equals(NetConfig.CODE_SUCCESS)) {
                        ArrayList<XingxiuResponse.XingxiuItem> items = xingxiuResponse.getResponse();
                        mDatas.addAll(xingxiuItemsToIteminfos(items));
                        mAdapter.notifyDataSetChangedHandler();
                    } else {
                        if (currentMode == MODE_MORE) {
                            pageNo--;
                        }
                    }
                } catch (Exception e) {
                    LogDebug.e(e);
                    if (currentMode == MODE_MORE) {
                        pageNo--;
                    }
                }
                if (currentMode == MODE_REFRESH) {
                    stopRefresh();
                } else {
                    stopLoadMore();
                }
            }
        });
    }

    private ArrayList<XingxiuResponse.ItemInfo> xingxiuItemsToIteminfos(ArrayList<XingxiuResponse.XingxiuItem> items) {
        ArrayList<XingxiuResponse.ItemInfo> itemInfos = new ArrayList<>();
        if (!CommonUtil.isEmpty(items)) {
            for (XingxiuResponse.XingxiuItem xingxiuItem : items) {
                ArrayList<XingxiuResponse.ItemInfo> datas = xingxiuItem.getData();
                for (XingxiuResponse.ItemInfo itemInfo : datas) {
                    itemInfo.setXuanxiu_id(xingxiuItem.getXuanxiu_id());
                }
                itemInfos.addAll(datas);
            }
        }
        return itemInfos;

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
            }
        }
    };

    private class Adapter extends BaseAdapter<Holder, XingxiuResponse.ItemInfo> {

        public Adapter(Context context, ArrayList<XingxiuResponse.ItemInfo> datas) {
            super(context, datas);
            LogDebug.v("XingxiuFragment:","public Adapter");
        }

        @Override
        public View createItemView(ViewGroup parent, int viewType) {
            LogDebug.v("XingxiuFragment:","public View createItemView");
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_xingxiu, parent, false);
            return view;
        }

        @Override
        protected Holder createViewHolder(ViewGroup parent, View itemView, int viewType) {
            LogDebug.v("XingxiuFragment:","protected Holder createViewHolder");
            return new Holder(itemView);
        }

        @Override
        protected void afterBindViewHolder(Holder holder, int position) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            lp.height = CommonUtil.getDisplayMetrics(getContext()).widthPixels / 2;
            lp.width = CommonUtil.getDisplayMetrics(getContext()).widthPixels / 2;
            XingxiuResponse.ItemInfo itemInfo = getItem(position);
            Utils.showImage(getContext(), holder.iv_pic, NetConfig.getXingxiuPic(itemInfo.getYirenpic()));
            if (position == 0) {
                holder.tv_number.setVisibility(View.INVISIBLE);
                Utils.showImage(getContext(), holder.iv_number, R.drawable.home_xingxiu_number_1);
            } else if (position == 1) {
                holder.tv_number.setVisibility(View.INVISIBLE);
                Utils.showImage(getContext(), holder.iv_number, R.drawable.home_xingxiu_number_2);
            } else if (position == 2) {
                holder.tv_number.setVisibility(View.INVISIBLE);
                Utils.showImage(getContext(), holder.iv_number, R.drawable.home_xingxiu_number_3);
            } else {
                holder.tv_number.setText(String.valueOf(position + 1));
                holder.tv_number.setVisibility(View.VISIBLE);
                Utils.showImage(getContext(), holder.iv_number, R.drawable.home_xingxiu_number_bg);
            }
            String name = "";
            if (!CommonUtil.isEmpty(itemInfo.getYiming())) {
                name = itemInfo.getYiming();
            } else {
                name = itemInfo.getNickname();
            }
            holder.tv_name.setText(name);
            holder.tv_renqi.setText(itemInfo.getPiaoshu2() + "人气");
        }

    }

    private class Holder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @Inject(R.id.tv_number)
        public TextView tv_number;
        @Inject(R.id.iv_pic)
        public ImageView iv_pic;
        @Inject(R.id.iv_number)
        public ImageView iv_number;
        @Inject(R.id.tv_name)
        public TextView tv_name;
        @Inject(R.id.tv_renqi)
        public TextView tv_renqi;

        public Holder(View itemView) {
            super(itemView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshEvent event) {
        LogDebug.v("XingxiuFragment:","public void refresh");
        rv_xingxiu.scrollToPosition(0);
        mXuanxiuId = event.getXuanxiuid();
        currentMode = MODE_REFRESH;
        pageNo = 1;
        getXingxiuList();
    }

    public static class RefreshEvent {
        public RefreshEvent() {
            super();
            LogDebug.v("XingxiuFragment:","public static class RefreshEvent1");
        }

        public RefreshEvent(String xuanxiuid) {
            super();
            setXuanxiuid(xuanxiuid);
            LogDebug.v("XingxiuFragment:","public static class RefreshEvent2");
        }

        private String xuanxiuid;

        public String getXuanxiuid() {
            return xuanxiuid;
        }

        public void setXuanxiuid(String xuanxiuid) {
            this.xuanxiuid = xuanxiuid;
        }
    }
}
