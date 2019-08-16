package com.ergou.hailiao.mvp.http;

import java.util.List;

/**
 * Created by LuoCy on 2017/9/26.
 */

public class HttpResponse<T> {

    /**
     * code : 200
     * msg :
     * data : {"server_time":"1525275215"}
     */

    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
