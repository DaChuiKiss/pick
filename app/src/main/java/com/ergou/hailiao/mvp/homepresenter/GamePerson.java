package com.ergou.hailiao.mvp.homepresenter;


import android.app.Activity;

import com.ergou.hailiao.base.RxPresenter;
import com.ergou.hailiao.mvp.bean.GameBean;
import com.ergou.hailiao.mvp.bean.LunBoBean;
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

public class GamePerson extends RxPresenter<GameContract.MainView>
        implements GameContract.Presenter {
    private RetrofitUtil mRetrofitHelper;
    private Activity activity;

    @Inject
    public GamePerson(RetrofitUtil mRetrofitHelper, Activity activity) {
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
    public void getGameBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getGroup(body),
                new ResourceSubscriber<HttpResponse<GameBean>>() {
                    @Override
                    public void onNext(HttpResponse<GameBean> response) {
                        LogUtils.e("=========群列表返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getGameTos(response.getData());
                        } else {
                            mView.get().showError();
                            ApiInterface.getToastUtils(activity, response.getMsg());
                        }
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================群列表异常：");
                        mView.get().onError(t);
                        ApiInterface.disPro(activity);
                        ApiInterface.getToastUtils(activity, "");
                    }

                    @Override
                    public void onComplete() {
                        ApiInterface.disPro(activity);
                        ApiInterface.getToastUtils(activity, "");
                    }
                }));
    }

    @Override
    public void getLunBoBean(RequestBody body) {

        addSubscrebe(mRetrofitHelper.startObservable(mRetrofitHelper.getApiService().getLunBo(body),
                new ResourceSubscriber<HttpResponse<List<LunBoBean>>>() {
                    @Override
                    public void onNext(HttpResponse<List<LunBoBean>> response) {
                        LogUtils.e("=========轮播列表返回：" + response.getData().toString());
                        if (response.getCode() == 200) {
                            mView.get().getLunBoTos(response.getData());
                        } else {
                            mView.get().showError();
                            ApiInterface.getToastUtils(activity, response.getMsg());
                        }
                        ApiInterface.disPro(activity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(t.toString() + "=================轮播列表异常：");
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
