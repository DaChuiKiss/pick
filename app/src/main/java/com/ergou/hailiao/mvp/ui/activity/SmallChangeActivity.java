package com.ergou.hailiao.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.SmallChangeBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.SmallChangeContract;
import com.ergou.hailiao.mvp.homepresenter.SmallChangePerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by LuoCY on 2019/8/10.
 * <p>
 * 零钱
 */
public class SmallChangeActivity extends BaseActivity<SmallChangePerson>
        implements SmallChangeContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.small_change)
    TextView smallChange;//零钱

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    @Override
    protected void initInject() {
        getActivityComponent().inject(SmallChangeActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_small_change;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.small_change));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTimeStamp();
    }

    public void getTimeStamp() {//获取服务器时间

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getSmallChange() {//零钱
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
        mPresenter.getSmallChangeBean(requestBody);
    }


    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        getSmallChange();
    }


    @OnClick({R.id.fallback, R.id.recharge, R.id.cash_withdrawal,
            R.id.recharge_record_rl, R.id.transfer_accounts_record_rl,
            R.id.cash_withdrawal_record_rl, R.id.red_envelopes_record_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.recharge://充值
                break;
            case R.id.cash_withdrawal://提现
                break;
            case R.id.recharge_record_rl://充值记录
                break;
            case R.id.transfer_accounts_record_rl://转账记录
                break;
            case R.id.cash_withdrawal_record_rl://提现记录
                break;
            case R.id.red_envelopes_record_rl://红包记录
                break;
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        getSmallChange();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getSmallChange();
    }

    @Override
    public void getSmallChangeTos(SmallChangeBean smallChangeBean) {
        smallChange.setText(smallChangeBean.getAsset());//零钱
    }
}
