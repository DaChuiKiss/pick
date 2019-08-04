package com.ergou.hailiao.di.module;


import com.ergou.hailiao.app.App;
import com.ergou.hailiao.di.ContextLife;
import com.ergou.hailiao.mvp.http.RetrofitUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class AppMoudle {
    private App application;

    public AppMoudle(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    RetrofitUtil provideRetrofitHelper() {
        return new RetrofitUtil();
    }

}
