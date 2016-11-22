package com.wpy.xh.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.view.recyclerview.AbsBaseAdapter;

/**
 * Created by harmy on 2016/8/9 0009.
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends AbsBaseAdapter<VH> {
    private ArrayList<T> mDatas;

    public BaseAdapter(Context context, int layoutId, ArrayList<T> datas) {
        super(context, layoutId);
        mDatas = datas;
    }

    public BaseAdapter(Context context, ArrayList<T> datas) {
        super(context);
        mDatas = datas;
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(mDatas)) {
            return 0;
        } else
            return mDatas.size();
    }

    public T getItem(int position) {
        int size = getItemCount();
        if (size == 0) {
            return null;
        } else if (position > size - 1) {
            return mDatas.get(size - 1);
        } else
            return mDatas.get(position);
    }
}
