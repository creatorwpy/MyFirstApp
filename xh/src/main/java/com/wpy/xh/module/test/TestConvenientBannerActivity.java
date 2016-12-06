package com.wpy.xh.module.test;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wpy.xh.R;
import com.wpy.xh.base.BaseActivity;
import com.wpy.xh.base.adapter.BaseBannerAdapter;

import java.util.ArrayList;
import java.util.List;

import library.mlibrary.util.inject.Inject;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.banner.ConvenientBanner;
import library.mlibrary.view.recyclerview.RecyclerView;

public class TestConvenientBannerActivity extends BaseActivity {
    @Inject(R.id.banner)
    private ConvenientBanner banner;
    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_test_banner);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {
        LogDebug.d("TestConvenientBannerActivity22");
    }

    private TestConvenientBannerActivity.HeadAdapter mHeadAdapter;
    private  ArrayList<String> mHeadInfos;
    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        mHeadInfos = new ArrayList<>();
        mHeadInfos.clear();
        mHeadInfos.add("a");
        mHeadInfos.add("b");

        mHeadAdapter = new TestConvenientBannerActivity.HeadAdapter(getThis(), mHeadInfos);
        banner.setAdapter(mHeadAdapter);
        banner.setPageIndicatorOrientation(ConvenientBanner.PageIndicatorOrientation.HORIZONTAL);
        banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL, ConvenientBanner.PageIndicatorAlign.BOTTOM);
        banner.setPageIndicatorVisible(true);
        banner.setPageIndicator(new int[]{R.drawable.shape_indicator_no, R.drawable.shape_indicator_on}, mHeadInfos.size());
    }
    private class HeadAdapter extends BaseBannerAdapter<TestConvenientBannerActivity.HeadHolder, String> {
        public HeadAdapter(Context Context, List<String> datas) {
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
        public TestConvenientBannerActivity.HeadHolder createViewHolder(ViewGroup parent, View itemView, int viewType) {
            return new TestConvenientBannerActivity.HeadHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TestConvenientBannerActivity.HeadHolder holder, int position) {
            String item = getItem(position);
            switch (getViewType(position)) {
                case 0:

                    break;
                case 1:

                    break;
            }
        }
    }

    public class HeadHolder extends RecyclerView.ViewHolder {

        public HeadHolder(View itemView) {
            super(itemView);
        }
    }
}
