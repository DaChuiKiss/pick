package com.ergou.hailiao.mvp.homepresenter;


import android.app.Activity;

import com.ergou.hailiao.base.RxPresenter;
import com.ergou.hailiao.mvp.bean.BankInformationBean;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.RedEnvelopesStatisticsBean;
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

public class RedEnvelopesStatisticsPerson extends RxPresenter<RedEnvelopesStatisticsContract.MainView>
        implements RedEnvelopesStatisticsContract.Presenter {
    private RetrofitUtil mRetrofitHelper;
    private Activity activity;

    @Inject
    public RedEnvelopesStatisticsPerson(RetrofitUtil mRetrofitHelper, Activity activity) {
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
    public void getRedEnvelopesStatisticsBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getCountsend(body),
                new ResourceSubscriber<HttpResponse<RedEnvelopesStatisticsBean>>() {
                    @Override
                    public void onNext(HttpResponse<RedEnvelopesStatisticsBean> response) {
                        LogUtils.e("=========银行信息返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getRedEnvelopesStatisticsTos(response.getData());
                        } else {
                            mView.get().showError();
                            ApiInterface.getToastUtils(activity, response.getMsg());
                        }
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================银行信息异常：");
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
