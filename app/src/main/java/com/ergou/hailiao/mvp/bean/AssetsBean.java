package com.ergou.hailiao.mvp.bean;

/**
 * Created by KissDa on 2018/7/25.
 */

public class AssetsBean {


    /**
     * coinname : NB
     * num : 10
     * freeze : 1
     * img : http://image.studyqian.top/iconusd@3x.png
     */

    private String coinname;
    private String num;
    private String freeze;
    private String img;

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFreeze() {
        return freeze;
    }

    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
