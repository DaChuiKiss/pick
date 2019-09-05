package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.RedPackageBean;
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
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;

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
    private String nickName = "";//
    private String mHeader = "";//

    private PopupWindow popupWindow;


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

    public void getRedPackag(String nick_name, String header, String order_id) {//
        nickName = nick_name;
        mHeader = header;
//        ApiInterface.showPro(mContext);
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("order_id", order_id);//红包唯一订单ID
        map.put("mobile", SPUtilsData.getPhoneNumber());//手机号
        map.put("type", "1");//类型（1:踩雷红包；2:福利红包）


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
        mPresenter.getRedPackagBean(requestBody);
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
    public void getRedPackageTos(RedPackageBean redPackageBean) {
        View view = new View(ConversationActivity.this);
        redPackageWindow(view);
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

    public void redPackageWindow(View view) {//红包状态
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_red_package, null);
//        final PopupWindow popupWindow = new PopupWindow(contentView, 600, 400);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
// 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });

        ImageView head_img = (ImageView) contentView.findViewById(R.id.head_img);//头像
        TextView name = (TextView) contentView.findViewById(R.id.name);//名字
        TextView chakan = (TextView) contentView.findViewById(R.id.chakan);//查看手气
        TextView red_delete = (TextView) contentView.findViewById(R.id.red_delete);//关闭
        name.setText(nickName);
        GlideManager.loadImageView(mContext, mHeader,
                head_img, R.mipmap.ic_launcher);//头像
        chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//查看手气
                popupWindow.dismiss();
            }
        });
        red_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//关闭

                popupWindow.dismiss();

            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.pop_window_anim);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

}
