package library.mlibrary.util.zxing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import library.mlibrary.util.log.LogDebug;

/**
 * Created by Harmy on 2016/5/10 0010.
 */
public class ZxingUtil {
    /**
     * 生成二维码
     *
     * @param content
     * @return
     */
    public static Bitmap barcodeEncode(String content, int size) {
        BitMatrix bitMatrix = null;// 生成矩阵
        try {
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, size, size);
            Bitmap bitmap = bitMatrix2Bitmap(bitMatrix);
            return bitmap;
        } catch (WriterException e) {
            LogDebug.e(e);
            return null;
        }
    }


    /**
     * 解析二维码
     *
     * @param bitmap
     * @return
     */
    public static Result barcodeDecode(Bitmap bitmap) {
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Result result = null;
        try {
            result = new MultiFormatReader().decode(binaryBitmap);
        } catch (NotFoundException e) {
            LogDebug.e(e);
        }
        return result;
    }

    /**
     *
     * 解析二维码
     *
     * @return
     */
    public static Result barcodeDecode(byte[] bytes, Camera camera) {
        int previewWidth = camera.getParameters().getPreviewSize().width;
        int previewHeight = camera.getParameters().getPreviewSize().height;
        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(bytes, previewWidth, previewHeight, 0, 0, previewWidth, previewHeight);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = null;
        try {
            result = new MultiFormatReader().decode(bitmap);
        } catch (NotFoundException e) {
            LogDebug.e(e);
        }
        return result;
    }

    private static Bitmap bitMatrix2Bitmap(BitMatrix matrix) {
        int w = matrix.getWidth();
        int h = matrix.getHeight();
        int[] rawData = new int[w * h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int color = Color.WHITE;
                if (matrix.get(i, j)) {
                    color = Color.BLACK;
                }
                rawData[i + (j * w)] = color;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
