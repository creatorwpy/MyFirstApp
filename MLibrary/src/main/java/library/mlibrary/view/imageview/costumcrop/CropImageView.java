package library.mlibrary.view.imageview.costumcrop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableResource;
import com.bumptech.glide.load.resource.gif.GifResourceEncoder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import library.mlibrary.util.bitmap.BitmapUtil;
import library.mlibrary.util.log.LogDebug;

/**
 * Created by Harmy on 2016/4/28 0028.
 */
public class CropImageView extends ImageView {

    /**
     * 长宽最小值
     */
    private int MINSIZE = 100;
    // 在touch重要用到的点，
    private float mX_1 = 0;
    private float mY_1 = 0;
    // 触摸事件判断
    private final int STATUS_SINGLE = 1;
    private final int STATUS_MULTI_START = 2;
    private final int STATUS_MULTI_TOUCHING = 3;
    // 当前状态
    private int mStatus = STATUS_SINGLE;
    // 默认裁剪的宽高
    private int cropWidth;
    private int cropHeight;
    // 浮层Drawable的四个点
    private final int EDGE_LT = 1;
    private final int EDGE_RT = 2;
    private final int EDGE_LB = 3;
    private final int EDGE_RB = 4;
    private final int EDGE_MOVE_IN = 5;
    private final int EDGE_MOVE_OUT = 6;
    private final int EDGE_NONE = 7;

    public int currentEdge = EDGE_NONE;

    protected float oriRationWH = 0;

    protected Drawable mDrawable;
    protected FloatDrawable mFloatDrawable;

    protected Rect mDrawableSrc = new Rect();// 图片Rect变换时的Rect
    protected Rect mDrawableDst = new Rect();// 图片Rect
    protected Rect mDrawableFloat = new Rect();// 浮层的Rect
    protected boolean isFrist = true;
    private boolean isTouchInSquare = true;

    public static final int MODE_FIX = 0;
    public static final int MODE_COSTUM = 1;
    /**
     * 剪裁模式，MODE_FIX 剪裁框固定长宽比， MODE_COSTUM长宽比随意调整
     */
    private int mCropMode = MODE_FIX;

    public void setCropMode(int mode) {
        mCropMode = mode;
        invalidate();
    }

    /**
     * cropwidth/cropheight
     */
    private float mCropScale = -1;

    protected Context mContext;

