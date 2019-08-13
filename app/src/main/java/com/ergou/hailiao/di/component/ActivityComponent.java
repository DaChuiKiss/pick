package com.ergou.hailiao.di.component;

import android.app.Activity;


import com.ergou.hailiao.di.ActivityScope;
import com.ergou.hailiao.di.module.ActivityMoudle;
import com.ergou.hailiao.mvp.ui.activity.ConversationActivity;
import com.ergou.hailiao.mvp.ui.activity.LaunchActivity;
import com.ergou.hailiao.mvp.ui.activity.MainActivity;
import com.ergou.hailiao.mvp.ui.activity.ModifyHeadImgActivity;
import com.ergou.hailiao.mvp.ui.activity.PersonalCenterActivity;
import com.ergou.hailiao.mvp.ui.activity.SignInActivity;

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


}
