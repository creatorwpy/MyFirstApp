package com.wpy.xh.module.home.xingxiu;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.xh.R;
import com.wpy.xh.base.BaseDialog;

import library.mlibrary.util.inject.Inject;

/**
 * Created by harmy on 2016/8/18 0018.
 */
public class VoteOptionDialog extends BaseDialog implements View.OnClickListener {
    public VoteOptionDialog(Context context) {
        super(context);
    }

    public VoteOptionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected VoteOptionDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.dialog_voteoption);
    }

    @Override
    protected void initViews() {
        initWindow(1.0f, 0, RelativeLayout.ALIGN_PARENT_BOTTOM);
    }

    @Override
    protected void onSetListener() {
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof TextView) {
            String vote = ((TextView) view).getText().toString();
            if (mListener != null) {
                mListener.onSelected(vote);
            }
        } else {

        }
        cancel();
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    @Inject(R.id.tv_1)
    private TextView tv_1;
    @Inject(R.id.tv_2)
    private TextView tv_2;
    @Inject(R.id.tv_3)
    private TextView tv_3;
    @Inject(R.id.tv_4)
    private TextView tv_4;
    @Inject(R.id.tv_5)
    private TextView tv_5;
    @Inject(R.id.tv_6)
    private TextView tv_6;
    private Listener mListener;

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public static class Listener {
        public void onSelected(String piaoshu) {
        }
    }
}
