package com.ergou.hailiao.mvp.bean;

/**
 * Created by KissDa on 2018/8/6.
 */

public class PurseAddressBean {

    /**
     * id : 2
     * username : 超级用户
     * wallet_addr : Le7ZuSfVVXJ7yzpZ2KfaSyVb6BWDUnxqcc
     * add_time : 2018-07-31 14:57:30
     */

    private String id;//记录ID
    private String username;//备注姓名
    private String wallet_addr;//钱包地址
    private String add_time;//添加时间
    private String coinname;//币种

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWallet_addr() {
        return wallet_addr;
    }

    public void setWallet_addr(String wallet_addr) {
        this.wallet_addr = wallet_addr;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }
}
