package com.ergou.hailiao.mvp.bean;

import java.io.Serializable;

public class TransferAccountsRecordBean implements Serializable {

    /**
     * receive_member_id : 18060055378
     * transfer_amount : 500.00
     * status : 转账成功
     * mark : 转你500记得还我
     * create_time : 2019-08-10 20:41:43
     */

    private String receive_member_id;
    private String transfer_amount;
    private String status;
    private String mark;
    private String create_time;

    public String getReceive_member_id() {
        return receive_member_id;
    }

    public void setReceive_member_id(String receive_member_id) {
        this.receive_member_id = receive_member_id;
    }

    public String getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(String transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
