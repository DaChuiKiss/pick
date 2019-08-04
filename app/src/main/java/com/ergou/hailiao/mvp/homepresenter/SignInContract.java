package com.ergou.hailiao.mvp.homepresenter;


import com.ergou.hailiao.base.BasePresenter;
import com.ergou.hailiao.base.BaseView;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.LoginBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public interface SignInContract {
    interface MainView extends BaseView {
        void onError(Throwable throwable);
        void timeOnError(Throwable throwable);
        void getTimeStampTos(TimeStampBean timeStampBean);//服务器时间
        void getSignInTos(List<LoginBean> loginBean);//登录
        void getCheckChangeDeviceTos(List<LoginBean> loginBeans);//更换手机设备号验证
        void getCheckCodePTos(List<BeanBean> beanBean);//获取验证码（手机注册）
        void getCheckCodeMTos(List<BeanBean> beanBean);//获取验证码（邮箱注册）
    }

    interface Presenter extends BasePresenter<MainView> {
        void getTimeStampBean(RequestBody body);
        void getSignInBean(RequestBody body);
        void getCheckChangeDeviceBean(RequestBody body);
        void getCheckCodePBean(RequestBody body);
        void getCheckCodeMBean(RequestBody body);
    }
}
