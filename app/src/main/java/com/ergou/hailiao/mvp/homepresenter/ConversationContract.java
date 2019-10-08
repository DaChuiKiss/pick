package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.RedPackageBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface ConversationContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getInfoTos(RongYunInfoBean rongYunInfo);//用户融云信息

        void getRedPackageTos(RedPackageBean redPackageBean);//红包信息

        void getJoinorquitTos(BeanBean beanBean);//加入、退出
    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getInfoBean(RequestBody body);

        void getRedPackagBean(RequestBody body);

        void getJoinorquitBean(RequestBody body);
    }
}
