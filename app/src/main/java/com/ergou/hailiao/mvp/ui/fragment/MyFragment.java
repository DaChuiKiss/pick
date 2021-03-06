package com.ergou.hailiao.mvp.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseFragment;
import com.ergou.hailiao.mvp.ui.activity.LoginPasswordActivity;
import com.ergou.hailiao.mvp.ui.activity.MyTeamActivity;
import com.ergou.hailiao.mvp.ui.activity.PaymenyPasswordActivity;
import com.ergou.hailiao.mvp.ui.activity.PersonalCenterActivity;
import com.ergou.hailiao.mvp.ui.activity.PromotionalBenefitsActivity;
import com.ergou.hailiao.mvp.ui.activity.PromotionalPostersActivity;
import com.ergou.hailiao.mvp.ui.activity.SignInActivity;
import com.ergou.hailiao.mvp.ui.activity.SmallChangeActivity;
import com.ergou.hailiao.utils.ActivityCollector;
import com.ergou.hailiao.utils.UserInfoSPUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * * 我的——Fragmen
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.head_img)
    ImageView headImg;//头像
    @BindView(R.id.nickname)
    TextView nickname;//昵称
    @BindView(R.id.hai_liao_number)
    TextView haiLiaoNumber;//号

    private Intent intent;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initEventAndData() {
        GlideManager.loadImageView(mContext, SPUtilsData.getUserHeaderImg(),
                headImg, R.mipmap.ic_launcher);//头像
        nickname.setText(SPUtilsData.getNickName());
        haiLiaoNumber.setText(SPUtilsData.getUserId());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {//判断在不在此页面
        super.onHiddenChanged(hidden);
        if (hidden) {
            //结束
        } else {
            GlideManager.loadImageView(mContext, SPUtilsData.getUserHeaderImg(),
                    headImg, R.mipmap.ic_launcher);//头像
            nickname.setText(SPUtilsData.getNickName());
            haiLiaoNumber.setText(SPUtilsData.getUserId());
        }
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {

    }

    @OnClick({R.id.head_img, R.id.qr_code, R.id.small_change_rl,
            R.id.payment_password_rl, R.id.login_password_rl, R.id.promotional_posters_rl,
            R.id.promotional_benefits_rl, R.id.my_team_rl, R.id.settings_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_img://头像
                intent = new Intent();
                intent.setClass(mContext, PersonalCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.qr_code://二维码
                intent = new Intent();
                intent.setClass(mContext, PersonalCenterActivity.class);
//                intent.setClass(mContext, ConversationActivity.class);
                startActivity(intent);
                break;
            case R.id.small_change_rl://零钱
                intent = new Intent();
                intent.setClass(mContext, SmallChangeActivity.class);
                startActivity(intent);
                break;
            case R.id.payment_password_rl://支付密码
                intent = new Intent();
                intent.setClass(mContext, PaymenyPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.login_password_rl://登陆密码
                intent = new Intent();
                intent.setClass(mContext, LoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.promotional_posters_rl://推广海报
                intent = new Intent();
                intent.setClass(mContext, PromotionalPostersActivity.class);
                startActivity(intent);
                break;
            case R.id.promotional_benefits_rl://推广收益
                intent = new Intent();
                intent.setClass(mContext, PromotionalBenefitsActivity.class);
                startActivity(intent);
                break;
            case R.id.my_team_rl://我的团队
                intent = new Intent();
                intent.setClass(mContext, MyTeamActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_rl://设置
//                intent = new Intent();
//                intent.setClass(mContext, SettingsActivity.class);
//                startActivity(intent);
                UserInfoSPUtils.getInstance().clear();
                intent = new Intent();
                intent.setClass(mContext, SignInActivity.class);
                startActivity(intent);
                ActivityCollector.finishAll();
                break;
        }
    }
}
