package com.ergou.hailiao.mvp.bean;

import java.io.Serializable;

/**
 * Created by LuoCY on 2019/8/16.
 */
public class RechargeRecordBean implements Serializable {

    /**
     * top_up_amount : 1000.00
     * status : 处理中
     * type : 线上充值
     * mark : 90625--充值1000
     * create_time : 2019-08-10 20:41:43
     */

    private String top_up_amount;
    private String status;
    private String type;
    private String mark;
    private String create_time;

    public String getTop_up_amount() {
        return top_up_amount;
    }

    public void setTop_up_amount(String top_up_amount) {
        this.top_up_amount = top_up_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
