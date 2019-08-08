package com.ergou.hailiao.mvp.homepresenter;


import android.app.Activity;

import com.ergou.hailiao.base.RxPresenter;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.LoginBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.http.HttpResponse;
import com.ergou.hailiao.mvp.http.RetrofitUtil;
import com.ergou.hailiao.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public class SignInPerson extends RxPresenter<SignInContract.MainView>
        implements SignInContract.Presenter {
    private RetrofitUtil mRetrofitHelper;
    private Activity activity;

    @Inject
    public SignInPerson(RetrofitUtil mRetrofitHelper, Activity activity) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.activity = activity;
    }

    @Override
    public void getTimeStampBean(RequestBody body) {
        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getTime(body),
                new ResourceSubscriber<HttpResponse<TimeStampBean>>() {
                    @Override
                    public void onNext(HttpResponse<TimeStampBean> response) {
                        LogUtils.e("=========获取系统时间返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getTimeStampTos(response.getData());
                        } else {
//                            mView.get().timeShowError(response.getMsg().get(ApiInterface.languageType(activity)));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================获取系统时间异常：");
                        mView.get().timeOnError(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getSignInBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getLogin(body),
                new ResourceSubscriber<HttpResponse<List<LoginBean>>>() {
                    @Override
                    public void onNext(HttpResponse<List<LoginBean>> response) {
                        LogUtils.e("=========登录返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getSignInTos(response.getData());
                        } else {
                            mView.get().codeTypeError(response.getCode());
//                            if (response.getCode() != ApiInterface.sizeThreeOneTwo) {
                                mView.get().showError(response.getMsg());
//                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================登录异常：");
                        mView.get().onError(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getCheckChangeDeviceBean(RequestBody body) {
        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getCheckChangeDevice(body),
                new ResourceSubscriber<HttpResponse<List<LoginBean>>>() {
                    @Override
                    public void onNext(HttpResponse<List<LoginBean>> response) {
                        LogUtils.e("=========更换手机设备号验证返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getCheckChangeDeviceTos(response.getData());
                        } else {
//                            mView.get().showError(response.getMsg().get(ApiInterface.languageType(activity)));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================更换手机设备号验证异常：");
                        mView.get().onError(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }


    @Override
    public void getCheckCodePBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getSendChuanglanSms(body),
                new ResourceSubscriber<HttpResponse<List<BeanBean>>>() {
                    @Override
                    public void onNext(HttpResponse<List<BeanBean>> response) {
                        LogUtils.e("=========验证码（手机注册）返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getCheckCodePTos(response.getData());
                        } else {
//                            mView.get().showError(response.getMsg().get(ApiInterface.languageType(activity)));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================验证码（手机注册）异常：");
                        mView.get().onError(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getCheckCodeMBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getSendMail(body),
                new ResourceSubscriber<HttpResponse<List<BeanBean>>>() {
                    @Override
                    public void onNext(HttpResponse<List<BeanBean>> response) {
                        LogUtils.e("=========验证码（邮箱注册）返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getCheckCodeMTos(response.getData());
                        } else {
//                            mView.get().showError(response.getMsg().get(ApiInterface.languageType(activity)));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================验证码（邮箱注册）异常：");
                        mView.get().onError(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
