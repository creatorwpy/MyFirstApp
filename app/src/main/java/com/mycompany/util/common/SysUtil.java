package com.mycompany.util.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;


/**
 * Created by harmy on 2015/7/29 0029.
 */
public class SysUtil {
    /**
     * 打开相机拍照，将照片保存到指定路径
     *
     * @param context
     * @param file    照片存放位置
     * @param request
     */
    public static void startCamera(Activity context, File file, int request) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        context.startActivityForResult(intent, request);
    }

    /**
     * 打开相机拍照，将照片保存到指定路径
     *
     * @param context
     * @param file    照片存放位置
     * @param request
     */
    public static void startCamera(Fragment context, File file, int request) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        context.startActivityForResult(intent, request);
    }

    /**
     * 打开相机拍照，将照片保存到指定路径
     *
     * @param context
     * @param file    照片存放位置
     * @param request
     */
    public static void startCamera(Context context, File file, int request) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        ((Activity) context).startActivityForResult(intent, request);
    }

    /**
     * 直接启动系统相机程序
     *
     * @param context
     */
    public static void startSystemCameraApp(Context context) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = context.getPackageManager();
            ResolveInfo info = pm.resolveActivity(i, 0);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(intent);
        } catch (Exception e) {

        }

    }

    /**
     * 从相册获取照片
     *
     * @param context
     * @param request
     */
    public static void choosePhoto(Context context, int request) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(intent, request);
    }

    public static void choosePhoto(Activity context, int request) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, request);
    }

    public static void choosePhoto(Fragment context, int request) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, request);
    }

    /**
     * 将URI转换为真实路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealpathFromUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String path = null;
        if (uri.getScheme().compareTo("content") == 0) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (uri.getScheme().compareTo("file") == 0) {
            path = uri.toString().replace("file://", "");
        }
        return path;
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    public static void chooseFile(Context context, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "请选择一个文件"),
                    request);
        } catch (Exception e) {
            Toast.makeText(context, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    /**
     * 调用文件选择软件来选择文件
     **/
    public static void chooseAudio(Context context, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    request);
        } catch (Exception e) {
            Toast.makeText(context, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    public static void chooseVideo(Context context, int request) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    request);
        } catch (Exception e) {
            Toast.makeText(context, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static WifiManager getWifiManager(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager;
    }

    public static void showSoftInput(View view) {
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && view != null)
            im.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftInput(View view) {
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && view != null && view.getWindowToken() != null)
            im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showSoftInput(Dialog context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static void installApk(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();

            if (type.equalsIgnoreCase("WIFI")) {
                return 1;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileInfo != null) {
                    switch (mobileInfo.getType()) {
                        case ConnectivityManager.TYPE_MOBILE:// 手机网络
                            switch (mobileInfo.getSubtype()) {
                                case TelephonyManager.NETWORK_TYPE_UMTS:
                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                case TelephonyManager.NETWORK_TYPE_HSDPA:
                                case TelephonyManager.NETWORK_TYPE_HSUPA:
                                case TelephonyManager.NETWORK_TYPE_HSPA:
                                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                case TelephonyManager.NETWORK_TYPE_EHRPD:
                                case TelephonyManager.NETWORK_TYPE_HSPAP:
                                    return 3;
                                case TelephonyManager.NETWORK_TYPE_CDMA:
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                case TelephonyManager.NETWORK_TYPE_EDGE:
                                case TelephonyManager.NETWORK_TYPE_1xRTT:
                                case TelephonyManager.NETWORK_TYPE_IDEN:
                                    return 2;
                                case TelephonyManager.NETWORK_TYPE_LTE:
                                    return 4;
                                default:
                                    return 0;
                            }
                    }
                }
            }
        }

        return 0;
    }
}
