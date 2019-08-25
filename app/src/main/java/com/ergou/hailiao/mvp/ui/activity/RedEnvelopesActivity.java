package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.LeiHaoBean;
import com.ergou.hailiao.mvp.bean.SmallChangeBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopesContract;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopesPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 红包
 */
public class RedEnvelopesActivity extends BaseActivity<RedEnvelopesPerson>
        implements RedEnvelopesContract.MainView {

    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.current_balance)
    TextView currentBalance;//当前余额
    @BindView(R.id.nine_individual)
    TextView nineIndividual;//9个
    @BindView(R.id.seven_individual)
    TextView sevenIndividual;//7个
    @BindView(R.id.red_envelopes_number_bottom)
    TextView redEnvelopesNumberBottom;//红包金额
    @BindView(R.id.one)
    TextView one;//1
    @BindView(R.id.two)
    TextView two;//2
    @BindView(R.id.three)
    TextView three;//3
    @BindView(R.id.zero)
    TextView zero;//0
    @BindView(R.id.four)
    TextView four;//4
    @BindView(R.id.five)
    TextView five;//5
    @BindView(R.id.six)
    TextView six;//6
    @BindView(R.id.seven)
    TextView seven;//7
    @BindView(R.id.eight)
    TextView eight;//8
    @BindView(R.id.nine)
    TextView nine;//9
    @BindView(R.id.red_envelopes_number)
    EditText redEnvelopesNumber;//红包金额
    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private Intent intent;
    private SmallChangeBean smallCBean = new SmallChangeBean();
    private String networkType = "1";//请求接口状态（1：获取零钱；2：红包）
    private String individual = "7";//红包个数（7：7个；9：9个）
    private List<LeiHaoBean> leiHaoBeans = new ArrayList<>();
    private LeiHaoBean leiHaoBean;
    private int leiInt = 0;//是否选择两个雷号
    private int leiHaoInt = 0;//选择哪个雷号

    @Override
    protected void initInject() {
        getActivityComponent().inject(RedEnvelopesActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_red_envelopes;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.red_envelopes));
        leiHaoBeans.clear();
        for (int i = 0; i < 10; i++) {
            leiHaoBean = new LeiHaoBean();
            leiHaoBean.setBoolean(false);
            leiHaoBeans.add(leiHaoBean);
        }
        //红包--输入监听
        redEnvelopesNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int i2) {

                if (s == null || s.length() == 0) {
                    redEnvelopesNumberBottom.setText("0.00");
                    return;
                } else {
                    redEnvelopesNumberBottom.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
        if (networkType.equals("1")) {
            getSmallChange();
        } else {
//            getZhuanZhang();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        if (networkType.equals("1")) {
            getSmallChange();
        } else {
//            getZhuanZhang();
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (networkType.equals("1")) {
            getSmallChange();
        } else {
//            getZhuanZhang();
        }
    }

    @Override
    public void getSmallChangeTos(SmallChangeBean smallChangeBean) {
        currentBalance.setText(smallChangeBean.getAsset());
    }

    @OnClick({R.id.fallback, R.id.view_rules, R.id.seven_individual_ll, R.id.nine_individual_ll,
            R.id.one_ll, R.id.two_ll, R.id.three_ll, R.id.zero_ll, R.id.four_ll, R.id.five_ll,
            R.id.six_ll, R.id.seven_ll, R.id.eight_ll, R.id.nine_ll, R.id.stuffed_red_envelopes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.view_rules://查看规则
                break;
            case R.id.seven_individual_ll://7个
                individual = "7";//红包个数
                sevenIndividual.setBackgroundResource(R.drawable.red_envelopes_y);
                nineIndividual.setBackgroundResource(R.drawable.red_envelopes_n);
                break;
            case R.id.nine_individual_ll://9个
                individual = "9";//红包个数
                sevenIndividual.setBackgroundResource(R.drawable.red_envelopes_n);
                nineIndividual.setBackgroundResource(R.drawable.red_envelopes_y);
                break;
            case R.id.one_ll://1
                if (leiHaoBeans.get(1).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 1;
                LeiHao();
                break;
            case R.id.two_ll://2
                if (leiHaoBeans.get(2).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 2;
                LeiHao();
                break;
            case R.id.three_ll://3
                if (leiHaoBeans.get(3).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 3;
                LeiHao();
                break;
            case R.id.zero_ll://0
                if (leiHaoBeans.get(0).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 0;
                LeiHao();
                break;
            case R.id.four_ll://4
                if (leiHaoBeans.get(4).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 4;
                LeiHao();
                break;
            case R.id.five_ll://5
                if (leiHaoBeans.get(5).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 5;
                LeiHao();
                break;
            case R.id.six_ll://6
                if (leiHaoBeans.get(6).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 6;
                LeiHao();
                break;
            case R.id.seven_ll://7
                if (leiHaoBeans.get(7).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 7;
                LeiHao();
                break;
            case R.id.eight_ll://8
                if (leiHaoBeans.get(8).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 8;
                LeiHao();
                break;
            case R.id.nine_ll://9
                if (leiHaoBeans.get(9).isBoolean()) {
                    leiInt = leiInt - 1;
                } else {
                    leiInt = leiInt + 1;
                }
                leiHaoInt = 9;
                LeiHao();
                break;
            case R.id.stuffed_red_envelopes://塞进红包
                break;
        }
    }

    public void LeiHao() {//雷号
        if (leiInt > 2) {
            leiInt = 2;
            leiHaoBeans.get(leiHaoInt).setBoolean(false);
            ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt36));
            return;
        } else {
            if (leiHaoInt == 0) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    zero.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    zero.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 1) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    one.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    one.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 2) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    two.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    two.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 3) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    three.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    three.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 4) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    four.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    four.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 5) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    five.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    five.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 6) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    six.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    six.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 7) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    seven.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    seven.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 8) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    eight.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    eight.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            } else if (leiHaoInt == 9) {
                if (leiHaoBeans.get(leiHaoInt).isBoolean()) {
                    leiHaoBeans.get(leiHaoInt).setBoolean(false);
                    nine.setBackgroundResource(R.drawable.red_envelopes_n);
                } else {
                    leiHaoBeans.get(leiHaoInt).setBoolean(true);
                    nine.setBackgroundResource(R.drawable.red_envelopes_y);
                }
            }
        }
    }

}
