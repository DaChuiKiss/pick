package com.ergou.hailiao.mvp.bean;

import com.google.gson.annotations.SerializedName;

public class RedTypeBean {
    /**
     * code : 200
     * msg : 成功
     * data : {"status":1}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : 1
         */

        private String status;//红包状态（1:已过期；2:红包已抢完；3:已抢过；4:可抢）

        public String getStatus() {
            return status;
        }

        public void setStatusX(String statusX) {
            this.status = statusX;
        }
    }
}
