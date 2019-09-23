package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.MemberListBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.MemberListContract;
import com.ergou.hailiao.mvp.homepresenter.MemberListPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.adapter.MemberListAdapter;
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
 * 成员列表
 */
public class MemberListActivity extends BaseActivity<MemberListPerson>
        implements MemberListContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.img_list)
    RecyclerView imgList;
    @BindView(R.id.title_right_text)
    TextView titleRightText;
    @BindView(R.id.title_right_rl)
    RelativeLayout titleRightRl;
    @BindView(R.id.group_chat_name)
    TextView groupChatName;
    @BindView(R.id.group_chat_number)
    TextView groupChatNumber;

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private String targetId = "";
    private String title = "";
    private String targetIdType = "";
    private String typeXML = "1";//1：群组；2：聊天室

    private MemberListAdapter adapter;
    private List<MemberListBean.ListsBean> listsBean = new ArrayList<>();

    @Override
    protected void initInject() {
        getActivityComponent().inject(MemberListActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_member_list;
    }

    @Override
    protected void initEventAndData() {
        sv.smoothScrollTo(0, 0);
        titleShare.setText(getResources().getText(R.string.member_list));
        titleRightText.setVisibility(View.VISIBLE);
        titleRightRl.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        targetId = intent.getStringExtra("targetId");//
        title = intent.getStringExtra("title");//
        targetIdType = intent.getStringExtra("targetIdType");//
        if (targetIdType.equals("GROUP")) {//1：群组；2：聊天室
            typeXML.equals("1");
        } else {
            typeXML.equals("2");
        }
        groupChatName.setText(title);
        groupChatNumber.setText(targetId);
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

    public void getMemberList() {//获取成员列表
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
        map.put("group_id", targetId);
        map.put("type", typeXML);

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
        mPresenter.getMemberListBean(requestBody);
    }

    @Override
    public void showError() {
        getMemberList();
    }

    @Override
    public void timeShowError() {
        getMemberList();
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
        getMemberList();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getMemberList();
    }

    @Override
    public void getMemberListTos(MemberListBean memberListBean) {
        titleRightText.setText(memberListBean.getTotal());
        if (memberListBean.getLists().size() != 0) {
            listsBean.clear();
            listsBean.addAll(memberListBean.getLists());
            imgList.setLayoutManager(new GridLayoutManager(this, 5));
            adapter = new MemberListAdapter(this, listsBean);
            imgList.setAdapter(adapter);

//            adapter.setOnItemClickListener(new ModifyHeadImgAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(String url) {
//                    portrait = url;
//                    networkType = "2";
//                    getTimeStamp();
//                }
//            });
        }
    }
}
