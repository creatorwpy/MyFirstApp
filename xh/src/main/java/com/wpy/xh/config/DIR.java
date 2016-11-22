package com.wpy.xh.config;

import android.os.Environment;

/**
 * 文件路径
 * Created by harmy on 2016/8/1 0001.
 */
public class DIR {
    private static final String privatedir = Environment.getExternalStorageDirectory().getPath() + "/Android/data/.com.wpy.xh/";
    //// /storage/emulated/0/Android/data/.com.wpy.xh/
    /**
     * 缓存文件目录
     */
    public static final String CACHE = privatedir + "cache/";
    /**
     * 异常崩溃日志目录
     */
    public static final String LOG_CRASH = CACHE + "crash/";
    /**
     * Glide缓存图片目录
     */
    public static final String CACHE_GLIDE = CACHE + "glide/";
    /**
     * 缓存图片目录
     */
    public static final String CACHE_PICTURE = CACHE + "picture/";
    /**
     * 缓存图片目录
     */
    public static final String CACHE_APK = CACHE + "apk/";

}
