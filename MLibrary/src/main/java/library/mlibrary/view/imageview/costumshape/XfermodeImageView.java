package library.mlibrary.view.imageview.costumshape;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import library.mlibrary.R;
import library.mlibrary.util.log.LogDebug;

/**
 * 自定义形状的imageview，通过background设置不同形状
 */
public class XfermodeImageView extends ImageView {

    protected Paint mPaint;
    protected Xfermode mXfermode = new PorterDuffXfermode(Mode.DST_IN);
    protected Bitmap mMaskBitmap;
    protected Bitmap mMaskBorderBitmap;

    /**
     * 边框宽度
     */
    protected float mBorderWidth;

    /**
     * 边框颜色
     */
    private int mBorderColor;

    protected WeakReference<Bitmap> mWeakBitmap;

    public XfermodeImageView(Context context) {
        this(context, null);
    }

    public XfermodeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XfermodeImageView, defStyle, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.XfermodeImageView_xiv_border_width, 0);
        mBorderColor = a.getColor(R.styleable.XfermodeImageView_xiv_border_color, Color.LTGRAY);
        a.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // 在缓存中取出bitmap
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        try {
            if (null == bitmap || bitmap.isRecycled()) {
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
                Canvas drawCanvas = new Canvas(bitmap);
                drawCanvas.drawBitmap(getBorderBitmap(), 0, 0, null);
                drawCanvas.drawBitmap(getSrcBitmap(), mBorderWidth, mBorderWidth, null);
                mPaint.setXfermode(null);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
                // bitmap缓存起来，避免每次调用onDraw，分配内存
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
            // 如果bitmap还存在，则直接绘制即可
            else if (bitmap != null) {
                mPaint.setXfermode(null);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
            }
        } catch (Exception e) {
//            LogDebug.e(e);
        }
    }

    private Bitmap getSrcBitmap() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            // 获取drawable的宽和高
            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();
            int rWidth = (int) (getWidth() - mBorderWidth * 2);
            int rHeight = (int) (getHeight() - mBorderWidth * 2);

            // 创建bitmap
            Bitmap bitmap = Bitmap.createBitmap(rWidth, rHeight, Config.ARGB_8888);
            float scale = 1.0f;
            // 创建画布
            Canvas drawCanvas = new Canvas(bitmap);
            // 按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
            scale = Math.max(rWidth * 1.0f / dWidth, rHeight * 1.0f / dHeight);
            // 根据缩放比例，设置bounds，相当于缩放图片了
            drawable.setBounds((rWidth - (int) (scale * dWidth)) / 2, (rHeight - (int) (scale * dHeight)) / 2
                    , (rWidth + (int) (scale * dWidth)) / 2, (rHeight + (int) (scale * dHeight)) / 2);
            drawable.draw(drawCanvas);
            if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
//                    mMaskBitmap = getBitmap();
                mMaskBitmap = drawable2Bitmap(getBackground(), (int) mBorderWidth);
            }
            // Draw Bitmap.
            mPaint.reset();
            mPaint.setFilterBitmap(false);
            mPaint.setXfermode(mXfermode);
            // 绘制形状
            drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
            mPaint.setXfermode(null);
            return bitmap;
        }
        return null;
    }

    private Bitmap getBorderBitmap() {
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        // 创建画布
        Canvas drawCanvas = new Canvas(bitmap);
        drawCanvas.drawColor(mBorderColor);
        if (mMaskBorderBitmap == null || mMaskBorderBitmap.isRecycled()) {
            mMaskBorderBitmap = drawable2Bitmap(getBackground(), 0);
        }
        // Draw Bitmap.
        mPaint.reset();
        mPaint.setFilterBitmap(false);
        mPaint.setXfermode(mXfermode);
        // 绘制形状
        drawCanvas.drawBitmap(mMaskBorderBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        return bitmap;
    }

    @Override
    public Drawable getBackground() {
        if (super.getBackground() == null) {
            setBackgroundResource(R.drawable.shape_rect);
        }
        return super.getBackground();
    }

    /**
     * 绘制形状
     *
     * @return
     */

    private Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(drawable2Bitmap(getBackground(), 0), 0, 0, paint);
        return bitmap;
    }

    /**
     * Drawable 转 bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawable2Bitmap(Drawable drawable, int cut) {
        int dWidth = getWidth();
        int dHeight = getHeight();
//        int dWidth = drawable.getIntrinsicWidth();
//        int dHeight = drawable.getIntrinsicHeight();
        int rWidth = (int) (getWidth() - cut * 2);
        int rHeight = (int) (getHeight() - cut * 2);
        float scale = 1.0f;
        Bitmap bitmap = Bitmap.createBitmap(rWidth, rHeight,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // 按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
        scale = Math.max(rWidth * 1.0f / dWidth, rHeight * 1.0f / dHeight);
        // 根据缩放比例，设置bounds，相当于缩放图片了
        drawable.setBounds((rWidth - (int) (scale * dWidth)) / 2, (rHeight - (int) (scale * dHeight)) / 2
                , (rWidth + (int) (scale * dWidth)) / 2, (rHeight + (int) (scale * dHeight)) / 2);
        drawable.draw(canvas);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        return bitmap;
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }
}