package com.ergou.hailiao.mvp.bean;

import java.util.List;

/**
 * Created by KissDa on 2018/8/2.
 */

public class AssetsFlowBean {

    /**
     * wallet_addr : LMBFPA9zJ8ytDzrTfyTqNh1V6dnxCR29Fc
     * coinname : ltc
     * num : 1
     * mum : 0.99
     * fee : 0.01
     * txid : 0
     * status : ["转出成功","Réussir","Turn out to be successful"]
     * add_time : 2018-07-27 17:08:19
     * log_type : 2
     */

    private String wallet_addr;//钱包地址
    private String coinname;//币种名称
    private String num;//数量
    private String mum;//实际数量
    private String fee;//手续费
    private String txid;//流水号
    private String add_time;//时间
    private String log_type;//记录类型（1:转入记录；2:转出记录）
    private List<String> status;//状态（为多语言数组）

    public String getWallet_addr() {
        return wallet_addr;
    }

    public void setWallet_addr(String wallet_addr) {
        this.wallet_addr = wallet_addr;
    }

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

    public String getMum() {
        return mum;
    }

    public void setMum(String mum) {
        this.mum = mum;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getLog_type() {
        return log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
