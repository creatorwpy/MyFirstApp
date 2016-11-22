package library.mlibrary.view.pulltorefresh.pullview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import library.mlibrary.R;

/**
 * Created by Harmy on 2016/5/31 0031.
 */
public class SimpleVFooter extends AbsBaseFooter {
    // 下拉箭头的转180°动画
    private RotateAnimation rotateAnimation;
    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;
    // 上拉头
    private View loadmoreView;
    private View footerview;
    // 上拉的箭头
    private View pullUpView;
    // 正在加载的图标
    private View loadingView;
    // 加载结果图标
    private View loadStateImageView;
    // 加载结果：成功或失败
    private TextView loadStateTextView;

    @Override
    public View onCreateFooterView(Context context) {
        loadmoreView = LayoutInflater.from(context).inflate(R.layout.load_more, null);
        pullUpView = loadmoreView.findViewById(R.id.pullup_icon);
        footerview = loadmoreView.findViewById(R.id.footerview);
        loadStateTextView = (TextView) loadmoreView
                .findViewById(R.id.loadstate_tv);
        loadingView = loadmoreView.findViewById(R.id.loading_icon);
        loadStateImageView = loadmoreView.findViewById(R.id.loadstate_iv);
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.reverse_anim);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);
        return loadmoreView;
    }

    @Override
    public View getFooterView() {
        return footerview;
    }

    @Override
    public void onInit() {
        loadStateImageView.setVisibility(View.GONE);
        loadStateTextView.setText(R.string.pullup_to_load);
        pullUpView.clearAnimation();
        pullUpView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRelease() {
        loadStateTextView.setText(R.string.release_to_load);
        pullUpView.startAnimation(rotateAnimation);
    }

    @Override
    public void onFooting() {
        pullUpView.clearAnimation();
        loadingView.setVisibility(View.VISIBLE);
        pullUpView.setVisibility(View.INVISIBLE);
        loadingView.startAnimation(refreshingAnimation);
        loadStateTextView.setText(R.string.loading);
    }

    @Override
    public void onSuccess() {
        loadingView.clearAnimation();
        loadingView.setVisibility(View.GONE);
        loadStateImageView.setVisibility(View.VISIBLE);
        loadStateTextView.setText(R.string.load_succeed);
        loadStateImageView.setBackgroundResource(R.drawable.load_succeed);
    }

    @Override
    public void onFailure() {
        loadingView.clearAnimation();
        loadingView.setVisibility(View.GONE);
        loadStateImageView.setVisibility(View.VISIBLE);
        loadStateTextView.setText(R.string.load_fail);
        loadStateImageView.setBackgroundResource(R.drawable.load_failed);
    }

    @Override
    public void onDone() {

    }
}
