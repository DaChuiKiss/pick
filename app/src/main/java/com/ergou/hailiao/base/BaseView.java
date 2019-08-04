package com.ergou.hailiao.base;

/**
 *  Created by LuoCy on 2017/7/28.
 * View基类
 */

public interface BaseView {
    void showError(String msg);
    void timeShowError(String time);
    void codeTypeError(int code);
}
