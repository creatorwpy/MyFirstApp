package com.wpy.xh.base.adapter;

import android.content.Context;

import java.util.ArrayList;

import library.mlibrary.view.recyclerview.RecyclerView;
import library.mlibrary.view.recyclerview.grouprecyclerview.AbsBaseDataGroupAdapter;

/**
 * Created by harmy on 2016/8/23 0023.
 */
public abstract class BaseGroupAdapter<GH extends RecyclerView.ViewHolder, VH extends RecyclerView.ViewHolder, T> extends AbsBaseDataGroupAdapter<GH, VH, T> {
    public BaseGroupAdapter(Context context, int layoutId, ArrayList<T> datas) {
        super(context, layoutId, datas);
    }

    public BaseGroupAdapter(Context context, int layoutId, int grouplayoutid, ArrayList<T> datas) {
        super(context, layoutId, grouplayoutid, datas);
    }

    public BaseGroupAdapter(Context context, ArrayList<T> datas) {
        super(context, datas);
    }
}
