package com.ergou.hailiao.di.component;

import android.app.Activity;


import com.ergou.hailiao.di.ActivityScope;
import com.ergou.hailiao.di.module.ActivityMoudle;
import com.ergou.hailiao.mvp.ui.activity.ConversationActivity;
import com.ergou.hailiao.mvp.ui.activity.ForgetLoginPasswordActivity;
import com.ergou.hailiao.mvp.ui.activity.ForgetPaymenyPasswordActivity;
import com.ergou.hailiao.mvp.ui.activity.LaunchActivity;
import com.ergou.hailiao.mvp.ui.activity.LoginPasswordActivity;
import com.ergou.hailiao.mvp.ui.activity.MainActivity;
import com.ergou.hailiao.mvp.ui.activity.ModifyHeadImgActivity;
import com.ergou.hailiao.mvp.ui.activity.PaymenyPasswordActivity;
import com.ergou.hailiao.mvp.ui.activity.PersonalCenterActivity;
import com.ergou.hailiao.mvp.ui.activity.SearchMailListActivity;
import com.ergou.hailiao.mvp.ui.activity.SignInActivity;
import com.ergou.hailiao.mvp.ui.activity.SmallChangeActivity;

import dagger.Component;


/**
 * Created by LuoCy on 2017/9/26.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityMoudle.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SignInActivity signInActivity);//登录

    void inject(ConversationActivity conversationActivity);//会话

    void inject(ModifyHeadImgActivity modifyHeadImgActivity);//修改头像

    void inject(PersonalCenterActivity personalCenterActivity);//个人中心

    void inject(SmallChangeActivity smallChangeActivity);//零钱

    void inject(PaymenyPasswordActivity paymenyPasswordActivity);//支付密码

    void inject(ForgetPaymenyPasswordActivity forgetPaymenyPasswordActivity);//忘记支付密码

    void inject(LoginPasswordActivity loginPasswordActivity);//登录密码

    void inject(ForgetLoginPasswordActivity forgetLoginPasswordActivity);//忘记登录密码

    void inject(SearchMailListActivity searchMailListActivity);//搜索


}
