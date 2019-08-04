package com.ergou.hailiao.di.module;

import android.app.Activity;


import com.ergou.hailiao.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 */

@Module
public class ActivityMoudle {
    private Activity activity;

    public ActivityMoudle(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return activity;
    }
}
