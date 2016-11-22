package com.wpy.xh.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.wpy.xh.R;
import com.wpy.xh.base.BaseActivity;
import com.wpy.xh.config.DIR;
import com.wpy.xh.config.Key;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.Inject;
import library.mlibrary.view.imageview.costumcrop.CropImageView;

/**
 * Created by harmy on 2016/8/11 0011.
 */
public class ImageCropActivity extends BaseActivity {
    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_imagecrop);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {
        tv_confirm.setOnClickListener(this);
        backRL.setOnClickListener(this);
        iv_src.setListener(new CropImageView.Listener() {
            @Override
            public void onCropException(Exception e) {
                dismissProgress();
            }

            @Override
            public void onLoadException(Exception e) {
                dismissProgress();
            }

            @Override
            public void onResourceReady() {
                dismissProgress();
            }

            @Override
            public void onSaveReady(String path) {
                dismissProgress();
                Intent intent = new Intent();
                intent.putExtra(Key.PICPATH, path);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backRL:
                finish();
                break;
            case R.id.tv_confirm:
                savePic();
                break;
        }
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mPicSrc = intent.getStringExtra(Key.PICPATH);
        if (CommonUtil.isEmpty(mPicSrc)) {
            finish();
            return;
        }
        showProgress("正在加载图片，请稍后");
        iv_src.showImage(mPicSrc);
        iv_src.setCropSize(CommonUtil.getDisplayMetrics(this).widthPixels / 3 * 2, CommonUtil.getDisplayMetrics(this).widthPixels / 3 * 2);
        iv_src.setCropMode(CropImageView.MODE_FIX);
    }

    @Inject(R.id.backRL)
    private RelativeLayout backRL;
    @Inject(R.id.tv_confirm)
    private TextView tv_confirm;
    @Inject(R.id.iv_src)
    private CropImageView iv_src;

    private String mPicSrc;

    private void savePic() {
        final String path = DIR.CACHE_PICTURE + CommonUtil.getCurrentMS() + ".jpg";
        showProgress("正在处理图片，请稍后");
        iv_src.getCropImage(0.5f,path);
    }
}
