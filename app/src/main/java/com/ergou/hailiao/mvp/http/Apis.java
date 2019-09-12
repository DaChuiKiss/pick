package com.ergou.hailiao.mvp.http;

import com.ergou.hailiao.mvp.bean.BankInformationBean;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.CashWithdrawalRecordBean;
import com.ergou.hailiao.mvp.bean.GameBean;
import com.ergou.hailiao.mvp.bean.GameMarqueelBean;
import com.ergou.hailiao.mvp.bean.HeadImgBean;
import com.ergou.hailiao.mvp.bean.LoginBean;
import com.ergou.hailiao.mvp.bean.LunBoBean;
import com.ergou.hailiao.mvp.bean.MailListBean;
import com.ergou.hailiao.mvp.bean.PromotionalPostersBean;
import com.ergou.hailiao.mvp.bean.RechargeBean;
import com.ergou.hailiao.mvp.bean.RechargeRecordBean;
import com.ergou.hailiao.mvp.bean.RedEnvelopeGrabBean;
import com.ergou.hailiao.mvp.bean.RedEnvelopesRecordBean;
import com.ergou.hailiao.mvp.bean.RedEnvelopesStatisticsBean;
import com.ergou.hailiao.mvp.bean.RedPackageBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.SearchMailListBean;
import com.ergou.hailiao.mvp.bean.SmallChangeBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.bean.TransferAccountsRecordBean;

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

    @POST("get_header_imgs")
//  头像列表
    Flowable<HttpResponse<List<HeadImgBean>>> getHeadImg(@Body RequestBody Body);

    @POST("update_user")
//  修改头像
    Flowable<HttpResponse<BeanBean>> getModifyHeadImg(@Body RequestBody Body);

    @POST("get_asset")
//  零钱
    Flowable<HttpResponse<SmallChangeBean>> getAsset(@Body RequestBody Body);

    @POST("update_pwd")
//  修改密码
    Flowable<HttpResponse<BeanBean>> getUpdatePwd(@Body RequestBody Body);

    @POST("forget_pwd")
//  忘记密码
    Flowable<HttpResponse<BeanBean>> getForgetPwd(@Body RequestBody Body);

    @POST("appsms")
//  获取验证码
    Flowable<HttpResponse<BeanBean>> getAppsms(@Body RequestBody Body);

    @POST("get_friends")
//  获取好友列表
    Flowable<HttpResponse<List<MailListBean>>> getFriends(@Body RequestBody Body);

    @POST("getgroup")
//  获取群列表
    Flowable<HttpResponse<GameBean>> getGroup(@Body RequestBody Body);

    @POST("search_info")
//  获取搜索信息
    Flowable<HttpResponse<List<SearchMailListBean>>> getSearchInfo(@Body RequestBody Body);

    @POST("lunbo")
//  获取轮播信息
    Flowable<HttpResponse<List<LunBoBean>>> getLunBo(@Body RequestBody Body);

    @POST("guangbo")
//  获取消息轮播
    Flowable<HttpResponse<List<GameMarqueelBean>>> getGuangBo(@Body RequestBody Body);

    @POST("chongzhi")
//  获取充值列表
    Flowable<HttpResponse<List<RechargeRecordBean>>> getChongzhi(@Body RequestBody Body);

    @POST("jinerpeizhi")
//  获取可充值列表
    Flowable<HttpResponse<List<RechargeBean>>> getJinerpeizhi(@Body RequestBody Body);

    @POST("get_bank")
//  获取银行信息
    Flowable<HttpResponse<BankInformationBean>> getBank(@Body RequestBody Body);

    @POST("bank")
//  修改、保存银行信息
    Flowable<HttpResponse<BankInformationBean>> getModifyBank(@Body RequestBody Body);

    @POST("apply")
//  申请提现
    Flowable<HttpResponse<BeanBean>> getWithdrawal(@Body RequestBody Body);

    @POST("tixian")
//  提现记录
    Flowable<HttpResponse<List<CashWithdrawalRecordBean>>> getTiXian(@Body RequestBody Body);

    @POST("zhuanzhang")
//  转账记录
    Flowable<HttpResponse<List<TransferAccountsRecordBean>>> getZhuanZhang(@Body RequestBody Body);

    @POST("hongbao")
//  红包记录
    Flowable<HttpResponse<List<RedEnvelopesRecordBean>>> getHongBao(@Body RequestBody Body);

    @POST("haibao")
//  推广地址
    Flowable<HttpResponse<PromotionalPostersBean>> getHaiBao(@Body RequestBody Body);

    @POST("transfer")
//  转账
    Flowable<HttpResponse<BeanBean>> getTransfer(@Body RequestBody Body);

    @POST("fahongbao")
//  发红包
    Flowable<HttpResponse<BeanBean>> getFaHongBao(@Body RequestBody Body);

    @POST("redstatus")
//  红包信息
    Flowable<HttpResponse<RedPackageBean>> getRedstatus(@Body RequestBody Body);

    @POST("redinfo")
// 查看大家手气
    Flowable<HttpResponse<RedEnvelopeGrabBean>> getRedinfo(@Body RequestBody Body);

    @POST("qianghongbao")
// 开
    Flowable<HttpResponse<RedEnvelopeGrabBean>> getOpen(@Body RequestBody Body);

    @POST("robfuli")
// 抢福利红包
    Flowable<HttpResponse<RedEnvelopeGrabBean>> getRobfuli(@Body RequestBody Body);

    @POST("countsend")
// 抢福利红包
    Flowable<HttpResponse<RedEnvelopesStatisticsBean>> getCountsend(@Body RequestBody Body);

    @POST("login/checkChangeDevice/")
// 更换手机设备号验证
    Flowable<HttpResponse<List<LoginBean>>> getCheckChangeDevice(@Body RequestBody Body);


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
