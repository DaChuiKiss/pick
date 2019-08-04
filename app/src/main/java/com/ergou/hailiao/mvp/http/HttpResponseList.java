package com.ergou.hailiao.mvp.http;

/**
 * Created by LuoCY on 2018/4/11.
 */

public class HttpResponseList<T> {

    /**
     * msg : 成功
     * data : {"token":"e1b708d6410cf2c4f8aecdfa3a73a799"}
     */

    private String msg;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
