package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.MemberRobBean;
import com.ergou.hailiao.mvp.bean.RedEnvelopeGrabBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.FuLiGrabContract;
import com.ergou.hailiao.mvp.homepresenter.FuLiGrabPerson;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopeGrabContract;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopeGrabPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.adapter.FuLiGrabAdapter;
import com.ergou.hailiao.mvp.ui.adapter.RedEnvelopeGrabAdapter;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnRcvScrollListener;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.widget.recyclerview.multitype.Items;
import com.ergou.hailiao.widget.recyclerview.multitype.MultiTypeAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 福利红包
 *
 * */

public class FuLiGrabActivity extends BaseActivity<FuLiGrabPerson>
        implements FuLiGrabContract.MainView {

    @BindView(R.id.name)
    TextView name;//你猜
    @BindView(R.id.money)
    TextView money;//红包
    @BindView(R.id.money_number)
    TextView moneyNumber;//红包个数
    @BindView(R.id.time)
    TextView time;//时间
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private String order_id = "";
    private String dataType = "";//进入状态

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";
    private MultiTypeAdapter multiTypeAdapter;
    private Items items;

    private MemberRobBean memberRobBean = new MemberRobBean();
    private RedEnvelopeGrabBean.SendBean sendBean = new RedEnvelopeGrabBean.SendBean();

    @Override
    protected void initInject() {
        getActivityComponent().inject(FuLiGrabActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_fuli_grab;
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
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");//
        dataType = intent.getStringExtra("dataType");//
        init();
    }

    private void init() {
        items = new Items();
        items.clear();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        multiTypeAdapter = new MultiTypeAdapter(items);
        FuLiGrabAdapter fuLiGrabAdapter = new FuLiGrabAdapter();
        multiTypeAdapter.register(RedEnvelopeGrabBean.AllRobBean.class, fuLiGrabAdapter);
        recyclerview.setAdapter(multiTypeAdapter);
        refresh.setColorSchemeResources(R.color.colorBlue, R.color.colorBlue, R.color.colorBlue); // 设置圈圈转的颜色
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {  // 调用下拉刷新操作
                items.clear();
                refresh.setRefreshing(true);
                multiTypeAdapter.notifyDataSetChanged();
                dataType = "chakan";
                getTimeStamp();
            }
        });
        recyclerview.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onLoadMore() {  // 调用上拉加载操作
//                if (refreshBoolean) {
//                    getTimeStamp();
//                }
            }
        });
        refresh.post(new Runnable() {
            @Override
            public void run() {
//                refreshBoolean = false;
//                refresh.setRefreshing(true);
                getTimeStamp();
            }
        });
    }

    public void getTimeStamp() {//获取服务器时间
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getRedEnvelopeGrab() {//查看大家手气
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("order_id", order_id);//唯一订单号
        map.put("mobile", SPUtilsData.getPhoneNumber());
        map.put("type", "2");//（1:踩雷红包；2:福利红包）

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
        mPresenter.getRedEnvelopeGrabBean(requestBody);
    }

    public void getOpen() {//开
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("order_id", order_id);//唯一订单号
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
        mPresenter.getOpenBean(requestBody);
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        if (dataType.equals("kai")) {
            getOpen();
        } else {
            getRedEnvelopeGrab();
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

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        if (dataType.equals("kai")) {
            getOpen();
        } else {
            getRedEnvelopeGrab();
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (dataType.equals("kai")) {
            getOpen();
        } else {
            getRedEnvelopeGrab();
        }
    }

    @Override
    public void getRedEnvelopeGrabTos(RedEnvelopeGrabBean redEnvelopeGrab) {//查看大家手气
        memberRobBean = redEnvelopeGrab.getMemberRob();
        sendBean = redEnvelopeGrab.getSend();
        dataOK();
        refresh.setRefreshing(false); // 关闭动画也就是圈圈消失
        items.clear();
        if (redEnvelopeGrab.getAllRob().size() != 0) {
            items.addAll(redEnvelopeGrab.getAllRob());
            multiTypeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getOpenTos(RedEnvelopeGrabBean redEnvelopeGrab) {//开
        memberRobBean = redEnvelopeGrab.getMemberRob();
        sendBean = redEnvelopeGrab.getSend();
        dataOK();
        refresh.setRefreshing(false); // 关闭动画也就是圈圈消失
        items.clear();
        if (redEnvelopeGrab.getAllRob().size() != 0) {
            items.addAll(redEnvelopeGrab.getAllRob());
            multiTypeAdapter.notifyDataSetChanged();
        }
    }

    public void dataOK() {
        money.setText(sendBean.getMoney());//总金额
        moneyNumber.setText(sendBean.getNum());//总包数
        if (StringUtils.isEmpty(sendBean.getDone_rob())){
            time.setVisibility(View.GONE);
        }else {
            time.setText(sendBean.getDone_rob());//发包时间
            time.setVisibility(View.VISIBLE);
        }

    }
}
