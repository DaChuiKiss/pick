package com.ergou.hailiao.mvp.homepresenter;


import android.app.Activity;

import com.ergou.hailiao.base.RxPresenter;
import com.ergou.hailiao.mvp.bean.RedPackageBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.http.HttpResponse;
import com.ergou.hailiao.mvp.http.RetrofitUtil;
import com.ergou.hailiao.utils.LogUtils;

import java.text.ParseException;

import javax.inject.Inject;

import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/7/30.
 */

public class ConversationPerson extends RxPresenter<ConversationContract.MainView>
        implements ConversationContract.Presenter {
    private RetrofitUtil mRetrofitHelper;
    private Activity activity;

    @Inject
    public ConversationPerson(RetrofitUtil mRetrofitHelper, Activity activity) {
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
    public void ggetInfoBean(RequestBody body) {

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
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.e("=========用户融云信息失败：" + response.getData().toString());
                            ApiInterface.getToastUtils(activity, "");
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================用户融云信息异常：");
                        mView.get().onError(t);
                        ApiInterface.getToastUtils(activity, "");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getRedPackagBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getRedstatus(body),
                new ResourceSubscriber<HttpResponse<RedPackageBean>>() {
                    @Override
                    public void onNext(HttpResponse<RedPackageBean> response) {
                        LogUtils.e("=========红包信息返回：" + response.getData().toString());
                        try {
                            if (response.getCode() == 200) {
                                mView.get().getRedPackageTos(response.getData());
                            } else {
                                mView.get().showError();
                                ApiInterface.getToastUtils(activity, response.getMsg());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.e("=========红包信息失败：" + response.getData().toString());
                            ApiInterface.getToastUtils(activity, "");
                        }
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================红包信息异常：");
                        mView.get().onError(t);
                        ApiInterface.getToastUtils(activity, "");
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

}
