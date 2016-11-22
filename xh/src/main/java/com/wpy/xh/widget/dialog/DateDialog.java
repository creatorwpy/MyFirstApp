package com.wpy.xh.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wpy.xh.R;
import com.wpy.xh.base.BaseDialog;

import java.util.Calendar;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.Inject;
import library.mlibrary.view.wheelview.TimeWheelview;

/**
 * Created by harmy on 2016/8/22 0022.
 */
public class DateDialog extends BaseDialog implements View.OnClickListener {
    public DateDialog(Context context) {
        super(context);
    }

    public DateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DateDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onSetContentView() {
        setContentView(R.layout.dialog_date);
    }

    @Override
    protected void initViews() {
        initWindow(0.6f, 0);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onSetListener() {
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                break;
            case R.id.tv_right:
                if (mListener != null) {
                    String time = CommonUtil.formatDate(tw_date.getSelectedTimeLong(), "yyyy-MM-dd");
                    mListener.onConfirm(time);
                }
                break;
        }
        cancel();
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        tw_date.setStartTime(CommonUtil.getDateOff(CommonUtil.getCurrentDayMS(), Calendar.YEAR, -100));
        tw_date.setEndTimeLong(CommonUtil.getCurrentDayMS());
        tw_date.setVisibleView(true, true, true, false, false, false);
        tw_date.setViewPadding(15, 0);
        tw_date.setTextSize(18);
        tw_date.setVisibleNum(5);
        tw_date.draw();
    }

    @Inject(R.id.tw_date)
    private TimeWheelview tw_date;
    @Inject(R.id.tv_left)
    private TextView tv_left;
    @Inject(R.id.tv_right)
    private TextView tv_right;

    private Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public static class Listener {
        public void onConfirm(String time) {
        }
    }
}
