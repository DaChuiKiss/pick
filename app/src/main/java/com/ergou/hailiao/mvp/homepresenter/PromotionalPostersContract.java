package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.PromotionalPostersBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface PromotionalPostersContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getPromotionalPostersTos(PromotionalPostersBean promotionalPostersBean);//推广地址
    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getPromotionalPostersBean(RequestBody body);
    }
}
