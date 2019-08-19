package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.GameBean;
import com.ergou.hailiao.mvp.bean.GameMarqueelBean;
import com.ergou.hailiao.mvp.bean.LunBoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface GameContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);

        void timeOnError(Throwable throwable);

        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间

        void getGameTos(GameBean gameBeans);//群列表

        void getLunBoTos(List<LunBoBean> lunBoBeans);//轮播列表

        void getGameMarqueelTos(List<GameMarqueelBean> gameMarqueelBeans);//消息轮播列表

    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);

        void getGameBean(RequestBody body);

        void getLunBoBean(RequestBody body);

        void getGameMarqueelBean(RequestBody body);
    }
}
