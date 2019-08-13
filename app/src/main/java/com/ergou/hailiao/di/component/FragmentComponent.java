package com.ergou.hailiao.di.component;

import android.app.Activity;


import com.ergou.hailiao.di.FragmentScope;
import com.ergou.hailiao.di.module.FragmentMoudle;
import com.ergou.hailiao.mvp.ui.fragment.DialogueFragment;

import dagger.Component;

/**
 * Created by LuoCy on 2017/9/26.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentMoudle.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(DialogueFragment dialogueFragment);


}
