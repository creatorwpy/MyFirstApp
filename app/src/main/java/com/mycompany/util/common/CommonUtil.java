package com.mycompany.util.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.mycompany.util.LogDebug;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Harmy on 2016/4/7 0007.
 */
public class CommonUtil {
    /* ******************************** dateutil begin *************************************************** */

    /**
     * 获取当前系统时间（毫秒）
     */
    public static long getCurrentMS() {
        long ms = System.currentTimeMillis();
        return ms;
    }

    public static long getCurrentDayMS() {
        long ms = getCurrentMS();
        String dataStr = formatDate(ms, "yyyyMMdd");
        long dayms = formatDate(dataStr, "yyyyMMdd");
        return dayms;
    }

    /**
     * 获取指定格式的当前时间，返回字符串
     *
     * @param format example:"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentDate(String format) {
        String dateStr = formatDate(getCurrentMS(), format);
        return dateStr;
    }

    /**
     * 将指定时间（毫秒）格式化成指定格式
     *
     * @param ms     example："45646548"
     * @param format example:"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String formatDate(long ms, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(ms);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    /**
     * 将指定格式的时间（字符串）格式化成毫秒,ps：只能精确到秒(后三位会显示000)
     *
     * @param datestr
     * @param format
     * @return
     */
    public static long formatDate(String datestr, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(datestr);
            calendar.setTime(date);
        } catch (ParseException e) {
            Date date = new Date(getCurrentMS());
            calendar.setTime(date);
        }
        long ms = calendar.getTimeInMillis();
        return ms;
    }

    /**
     * 将指定格式的时间（字符串）格式化成指定格式的时间（字符串）
     *
     * @param datestr
     * @param informat,outformat
     * @return
     */
    public static String formatDate(String datestr, String informat, String outformat) {
        long ms = formatDate(datestr, informat);
        String outStr = formatDate(ms, outformat);
        return outStr;
    }

    /**
     * 获取当前日期偏离后的日期
     *
     * @param field 单位：Calendar.DATE Calendar.MONTH Calendar.YEAR
     * @param off   偏离的数值，正负表示加减
     * @return
     */
    public static long getDateOffCurrent(int field, int off) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, off);
        long time = calendar.getTimeInMillis();
        return time;
    }

    /**
     * 获取指定日期偏离后的日期
     *
     * @param field  单位：Calendar.DATE Calendar.MONTH Calendar.YEAR
     * @param off    偏离的数值，正负表示加减
     * @param format
     * @return
     */
    public static long getDateOff(String datestr, int field, int off, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(datestr);
            calendar.setTime(date);
        } catch (ParseException e) {
            Date date = new Date(getCurrentMS());
            calendar.setTime(date);
        }
        calendar.add(field, off);
        long time = calendar.getTimeInMillis();
        return time;
    }

    /**
     * 获取指定日期偏离后的日期
     *
     * @param field 单位：Calendar.DATE Calendar.MONTH Calendar.YEAR
     * @param off   偏离的数值，正负表示加减
     * @return
     */
    public static long getDateOff(long timems, int field, int off) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(timems);
        calendar.setTime(date);
        calendar.add(field, off);
        long time = calendar.getTimeInMillis();
        return time;
    }

    /**
     * 获得指定时间是第几周
     *
     * @return 1-7： 周日-周六
     */
    public static int getDayInWeek(String datestr, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(datestr);
            calendar.setTime(date);
        } catch (ParseException e) {
            Date date = new Date(getCurrentMS());
            calendar.setTime(date);
        }
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得指定时间是第几周
     *
     * @return 1-7： 周日-周六
     */
    public static int getDayInWeek(long timems) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(timems);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 获取友好时间
     * 例如：刚刚，一分钟前，半小时前，一小时前
     *
     * @return
     */
    public static String getFixedTime(long beginMillis) {
        long second = 1000;
        long minute = 60 * second;
        long half_hour = 30 * minute;
        long hour = 2 * half_hour;
        long currentMillis = getCurrentMS();
        long off = currentMillis - beginMillis;
        if (off <= minute) {
            return "刚刚";
        } else if (off <= half_hour) {
            int num = (int) (off / minute);
            return num + "分钟前";
        } else if (off <= hour) {
            return "半小时前";
        } else if (off <= 2 * hour) {
            return "1小时前";
        } else return formatDate(beginMillis, "HH:mm");
    }

    /* ******************************** dateutil end *************************************************** */


    /* ******************************** stringutil begin *************************************************** */

    /**
     * 判断字符串是否为空(包括null,"","null")
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        if (value == null) {
            return true;
        }
        if (value.equals("")) {
            return true;
        }
        return value.equals("null");
    }

    /**
     * 是否全为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 正则表达式匹配
     *
     * @param str
     * @param reg
     * @return
     */
    public static boolean stringMatcher(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 过滤匹配的字符
     *
     * @param str
     * @param reg
     * @return
     */
    public static String stringFilter(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
    /* ******************************** stringutil end *************************************************** */

    /* ******************************** 数据转换 begin *************************************************** */

    public static InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static InputStream String2Input(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    public static byte[] Input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static String Input2String(InputStream inStream)
            throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static byte[] File2byte(String filePath) throws FileNotFoundException, IOException {
        byte[] buffer = null;
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        return buffer;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName) throws FileNotFoundException, IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            dir.mkdirs();
        }
        file = new File(filePath + File.separator + fileName);
        fos = new FileOutputStream(file);
        bos = new BufferedOutputStream(fos);
        bos.write(buf);
        if (bos != null) {
            bos.close();
        }
        if (fos != null) {
            fos.close();
        }
    }

    public static void byteWriteInFile(byte[] buf, BufferedOutputStream bos) throws IOException {
        bos.write(buf);
    }

    public static void Input2File(InputStream input, String filePath, String fileName) throws FileNotFoundException, IOException {
        File file = null;
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            dir.mkdirs();
        }
        file = new File(filePath + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file.getPath());
        int ch = 0;
        while ((ch = input.read()) != -1) {
            fos.write(ch);
        }
        if (fos != null) {
            fos.close();
        }
        if (input != null) {
            input.close();
        }
    }

    public static InputStream File2Input(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /* ******************************** 数据转换 end *************************************************** */


    /* ******************************** list begin *************************************************** */

    /**
     * 判断一个list是否为null,是否有数据
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object[] list) {
        if (list == null) {
            return true;
        }
        if (list.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    /* ******************************** list end *************************************************** */


    /* ******************************** file begin *************************************************** */

    /**
     * 目录大小,文件夹大小
     *
     * @return
     */
    public static long getFileSize(String file) {
        File filef = new File(file);
        return getFileSize(filef);
    }

    /**
     * @return
     */
    public static long getFileSize(File file) {
        if (!file.exists()) {
            return 0;
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            long size = 0;
            for (File child : files) {
                size += getFileSize(child);
            }
            return size;
        } else {
            return file.length();
        }
    }

    public static boolean deleteFile(String file) {
        File filef = new File(file);
        return deleteFile(filef);
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            boolean result = true;
            for (File child : files) {
                if (!deleteFile(child)) {
                    result = false;
                    break;
                }
            }
            return result;
        } else {
            return file.delete();
        }
    }

    public static boolean deleteFileAndDir(String file) {
        File filef = new File(file);
        return deleteFileAndDir(filef);
    }

    public static boolean deleteFileAndDir(File file) {
        if (file == null || file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        File[] files = file.listFiles();
        if (files != null) {
            for (File child : files) {
                deleteFileAndDir(child);
            }
        }
        return file.delete();
    }


    public static void copyFile(String fromPath, String toPath) throws IOException {
        File from = new File(fromPath);
        if (!from.exists()) {
            return;
        }
        File toFile = new File(toPath);
        IOUtil.deleteFileOrDir(toFile);
        File toDir = toFile.getParentFile();
        if (toDir.exists() || toDir.mkdirs()) {
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(from);
                out = new FileOutputStream(toFile);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            } catch (Exception ex) {
                LogDebug.e(ex);
            } finally {
                IOUtil.closeQuietly(in);
                IOUtil.closeQuietly(out);
            }
        }
    }

    /**
     * 复制文件到指定文件
     *
     * @param fromPath 源文件
     * @param toPath   复制到的文件
     * @return true 成功，false 失败
     */
    public static boolean copyFileResult(String fromPath, String toPath) {
        boolean result = false;
        File from = new File(fromPath);
        if (!from.exists()) {
            return result;
        }
        File toFile = new File(toPath);
        IOUtil.deleteFileOrDir(toFile);
        File toDir = toFile.getParentFile();
        if (toDir.exists() || toDir.mkdirs()) {
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(from);
                out = new FileOutputStream(toFile);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();

                result = true;
            } catch (Exception ex) {
                LogDebug.e(ex);
                result = false;
            } finally {
                IOUtil.closeQuietly(in);
                IOUtil.closeQuietly(out);
            }
        }
        return result;
    }

    /**
     * 合并文件
     *
     * @param tempFiles 合并的文件列表
     * @param filePath  合并后文件路径
     * @return
     */
    public static File mergerFile(ArrayList<File> tempFiles, String filePath) {

        File file = new File(filePath);
//        file.deleteOnExit();
        if (file.exists()) {
            if (!file.delete()) {
                LogDebug.d("删除文件失败");
            }

        }
        // 合并文件
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            for (int i = 0; i < tempFiles.size(); i++) {
                // 分块文件路径
                String pieceFilePath = tempFiles.get(i).getPath();

                //读取分块文件
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(pieceFilePath);

                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                } catch (IOException e) {
                    LogDebug.e(e);
                } finally {
                    fis.close();
                }
            }
            //完成分块文件合并
            return file;
        } catch (IOException e) {
            LogDebug.e(e);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                LogDebug.e(e);
            } catch (Exception e) {
                LogDebug.e(e);
            }
        }
        return null;
    }

    /**
     * 合并文件
     *
     * @param tempFiles 合并的文件列表
     * @param filePath  合并后文件路径
     * @return
     */
    public static File mergerFile(ArrayList<File> tempFiles, String filePath, boolean deletetemp) {

        File file = new File(filePath);
//        file.deleteOnExit();
        if (file.exists()) {
            if (!file.delete()) {
                LogDebug.d("删除文件失败");
            }

        }
        // 合并文件
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            for (int i = 0; i < tempFiles.size(); i++) {
                // 分块文件路径
                String pieceFilePath = tempFiles.get(i).getPath();

                //读取分块文件
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(pieceFilePath);

                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    if (deletetemp) {
                        tempFiles.get(i).delete();
                    }
                } catch (IOException e) {
                    LogDebug.e(e);
                } finally {
                    fis.close();
                }
            }
            //完成分块文件合并
            return file;
        } catch (IOException e) {
            LogDebug.e(e);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                LogDebug.e(e);
            } catch (Exception e) {
                LogDebug.e(e);
            }
        }
        return null;
    }

    /**
     * 分割文件
     *
     * @param filePath  源文件路径
     * @param tempDir   临时文件路径
     * @param blocksize 分块大小
     * @return
     */
    public static ArrayList<File> splitFile(String filePath, String tempDir, int blocksize) {
        File tempDirF = new File(tempDir);
        if (!tempDirF.exists()) {
            tempDirF.mkdirs();
        }
        File file = new File(filePath);
        ArrayList<File> tempFiles = new ArrayList<>();
        String[] split = filePath.split("/");
        String filename = split[split.length - 1];
        String[] splitz = filename.split("\\.");
        String name = splitz[0];
        String filetype = splitz[1];
        if (file.length() > blocksize) {
            long picecount = file.length() / blocksize + (file.length() % blocksize == 0 ? 0 : 1);
            for (int i = 0; i < picecount; i++) {
                RandomAccessFile raf = null;
                FileOutputStream faos = null;
                File tempfile = new File(tempDir + "/" + name + "temp" + i + "." + filetype);
                try {
                    faos = new FileOutputStream(tempfile);
                    // 负责读取数据
                    raf = new RandomAccessFile(filePath, "r");
                    //设置缓冲大小
                    byte[] buf;
                    if (blocksize > 1024) {
                        buf = new byte[1024];
                    } else {
                        buf = new byte[blocksize];
                    }
                    //跳过的字节数
                    raf.seek(blocksize * i);
                    //读取对应文件的流
                    int length = 0;//累计读取流的长度
                    int count = 0;//一次读取的流的长度
                    while ((count = raf.read(buf)) != -1 && length < blocksize) {
                        //在分块时，判断分块文件的最后一组字节数组是否需要全部写出
                        if (blocksize - length < buf.length) {
                            faos.write(buf, 0, blocksize - length);
                            count = blocksize - length;
                        } else {
                            faos.write(buf, 0, count);
                        }
                        //已经写出的字节长度
                        length += count;
                    }
                    tempFiles.add(tempfile);
                } catch (FileNotFoundException e) {
                    LogDebug.e(e);
                } catch (IOException e) {
                    LogDebug.e(e);
                } finally {
                    try {
                        raf.close();
                        faos.close();
                    } catch (IOException e) {
                        LogDebug.e(e);
                    }
                }
            }
        } else {
            tempFiles.add(file);
        }
        return tempFiles;
    }

    /**
     * 根据索引获取文件
     *
     * @param filePath   文件路径
     * @param startIndex 读取文件字节起始索引
     */
    public static byte[] getSplitFileByte(String filePath, int startIndex, int blocksize) {

        // 根据要求读取块文件
        RandomAccessFile raf = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 负责读取数据
            raf = new RandomAccessFile(filePath, "r");
            //设置缓冲大小
            byte[] buf;
            if (blocksize > 1024) {
                buf = new byte[1024];
            } else {
                buf = new byte[blocksize];
            }
            //跳过的字节数
            raf.seek(startIndex * blocksize);
            //读取对应文件的流
            int length = 0;//累计读取流的长度
            int count = 0;//一次读取的流的长度
            while ((count = raf.read(buf)) != -1 && length < blocksize) {
                //在分块时，判断分块文件的最后一组字节数组是否需要全部写出
                if (blocksize - length < buf.length) {
                    baos.write(buf, 0, blocksize - length);
                    count = blocksize - length;
                } else {
                    baos.write(buf, 0, count);
                }
                //已经写出的字节长度
                length += count;
            }
            //压缩
            /*ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
            compress(is, baos);*/
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            LogDebug.e(e);
        } catch (Exception e) {
            LogDebug.e(e);
        } finally {
            try {
                if (null != raf) {
                    raf.close();
                }
            } catch (Exception e) {
                LogDebug.e(e);
            }
        }
        return null;

    }

    /**
     * 分割文件
     *
     * @param filePath  源文件路径
     * @param blocksize 分块大小
     * @return
     */
    public static long splitFileCount(String filePath, int blocksize) {
        File file = new File(filePath);
        long picecount = file.length() / blocksize + (file.length() % blocksize == 0 ? 0 : 1);
        return picecount;
    }

    /* ******************************** file end *************************************************** */


    /* ******************************** array begin *************************************************** */

    /**
     * 合并两个byte[]
     *
     * @param first
     * @param second
     * @return
     */
    public static byte[] concatArray(byte[] first, byte[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        int flength = first.length;
        int slength = second.length;
        int length = flength + slength;
        byte[] result = new byte[length];
        for (int i = 0; i < flength; i++) {
            result[i] = first[i];
        }
        for (int i = 0; i < slength; i++) {
            result[flength + i] = second[i];
        }
        return result;
    }

    public static Field[] concatArray(Field[] first, Field[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        int flength = first.length;
        int slength = second.length;
        int length = flength + slength;
        Field[] result = new Field[length];
        for (int i = 0; i < flength; i++) {
            result[i] = first[i];
        }
        for (int i = 0; i < slength; i++) {
            result[flength + i] = second[i];
        }
        return result;
    }

    /* ******************************** array end *************************************************** */


    /* ******************************** 屏幕 begin *************************************************** */

    /**
     * 获取屏幕DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int dip2px(float dpValue, float density) {
        return (int) (dpValue * density + 0.5F);
    }

    public static int px2dip(float pxValue, float density) {
        return (int) (pxValue / density + 0.5F);
    }

    public static int dip2px(float dpValue, Context context) {
        return dip2px(dpValue, getDisplayMetrics(context).density);
    }

    public static int px2dip(float pxValue, Context context) {
        return px2dip(pxValue, getDisplayMetrics(context).density);
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /* ******************************** 屏幕 end *************************************************** */


    /* ******************************** package begin *************************************************** */

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    public static int compareVersionName(String version1, String version2) {
        int result = 0;
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        for (int i = 0; i < v1.length; i++) {
            int v1i = Integer.valueOf(v1[i]);
            int v2i = Integer.valueOf(v2[i]);
            if (v1i > v2i) {
                result = 1;
                break;
            } else if (v1i == v2i) {
                result = 0;
            } else {
                result = 2;
                break;
            }
        }
        return result;
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static void installApk(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    //获取手机DEVICE_ID
    public static String getDEVICE_ID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /* ******************************** package end *************************************************** */

    /* ******************************** View begin *************************************************** */

    /**
     * ev是否在view的内部
     *
     * @param v
     * @param ev
     * @return
     */
    public static boolean isTouchInView(View v, MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        float sy = location[1];
        float endy = v.getHeight() + sy;
        float sx = location[0];
        float endx = v.getWidth() + sx;
        if (!(sx < x && x < endx && sy < y && y < endy)) {
            return false;
        } else {
            return true;
        }
    }

    public static float getViewDistX(View v1, View v2) {
        int[] location1 = new int[2];
        v1.getLocationOnScreen(location1);
        float sx1 = location1[0];
        int[] location2 = new int[2];
        v2.getLocationOnScreen(location2);
        float sx2 = location2[0];
        return sx1 - sx2;
    }

    public static float getViewDistY(View v1, View v2) {
        int[] location1 = new int[2];
        v1.getLocationOnScreen(location1);
        float sy1 = location1[1];
        int[] location2 = new int[2];
        v2.getLocationOnScreen(location2);
        float sy2 = location2[1];
        return sy1 - sy2;
    }

    /**
     * point是否在view的内部
     *
     * @param v
     * @param point
     * @return
     */
    public static boolean isPointInView(View v, Point point) {
        float x = point.x;
        float y = point.y;
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        float sy = location[1];
        float endy = v.getHeight() + sy;
        float sx = location[0];
        float endx = v.getWidth() + sx;
        if (!(sx < x && x < endx && sy < y && y < endy)) {
            return false;
        } else {
            return true;
        }
    }


    /* ******************************** View end *************************************************** */


}
