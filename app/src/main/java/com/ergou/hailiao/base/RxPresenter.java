package com.ergou.hailiao.base;


import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *  Created by LuoCy on 2017/7/28.
 */

public abstract class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected WeakReference<T> mView;
    protected CompositeDisposable mDisposables;

    protected void unSubscribe() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }
    protected T getView(){
        if(mView != null){
            return mView.get();
        }
        return null;
    }

    protected void addSubscrebe(Disposable disposable) {
        if (disposable == null) return;
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    @Override
    public void attachView(T view) {
        mView = new WeakReference<T>(view);
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
