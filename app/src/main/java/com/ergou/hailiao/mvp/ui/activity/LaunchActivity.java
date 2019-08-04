package com.ergou.hailiao.mvp.ui.activity;


import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by KissDa on 2018/7/27.
 * <p>
 * <p>
 * 启动——Activity
 */

public class LaunchActivity extends BaseActivity {
    @BindView(R.id.rlSplash)
    LinearLayout rlSplash;
    private AlphaAnimation mAlphaAnimation;

    @Override
    public void showError(String msg) {

    }

    @Override
    public void timeShowError(String time) {

    }

    @Override
    public void codeTypeError(int code) {
    }

    @Override
    protected void initInject() {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initEventAndData() {
        StatusBarUtil.setStatusBarLightMode(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        star();
    }

    protected void star() {
        mAlphaAnimation = new AlphaAnimation(.5f, 1);
        mAlphaAnimation.setDuration(1000);
        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent();
                intent.setClass(mContext, SignInActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        rlSplash.startAnimation(mAlphaAnimation);
    }

}
