package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.BeanBean;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by KissDa on 2018/8/22.
 */

public class SignInActivity extends BaseActivity<SignInPerson>
        implements SignInContract.MainView {
    @BindView(R.id.phone)
    EditText phone;//账号/邮箱
    @BindView(R.id.phone_img)
    ImageView phoneImg;//清空账号
    @BindView(R.id.password)
    EditText password;//密码
    @BindView(R.id.password_img_s)
    ImageView passwordImgS;//是否显示密码

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

    @Override
    public void showError(String msg) {
        ApiInterface.disPro(mContext);
        ToastUtils.showLongToast(mContext, msg);
    }

    @Override
    public void timeShowError(String time) {
        LogUtils.e("获取服务器时间=====获取服务器时间失败");
//        if (networkType.equals("1")) {
        getSignIn();
//        } else if (networkType.equals("2")) {
//            getCheckChangeDevice();
//        } else {
//            if (mobile_mailbox.equals("1")) {//手机注册获取验证码
//                getCheckCodeP();
//            } else if (mobile_mailbox.equals("2")) {//邮箱注册获取验证码
//                getCheckCodeM();
//            }
//        }
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
//        map.put("token", token);//
//        map.put("mobile", mobile);//手机/邮箱
//        map.put("pwd", EncryptUtils.encryptMD5ToString(passwordString));//密码

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

    public void getCheckChangeDevice() {//更换手机设备号验证
        ApiInterface.showPro(mContext);
        device_token = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        version = AppUtils.getAppVersionName(mContext);
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("device_token", device_token);
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("token", token);
        map.put("code", code_code);
        map.put("mobile", mobile + "");//手机号码
        map.put("timestamp", timestamp);

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
        mPresenter.getCheckChangeDeviceBean(requestBody);
    }

    public void getCheckCodeP() {//获取验证码（手机注册）
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("token", token);//（1：注册或者忘记密码，其它传返回值）
        map.put("mobile", mobile);//电话号码
        map.put("prefix", "");//（国际手机号前缀（传空值默认为中国）
        map.put("remark", "2");//（1：注册验证；其他类型填大于1整数）

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
        mPresenter.getCheckCodePBean(requestBody);
    }

    public void getCheckCodeM() {//获取验证码（邮箱注册）
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("token", token);//（1：注册或者忘记密码，其它传返回值）
        map.put("mail", mobile);//邮箱

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
        mPresenter.getCheckCodeMBean(requestBody);
    }

    public void pop() {
        View view = new View(SignInActivity.this);
//        promptWindow(view);
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
//                mobile = phone.getText().toString().replace(" ", "");
//                passwordString = password.getText().toString();
//                if (StringUtils.isEmpty(mobile)) {
//                    ToastUtils.showLongToast(SignInActivity.this, getResources().getText(R.string.prompt3));
//                    return;
//                } else if (StringUtils.isEmpty(passwordString)) {
//                    ToastUtils.showLongToast(SignInActivity.this, getResources().getText(R.string.prompt4));
//                    return;
//                }
////                else if (passwordString.length() <= 5) {
////                    ToastUtils.showLongToast(SignInActivity.this, getResources().getText(R.string.prompt14));
////                    return;
////                } else if (passwordString.length() > 21) {
////                    ToastUtils.showLongToast(SignInActivity.this, getResources().getText(R.string.prompt15));
////                    return;
////                }
//                else {
//                    networkType = "1";
//                    ApiInterface.showPro(mContext);
//
//                    getTimeStamp();

                Intent intent = new Intent();
                intent.setClass(mContext, MainActivity.class);
                startActivity(intent);


//                    getSignIn();
//                }
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
        if (networkType.equals("1")) {
            getSignIn();
        } else if (networkType.equals("2")) {
            getCheckChangeDevice();
        } else {
            if (mobile_mailbox.equals("1")) {//手机注册获取验证码
                getCheckCodeP();
            } else if (mobile_mailbox.equals("2")) {//邮箱注册获取验证码
                getCheckCodeM();
            }
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
//        timeStamp = timeStampBean.get(ApiInterface.languageType(mContext)).getServer_time();
        timeStamp = timeStampBean.getServer_time();
        LogUtils.e("获取服务器时间=====" + timeStamp);
//        if (networkType.equals("1")) {
        getSignIn();
//        } else if (networkType.equals("2")) {
//            getCheckChangeDevice();
//        } else {
//            if (mobile_mailbox.equals("1")) {//手机注册获取验证码
//                getCheckCodeP();
//            } else if (mobile_mailbox.equals("2")) {//邮箱注册获取验证码
//                getCheckCodeM();
//            }
//        }
    }

    @Override
    public void getSignInTos(List<LoginBean> loginBean) {//登录
        ApiInterface.disPro(mContext);
        ToastUtils.showLongToast(mContext, "获取成功");
//        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt17));
//        loginBeanw = mPresenter.getHelper().getLoginBean();
//        LogUtils.e("=======" + loginBeanw.getMobile() + "=====" + loginBeanw.getPwd());
//        intent = new Intent();
//        intent.setClass(mContext, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void getCheckChangeDeviceTos(List<LoginBean> loginBeans) {
//        ApiInterface.disPro(mContext);
//        popupWindow.dismiss();
//        networkType = "1";
//        getTimeStamp();
    }

    @Override
    public void getCheckCodePTos(List<BeanBean> beanBean) {
        ApiInterface.disPro(mContext);
    }

    @Override
    public void getCheckCodeMTos(List<BeanBean> beanBean) {
        ApiInterface.disPro(mContext);
    }

//    public void promptWindow(View view) {//更换设备（输入验证码）
//        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_whether_s, null);
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
