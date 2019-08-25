package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.TransferAccountsContract;
import com.ergou.hailiao.mvp.homepresenter.TransferAccountsPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by LuoCY on 2019/8/25.
 */
public class TransferAccountsActivity extends BaseActivity<TransferAccountsPerson>
        implements TransferAccountsContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.name)
    TextView name;//名字
    @BindView(R.id.head_img)
    ImageView headImg;//头像
    @BindView(R.id.zhuanzhang_number)
    EditText zhuanzhangNumber;//转账金额
    @BindView(R.id.zhuanzhang_text)
    EditText zhuanzhangText;//转账记录
    private RongYunInfoBean mRongYunInfoBean = new RongYunInfoBean();
    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";
    private String targetId = "";
    private String networkType = "1";//请求接口状态（1：获取用户信息；2：转账）
    private String mobile = "";//手机号码
    private String amount = "";//转账金额
    private String mark = "";//转账备注

    @Override
    protected void initInject() {
        getActivityComponent().inject(TransferAccountsActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_transfer_accounts;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.zhuanzhang));
        Intent intent = getIntent();
        targetId = intent.getStringExtra("targetId");//
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkType = "1";
        getTimeStamp();
    }

    public void getTimeStamp() {//获取服务器时间

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getInfo() {//获取转账用户信息
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
        map.put("user_id", targetId);

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
        mPresenter.getInfoBean(requestBody);
    }

    public void getZhuanZhang() {//获取转账
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
        map.put("mobile", mobile);//	用户手机号
        map.put("user_id", targetId);//转账收款用户融云ID
        map.put("amount", amount);//转账金额
        map.put("mark", mark);//转账备注

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
        mPresenter.getZhuanZhangBean(requestBody);
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        if (networkType.equals("1")) {
            getInfo();
        } else {
            getZhuanZhang();
        }
    }

    @Override
    public void onError(Throwable throwable) {


    }

    @Override
    public void timeOnError(Throwable throwable) {
        if (networkType.equals("1")) {
            getInfo();
        } else {
            getZhuanZhang();
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (networkType.equals("1")) {
            getInfo();
        } else {
            getZhuanZhang();
        }
    }

    @Override
    public void getInfoTos(RongYunInfoBean rongYunInfo) {
        GlideManager.loadImageView(mContext, rongYunInfo.getUser_header_img(),
                headImg, R.mipmap.ic_launcher);//头像
        name.setText(rongYunInfo.getNick_name());
    }

    @Override
    public void getZhuanZhangTos(BeanBean beanBean) {
        finish();
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt8));
    }

    @OnClick({R.id.fallback, R.id.zhuanzhang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.zhuanzhang://转账
                mobile = SPUtilsData.getPhoneNumber();
                amount = zhuanzhangNumber.getText().toString();
                mark = zhuanzhangText.getText().toString();
                if (StringUtils.isEmpty(amount)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt32));
                    return;
                }
//                if (StringUtils.isEmpty(mark)) {
//                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt33));
//                    return;
//                }
                networkType = "2";
                getTimeStamp();
                break;
        }
    }
}
