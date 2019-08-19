package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.BankInformationBean;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.MailListBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface CashWithdrawlContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getBankInformationTos(BankInformationBean bankInformationBean);//银行信息

        void getModifyBankInformationTos(BankInformationBean bankInformationBean);//修改、保存银行信息

        void getWithdrawalTos(BeanBean beanBean);//申请提现


    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getBankInformationBean(RequestBody body);

        void getModifyBankInformationBean(RequestBody body);

        void getWithdrawalBean(RequestBody body);

    }
}