    public CropImageView(Context context) {
        super(context);
        init(context);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    @SuppressLint("NewApi")
    private void init(Context context) {
        this.mContext = context;
        try {
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                this.setLayerType(LAYER_TYPE_SOFTWARE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mFloatDrawable = new FloatDrawable(context);
    }

    public void setCropSize(int cropWidth, int cropHeight) {
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;
        this.isFrist = true;
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getPointerCount() > 1) {
            if (mStatus == STATUS_SINGLE) {
                mStatus = STATUS_MULTI_START;
            } else if (mStatus == STATUS_MULTI_START) {
                mStatus = STATUS_MULTI_TOUCHING;
            }
        } else {
            if (mStatus == STATUS_MULTI_START
                    || mStatus == STATUS_MULTI_TOUCHING) {
                mX_1 = event.getX();
                mY_1 = event.getY();
            }

            mStatus = STATUS_SINGLE;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX_1 = event.getX();
                mY_1 = event.getY();
                currentEdge = getTouch((int) mX_1, (int) mY_1);
                isTouchInSquare = mDrawableFloat.contains((int) event.getX(),
                        (int) event.getY());

                break;

            case MotionEvent.ACTION_UP:
//                checkBounds();
                break;

            case MotionEvent.ACTION_POINTER_UP:
                currentEdge = EDGE_NONE;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mStatus == STATUS_MULTI_TOUCHING) {

                } else if (mStatus == STATUS_SINGLE) {
                    int dx = (int) (event.getX() - mX_1);
                    int dy = (int) (event.getY() - mY_1);
                    mX_1 = event.getX();
                    mY_1 = event.getY();
                    // 根據得到的那一个角，并且变换Rect
                    if (!(dx == 0 && dy == 0)) {
                        switch (currentEdge) {
                            case EDGE_LT:
                                if (mCropMode == MODE_FIX) {
                                    if (Math.abs(dx) > Math.abs(dy)) {
                                        dy = (int) (dx / mCropScale);
                                    } else {
                                        dx = (int) (mCropScale * dy);
                                    }
                                    if (mDrawableFloat.left + dx < mDrawableLeft) {
                                        dx = mDrawableLeft - mDrawableFloat.left;
                                        dy = (int) (dx / mCropScale);
                                    }
                                    if (mDrawableFloat.top + dy < mDrawableTop) {
                                        dy = mDrawableTop - mDrawableFloat.top;
                                        dx = (int) (mCropScale * dy);
                                    }
                                    if (mDrawableFloat.width() - dx < MINSIZE) {
                                        dx = mDrawableFloat.width() - MINSIZE;
                                        dy = (int) (dx / mCropScale);
                                    }
                                    if (mDrawableFloat.height() - dy < MINSIZE) {
                                        dy = mDrawableFloat.height() - MINSIZE;
                                        dx = (int) (mCropScale * dy);
                                    }
                                } else {
                                    if (mDrawableFloat.left + dx < mDrawableLeft) {
                                        dx = mDrawableLeft - mDrawableFloat.left;
                                    }
                                    if (mDrawableFloat.top + dy < mDrawableTop) {
                                        dy = mDrawableTop - mDrawableFloat.top;
                                    }
                                    if (mDrawableFloat.width() - dx < MINSIZE) {
                                        dx = mDrawableFloat.width() - MINSIZE;
                                    }
                                    if (mDrawableFloat.height() - dy < MINSIZE) {
                                        dy = mDrawableFloat.height() - MINSIZE;
                                    }
                                }
                                mDrawableFloat.set(mDrawableFloat.left + dx,
                                        mDrawableFloat.top + dy, mDrawableFloat.right,
                                        mDrawableFloat.bottom);
                                if (mDrawableFloat.width() < MINSIZE) {
                                    mDrawableFloat.left = mDrawableFloat.right - MINSIZE;
                                }
                                if (mDrawableFloat.height() < MINSIZE) {
                                    mDrawableFloat.top = mDrawableFloat.bottom - MINSIZE;
                                }
                                break;

                            case EDGE_RT:
                                if (mCropMode == MODE_FIX) {
                                    if (Math.abs(dx) > Math.abs(dy)) {
                                        dy = (int) (-dx / mCropScale);
                                    } else {
                                        dx = (int) (-mCropScale * dy);
                                    }
                                    if (mDrawableFloat.right + dx > mDrawableRight) {
                                        dx = mDrawableRight - mDrawableFloat.right;
                                        dy = (int) (-dx / mCropScale);
                                    }
                                    if (mDrawableFloat.top + dy < mDrawableTop) {
                                        dy = mDrawableTop - mDrawableFloat.top;
                                        dx = (int) (-mCropScale * dy);
                                    }
                                    if (mDrawableFloat.width() + dx < MINSIZE) {
                                        dx = MINSIZE - mDrawableFloat.width();
                                        dy = (int) (-dx / mCropScale);
                                    }
                                    if (mDrawableFloat.height() - dy < MINSIZE) {
                                        dy = mDrawableFloat.height() - MINSIZE;
                                        dx = (int) (-mCropScale * dy);
                                    }
                                } else {
                                    if (mDrawableFloat.right + dx > mDrawableRight) {
                                        dx = mDrawableRight - mDrawableFloat.right;
                                    }
                                    if (mDrawableFloat.top + dy < mDrawableTop) {
                                        dy = mDrawableTop - mDrawableFloat.top;
                                    }
                                    if (mDrawableFloat.width() + dx < MINSIZE) {
                                        dx = MINSIZE - mDrawableFloat.width();
                                    }
                                    if (mDrawableFloat.height() - dy < MINSIZE) {
                                        dy = mDrawableFloat.height() - MINSIZE;
                                    }
                                }
                                mDrawableFloat.set(mDrawableFloat.left,
                                        mDrawableFloat.top + dy, mDrawableFloat.right
                                                + dx, mDrawableFloat.bottom);
                                if (mDrawableFloat.width() < MINSIZE) {
                                    mDrawableFloat.right = mDrawableFloat.left + MINSIZE;
                                }
                                if (mDrawableFloat.height() < MINSIZE) {
                                    mDrawableFloat.top = mDrawableFloat.bottom - MINSIZE;
                                }
                                break;

                            case EDGE_LB:
                                if (mCropMode == MODE_FIX) {
                                    if (Math.abs(dx) > Math.abs(dy)) {
                                        dy = (int) (-dx / mCropScale);
                                    } else {
                                        dx = (int) (-mCropScale * dy);
                                    }
                                    if (mDrawableFloat.left + dx < mDrawableLeft) {
                                        dx = mDrawableLeft - mDrawableFloat.left;
                                        dy = (int) (-dx / mCropScale);
                                    }
                                    if (mDrawableFloat.bottom + dy > mDrawableBottom) {
                                        dy = mDrawableBottom - mDrawableFloat.bottom;
                                        dx = (int) (-mCropScale * dy);
                                    }
                                    if (mDrawableFloat.width() - dx < MINSIZE) {
                                        dx = mDrawableFloat.width() - MINSIZE;
                                        dy = (int) (-dx / mCropScale);
                                    }
                                    if (mDrawableFloat.height() + dy < MINSIZE) {
                                        dy = MINSIZE - mDrawableFloat.height();
                                        dx = (int) (-mCropScale * dy);
                                    }
                                } else {
                                    if (mDrawableFloat.left + dx < mDrawableLeft) {
                                        dx = mDrawableLeft - mDrawableFloat.left;
                                    }
                                    if (mDrawableFloat.bottom + dy > mDrawableBottom) {
                                        dy = mDrawableBottom - mDrawableFloat.bottom;
                                    }
                                    if (mDrawableFloat.width() - dx < MINSIZE) {
                                        dx = mDrawableFloat.width() - MINSIZE;
                                    }
                                    if (mDrawableFloat.height() + dy < MINSIZE) {
                                        dy = MINSIZE - mDrawableFloat.height();
                                    }
                                }
                                mDrawableFloat.set(mDrawableFloat.left + dx,
                                        mDrawableFloat.top, mDrawableFloat.right,
                                        mDrawableFloat.bottom + dy);
                                if (mDrawableFloat.width() < MINSIZE) {
                                    mDrawableFloat.left = mDrawableFloat.right - MINSIZE;
                                }
                                if (mDrawableFloat.height() < MINSIZE) {
                                    mDrawableFloat.bottom = mDrawableFloat.top + MINSIZE;
                                }
                                break;

                            case EDGE_RB:
                                if (mCropMode == MODE_FIX) {
                                    if (Math.abs(dx) > Math.abs(dy)) {
                                        dy = (int) (dx / mCropScale);
                                    } else {
                                        dx = (int) (mCropScale * dy);
                                    }
                                    if (mDrawableFloat.right + dx > mDrawableRight) {
                                        dx = mDrawableRight - mDrawableFloat.right;
                                        dy = (int) (dx / mCropScale);
                                    }
                                    if (mDrawableFloat.bottom + dy > mDrawableBottom) {
                                        dy = mDrawableBottom - mDrawableFloat.bottom;
                                        dx = (int) (mCropScale * dy);
                                    }
                                    if (mDrawableFloat.width() + dx < MINSIZE) {
                                        dx = MINSIZE - mDrawableFloat.width();
                                        dy = (int) (dx / mCropScale);
                                    }
                                    if (mDrawableFloat.height() + dy < MINSIZE) {
                                        dy = MINSIZE - mDrawableFloat.height();
                                        dx = (int) (mCropScale * dy);
                                    }
                                } else {
                                    if (mDrawableFloat.right + dx > mDrawableRight) {
                                        dx = mDrawableRight - mDrawableFloat.right;
                                    }
                                    if (mDrawableFloat.bottom + dy > mDrawableBottom) {
                                        dy = mDrawableBottom - mDrawableFloat.bottom;
                                    }
                                    if (mDrawableFloat.width() + dx < MINSIZE) {
                                        dx = MINSIZE - mDrawableFloat.width();
                                    }
                                    if (mDrawableFloat.height() + dy < MINSIZE) {
                                        dy = MINSIZE - mDrawableFloat.height();
                                    }
                                }
                                mDrawableFloat.set(mDrawableFloat.left,
                                        mDrawableFloat.top, mDrawableFloat.right + dx,
                                        mDrawableFloat.bottom + dy);
                                if (mDrawableFloat.width() < MINSIZE) {
                                    mDrawableFloat.right = mDrawableFloat.left + MINSIZE;
                                }
                                if (mDrawableFloat.height() < MINSIZE) {
                                    mDrawableFloat.bottom = mDrawableFloat.top + MINSIZE;
                                }
                                break;

                            case EDGE_MOVE_IN:
                                if (isTouchInSquare) {
                                    if (mDrawableFloat.left + dx < mDrawableLeft) {
                                        dx = mDrawableLeft - mDrawableFloat.left;
                                    }
                                    if (mDrawableFloat.right + dx > mDrawableRight) {
                                        dx = mDrawableRight - mDrawableFloat.right;
                                    }
                                    if (mDrawableFloat.top + dy < mDrawableTop) {
                                        dy = mDrawableTop - mDrawableFloat.top;
                                    }
                                    if (mDrawableFloat.bottom + dy > mDrawableBottom) {
                                        dy = mDrawableBottom - mDrawableFloat.bottom;
                                    }
                                    mDrawableFloat.offset((int) dx, (int) dy);
                                }
                                break;

                            case EDGE_MOVE_OUT:
                                break;
                        }
                        mDrawableFloat.sort();
                        invalidate();
                    }
                }
                break;
        }

        return true;
    }

    private float touchOffset = 50;

    // 根据初触摸点判断是触摸的Rect哪一个角
    public int getTouch(int eventX, int eventY) {
        if (mFloatDrawable.getBounds().left <= eventX
                && eventX < (mFloatDrawable.getBounds().left + mFloatDrawable
                .getBorderWidth()+touchOffset)
                && mFloatDrawable.getBounds().top <= eventY
                && eventY < (mFloatDrawable.getBounds().top + mFloatDrawable
                .getBorderHeight()+touchOffset)) {
            return EDGE_LT;
        } else if ((mFloatDrawable.getBounds().right - mFloatDrawable
                .getBorderWidth()-touchOffset) <= eventX
                && eventX < mFloatDrawable.getBounds().right
                && mFloatDrawable.getBounds().top <= eventY
                && eventY < (mFloatDrawable.getBounds().top + mFloatDrawable
                .getBorderHeight()+touchOffset)) {
            return EDGE_RT;
        } else if (mFloatDrawable.getBounds().left <= eventX
                && eventX < (mFloatDrawable.getBounds().left + mFloatDrawable
                .getBorderWidth()+touchOffset)
                && (mFloatDrawable.getBounds().bottom - mFloatDrawable
                .getBorderHeight()-touchOffset) <= eventY
                && eventY < mFloatDrawable.getBounds().bottom) {
            return EDGE_LB;
        } else if ((mFloatDrawable.getBounds().right - mFloatDrawable
                .getBorderWidth()-touchOffset) <= eventX
                && eventX < mFloatDrawable.getBounds().right
                && (mFloatDrawable.getBounds().bottom - mFloatDrawable
                .getBorderHeight()-touchOffset) <= eventY
                && eventY < mFloatDrawable.getBounds().bottom) {
            return EDGE_RB;
        } else if (mFloatDrawable.getBounds().contains(eventX, eventY)) {
            return EDGE_MOVE_IN;
        }
        return EDGE_MOVE_OUT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mDrawable = getDrawable();
        if (mDrawable == null) {
            return;
        }

        if (mDrawable.getIntrinsicWidth() == 0
                || mDrawable.getIntrinsicHeight() == 0) {
            return;
        }

        configureBounds();
        // 在画布上花图片
        mDrawable.draw(canvas);
        canvas.save();
        // 在画布上画浮层FloatDrawable,Region.Op.DIFFERENCE是表示Rect交集的补集
        canvas.clipRect(mDrawableFloat, Region.Op.DIFFERENCE);
        // 在交集的补集上画上灰色用来区分
        canvas.drawColor(Color.parseColor("#a0000000"));
        canvas.restore();
        // 画浮层
        mFloatDrawable.draw(canvas);
    }

    private int mDrawableLeft;
    private int mDrawableTop;
    private int mDrawableRight;
    private int mDrawableBottom;

    protected void configureBounds() {
        // configureBounds在onDraw方法中调用
        // isFirst的目的是下面对mDrawableSrc和mDrawableFloat只初始化一次，
        // 之后的变化是根据touch事件来变化的，而不是每次执行重新对mDrawableSrc和mDrawableFloat进行设置
        if (isFrist) {
            mCropScale = (float) cropWidth / (float) cropHeight;
            if (cropHeight < MINSIZE) {
                cropHeight = MINSIZE;
                if (mCropMode == MODE_FIX) {
                    cropWidth = (int) (mCropScale * cropHeight);
                }
            }
            if (cropWidth < MINSIZE) {
                cropWidth = MINSIZE;
                if (mCropMode == MODE_FIX) {
                    cropHeight = (int) (cropWidth / mCropScale);
                }
            }
            oriRationWH = ((float) mDrawable.getIntrinsicWidth())
                    / ((float) mDrawable.getIntrinsicHeight());

            final float scale = mContext.getResources().getDisplayMetrics().density;
            int w = Math.min(getWidth(), (int) (mDrawable.getIntrinsicWidth()
                    * scale + 0.5f));
            int h = (int) (w / oriRationWH);

            mDrawableLeft = (getWidth() - w) / 2;
            if (mDrawableLeft < 0) {
                mDrawableLeft = 0;
            }
            mDrawableTop = (getHeight() - h) / 2;
            if (mDrawableTop < 0) {
                mDrawableTop = 0;
            }
            mDrawableRight = mDrawableLeft + w;
            if (mDrawableRight > getWidth()) {
                mDrawableRight = getWidth();
            }
            mDrawableBottom = mDrawableTop + h;
            if (mDrawableBottom > getHeight()) {
                mDrawableBottom = getHeight();
            }
            mDrawableSrc.set(mDrawableLeft, mDrawableTop, mDrawableRight, mDrawableBottom);
            mDrawableDst.set(mDrawableSrc);

            if (cropWidth > mDrawableRight - mDrawableLeft) {
                cropWidth = mDrawableRight - mDrawableLeft;
                cropHeight = (int) (cropWidth / mCropScale);
            }

            if (cropHeight > mDrawableBottom - mDrawableTop) {
                cropHeight = mDrawableBottom - mDrawableTop;
                cropWidth = (int) (mCropScale * cropHeight);
            }

            if (cropWidth > getWidth()) {
                cropWidth = getWidth();
                cropHeight = (int) (cropWidth / mCropScale);
            }

            if (cropHeight > getHeight()) {
                cropHeight = getHeight();
                cropWidth = (int) (mCropScale * cropHeight);
            }

            int floatLeft = (getWidth() - cropWidth) / 2;
            int floatTop = (getHeight() - cropHeight) / 2;
            mDrawableFloat.set(floatLeft, floatTop, floatLeft + cropWidth,
                    floatTop + cropHeight);

            isFrist = false;
        }

        mDrawable.setBounds(mDrawableDst);
        mFloatDrawable.setBounds(mDrawableFloat);
    }

    // 在up事件中调用了该方法，目的是检查是否把浮层拖出了屏幕
    protected void checkBounds() {
        int newLeft = mDrawableFloat.left;
        int newTop = mDrawableFloat.top;

        boolean isChange = false;
        if (mDrawableFloat.left < getLeft()) {
            newLeft = getLeft();
            isChange = true;
        }

        if (mDrawableFloat.top < getTop()) {
            newTop = getTop();
            isChange = true;
        }

        if (mDrawableFloat.right > getRight()) {
            newLeft = getRight() - mDrawableFloat.width();
            isChange = true;
        }

        if (mDrawableFloat.bottom > getBottom()) {
            newTop = getBottom() - mDrawableFloat.height();
            isChange = true;
        }

        mDrawableFloat.offsetTo(newLeft, newTop);
        if (isChange) {
            invalidate();
        }
    }

    // 进行图片的裁剪，所谓的裁剪就是根据Drawable的新的坐标在画布上创建一张新的图片
    public Bitmap getCropImage(float scale) {
        if (mDrawable == null) {
            return null;
        }
        Bitmap tmpBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(tmpBitmap);
        mDrawable.draw(canvas);
        return getCropImage(tmpBitmap, false, true, scale);
    }

    public Bitmap getCropImage(Bitmap tmpBitmap, boolean trans, boolean recycle, float scale) {
        if (mDrawableFloat.left < mDrawableLeft) {
            mDrawableFloat.left = mDrawableLeft;
        }
        if (mDrawableFloat.top < mDrawableTop) {
            mDrawableFloat.top = mDrawableTop;
        }
        if (mDrawableFloat.right > mDrawableRight) {
            mDrawableFloat.right = mDrawableRight;
        }
        if (mDrawableFloat.bottom > mDrawableBottom) {
            mDrawableFloat.bottom = mDrawableBottom;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        int bitmapwith = tmpBitmap.getWidth();
        float scalez = (float) bitmapwith / (float) (mDrawableRight - mDrawableLeft);
        int newleft = (int) ((mDrawableFloat.left - (!trans ? 0 : mDrawableLeft)) * scalez);
        int newtop = (int) ((mDrawableFloat.top - (!trans ? 0 : mDrawableTop)) * scalez);
        int newwidth = (int) (mDrawableFloat.width() * scalez);
        int newheight = (int) (mDrawableFloat.height() * scalez);
        Bitmap ret = Bitmap.createBitmap(tmpBitmap,
                newleft, newtop, newwidth, newheight,
                matrix, true);
        if (recycle) {
            tmpBitmap.recycle();
        }
        LogDebug.d(ret.getRowBytes() * ret.getHeight() + "");
        tmpBitmap = null;
        return ret;
    }

    public void getCropImage(final float scale, String outpath) {
        File file = new File(outpath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (isGif) {
            mGif = new GifDrawable(mGif, mGif.getFirstFrame(), new BitmapTransformation(getContext()) {
                @Override
                protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                    Bitmap bitmap = getCropImage(toTransform, true, false, scale);
                    return bitmap;
                }

                @Override
                public String getId() {
                    return "";
                }
            });
            new SaveGifTask().execute(outpath);
        } else {
            mTempBitmap = getCropImage(scale);
            new SavePicTask().execute(outpath);
        }
    }

    public int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private boolean isGif = false;
    private GifDrawable mGif;

    public void showImage(String url) {
        Glide.with(getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                if (mListener != null) {
                    mListener.onLoadException(e);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (resource instanceof GifDrawable) {
                    isGif = true;
                    mGif = (GifDrawable) resource;
                }
                if (mListener != null) {
                    mListener.onResourceReady();
                }
                return false;
            }
        }).into(this);
    }

    private Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public static class Listener {
        public void onLoadException(Exception e) {
        }

        public void onResourceReady() {
        }

        public void onCropException(Exception e) {
        }

        public void onSaveReady(String path) {
        }
    }

    private Bitmap mTempBitmap;

    private class SavePicTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            try {
                BitmapUtil.storeImage(mTempBitmap, voids[0]);
            } catch (FileNotFoundException e) {
                LogDebug.e(e);
                if (mListener != null) {
                    mListener.onCropException(e);
                }
                return null;
            }
            return voids[0];
        }

        @Override
        protected void onPostExecute(String s) {
            if (mListener != null && s != null) {
                mListener.onSaveReady(s);
            }
        }
    }

    private class SaveGifTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                GifResourceEncoder encoder = new GifResourceEncoder(Glide.get(mContext).getBitmapPool());
                encoder.encode(new GifDrawableResource(mGif), new FileOutputStream(strings[0]));
            } catch (FileNotFoundException e) {
                LogDebug.e(e);
                if (mListener != null) {
                    mListener.onCropException(e);
                }
                return null;
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            if (mListener != null && s != null) {
                mListener.onSaveReady(s);
            }
        }
    }
}
