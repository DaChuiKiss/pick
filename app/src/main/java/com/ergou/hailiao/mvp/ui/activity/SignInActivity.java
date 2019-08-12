package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.LoginBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.SignInContract;
import com.ergou.hailiao.mvp.homepresenter.SignInPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.UserInfoSPUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/8/22.
 */

public class SignInActivity extends BaseActivity<SignInPerson>
        implements SignInContract.MainView, RongIM.UserInfoProvider {
    @BindView(R.id.phone)
    EditText phone;//账号/邮箱
    @BindView(R.id.phone_img)
    ImageView phoneImg;//清空账号
    @BindView(R.id.password)
    EditText password;//密码
    @BindView(R.id.password_img_s)
    ImageView passwordImgS;//是否显示密码
    @BindView(R.id.iv_login_bg)
    View iv_login_bg;//

    private Intent intent;
    private Boolean showPassword = true;

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private String networkType = "1";//判断接口请求（1为登录）
    private String mobile = "";//手机/邮箱
    private String passwordString = "";//密码
    private String token = "";//
    private String code_code = "";//验证码
    private String mobile_mailbox = "";//手机或者邮箱（1手机，2邮箱）

    private LoginBean mLoginBean = new LoginBean();
    private LoginBean loginBeanw;
    private PopupWindow popupWindow;
    private String mUserId = "";//融云ID

    @Override
    public void showError(String msg) {
        ApiInterface.disPro(mContext);
        ToastUtils.showLongToast(mContext, msg);
    }

    @Override
    public void timeShowError(String time) {
        LogUtils.e("获取服务器时间=====获取服务器时间失败");
        getSignIn();
    }

    @Override
    public void codeTypeError(int code) {
        ApiInterface.disPro(mContext);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(SignInActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initEventAndData() {
        //Activity上面的状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        init();
        startBgAnimation();
        if (StringUtils.isEmpty(SPUtilsData.getRongToken())) {
            token = "";
        } else {
            token = SPUtilsData.getRongToken();
            mobile = "";//手机
            passwordString = "";//密码

//            ApiInterface.showPro(mContext);
//            connect(SPUtilsData.getRongToken());
//            getTimeStamp();
        }
    }

    /**
     * 背景微动画
     */
    private void startBgAnimation() {
        Animation animation = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.seal_login_bg_translate_anim);
        iv_login_bg.startAnimation(animation);
    }

    public void init() {

        //手机号码--输入监听
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int i2) {

                if (s == null || s.length() == 0) {
                    phoneImg.setVisibility(View.GONE);
                    return;
                } else {
                    phoneImg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void getTimeStamp() {//获取服务器时间

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getSignIn() {//登录
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("token", token);//
        map.put("mobile", mobile);//手机/邮箱
        map.put("pwd", passwordString);//密码

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
        mPresenter.getSignInBean(requestBody);
    }


    @OnClick({R.id.return_img, R.id.password_img_s, R.id.sign_in
            , R.id.forget_password, R.id.register, R.id.phone_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_img://返回
                finish();
                break;
            case R.id.phone_img://清空账号输入内容
                phone.setText("");
                break;
            case R.id.password_img_s://是否显示密码
                if (showPassword) {// 显示密码
                    passwordImgS.setImageResource(R.drawable.password_look_s);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    passwordImgS.setImageResource(R.drawable.password_look);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.sign_in://登录
                mobile = phone.getText().toString().replace(" ", "");
                passwordString = password.getText().toString();
                mobile = "18670316044";
                passwordString = "123456";
                if (StringUtils.isEmpty(mobile)) {
                    ToastUtils.showLongToast(SignInActivity.this, getResources().getText(R.string.prompt6));
                    return;
                } else if (StringUtils.isEmpty(passwordString)) {
                    ToastUtils.showLongToast(SignInActivity.this, getResources().getText(R.string.prompt7));
                    return;
                }
                ApiInterface.showPro(mContext);
                getTimeStamp();
                break;
            case R.id.forget_password://忘记密码
//                intent = new Intent();
//                intent.setClass(mContext, ForgetPasswordActivity.class);
//                startActivity(intent);
                break;
            case R.id.register://注册账号
//                intent = new Intent();
//                intent.setClass(mContext, RegisterActivity.class);
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void onError(Throwable throwable) {
        ApiInterface.disPro(mContext);
        ToastUtils.showLongToast(mContext, getResources().getString(R.string.prompt5));
    }

    @Override
    public void timeOnError(Throwable throwable) {
        LogUtils.e("获取服务器时间=====获取服务器时间失败");
        getSignIn();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        timeStamp = timeStampBean.getServer_time();
        LogUtils.e("获取服务器时间=====" + timeStamp);
        getSignIn();
    }

    @Override
    public void getSignInTos(LoginBean loginBean) {//登录
//        ApiInterface.disPro(mContext);
        UserInfoSPUtils.getInstance().put("rong_token", loginBean.getRong_token());//token
        UserInfoSPUtils.getInstance().put("nick_name", loginBean.getNick_name());//昵称
        UserInfoSPUtils.getInstance().put("user_header_img", loginBean.getUser_header_img());//头像
        UserInfoSPUtils.getInstance().put("user_id", loginBean.getUser_id());//融云ID
        connect(SPUtilsData.getRongToken());
//        intent = new Intent();
//        intent.setClass(mContext, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @param
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                mUserId = userId;
                RongIM.getInstance().setCurrentUserInfo(new UserInfo(mUserId, SPUtilsData.getNickName(), Uri.parse(SPUtilsData.getUserHeaderImg())));
                getRefreshUserInfoCache();//刷新用户缓存数据。需要更新的用户缓存数据。
                getUserInfoProvider();//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK
                Log.e("main", "融云连接成功：" + mUserId);
                ApiInterface.disPro(mContext);
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                ApiInterface.disPro(mContext);
                Log.e("main", "融云连失败:" + errorCode.getValue());
            }

            @Override
            public void onTokenIncorrect() {
                Log.e("main", "token is error , please check token and appkey ");
                ApiInterface.disPro(mContext);
            }
        });

    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return new UserInfo(userId, SPUtilsData.getNickName(), Uri.parse(SPUtilsData.getUserHeaderImg()));
    }

    public void getUserInfoProvider() {///根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {

                return new UserInfo(mUserId, SPUtilsData.getNickName(), Uri.parse(SPUtilsData.getUserHeaderImg()));//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
    }

    /**
     * @ 刷新用户缓存数据。需要更新的用户缓存数据。
     */
    public void getRefreshUserInfoCache() {
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(mUserId, SPUtilsData.getNickName(), Uri.parse(SPUtilsData.getUserHeaderImg())));
    }


//    public void promptWindow(View view) {//更换设备（输入验证码）
//        View contentView = LayoutInflater.from(this).inflate(R.test.pop_whether_s, null);
////        final PopupWindow popupWindow = new PopupWindow(contentView, 600, 400);
//        popupWindow = new PopupWindow(contentView);
//        popupWindow.setContentView(contentView);
//        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//// 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
//        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
//        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
//        getWindow().setAttributes(wlBackground);
//        // 当PopupWindow消失时,恢复其为原来的颜色
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                wlBackground.alpha = 1.0f;
//                getWindow().setAttributes(wlBackground);
//            }
//        });
//
//        final EditText verification_code = (EditText) contentView.findViewById(R.id.verification_code);//验证码
//        TextView rl2 = (TextView) contentView.findViewById(R.id.confirm_no);//取消
//        TextView rl3 = (TextView) contentView.findViewById(R.id.confirm);//接受
//        TextView rl4 = (TextView) contentView.findViewById(R.id.give_up);//提示
//        rl2.setText(getResources().getText(R.string.cancel));
//        rl3.setText(getResources().getText(R.string.determine));
//        String pop = (String) getResources().getText(R.string.prompt38);
//        rl4.setText(pop + mobile);
//        rl2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {//取消
//                popupWindow.dismiss();
//            }
//        });
//        rl3.setOnClickListener(new View.OnClickListener() {//确定
//            @Override
//            public void onClick(View view) {
//
//                if (StringUtils.isEmpty(verification_code.getText().toString())) {
//                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt13));
//                    return;
//                }
//                networkType = "2";
//                code_code = verification_code.getText().toString();
//                getTimeStamp();
//
//            }
//        });
//        //设置PopupWindow进入和退出动画
//        popupWindow.setAnimationStyle(R.style.pop_window_anim);
//        // 设置PopupWindow显示在中间
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//    }

}
