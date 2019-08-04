package com.ergou.hailiao.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.ergou.hailiao.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 */

@Module
public class FragmentMoudle {
    private Fragment fragment;

    public FragmentMoudle(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Activity provideActivity() {
        return fragment.getActivity();
    }
}
