package com.ergou.hailiao.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ergou.hailiao.app.App;
import com.ergou.hailiao.app.AppManager;
import com.ergou.hailiao.di.component.ActivityComponent;
import com.ergou.hailiao.di.component.DaggerActivityComponent;
import com.ergou.hailiao.di.module.ActivityMoudle;
import com.ergou.hailiao.utils.ActivityCollector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
//

/**
 * Created by Luocy on 2017/9/26.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{
    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        ActivityCollector.AddActivity(this);
        if (mPresenter != null)
            mPresenter.attachView(this);
        AppManager.getAppManager().addActivity(this);
        initEventAndData();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityMoudle(getActivityModule())
                .build();
    }

    protected ActivityMoudle getActivityModule() {
        return new ActivityMoudle(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}
