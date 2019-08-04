package com.ergou.hailiao.mvp.bean;

/**
 * Created by KissDa on 2018/8/8.
 */

public class MagicianDetailsBean {

    /**
     * coinname : LTC
     * type : 开启
     * open_num : 0.01
     * close_return_num : 0
     * close_return_mum : 0
     * close_fee : 0
     * add_time : 2018-08-02 17:28:27
     */

    private String coinname;//币种名称
    private String type;//记录类型
    private String open_num;//开启数量
    private String close_return_num;//关闭预计返还数量
    private String close_return_mum;//关闭实际返还数量
    private String close_fee;//关闭手续费
    private String add_time;//时间

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpen_num() {
        return open_num;
    }

    public void setOpen_num(String open_num) {
        this.open_num = open_num;
    }

    public String getClose_return_num() {
        return close_return_num;
    }

    public void setClose_return_num(String close_return_num) {
        this.close_return_num = close_return_num;
    }

    public String getClose_return_mum() {
        return close_return_mum;
    }

    public void setClose_return_mum(String close_return_mum) {
        this.close_return_mum = close_return_mum;
    }

    public String getClose_fee() {
        return close_fee;
    }

    public void setClose_fee(String close_fee) {
        this.close_fee = close_fee;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
