package com.ergou.hailiao.mvp.homepresenter;


import android.app.Activity;

import com.ergou.hailiao.base.RxPresenter;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.PromotionalPostersBean;
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

public class PromotionalPostersPerson extends RxPresenter<PromotionalPostersContract.MainView>
        implements PromotionalPostersContract.Presenter {
    private RetrofitUtil mRetrofitHelper;
    private Activity activity;

    @Inject
    public PromotionalPostersPerson(RetrofitUtil mRetrofitHelper, Activity activity) {
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

                    }
                }));
    }

    @Override
    public void getPromotionalPostersBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getHaiBao(body),
                new ResourceSubscriber<HttpResponse<PromotionalPostersBean>>() {
                    @Override
                    public void onNext(HttpResponse<PromotionalPostersBean> response) {
                        LogUtils.e("=========推广地址返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getPromotionalPostersTos(response.getData());
                        } else {
                            mView.get().showError();
                            ApiInterface.getToastUtils(activity, response.getMsg());
                        }
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================推广地址异常：");
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
