package com.ergou.hailiao.mvp.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.RechargeBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.RechargeContract;
import com.ergou.hailiao.mvp.homepresenter.RechargePerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.adapter.RechargAdapter;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 充值中心
 */
public class RechargeActivity extends BaseActivity<RechargePerson>
        implements RechargeContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.money_list)
    RecyclerView moneyList;
    @BindView(R.id.sv)
    ScrollView sv;

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private String networkType = "1";//请求接口状态（1：获取头像列表；2：修改头像）
    private String portrait = "";//头像地址
    private RechargAdapter adapter;
    private List<RechargeBean> rechargList = new ArrayList<>();

    @Override
    protected void initInject() {
        getActivityComponent().inject(RechargeActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initEventAndData() {
        sv.smoothScrollTo(0, 0);
        titleShare.setText(getResources().getText(R.string.voucher_center));
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

    public void getRecharge() {//获取可充值列表
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
        mPresenter.getRechargeBean(requestBody);
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        if (networkType.equals("1")) {
            getRecharge();
        } else {
//            getModifyHeadImg();
        }
    }

    @OnClick({R.id.fallback, R.id.immediate_recharge, R.id.line_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.immediate_recharge://立即充值
                break;
            case R.id.line_recharge://线下充值
                break;
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        if (networkType.equals("1")) {
            getRecharge();
        } else {
//            getModifyHeadImg();
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (networkType.equals("1")) {
            getRecharge();
        } else {
//            getModifyHeadImg();
        }
    }

    @Override
    public void getRechargeTos(List<RechargeBean> rechargeBeans) {
        if (rechargeBeans.size() != 0) {
            rechargList.clear();
            rechargList.addAll(rechargeBeans);
            moneyList.setLayoutManager(new GridLayoutManager(this, 3));
            adapter = new RechargAdapter(this, rechargList);
            moneyList.setAdapter(adapter);

            adapter.setOnItemClickListener(new RechargAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String url) {
                    portrait = url;
                    networkType = "2";
                    getTimeStamp();
                }
            });
        }
    }
}
