package com.ergou.hailiao.di.component;

import android.app.Activity;


import com.ergou.hailiao.di.ActivityScope;
import com.ergou.hailiao.di.module.ActivityMoudle;
import com.ergou.hailiao.mvp.ui.activity.SignInActivity;

import dagger.Component;


/**
 * Created by LuoCy on 2017/9/26.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityMoudle.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SignInActivity signInActivity);
//
//    void inject(LaunchActivity launchActivity);


}
