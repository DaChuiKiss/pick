package com.ergou.hailiao.mvp.ui.fragment;


import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseFragment;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.DialogueFContract;
import com.ergou.hailiao.mvp.homepresenter.DialogueFPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 对话
 */
public class DialogueFragment extends BaseFragment<DialogueFPerson>
        implements DialogueFContract.MainView, RongIM.UserInfoProvider {

    @BindView(R.id.rc_viewpager)
    ViewPager mViewPager;

    private UserInfo userInfo;
    private String mUserId = "";//
    private RongYunInfoBean mRongYunInfoBean = new RongYunInfoBean();
    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    /**
     * Fragment的数据适配器
     */
    private FragmentPagerAdapter mFragmentPagerAdapter;
    /**
     * ViewPager中的数据
     */
    private List<Fragment> mFragmentList;

    private Fragment mConversationListFragment;//会话列表的fragment 的声明

    @Override
    protected void initInject() {
        getFragmentComponent().inject(DialogueFragment.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dialogue;
    }

    @Override
    protected void initEventAndData() {
        mConversationListFragment = initConversationListFragment();
        initView();
    }

    private void initView() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.clear();
        mFragmentList.add(mConversationListFragment);

        mFragmentPagerAdapter = new FragmentPagerAdapter(
                getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragmentList.get(i);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
    }


    /**
     * 封装的代码加载融云的会话列表的 fragment
     *
     * @return
     */
    private Fragment initConversationListFragment() {
//        FragmentManager fragmentManage = getActivity().getSupportFragmentManager();
//        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        mUserId = SPUtilsData.getUserId();
        msgMsg1 = new Message();
        msgMsg1.what = 0;
        mHandler1.sendMessage(msgMsg1);

        RongIM.setUserInfoProvider(this, true);
        ConversationListFragment fragement = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragement.setUri(uri);
        return fragement;
    }

    public void getSignIn() {//
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("user_id", mUserId);//


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

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {

    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {

    }

    @Override
    public void getInfoTos(RongYunInfoBean rongYunInfo) {
        mRongYunInfoBean = rongYunInfo;
        userInfo = new UserInfo(mUserId, mRongYunInfoBean.getNick_name(), Uri.parse(mRongYunInfoBean.getUser_header_img()));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        if (mUserId.equals(userId)) {
            userInfo = new UserInfo(userId, SPUtilsData.getNickName(), Uri.parse(SPUtilsData.getUserHeaderImg()));
            RongIM.getInstance().refreshUserInfoCache(userInfo);
            return userInfo;
        } else {
            mUserId = userId;
            getSignIn();
            return userInfo;
        }
    }

    private Message msgMsg1;
    private Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {
            //
            switch (msg.what) {
                case 0:
                    getSignIn();
                    break;

            }
        }

        ;
    };



}
