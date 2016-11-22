package com.wpy.xh.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import library.mlibrary.view.banner.adapter.AbsBannerPageAdapter;

/**
 * Created by harmy on 2016/8/9 0009.
 */
public abstract class BaseBannerAdapter<VH extends RecyclerView.ViewHolder,T> extends AbsBannerPageAdapter<VH,T> {
    public BaseBannerAdapter(Context Context, List<T> datas, int layoutid) {
        super(Context, datas, layoutid);
    }

    public BaseBannerAdapter(Context Context, List<T> datas) {
        super(Context, datas);
    }
}
