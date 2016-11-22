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
public class SimpleVHeader extends AbsBaseHeader {
    // 下拉箭头的转180°动画
    private RotateAnimation rotateAnimation;
    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;

    // 下拉头
    private View refreshView;
    // 下拉的箭头
    private View pullView;
    private View headerview;
    // 正在刷新的图标
    private View refreshingView;
    // 刷新结果图标
    private View refreshStateImageView;
    // 刷新结果：成功或失败
    private TextView refreshStateTextView;

    @Override
    public View onCreateHeaderView(Context context) {
        refreshView = LayoutInflater.from(context).inflate(R.layout.refresh_head, null);
        pullView = refreshView.findViewById(R.id.pull_icon);
        headerview = refreshView.findViewById(R.id.headerview);
        refreshStateTextView = (TextView) refreshView.findViewById(R.id.state_tv);
        refreshingView = refreshView.findViewById(R.id.refreshing_icon);
        refreshStateImageView = refreshView.findViewById(R.id.state_iv);
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.reverse_anim);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);
        return refreshView;
    }

    @Override
    public View getHeaderView() {
        return headerview;
    }

    @Override
    public void onInit() {
        refreshStateImageView.setVisibility(View.GONE);
        refreshStateTextView.setText(R.string.pull_to_refresh);
        pullView.clearAnimation();
        pullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRelease() {
        refreshStateTextView.setText(R.string.release_to_refresh);
        pullView.startAnimation(rotateAnimation);
    }

    @Override
    public void onHeading() {
        pullView.clearAnimation();
        refreshingView.setVisibility(View.VISIBLE);
        pullView.setVisibility(View.INVISIBLE);
        refreshingView.startAnimation(refreshingAnimation);
        refreshStateTextView.setText(R.string.refreshing);
    }

    @Override
    public void onSuccess() {
        refreshingView.clearAnimation();
        refreshingView.setVisibility(View.GONE);
        refreshStateImageView.setVisibility(View.VISIBLE);
        refreshStateTextView.setText(R.string.refresh_succeed);
        refreshStateImageView
                .setBackgroundResource(R.drawable.refresh_succeed);
    }

    @Override
    public void onFailure() {
        refreshingView.clearAnimation();
        refreshingView.setVisibility(View.GONE);
        refreshStateImageView.setVisibility(View.VISIBLE);
        refreshStateTextView.setText(R.string.refresh_fail);
        refreshStateImageView
                .setBackgroundResource(R.drawable.refresh_failed);
    }

    @Override
    public void onDone() {

    }
}
