package com.ergou.hailiao.mvp.ui.activity;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.MailListBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.CashWithdrawlContract;
import com.ergou.hailiao.mvp.homepresenter.CashWithdrawlPerson;

import java.util.List;

/**
 * 充值记录
 */
public class CashWithdrawalActivity extends BaseActivity<CashWithdrawlPerson>
        implements CashWithdrawlContract.MainView {
    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cash_withdrawal;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {

    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {

    }

    @Override
    public void getMailListTos(List<MailListBean> mailListBeanList) {

    }
}
