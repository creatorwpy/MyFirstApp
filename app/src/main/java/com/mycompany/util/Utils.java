package com.mycompany.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;

/**
 * Created by harmy on 2016/8/3 0003.
 */
public class Utils {
    public static final String TAG_GLIDE = "pic";
    public static void showImage(Activity activity, ImageView imageView, @DrawableRes int resId) {
        Glide.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImage(Fragment activity, ImageView imageView, @DrawableRes int resId) {
        Glide.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImage(Context activity, ImageView imageView, @DrawableRes int resId) {
        Glide.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImage(Activity activity, ImageView imageView, String url) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImage(Fragment activity, ImageView imageView, String url) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImage(Context activity, ImageView imageView, String url) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImageNoGif(Activity activity, ImageView imageView, @DrawableRes int resId) {
        Glide.with(activity).load(resId).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImageNoGif(Fragment activity, ImageView imageView, @DrawableRes int resId) {
        Glide.with(activity).load(resId).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImageNoGif(Context activity, ImageView imageView, @DrawableRes int resId) {
        Glide.with(activity).load(resId).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImageNoGif(Activity activity, ImageView imageView, String url) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImageNoGif(Fragment activity, ImageView imageView, String url) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImageNoGif(Context activity, ImageView imageView, String url) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
    }

    public static void showImageWithListener(Activity activity, ImageView imageView, @DrawableRes int resId, RequestListener listener) {
        Glide.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().listener(listener).into(imageView);
    }

    public static void showImageWithListener(Fragment activity, ImageView imageView, @DrawableRes int resId, RequestListener listener) {
        Glide.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().listener(listener).into(imageView);
    }

    public static void showImageWithListener(Context activity, ImageView imageView, @DrawableRes int resId, RequestListener listener) {
        Glide.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().listener(listener).into(imageView);
    }

    public static void showImageWithListener(Activity activity, ImageView imageView, String url, RequestListener listener) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().listener(listener).into(imageView);
    }

    public static void showImageWithListener(Fragment activity, ImageView imageView, String url, RequestListener listener) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().listener(listener).into(imageView);
    }

    public static void showImageWithListener(Context activity, ImageView imageView, String url, RequestListener listener) {
        LogDebug.d(TAG_GLIDE, url);
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().listener(listener).into(imageView);
    }
}
