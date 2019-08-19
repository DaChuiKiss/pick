package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.BankInformationBean;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.CashWithdrawlContract;
import com.ergou.hailiao.mvp.homepresenter.CashWithdrawlPerson;
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
 * 充值记录
 */
public class CashWithdrawalActivity extends BaseActivity<CashWithdrawlPerson>
        implements CashWithdrawlContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.bank_card_number)
    EditText bankCardNumber;//银行卡号
    @BindView(R.id.real_name)
    EditText realName;//真实姓名
    @BindView(R.id.opening_bank)
    EditText openingBank;//开户银行
    @BindView(R.id.phone_number)
    EditText phoneNumber;//手机号码
    @BindView(R.id.save_information)
    TextView saveInformation;//保存信息
    @BindView(R.id.exchange_amount)
    EditText exchangeAmount;//兑换数额
    @BindView(R.id.available_balance)
    TextView availableBalance;//可用余额

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private String networkType = "1";//请求接口状态（(1:获取银行信息;2:保存、修改信息;3：确认提现)）
    private String mRealName = "";//真实姓名
    private String mBankCardNumber = "";//银行卡号
    private String mOpeningBank = "";//开户银行
    private String mPhoneNumber = "";//手机号
    private String mExchangeAmount = "";//兑换数额
    private String asset = "";//余额

    private BankInformationBean bankIBean = new BankInformationBean();

    @Override
    protected void initInject() {
        getActivityComponent().inject(CashWithdrawalActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cash_withdrawal;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.bank_card_withdrawal));
        phoneNumber.setText(SPUtilsData.getPhoneNumber());
        Intent intent = getIntent();
        asset = intent.getStringExtra("asset");//余额
        availableBalance.setText(asset);
        getTimeStamp();
    }

    public void getTimeStamp() {//获取服务器时间

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    /**
     * 获取银行信息
     */
    public void getBankInformation() {
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
        mPresenter.getBankInformationBean(requestBody);
    }

    public void getModifyBankInformation() {//保存、修改银行信息
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
        map.put("mobile", SPUtilsData.getPhoneNumber());//用户手机号
        map.put("card", mBankCardNumber);//银行卡
        map.put("true_username", mRealName);//账户名
        map.put("tel", mPhoneNumber);//开户手机号
        map.put("open_bank", mOpeningBank);//开户行地址

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
        mPresenter.getModifyBankInformationBean(requestBody);
    }

    public void getWithdrawal() {//申请提现
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
        map.put("mobile", SPUtilsData.getPhoneNumber());//用户手机号
        map.put("amount", mExchangeAmount);//金额

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
        mPresenter.getWithdrawalBean(requestBody);
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        if (networkType.equals("1")) {
            getBankInformation();//银行信息
        } else if (networkType.equals("2")) {
            getModifyBankInformation();//修改、保存银行信息
        } else {
            getWithdrawal();//申请提现
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        if (networkType.equals("1")) {
            getBankInformation();//银行信息
        } else if (networkType.equals("2")) {
            getModifyBankInformation();//修改、保存银行信息
        } else {
            getWithdrawal();//申请提现
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (networkType.equals("1")) {
            getBankInformation();//银行信息
        } else if (networkType.equals("2")) {
            getModifyBankInformation();//修改、保存银行信息
        } else {
            getWithdrawal();//申请提现
        }
    }

    @Override
    public void getBankInformationTos(BankInformationBean bankInformationBean) {//银行信息
        bankIBean = bankInformationBean;
        if (StringUtils.isEmpty(bankIBean.getBank_card())) {
            saveInformation.setBackground(getResources().getDrawable(R.drawable.backpround_corners5_32cd32));
            saveInformation.setText(R.string.save_information);
        } else {
            saveInformation.setBackground(getResources().getDrawable(R.drawable.backpround_corners5_fd7d01));
            saveInformation.setText(R.string.modify_information);
            phoneNumber.setText(bankIBean.getBank_tel());//手机号码
        }
        saveInformation.setVisibility(View.VISIBLE);
        bankCardNumber.setText(bankIBean.getBank_card());//银行卡号
        realName.setText(bankIBean.getBank_user());//真实姓名
        openingBank.setText(bankIBean.getBank_where_is());//开户银行
    }

    @Override
    public void getModifyBankInformationTos(BankInformationBean bankInformationBean) {//保存、修改银行信息
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt8));
        saveInformation.setBackground(getResources().getDrawable(R.drawable.backpround_corners5_fd7d01));
        saveInformation.setText(R.string.modify_information);
        bankCardNumber.setText(bankInformationBean.getBank_card());//银行卡号
        realName.setText(bankInformationBean.getBank_user());//真实姓名
        openingBank.setText(bankInformationBean.getBank_where_is());//开户银行
        phoneNumber.setText(bankInformationBean.getBank_tel());//手机号码
    }

    @Override
    public void getWithdrawalTos(BeanBean beanBean) {
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt8));
        finish();
    }


    @OnClick({R.id.fallback, R.id.save_information, R.id.confirmation_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.save_information://修改、保存信息
                mBankCardNumber = bankCardNumber.getText().toString();//银行卡号
                mRealName = realName.getText().toString();//真实姓名
                mOpeningBank = openingBank.getText().toString();//开户银行
                mPhoneNumber = phoneNumber.getText().toString();//手机号码
                if (StringUtils.isEmpty(mBankCardNumber)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt24));
                    return;
                }
                if (StringUtils.isEmpty(mRealName)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt25));
                    return;
                }
                if (StringUtils.isEmpty(mOpeningBank)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt26));
                    return;
                }
                if (StringUtils.isEmpty(mPhoneNumber)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt15));
                    return;
                }
                networkType = "2";
                getTimeStamp();
                break;
            case R.id.confirmation_withdrawal://确认提现
                if (StringUtils.isEmpty(bankIBean.getBank_card())) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt28));
                    return;
                }
                mExchangeAmount = exchangeAmount.getText().toString();//兑换数额
                if (StringUtils.isEmpty(mExchangeAmount)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt27));
                    return;
                }
                networkType = "3";
                getTimeStamp();
                break;
        }
    }
}
