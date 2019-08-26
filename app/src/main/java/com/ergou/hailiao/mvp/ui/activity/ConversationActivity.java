package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.ConversationContract;
import com.ergou.hailiao.mvp.homepresenter.ConversationPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.adapter.HomeAdapter;
import com.ergou.hailiao.rongyun.SealExtensionModule;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by LuoCY on 2019/8/10.
 */
public class ConversationActivity extends BaseActivity<ConversationPerson>
        implements ConversationContract.MainView, RongIM.UserInfoProvider {
    @BindView(R.id.title_share)
    TextView titleShare;
    private String title;
    /**
     * 对方id
     */
    private String targetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType conversationType;
    private UserInfo userInfo;
    private String mUserId = "";//
    private RongYunInfoBean mRongYunInfoBean = new RongYunInfoBean();
    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";


    @Override
    protected void initInject() {
        getActivityComponent().inject(ConversationActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.actvity_conversationt;
    }

    @Override
    protected void initEventAndData() {

        // 没有intent 的则直接返回
        Intent intent = getIntent();
        if (intent == null || intent.getData() == null) {
            finish();
            return;
        }
        targetId = intent.getData().getQueryParameter("targetId");
        conversationType = Conversation.ConversationType.valueOf(intent.getData()
                .getLastPathSegment().toUpperCase(Locale.US));
        title = intent.getData().getQueryParameter("title");
        titleShare.setText(title);
        mUserId = targetId;
        getSignIn();
        RongIM.setUserInfoProvider(this, true);
        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(conversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", targetId).build();

        fragement.setUri(uri);

    }



//    public void getfindUserById(){
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//
//            @Override
//            public UserInfo getUserInfo(String userId) {
//                return findUserById(userId);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
//            }
//
//        }, true);
//    }

    public void getTimeStamp() {//获取服务器时间

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getSignIn() {//
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("user_id", mUserId);//


        cmd = InterfaceInteraction.getCmdValue(map);
        sign = EncryptUtils.encryptMD5ToString(InterfaceInteraction.getSign(code, cmd));

        LogUtils.e("code=" + code);
        LogUtils.e("sign=" + sign);
        LogUtils.e("cmd=" + cmd);

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("code", code)
                .addFormDataPart("sign", sign)
                .addFormDataPart("cmd", cmd);
        RequestBody requestBody = build.build();
        mPresenter.ggetInfoBean(requestBody);
    }


    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void timeOnError(Throwable throwable) {
        getSignIn();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getSignIn();
    }

    @Override
    public void getInfoTos(RongYunInfoBean rongYunInfo) {
        mRongYunInfoBean = rongYunInfo;
        userInfo = new UserInfo(mUserId, mRongYunInfoBean.getNick_name(), Uri.parse(mRongYunInfoBean.getUser_header_img()));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
//        getfindUserById();
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {

    }

//    private UserInfo findUserById(String userId) {
//        if (mUserId.equals(userId)) {
//            userInfo = new UserInfo(userId, mRongYunInfoBean.getNick_name(), Uri.parse(mRongYunInfoBean.getUser_header_img()));
//            RongIM.getInstance().refreshUserInfoCache(userInfo);
//            return userInfo;
//        } else {
//            userInfo = new UserInfo(userId, SPUtilsData.getNickName(), Uri.parse(SPUtilsData.getUserHeaderImg()));
//            RongIM.getInstance().refreshUserInfoCache(userInfo);
//            return userInfo;
//        }
//        return null;
//    }

    @Override
    public UserInfo getUserInfo(String userId) {
        if (mUserId.equals(userId)) {
            mUserId = userId;
            getSignIn();
            return userInfo;
        } else if (mUserId.equals(SPUtilsData.getUserId())) {
            userInfo = new UserInfo(userId, SPUtilsData.getNickName(), Uri.parse(SPUtilsData.getUserHeaderImg()));
            RongIM.getInstance().refreshUserInfoCache(userInfo);
            return userInfo;
        } else {
            mUserId = userId;
            getSignIn();
            return userInfo;
        }
    }


    @OnClick({R.id.fallback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
        }
    }

}
