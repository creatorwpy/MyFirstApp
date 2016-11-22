package library.mlibrary.view.imageview.blur;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import library.mlibrary.R;
import library.mlibrary.util.bitmap.BitmapUtil;

/**
 * Created by harmy on 2016/8/18 0018.
 */
public class BlurImageView extends ImageView {
    public BlurImageView(Context context) {
        this(context, null);
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BlurImageView, defStyleAttr, 0);
        mBlurRadius = a.getInteger(R.styleable.BlurImageView_biv_radius, mBlurRadius);
    }

    private int mBlurRadius = 5;
    protected WeakReference<Bitmap> mWeakBitmap;
    protected Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        // 在缓存中取出bitmap
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        try {
            if (null == bitmap || bitmap.isRecycled()) {
                Bitmap srcBitmap=getSrcBitmap();
                bitmap = BitmapUtil.doBlur(srcBitmap, mBlurRadius, true);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
                // bitmap缓存起来，避免每次调用onDraw，分配内存
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
            // 如果bitmap还存在，则直接绘制即可
            else if (bitmap != null) {
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
            int rWidth = getWidth();
            int rHeight = getHeight();

            // 创建bitmap
            Bitmap bitmap = Bitmap.createBitmap(rWidth, rHeight, Bitmap.Config.ARGB_8888);
            float scale = 1.0f;
            // 创建画布
            Canvas drawCanvas = new Canvas(bitmap);
            // 按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
            scale = Math.max(rWidth * 1.0f / dWidth, rHeight * 1.0f / dHeight);
            // 根据缩放比例，设置bounds，相当于缩放图片了
            drawable.setBounds((rWidth - (int) (scale * dWidth)) / 2, (rHeight - (int) (scale * dHeight)) / 2
                    , (rWidth + (int) (scale * dWidth)) / 2, (rHeight + (int) (scale * dHeight)) / 2);
            drawable.draw(drawCanvas);
            return bitmap;
        }
        return null;
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        super.invalidate();
    }
}
