package com.ergou.hailiao.mvp.http;


import com.ergou.hailiao.app.App;
import com.ergou.hailiao.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 */

public class ExceptionSubscriber<T> extends ResourceSubscriber<T> {
    private SimpleCallback<T> simpleCallback;

    public ExceptionSubscriber(SimpleCallback simpleCallback) {
        this.simpleCallback = simpleCallback;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (simpleCallback != null)
            simpleCallback.onStart();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showShortToast(App.getInstance().getApplicationContext(), "网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShortToast(App.getInstance().getApplicationContext(), "网络中断，请检查您的网络状态");
        } else {
            ToastUtils.showShortToast(App.getInstance().getApplicationContext(), "error:" + e.getMessage());
        }
        if (simpleCallback != null)
            simpleCallback.onComplete();
    }

    @Override
    public void onComplete() {
        if (simpleCallback != null)
            simpleCallback.onComplete();
    }

    @Override
    public void onNext(T t) {
        if (simpleCallback != null)
            simpleCallback.onNext(t);
    }
}
