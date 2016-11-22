package com.wpy.xh.base.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import library.mlibrary.view.banner.adapter.AbsBannerFragmentAdapter;

/**
 * Created by harmy on 2016/8/4 0004.
 */
public abstract class BaseFragmentAdapter<F extends Fragment> extends AbsBannerFragmentAdapter<F> {
    public BaseFragmentAdapter(Context Context, FragmentManager fm, List<F> fragments) {
        super(Context, fm, fragments);
    }
}
