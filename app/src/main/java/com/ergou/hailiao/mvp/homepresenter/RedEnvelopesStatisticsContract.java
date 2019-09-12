package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.BankInformationBean;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.RedEnvelopesStatisticsBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface RedEnvelopesStatisticsContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getRedEnvelopesStatisticsTos(RedEnvelopesStatisticsBean redEnvelopesStatisticsBean);//

    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getRedEnvelopesStatisticsBean(RequestBody body);
    }
}
