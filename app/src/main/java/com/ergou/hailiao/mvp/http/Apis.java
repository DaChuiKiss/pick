package com.ergou.hailiao.mvp.http;

import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.LoginBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by LuoCY on 2016/12/29 0029.
 */

public interface Apis {


    @POST("time")
//  获取系统时间
    Flowable<HttpResponse<TimeStampBean>> getTime(@Body RequestBody Body);

    @POST("login")
//  登录
    Flowable<HttpResponse<LoginBean>> getLogin(@Body RequestBody Body);

    @POST("get_info")
//  用户融云信息
    Flowable<HttpResponse<RongYunInfoBean>> getInfo(@Body RequestBody Body);

    @POST("login/checkChangeDevice/")
// 更换手机设备号验证
    Flowable<HttpResponse<List<LoginBean>>> getCheckChangeDevice(@Body RequestBody Body);

    @POST("login/SendChuanglanSms/")
//  获取验证码（手机注册）
    Flowable<HttpResponse<List<BeanBean>>> getSendChuanglanSms(@Body RequestBody Body);

    @POST("login/sendMail/")
//  获取验证码（邮箱注册）
    Flowable<HttpResponse<List<BeanBean>>> getSendMail(@Body RequestBody Body);
//    @POST("faq/faqdetails.aspx")
////  常见问题——详情
//    Flowable<CPDetailsBean> faqdetails(@Body RequestBody Body);

//    @GET("Project/project_stay")
////获得 我的——项目管理数据
//    Flowable<HttpResponse<List<MainBean>>> project_stay(@Query("manager_id") String manager_id,
//                                                        @Query("type") String type,
//                                                        @Query("page") String page,
//                                                        @Query("group") String group);
//
//
//    @GET("Project/project_major")
////获得 项目经理人——项目管理——待施工——项目详情——申报施工——工程专业
//    Flowable<HttpResponse<List<SelectionProcessBean>>> project_major();


}
