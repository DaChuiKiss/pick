package com.ergou.hailiao.mvp.http;

/**
 */

public interface SimpleCallback<T> {
    void onStart();

    void onNext(T t);

    void onComplete();
}
