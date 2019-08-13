package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.HeadImgBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface PersonalCenterContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getModifyNickNameTos(BeanBean beanBean);//修改头像
    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getModifyNickNameBean(RequestBody body);
    }
}
