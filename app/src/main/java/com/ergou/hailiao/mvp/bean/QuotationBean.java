package com.ergou.hailiao.mvp.bean;

/**
 * Created by KissDa on 2018/8/1.
 */

public class QuotationBean {

    /**
     * market : EOS
     * rmb_price : ￥49.92
     * usd_price : $7.31
     * updownrate : -10.67%
     * color : 2
     */

    private String market;
    private String rmb_price;
    private String usd_price;
    private String updownrate;
    private String color;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getRmb_price() {
        return rmb_price;
    }

    public void setRmb_price(String rmb_price) {
        this.rmb_price = rmb_price;
    }

    public String getUsd_price() {
        return usd_price;
    }

    public void setUsd_price(String usd_price) {
        this.usd_price = usd_price;
    }

    public String getUpdownrate() {
        return updownrate;
    }

    public void setUpdownrate(String updownrate) {
        this.updownrate = updownrate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
