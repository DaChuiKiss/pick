package com.ergou.hailiao.base;

/**
 *  Created by LuoCy on 2017/9/26.
 */

public interface BasePresenter <T extends BaseView>{

    void attachView(T view);

    void detachView();
}

