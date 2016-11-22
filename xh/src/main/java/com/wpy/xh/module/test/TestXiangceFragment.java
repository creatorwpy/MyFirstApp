package com.wpy.xh.module.test;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.recyclerview.DividerItemDecoration;
import library.mlibrary.view.recyclerview.OnItemClickListener;
import library.mlibrary.view.recyclerview.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.R;
import com.wpy.xh.base.BaseFragment;
import com.wpy.xh.base.adapter.BaseAdapter;
import com.wpy.xh.config.NetConfig;
import com.wpy.xh.pojo.test.TestXiangceResponse;
import com.wpy.xh.util.HttpUtils;
import com.wpy.xh.util.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import library.mlibrary.util.inject.Inject;
import library.mlibrary.view.pulltorefresh.pullview.PullLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestXiangceFragment extends BaseFragment {
    @Inject(R.id.pullView)
    private PullLayout pullView;
    @Inject(R.id.rv_xiangce)
    private RecyclerView rv_xiangce;
    private String userid = "0";
    private int pageNo = 1;
    private final static int MODE_REFRESH = 0;
    private final static int MODE_MORE = 1;
    private int currentMode = MODE_REFRESH;
    private ArrayList<TestXiangceResponse.Photo> mDatas;
    private Adapter mAdapter;

    @Override
    protected void onSetContentView() {
        try {
            userid = getArguments().getString("key");
        }catch (Exception e){
            userid="20417";
            e.printStackTrace();
        }


        setContentView(R.layout.test_fragment_xiangce);
        LogDebug.d("来了，TestXiangceFragment:"+userid);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {
        pullView.setPullListener(new PullLayout.PullListener() {
            @Override
            public void onHeading(PullLayout pullToRefreshLayout) {
                super.onHeading(pullToRefreshLayout);
                currentMode = MODE_REFRESH;
                pageNo = 1;
                getDataList();
                LogDebug.d("来了，onHeading");
            }

            @Override
            public void onFooting(PullLayout pullToRefreshLayout) {
                super.onFooting(pullToRefreshLayout);
                currentMode = MODE_MORE;
                pageNo++;
                getDataList();
                LogDebug.d("来了，onFooting");
            }
        });
        rv_xiangce.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToastShort("单击我了" + position);
            }
        });

    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        mAdapter = new Adapter(getActivity(), mDatas);
        rv_xiangce.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        rv_xiangce.setAdapter(mAdapter);
        rv_xiangce.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.GRID_LIST, getResources().getDrawable(R.drawable.shape_div_2)));
        getDataList();
    }

    private void getDataList() {
        HttpUtils.RequestParam params = new HttpUtils.RequestParam();
        params.action("yiren_info");
        params.add("userid", userid);

        params.add("p", String.valueOf(pageNo));
        params.add("limit", "20");
        //http://www.258star.com/iumobile/apis/index.php?action=yiren_info&userid=20417&p=1&limit=5
        HttpUtils.getInstance().interfaceapi(NetConfig.iumobileapi).params(params).executeGet(new HttpUtils.HttpListener() {
            @Override
            public void onHttpSuccess(String string) {
                super.onHttpSuccess(string);
                if (currentMode == MODE_REFRESH) {
                    mDatas.clear();
                }
                Gson gson = new Gson();
                TypeToken<TestXiangceResponse> typeToken = new TypeToken<TestXiangceResponse>() {
                };
                Type type = typeToken.getType();
                TestXiangceResponse testXiangceResponse = gson.fromJson(string, type);
                if (testXiangceResponse.getCode().equals("200")) {
                    ArrayList<TestXiangceResponse.Photo> photo = testXiangceResponse.getPhoto();
                    for (int i = 0; i < photo.size(); i++) {
                        mDatas.add(photo.get(i));
                    }
                    mAdapter.notifyDataSetChangedHandler();

                } else {
                    if (currentMode == MODE_MORE) {
                        pageNo--;
                    }
                }
                if (currentMode == MODE_REFRESH) {
                    stopRefrech();
                } else {
                    stopLoadMore();
                }
            }

            @Override
            public void onException(Throwable e) {
                super.onException(e);
                if (currentMode == MODE_REFRESH) {
                    stopRefrech();
                } else {
                    stopLoadMore();
                    pageNo--;
                }
            }
        });
    }

    private void stopRefrech() {
        mHandler.sendEmptyMessage(0);
    }

    private void stopLoadMore() {
        mHandler.sendEmptyMessage(1);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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

    private class Adapter extends BaseAdapter<Holder, TestXiangceResponse.Photo> {
        public Adapter(Context context, ArrayList<TestXiangceResponse.Photo> datas) {
            super(context, datas);
        }

        @Override
        protected Holder createViewHolder(ViewGroup parent, View itemView, int viewType) {
            return new Holder(itemView);
        }

        @Override
        public View createItemView(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.test_item_xiangce, parent, false);
            return view;
        }

        @Override
        protected void afterBindViewHolder(Holder holder, int position) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            lp.height = CommonUtil.getDisplayMetrics(getContext()).widthPixels/2;
            lp.width = CommonUtil.getDisplayMetrics(getContext()).heightPixels/2;
            TestXiangceResponse.Photo itemInfo = getItem(position);
            holder.tv_title.setText(itemInfo.getTitle());
            Utils.showImage(getContext(),holder.iv_pic,("http://www.258star.com/static_data/uploaddata/photo/"+itemInfo.getPath()));
            long tmp = Long.parseLong(itemInfo.getAddtime())*1000;
            holder.tv_addtime.setText(CommonUtil.formatDate(tmp,"yyyy-MM-dd HH:mm:ss"));
        }
    }

    private class Holder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @Inject(R.id.iv_pic)
        public ImageView iv_pic;
        @Inject(R.id.tv_title)
        public TextView tv_title;
        @Inject(R.id.tv_addtime)
        public TextView tv_addtime;

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
