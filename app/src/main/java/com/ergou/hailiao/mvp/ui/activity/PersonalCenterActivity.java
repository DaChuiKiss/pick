package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.PersonalCenterContract;
import com.ergou.hailiao.mvp.homepresenter.PersonalCenterPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.UserInfoSPUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by LuoCY on 2019/8/10.
 * <p>
 * 个人中心
 */
public class PersonalCenterActivity extends BaseActivity<PersonalCenterPerson>
        implements PersonalCenterContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.head_img)
    ImageView headImg;//头像
    @BindView(R.id.account_number)
    TextView accountNumber;//账号
    @BindView(R.id.nick_name)
    TextView nickName;//昵称
    @BindView(R.id.real_name)
    TextView realName;//真实姓名
    @BindView(R.id.phone_number)
    TextView phoneNumber;//手机号码

    private PopupWindow popupWindow;

    private Intent intent;

    private String nameNick = "";//昵称

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    @Override
    protected void initInject() {
        getActivityComponent().inject(PersonalCenterActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
        getModifyNickName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    public void init() {
        titleShare.setText(getResources().getText(R.string.personal_center));
        GlideManager.loadImageView(mContext, SPUtilsData.getUserHeaderImg(),
                headImg, R.mipmap.ic_launcher);//头像
        nickName.setText(SPUtilsData.getNickName());//昵称
        accountNumber.setText(SPUtilsData.getUserId());//账号
        phoneNumber.setText(SPUtilsData.getPhoneNumber());//手机号码
    }

    public void getTimeStamp() {//获取服务器时间

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }


    public void getModifyNickName() {//修改昵称
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
        map.put("nick_name", nameNick);
        map.put("portrait", SPUtilsData.getUserHeaderImg());

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
        mPresenter.getModifyNickNameBean(requestBody);
    }

    @OnClick({R.id.fallback, R.id.head_img_rl, R.id.nick_name_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.head_img_rl://修改头像
                intent = new Intent();
                intent.setClass(mContext, ModifyHeadImgActivity.class);
                startActivity(intent);
                break;
            case R.id.nick_name_rl://修改昵称
                promptWindow(view);
                break;
        }
    }

    public void promptWindow(View view) {//更换设备（输入验证码）
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_modify_nick_name, null);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
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

        final EditText nick_name = (EditText) contentView.findViewById(R.id.nick_name);//昵称
        TextView cancel = (TextView) contentView.findViewById(R.id.cancel);//取消
        TextView determine = (TextView) contentView.findViewById(R.id.determine);//确定
        nick_name.setText(SPUtilsData.getNickName());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//取消
                popupWindow.dismiss();
            }
        });
        determine.setOnClickListener(new View.OnClickListener() {//确定
            @Override
            public void onClick(View view) {
                nameNick = nick_name.getText().toString();
                if (StringUtils.isEmpty(nameNick)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt9));
                    return;
                }
                getTimeStamp();
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.pop_window_anim);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        getModifyNickName();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getModifyNickName();
    }

    @Override
    public void getModifyNickNameTos(BeanBean beanBean) {
        popupWindow.dismiss();
        nickName.setText(nameNick);
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt8));
        UserInfoSPUtils.getInstance().put("nick_name", nameNick);//头像
    }
}
