package library.mlibrary.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import library.mlibrary.constant.Tags;
import library.mlibrary.util.log.LogDebug;

public abstract class AbsCrashHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private Map<String, String> infos = new HashMap<String, String>();
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private String mCrashDir;


    public void init(Context mContext, String dir) {
        this.mContext = mContext;
        mCrashDir = dir;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(thread,ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LogDebug.e(e);
            }
            afterCaughtException(thread,ex);
            // 退出程序
//			android.os.Process.killProcess(android.os.Process.myPid());
//			System.exit(1);
        }
    }

    public abstract void onCaughtException(Thread thread,Throwable e);

    public abstract void afterCaughtException(Thread thread,Throwable e);

    public Context getContext() {
        return mContext;
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Thread thread,Throwable ex) {
        if (ex == null) {
            return false;
        }
        LogDebug.e(Tags.CRASH, thread.getName());
        LogDebug.e(Tags.CRASH, ex.toString(), ex);
        onCaughtException(thread,ex);
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            LogDebug.e(Tags.EXCEPTION, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                LogDebug.d(Tags.DEBUG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogDebug.e(Tags.EXCEPTION, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
//            if (Environment.getExternalStorageState().equals(
//                    Environment.MEDIA_MOUNTED)) {
//                String path = "/sdcard/crash/";
            String path = mCrashDir;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(dir.getPath() +File.separator+ fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
//            }
            return fileName;
        } catch (Exception e) {
            LogDebug.e(Tags.EXCEPTION, "an error occured while writing file...", e);
        }
        return null;
    }
}
