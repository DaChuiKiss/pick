package com.ergou.hailiao.mvp.homepresenter;


import android.app.Activity;

import com.ergou.hailiao.base.RxPresenter;
import com.ergou.hailiao.mvp.bean.RedEnvelopeGrabBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.http.HttpResponse;
import com.ergou.hailiao.mvp.http.RetrofitUtil;
import com.ergou.hailiao.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public class RedEnvelopesGrabPerson extends RxPresenter<RedEnvelopeGrabContract.MainView>
        implements RedEnvelopeGrabContract.Presenter {
    private RetrofitUtil mRetrofitHelper;
    private Activity activity;

    @Inject
    public RedEnvelopesGrabPerson(RetrofitUtil mRetrofitHelper, Activity activity) {
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
                            mView.get().timeShowError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================获取系统时间异常：");
                        mView.get().timeOnError(t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("main" + "=================获取系统时间失败：");

                    }
                }));
    }

    @Override
    public void getRedEnvelopeGrabBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getRedinfo(body),
                new ResourceSubscriber<HttpResponse<RedEnvelopeGrabBean>>() {
                    @Override
                    public void onNext(HttpResponse<RedEnvelopeGrabBean> response) {
                        LogUtils.e("=========查看手气返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getRedEnvelopeGrabTos(response.getData());
                        } else {
                            mView.get().showError();
                            ApiInterface.getToastUtils(activity, response.getMsg());
                        }
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================查看手气异常：");
                        mView.get().onError(t);
                        ApiInterface.disPro(activity);
                        ApiInterface.getToastUtils(activity, "");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getOpenBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getOpen(body),
                new ResourceSubscriber<HttpResponse<RedEnvelopeGrabBean>>() {
                    @Override
                    public void onNext(HttpResponse<RedEnvelopeGrabBean> response) {
                        LogUtils.e("=========开返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getOpenTos(response.getData());
                        } else {
                            mView.get().showError();
                            ApiInterface.getToastUtils(activity, response.getMsg());
                        }
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================开异常：");
                        mView.get().onError(t);
                        ApiInterface.disPro(activity);
                        ApiInterface.getToastUtils(activity, "");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
