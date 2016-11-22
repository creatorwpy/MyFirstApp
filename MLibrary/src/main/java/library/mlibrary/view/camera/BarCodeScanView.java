package library.mlibrary.view.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.google.zxing.Result;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import library.mlibrary.util.log.LogDebug;
import library.mlibrary.util.zxing.ZxingUtil;

/**
 * Created by Harmy on 2016/5/11 0011.
 */
public class BarCodeScanView extends RelativeLayout implements SurfaceHolder.Callback, Camera.AutoFocusCallback, Camera.PreviewCallback {
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;

    public BarCodeScanView(Context context) {
        super(context);
        init();
    }

    public BarCodeScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarCodeScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        SurfaceView surfaceView = new SurfaceView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(surfaceView, 0, params);
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            LogDebug.e(e);
            mCamera.release();
            mCamera = null;
        }
    }

    public void restart() {
        hasShot = false;
    }

    public void stop() {
        hasShot = true;
    }

    protected boolean isPortrait() {
        return (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        initCamera(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size previewSize = previewSizes.get(0);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        Log.d("binzhou", previewSize.width+"___"+previewSize.height);
        if (isPortrait()) {
            mCamera.setDisplayOrientation(90);
        } else {
            mCamera.setDisplayOrientation(0);
        }
        mCamera.setParameters(parameters);
        mCamera.startPreview();
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new AutoFocusTask(), 0, 1500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera == null) {
            return;
        }
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        mScheduledExecutorService.shutdown();
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            stop();
            camera.setOneShotPreviewCallback(this);
        }
        LogDebug.d("onAutoFocus");
    }

    private ScheduledExecutorService mScheduledExecutorService;

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Result result = ZxingUtil.barcodeDecode(bytes, camera);
        if (result != null) {
            if (mOnDecodeListener != null) {
                mOnDecodeListener.onScan(result);
            }
        } else {
            restart();
        }
    }

    private ScanListener mOnDecodeListener;

    public void setScanListener(ScanListener listener) {
        mOnDecodeListener = listener;
    }

    private boolean hasShot = false;

    private class AutoFocusTask implements Runnable {

        @Override
        public void run() {
            if (!hasShot) {
                mHandler.obtainMessage().sendToTarget();
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mCamera != null) {
                mCamera.autoFocus(BarCodeScanView.this);
            }
        }
    };

    public interface ScanListener {
        public void onScan(Result result);
    }
}
