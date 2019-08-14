package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.ModifyPayPasswordContract;
import com.ergou.hailiao.mvp.homepresenter.ModifyPayPasswordPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 支付密码
 */
public class PaymenyPasswordActivity extends BaseActivity<ModifyPayPasswordPerson>
        implements ModifyPayPasswordContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.title_right_text)
    TextView titleRightText;//忘记密码
    @BindView(R.id.title_right_rl)
    RelativeLayout titleRightRl;
    @BindView(R.id.original_password)
    EditText originalPassword;//原密码
    @BindView(R.id.password_img_1)
    ImageView passwordImg1;
    @BindView(R.id.new_password)
    EditText newPassword;//新密码
    @BindView(R.id.password_img_2)
    ImageView passwordImg2;
    @BindView(R.id.new_password_determine)
    EditText newPasswordDetermine;//确认密码
    @BindView(R.id.password_img_3)
    ImageView passwordImg3;

    private Boolean showPassword1 = true;
    private Boolean showPassword2 = true;
    private Boolean showPassword3 = true;

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private String networkType = "2";//请求接口状态（(1:登录密码;2:支付密码)）
    private String mOriginalPassword = "";//原密码
    private String mNewPassword = "";//新密码

    private Intent intent;

    @Override
    protected void initInject() {
        getActivityComponent().inject(PaymenyPasswordActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_payment_password;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.payment_password));
        titleRightText.setText(getResources().getText(R.string.forget_password));
        titleRightText.setVisibility(View.VISIBLE);
        titleRightRl.setVisibility(View.VISIBLE);
        //原密码-输入监听
        originalPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int i2) {

                if (s == null || s.length() == 0) {
                    passwordImg1.setVisibility(View.GONE);
                    return;
                } else {
                    passwordImg1.setVisibility(View.VISIBLE);
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

    public void getModifyPayPassword() {//修改支付密码
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
        map.put("mobile", SPUtilsData.getPhoneNumber());
        map.put("type", networkType);//修改类型：1：登录密码；2支付密码
        map.put("pwd", mOriginalPassword);//旧密码
        map.put("new_pwd", mNewPassword);//新密码

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
        mPresenter.getModifyPayPasswordBean(requestBody);
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        getModifyPayPassword();
    }

    @OnClick({R.id.fallback, R.id.title_right_rl, R.id.password_img_1,
            R.id.password_img_2, R.id.password_img_3, R.id.determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.title_right_rl://忘记密码
                intent = new Intent();
                intent.setClass(mContext, ForgetPaymenyPasswordActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.password_img_1://原密码
                if (showPassword1) {// 显示密码
                    passwordImg1.setImageResource(R.drawable.password_look);
                    originalPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    originalPassword.setSelection(originalPassword.getText().toString().length());
                    showPassword1 = !showPassword1;
                } else {// 隐藏密码
                    passwordImg1.setImageResource(R.drawable.password_look_s);
                    originalPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    originalPassword.setSelection(originalPassword.getText().toString().length());
                    showPassword1 = !showPassword1;
                }
                break;
            case R.id.password_img_2://新密码
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
            case R.id.password_img_3://确认密码
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
            case R.id.determine://确定
                mOriginalPassword = originalPassword.getText().toString();//旧密码
                mNewPassword = newPassword.getText().toString();//新密码
                if (StringUtils.isEmpty(mOriginalPassword)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt10));
                    return;
                }
                if (mOriginalPassword.length() < 6) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt13));
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
                if (StringUtils.isEmpty(newPasswordDetermine.getText().toString())) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt12));
                    return;
                }
                if (newPasswordDetermine.getText().toString().length() < 6) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt13));
                    return;
                }
                if (!newPasswordDetermine.getText().toString().equals(mNewPassword)) {
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
        getModifyPayPassword();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getModifyPayPassword();
    }

    @Override
    public void getModifyPayPasswordTos(BeanBean beanBean) {
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt8));
        finish();
    }
}
