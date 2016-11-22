package com.wpy.xh.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Harmy on 2016/3/3 0003.
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        builder.setDiskCache(new DiskLruCacheFactory(DIR.CACHE_GLIDE, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }

}
