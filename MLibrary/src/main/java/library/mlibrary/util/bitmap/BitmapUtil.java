package library.mlibrary.util.bitmap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.ThumbnailUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Harmy on 2016/4/29 0029.
 */
public class BitmapUtil {
    /* ******************************** bitmap begin *************************************************** */

    /**
     * 生成圆形bitmap
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int radius = height > width ? width / 2 : height / 2;//得到半径
//        int roundPx = (int) (Math.sqrt(radius*2 * radius*2 * 2.0d) / 2);
        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(width,
                    height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
//            canvas.drawRoundRect(0,0,width,height,roundPx,roundPx,paint);
            canvas.drawCircle(width / 2, height / 2, radius, paint);//先画一个圆
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//SRC_IN的属性是去两者重叠部分的上层的图像
            canvas.drawBitmap(bitmap, 0, 0, paint);//把需要的bitmap画上去，因为设置了SRC_IN,将会的到一个圆形的bitmap
            bitmap.recycle();
            return output;
        } catch (Exception e) {
            if (output != null) {
                output.recycle();
            }
            return bitmap;
        }
    }

    /**
     * 生成圆角bitmap
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    public static Bitmap getRoundBitmap(Bitmap bitmap, float roundPx) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(width,
                    height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(0, 0, width, height, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, 0, 0, paint);
            bitmap.recycle();
            return output;
        } catch (Exception e) {
            if (output != null) {
                output.recycle();
            }
            return bitmap;
        }
    }

    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    public static Bitmap sizeCompress(String imgPath, float pixelW, float pixelH) {
        Bitmap image = getBitmap(imgPath);
        Bitmap bitmap = sizeCompress(image, pixelW, pixelH);
        image.recycle();
        return bitmap;
    }

    public static Bitmap sizeCompress(String imgPath, int maxSize) {
        Bitmap image = getBitmap(imgPath);
        Bitmap bitmap = sizeCompress(image, maxSize);
        image.recycle();
        return bitmap;
    }

    public static Bitmap sizeCompress(Bitmap image, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        int size = os.toByteArray().length / 1024;//kb
        double scale = Math.sqrt((double) maxSize / (double) size);
        int neww = (int) (image.getWidth() * scale);
        int newh = (int) (image.getHeight() * scale);
        return sizeCompress(image, neww, newh);
    }

    public static Bitmap sizeCompress(Bitmap image, float pixelW, float pixelH) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scalew = pixelW / width;
        float scaleh = pixelH / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scalew, scaleh);
        Bitmap bitmap = Bitmap.createBitmap(image, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    private static ByteArrayOutputStream Compress2os(Bitmap image, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        int size = os.toByteArray().length / 1024;//kb
        int step = 10;
        int min = 5;
        if (size < 1024 * 1) {//小于1M
            min = 1;
            step = 2;
        } else if (size < 1024 * 5) {//小于5M
            min = 2;
            step = 5;
        } else if (size < 1024 * 10) {//小于10M
            min = 3;
            step = 10;
        } else {//大于10M
            min = 5;
            step = 20;
        }
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize && options > min) {
            // Clean up os
            os.reset();
            // interval 10
            options -= step;
            if (options < min) {
                options = min;
            }
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        return os;
    }

    public static void qualityCompress(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = Compress2os(image, maxSize);

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    public static void qualityCompress(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        Bitmap bitmap = getBitmap(imgPath);
        qualityCompress(bitmap, outPath, maxSize);
        bitmap.recycle();
        bitmap = null;
        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void sizeCompress(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = sizeCompress(image, pixelW, pixelH);
        storeImage(bitmap, outPath);
    }

    public static void sizeCompress(Bitmap image, String outPath, int maxSize) throws FileNotFoundException {
        Bitmap bitmap = sizeCompress(image, maxSize);
        storeImage(bitmap, outPath);
        bitmap.recycle();
    }

    public static void sizeCompress(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = sizeCompress(imgPath, pixelW, pixelH);
        storeImage(bitmap, outPath);
        bitmap.recycle();
        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void sizeCompress(String imgPath, String outPath, int maxSize, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = sizeCompress(imgPath, maxSize);
        storeImage(bitmap, outPath);
        bitmap.recycle();
        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    /* ******************************** bitmap end *************************************************** */
}
