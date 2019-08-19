package com.ergou.hailiao.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseFragment;
import com.ergou.hailiao.mvp.bean.MailListBean;
import com.ergou.hailiao.mvp.bean.SearchMailListBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.MailListContract;
import com.ergou.hailiao.mvp.homepresenter.MailListPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.activity.PersonalCenterActivity;
import com.ergou.hailiao.mvp.ui.activity.SearchMailListActivity;
import com.ergou.hailiao.mvp.ui.adapter.MailListAdapter;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnRcvScrollListener;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;
import com.ergou.hailiao.widget.recyclerview.multitype.Items;
import com.ergou.hailiao.widget.recyclerview.multitype.MultiTypeAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * * 通讯录——Fragmen
 */
public class MailListFragment extends BaseFragment<MailListPerson>
        implements MailListContract.MainView {
    @BindView(R.id.whether)
    TextView whether;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private MultiTypeAdapter multiTypeAdapter;
    private Items items;
    private String timeType = "1";

    private Intent intent;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(MailListFragment.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mail_list;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        items = new Items();
        items.clear();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        multiTypeAdapter = new MultiTypeAdapter(items);
        MailListAdapter mailListAdapter = new MailListAdapter();
        multiTypeAdapter.register(MailListBean.class, mailListAdapter);
        recyclerview.setAdapter(multiTypeAdapter);
        refresh.setColorSchemeResources(R.color.colorBlue, R.color.colorBlue, R.color.colorBlue); // 设置圈圈转的颜色
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {  // 调用下拉刷新操作
                items.clear();
                refresh.setRefreshing(true);
//                whether.setVisibility(View.VISIBLE);
                multiTypeAdapter.notifyDataSetChanged();
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
                refresh.setRefreshing(true);
                getMailList();
            }
        });
    }

    public void getTimeStamp() {//获取服务器时间
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getMailList() {//获取通讯录列表
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
        mPresenter.getMailListBean(requestBody);
    }


    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void showError() {
        refresh.setRefreshing(false);
    }

    @Override
    public void timeShowError() {
        if (timeType.equals("1")) {//第一次进入页面获取数据
            init();
        } else {//下拉刷新、上拉加载数据
            getMailList();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        refresh.setRefreshing(false);
    }

    @Override
    public void timeOnError(Throwable throwable) {
        if (timeType.equals("1")) {//第一次进入页面获取数据
            init();
        } else {//下拉刷新、上拉加载数据
            getMailList();
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (timeType.equals("1")) {//第一次进入页面获取数据
            init();
        } else {//下拉刷新、上拉加载数据
            getMailList();
        }
    }

    @Override
    public void getMailListTos(List<MailListBean> mailListBeanList) {
        refresh.setRefreshing(false); // 关闭动画也就是圈圈消失
        items.clear();
        if (mailListBeanList.size() != 0) {
            whether.setVisibility(View.GONE);
            items.addAll(mailListBeanList);
            multiTypeAdapter.notifyDataSetChanged();
        } else {
            whether.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.phone_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_id://搜索
                intent = new Intent();
                intent.setClass(mContext, SearchMailListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
