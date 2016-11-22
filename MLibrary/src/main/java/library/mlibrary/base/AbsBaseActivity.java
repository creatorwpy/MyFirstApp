package library.mlibrary.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import library.mlibrary.R;
import library.mlibrary.util.common.FragmentManager;
import library.mlibrary.util.inject.InjectUtil;

/**
 * Created by Harmy on 2016/4/7 0007.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {
    protected AbsBaseActivity mThis;
    private FragmentManager mFragmentManager;
    private AbsBaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onGetIntent(getIntent());
        super.onCreate(savedInstanceState);
        mThis = this;
        AbsBaseApplication.getAbsBaseApp().addActivity(this);
        beforeSetContentView();
        onSetContentView();
        afterSetContentView();
        onFindView();
        injectView();
        initViews();
        beforeOnCreate(savedInstanceState);
        afterOnCreate(savedInstanceState);
        onSetListener();
    }

    public AbsBaseActivity getThis() {
        return mThis;
    }

    protected void onGetIntent(Intent intent) {
    }

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView() {
        InjectUtil.injectActivityFields(this, false);
    }

    protected void beforeSetContentView() {

    }

    protected abstract void onSetContentView();

    protected void afterSetContentView() {

    }

    protected void onFindView() {
    }

    protected abstract void initViews();

    protected abstract void onSetListener();

    protected abstract void afterOnCreate(Bundle savedInstanceState);

    protected void beforeOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void onDestroy() {
        AbsBaseApplication.getAbsBaseApp().removeActivity(this);
        super.onDestroy();
    }

    private boolean overridePending = false;

    /**
     * 设置是否重写Activity载入退出动画
     *
     * @param override
     */
    public void setOverridePending(boolean override) {
        overridePending = override;
    }

    private boolean isFirst = true;

    @Override
    protected void onResume() {
        if (!overridePending) {
            if (isFirst) {
                overridePendingTransition(R.anim.in_activity_rtl, R.anim.out_activity_ltr);
            } else {
                overridePendingTransition(R.anim.in_back_activity_rtl, R.anim.out_back_activity_ltr);
            }
            isFirst = false;
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null) {
            mCurrentFragment.onBackPressed();
            return;
        }
        afterBackPressed();
    }

    protected void afterBackPressed() {
        super.onBackPressed();
    }


    public FragmentManager initFragmentManager(int framlayout) {
        mFragmentManager = new FragmentManager(getThis(), framlayout);
        return mFragmentManager;
    }

    public void setCurrentFragment(AbsBaseFragment fragment) {
        mCurrentFragment = fragment;
    }

    public AbsBaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public boolean checkStoragePermission(int request_code) {
        int permissionr = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionw = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionr == PackageManager.PERMISSION_GRANTED && permissionw == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    request_code);
            return false;
        }
    }

    public boolean checkCameraPermission(int request_code) {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.CAMERA
            };
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    request_code);
            return false;
        }
    }

    public boolean checkRecordAudioPermission(int request_code) {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.RECORD_AUDIO
            };
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    request_code);
            return false;
        }
    }

    public boolean checkLocationPermission(int request_code) {
        int permissionf = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionc = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionf == PackageManager.PERMISSION_GRANTED && permissionc == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    request_code);
            return false;
        }
    }

    public boolean checkReadPhoneStatePermission(int request_code) {
        int permissionf = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionf == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_PHONE_STATE};
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    request_code);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (permissions[0]) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    onStoragePermission(requestCode, true);
                } else {
                    onStoragePermission(requestCode, false);
                }
                break;
            case Manifest.permission.CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCameraPermission(requestCode, true);
                } else {
                    onCameraPermission(requestCode, false);
                }
                break;
            case Manifest.permission.RECORD_AUDIO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onRecordAudioPermission(requestCode, true);
                } else {
                    onRecordAudioPermission(requestCode, false);
                }
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    onLocationPermission(requestCode, true);
                } else {
                    onLocationPermission(requestCode, false);
                }
                break;
            case Manifest.permission.READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onReadPhoneStatePermission(requestCode, true);
                } else {
                    onReadPhoneStatePermission(requestCode, false);
                }
                break;
        }
    }

    public void onStoragePermission(int result_code, boolean result) {

    }

    public void onCameraPermission(int result_code, boolean result) {

    }

    public void onRecordAudioPermission(int result_code, boolean result) {

    }

    public void onLocationPermission(int result_code, boolean result) {

    }
    public void onReadPhoneStatePermission(int result_code, boolean result) {

    }
}
