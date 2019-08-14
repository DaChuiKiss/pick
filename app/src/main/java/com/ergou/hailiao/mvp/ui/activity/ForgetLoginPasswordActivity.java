package com.ergou.hailiao.mvp.ui.activity;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.ForgetPayPasswordContract;
import com.ergou.hailiao.mvp.homepresenter.ForgetPayPasswordPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 忘记登录密码
 */
public class ForgetLoginPasswordActivity extends BaseActivity<ForgetPayPasswordPerson>
        implements ForgetPayPasswordContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.phone_number)
    EditText phoneNumber;//号码
    @BindView(R.id.phone_img)
    ImageView phoneImg;
    @BindView(R.id.code_code)
    EditText codeCode;//验证码
    @BindView(R.id.get_check_code)
    TextView getCheckCode;
    @BindView(R.id.new_password)
    EditText newPassword;//新密码
    @BindView(R.id.password_img_2)
    ImageView passwordImg2;
    @BindView(R.id.new_password_determine)
    EditText newPasswordDetermine;//确认新密码
    @BindView(R.id.password_img_3)
    ImageView passwordImg3;

    private Boolean showPassword2 = true;
    private Boolean showPassword3 = true;

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private String networkType = "1";//请求接口状态（(1:登录密码;2:支付密码)）
    private String mNewPassword = "";//新密码
    private String mNewPasswordDetermine = "";//确认新密码
    private String code_code = "";//手机验证码
    private String mPhoneNumber = "";//手机号

    private TimeCount timer;//验证码倒计时

    @Override
    protected void initInject() {
        getActivityComponent().inject(ForgetLoginPasswordActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_login_password;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.forget_login_password));
        timer = new TimeCount(ApiInterface.getCode, ApiInterface.getCode_s);

        //手机号-输入监听
        phoneNumber.addTextChangedListener(new TextWatcher() {
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
        //新密码-输入监听
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int i2) {

                if (s == null || s.length() == 0) {
                    passwordImg2.setVisibility(View.GONE);
                    return;
                } else {
                    passwordImg2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //确认密码-输入监听
        newPasswordDetermine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int i2) {

                if (s == null || s.length() == 0) {
                    passwordImg3.setVisibility(View.GONE);
                    return;
                } else {
                    passwordImg3.setVisibility(View.VISIBLE);
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

    /**
     * 获取验证码
     */
    public void getSentCode() {
        ApiInterface.showPro(mContext);
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("mobile", mPhoneNumber);
        map.put("type", "2");//(短信类型:1:注册;2:忘记密码)

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
        mPresenter.getSentCodeBean(requestBody);
    }

    public void getForgetPayPassword() {//忘记支付密码
        ApiInterface.showPro(mContext);
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("mobile", mPhoneNumber);
        map.put("type", networkType);//修改类型：1：登录密码；2支付密码
        map.put("code", code_code);//手机验证码
        map.put("new_pass", mNewPassword);//新密码

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
        mPresenter.getForgetPayPasswordBean(requestBody);
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        getForgetPayPassword();
    }

    @OnClick({R.id.fallback, R.id.phone_img, R.id.get_check_code, R.id.password_img_2, R.id.password_img_3, R.id.determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.phone_img://清空手机号
                phoneNumber.setText("");
                break;
            case R.id.get_check_code://获取验证码
                mPhoneNumber = phoneNumber.getText().toString();//手机号
                if (StringUtils.isEmpty(mPhoneNumber)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt3));
                    return;
                }
                if (mPhoneNumber.length() < 11) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt17));
                    return;
                }
                getSentCode();
                break;
            case R.id.password_img_2:
                if (showPassword2) {// 显示密码
                    passwordImg2.setImageResource(R.drawable.password_look);
                    newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPassword.setSelection(newPassword.getText().toString().length());
                    showPassword2 = !showPassword2;
                } else {// 隐藏密码
                    passwordImg2.setImageResource(R.drawable.password_look_s);
                    newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPassword.setSelection(newPassword.getText().toString().length());
                    showPassword2 = !showPassword2;
                }
                break;
            case R.id.password_img_3:
                if (showPassword3) {// 显示密码
                    passwordImg3.setImageResource(R.drawable.password_look);
                    newPasswordDetermine.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPasswordDetermine.setSelection(newPasswordDetermine.getText().toString().length());
                    showPassword3 = !showPassword3;
                } else {// 隐藏密码
                    passwordImg3.setImageResource(R.drawable.password_look_s);
                    newPasswordDetermine.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPasswordDetermine.setSelection(newPasswordDetermine.getText().toString().length());
                    showPassword3 = !showPassword3;
                }
                break;
            case R.id.determine:
                mPhoneNumber = phoneNumber.getText().toString();//手机号
                code_code = codeCode.getText().toString();//验证码
                mNewPassword = newPassword.getText().toString();//新密码
                mNewPasswordDetermine = newPasswordDetermine.getText().toString();//确认密码
                if (StringUtils.isEmpty(mPhoneNumber)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt15));
                    return;
                }
                if (mPhoneNumber.length() < 11) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt17));
                    return;
                }
                if (StringUtils.isEmpty(code_code)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt16));
                    return;
                }
                if (code_code.length() < 6) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt18));
                    return;
                }
                if (StringUtils.isEmpty(mNewPassword)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt11));
                    return;
                }
                if (mNewPassword.length() < 6) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt13));
                    return;
                }
                if (StringUtils.isEmpty(mNewPasswordDetermine)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt12));
                    return;
                }
                if (mNewPasswordDetermine.length() < 6) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt13));
                    return;
                }
                if (!mNewPasswordDetermine.equals(mNewPassword)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt14));
                    return;
                }
                getTimeStamp();
                break;
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        getForgetPayPassword();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getForgetPayPassword();
    }

    @Override
    public void getSentCodeTos(BeanBean beanBean) {
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt19));
        timer.start();
    }

    @Override
    public void getForgetPayPasswordTos(BeanBean beanBean) {
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt8));
        finish();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            if (!ForgetLoginPasswordActivity.this.isFinishing()) {
                getCheckCode.setClickable(false);
                String str = (String) getResources().getText(R.string.prompt20);
                getCheckCode.setText(millisUntilFinished / ApiInterface.getCode_s + str);

            }
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            if (!ForgetLoginPasswordActivity.this.isFinishing()) {
                getCheckCode.setText(R.string.get_code);
                getCheckCode.setClickable(true);
            }
        }
    }

}
