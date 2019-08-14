package com.ergou.hailiao.mvp.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.BeanBean;
import com.ergou.hailiao.mvp.bean.HeadImgBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.ModifyHeadImContract;
import com.ergou.hailiao.mvp.homepresenter.ModifyHeadImPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.adapter.ModifyHeadImgAdapter;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.UserInfoSPUtils;
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
 * 修改头像
 */
public class ModifyHeadImgActivity extends BaseActivity<ModifyHeadImPerson>
        implements ModifyHeadImContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.img_list)
    RecyclerView imgList;

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private String networkType = "1";//请求接口状态（1：获取头像列表；2：修改头像）
    private String portrait = "";//头像地址
    private ModifyHeadImgAdapter adapter;
    private List<HeadImgBean> mHeadImgBeanList = new ArrayList<>();

    @Override
    protected void initInject() {
        getActivityComponent().inject(ModifyHeadImgActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_modify_head_img;
    }

    @Override
    protected void initEventAndData() {
        sv.smoothScrollTo(0, 0);
        titleShare.setText(getResources().getText(R.string.modify_head_img));
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

    public void getHeadImg() {//获取头像列表
        networkType = "1";
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
        mPresenter.getHeadImgBean(requestBody);
    }

    public void getModifyHeadImg() {//修改头像
        ApiInterface.showPro(mContext);
        networkType = "1";
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
        map.put("nick_name", SPUtilsData.getNickName());
        map.put("portrait", portrait);

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
        mPresenter.getModifyHeadImgBean(requestBody);
    }


    @Override
    public void showError() {
    }

    @Override
    public void timeShowError() {
        if (networkType.equals("1")) {
            getHeadImg();
        }
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void timeOnError(Throwable throwable) {
        if (networkType.equals("1")) {
            getHeadImg();
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (networkType.equals("1")) {
            getHeadImg();
        }
    }

    @Override
    public void getHeadImgTos(List<HeadImgBean> headImgBeanList) {
        if (headImgBeanList.size() != 0) {
            mHeadImgBeanList.clear();
            mHeadImgBeanList.addAll(headImgBeanList);
            imgList.setLayoutManager(new GridLayoutManager(this, 4));
            adapter = new ModifyHeadImgAdapter(this, mHeadImgBeanList);
            imgList.setAdapter(adapter);

            adapter.setOnItemClickListener(new ModifyHeadImgAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String url) {
                    portrait = url;
                    getModifyHeadImg();
                }
            });
        }
    }

    @Override
    public void getModifyHeadImgTos(BeanBean beanBean) {
        ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt8));
        UserInfoSPUtils.getInstance().put("user_header_img", portrait);//头像
        finish();
    }

    @OnClick({R.id.fallback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;

        }
    }
}
