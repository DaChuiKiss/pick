package com.ergou.hailiao.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.RedEnvelopesStatisticsBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopesStatisticsContract;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopesStatisticsPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 红包统计
 */
public class RedEnvelopesStatisticsActivity extends BaseActivity<RedEnvelopesStatisticsPerson>
        implements RedEnvelopesStatisticsContract.MainView {
    @BindView(R.id.text_cen)
    TextView textCen;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.number)
    TextView number;
    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";
    private String typeText = "2";//（1:发包；2:抢包）

    private PopupWindow popupWindow;

    @Override
    protected void initInject() {
        getActivityComponent().inject(RedEnvelopesStatisticsActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_red_envelopes_statistics;
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
        if (typeText.equals("2")) {
            textCen.setText(getResources().getText(R.string.prompt52));
        }
        getTimeStamp();
    }

    public void getTimeStamp() {//获取服务器时间
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getRedEnvelopesStatistics() {//
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
        map.put("type", typeText);//（1:发包；2:抢包）

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
        mPresenter.getRedEnvelopesStatisticsBean(requestBody);
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        getRedEnvelopesStatistics();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        getRedEnvelopesStatistics();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getRedEnvelopesStatistics();
    }

    @Override
    public void getRedEnvelopesStatisticsTos(RedEnvelopesStatisticsBean redEnvelopesStatisticsBean) {
        if (typeText.equals("2")) {
            textCen.setText(getResources().getText(R.string.prompt52));
            number.setText("一共抢到" + redEnvelopesStatisticsBean.getNum() + "个红包");
        } else {
            textCen.setText(getResources().getText(R.string.prompt53));
            number.setText("一共发出" + redEnvelopesStatisticsBean.getNum() + "个红包");
        }
        GlideManager.loadImageView(mContext, redEnvelopesStatisticsBean.getHeader(),
                headImg, R.mipmap.ic_launcher);//头像
        name.setText(redEnvelopesStatisticsBean.getNick_name());
        money.setText(redEnvelopesStatisticsBean.getMoney() + getResources().getText(R.string.cny_ch));

    }

    @OnClick({R.id.fallback, R.id.text_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.text_right:
                textRightWindow(view);
                break;
        }
    }

    public void textRightWindow(View view) {//红包状态
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_bottom, null);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setContentView(contentView);
        //3、获取屏幕的默认分辨率
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        popupWindow.setWidth(width - 40);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
// 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });

        TextView top = (TextView) contentView.findViewById(R.id.top);//上
        TextView center = (TextView) contentView.findViewById(R.id.center);//中
        TextView bottom = (TextView) contentView.findViewById(R.id.bottom);//下
        top.setText(getResources().getText(R.string.prompt53));
        center.setText(getResources().getText(R.string.prompt52));
        bottom.setText(getResources().getText(R.string.cancel));
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//上
                popupWindow.dismiss();
                typeText = "1";//（1:发包；2:抢包）
                getTimeStamp();
            }
        });
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//中
                popupWindow.dismiss();
                typeText = "2";//（1:发包；2:抢包）
                getTimeStamp();
            }
        });
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//关闭
                popupWindow.dismiss();
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.popupwindow_anim);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 20);
    }

}
