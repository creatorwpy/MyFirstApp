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
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import library.mlibrary.R;
import library.mlibrary.util.log.LogDebug;

/**
 * 图片文字，显示background与text重叠的部分
 */
public class TextImageView extends TextView {

    protected Paint mPaint;
    protected Xfermode mXfermode = new PorterDuffXfermode(Mode.DST_IN);
    protected Bitmap mMaskBitmap;

    protected WeakReference<Bitmap> mWeakBitmap;

    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextImageView, defStyle, 0);
        mSrcId = a.getResourceId(R.styleable.TextImageView_tiv_src, R.drawable.shape_black);
        a.recycle();
        setPadding(0, 0, 8, 0);
    }

    private int mSrcId;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // 在缓存中取出bitmap
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        try {
            if (null == bitmap || bitmap.isRecycled()) {
                bitmap = getSrcBitmap();
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                // bitmap缓存起来，避免每次调用onDraw，分配内存
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
            // 如果bitmap还存在，则直接绘制即可
            else if (bitmap != null) {
                mPaint.setXfermode(null);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
            }
        } catch (Exception e) {
            LogDebug.e(e);
        }
    }

    protected Bitmap getSrcBitmap() {
        Drawable drawable = getResources().getDrawable(mSrcId);
        if (drawable != null) {
            // 获取drawable的宽和高
            int dWidth = getWidth();
            int dHeight = getHeight();
            int rWidth = (int) (getWidth());
            int rHeight = (int) (getHeight());

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
                mMaskBitmap = Text2Bitmap(getText().toString(), 0);
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

    private Bitmap Text2Bitmap(String text, int cut) {
        Bitmap bitmap = drawable2Bitmap(getResources().getDrawable(R.drawable.shape_transparent), cut);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(getTextSize());
        paint.setColor(getCurrentTextColor());
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text, getMeasuredWidth() / 2 - bounds.width() / 2, baseline, paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
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