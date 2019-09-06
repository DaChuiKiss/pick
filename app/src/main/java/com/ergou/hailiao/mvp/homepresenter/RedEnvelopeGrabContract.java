package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.RedEnvelopeGrab;
import com.ergou.hailiao.mvp.bean.RedEnvelopesRecordBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface RedEnvelopeGrabContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getRedEnvelopeGrabTos(RedEnvelopeGrab redEnvelopeGrab);//查看手气


    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getRedEnvelopeGrabBean(RequestBody body);

    }
}
