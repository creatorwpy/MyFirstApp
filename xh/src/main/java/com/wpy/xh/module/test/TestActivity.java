package com.wpy.xh.module.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.wpy.xh.R;
import com.wpy.xh.base.BaseActivity;

import library.mlibrary.util.inject.Inject;
import library.mlibrary.util.log.LogDebug;

public class TestActivity extends BaseActivity {

    @Inject(R.id.test_banner)
    private Button test_banner;
    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {
        test_banner.setOnClickListener(this);
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.test_banner://
                LogDebug.d("点击了");
                Intent intent = new Intent(getThis(), TestConvenientBannerActivity.class);
                startActivity(intent);
                LogDebug.d("点击了2");
                break;

        }
    }
}
