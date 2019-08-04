package com.ergou.hailiao.di.component;


import com.ergou.hailiao.app.App;
import com.ergou.hailiao.di.ContextLife;
import com.ergou.hailiao.di.module.AppMoudle;
import com.ergou.hailiao.mvp.http.RetrofitUtil;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 *  Created by LuoCy on 2017/9/26.
 */
@Singleton
@Component(modules = AppMoudle.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();

    RetrofitUtil getRetrofitUtil();

}
