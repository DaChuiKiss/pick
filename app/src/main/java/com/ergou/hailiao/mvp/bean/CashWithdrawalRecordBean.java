package com.ergou.hailiao.mvp.bean;

public class CashWithdrawalRecordBean {

    /**
     * withdrawal_amount : 2000.00
     * status : 提现处理中
     * create_time : 2019-08-10 20:41:43
     */

    private String withdrawal_amount;
    private String status;
    private String create_time;

    public String getWithdrawal_amount() {
        return withdrawal_amount;
    }

    public void setWithdrawal_amount(String withdrawal_amount) {
        this.withdrawal_amount = withdrawal_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
