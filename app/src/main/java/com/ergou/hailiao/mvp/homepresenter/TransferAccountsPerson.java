package com.ergou.hailiao.mvp.homepresenter;


import android.app.Activity;

import com.ergou.hailiao.base.RxPresenter;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
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

public class TransferAccountsPerson extends RxPresenter<TransferAccountsContract.MainView>
        implements TransferAccountsContract.Presenter {
    private RetrofitUtil mRetrofitHelper;
    private Activity activity;

    @Inject
    public TransferAccountsPerson(RetrofitUtil mRetrofitHelper, Activity activity) {
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
    public void getInfoBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getInfo(body),
                new ResourceSubscriber<HttpResponse<RongYunInfoBean>>() {
                    @Override
                    public void onNext(HttpResponse<RongYunInfoBean> response) {
                        LogUtils.e("=========用户融云信息返回：" + response.getData().toString());
                        try {
                            if (response.getCode() == 200) {
                                mView.get().getInfoTos(response.getData());
                            } else {
                                mView.get().showError();
                                ApiInterface.getToastUtils(activity, response.getMsg());
                            }
                            ApiInterface.disPro(activity);
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.e("=========用户融云信息失败：" + response.getData().toString());
                            ApiInterface.getToastUtils(activity, "");
                            ApiInterface.disPro(activity);
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================用户融云信息异常：");
                        mView.get().onError(t);
                        ApiInterface.getToastUtils(activity, "");
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onComplete() {
                        ApiInterface.disPro(activity);
                    }
                }));
    }
    @Override
    public void getZhuanZhangBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getTransfer(body),
                new ResourceSubscriber<HttpResponse<BeanBean>>() {
                    @Override
                    public void onNext(HttpResponse<BeanBean> response) {
                        LogUtils.e("=========转账返回：" + response.getData().toString());
                        try {
                            if (response.getCode() == 200) {
                                mView.get().getZhuanZhangTos(response.getData());
                            } else {
                                mView.get().showError();
                                ApiInterface.getToastUtils(activity, response.getMsg());
                            }
                            ApiInterface.disPro(activity);
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.e("=========转账失败：" + response.getData().toString());
                            ApiInterface.getToastUtils(activity, "");
                            ApiInterface.disPro(activity);
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================转账异常：");
                        mView.get().onError(t);
                        ApiInterface.getToastUtils(activity, "");
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onComplete() {
                        ApiInterface.disPro(activity);
                    }
                }));
    }

}
