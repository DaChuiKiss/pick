package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface TransferAccountsContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getInfoTos(RongYunInfoBean rongYunInfo);//用户融云信息

        void getZhuanZhangTos(BeanBean beanBean);//转账
    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getInfoBean(RequestBody body);

        void getZhuanZhangBean(RequestBody body);
    }
}
