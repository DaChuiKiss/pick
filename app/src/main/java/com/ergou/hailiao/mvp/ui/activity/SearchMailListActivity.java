package com.ergou.hailiao.mvp.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.SearchMailListBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.SearchMailListContract;
import com.ergou.hailiao.mvp.homepresenter.SearchMailListPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.adapter.SearchMailListAdapter;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnRcvScrollListener;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.StringUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.widget.recyclerview.multitype.Items;
import com.ergou.hailiao.widget.recyclerview.multitype.MultiTypeAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SearchMailListActivity extends BaseActivity<SearchMailListPerson>
        implements SearchMailListContract.MainView {
    @BindView(R.id.phone_id)
    EditText phoneId;//
    @BindView(R.id.whether)
    TextView whether;
    @BindView(R.id.title_share)
    TextView titleShare;
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
    private String search_number = "";

    @Override
    protected void initInject() {
        getActivityComponent().inject(SearchMailListActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_mail_list;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.search));
    }

    private void init() {
        items = new Items();
        items.clear();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        multiTypeAdapter = new MultiTypeAdapter(items);
        SearchMailListAdapter searchMailListAdapter = new SearchMailListAdapter();
        multiTypeAdapter.register(SearchMailListBean.class, searchMailListAdapter);
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
                getSearchMailList();
            }
        });
    }

    public void getTimeStamp() {//获取服务器时间
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getSearchMailList() {//获取搜索列表
        device_token = ApiInterface.deviceToken(mContext);//设备号
        version = AppUtils.getAppVersionName(mContext);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("search_data", search_number);

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
        mPresenter.getSearchMailListBean(requestBody);
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
            getSearchMailList();
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
            getSearchMailList();
        }
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        if (timeType.equals("1")) {//第一次进入页面获取数据
            init();
        } else {//下拉刷新、上拉加载数据
            getSearchMailList();
        }
    }

    @Override
    public void getSearchMailListTos(List<SearchMailListBean> searchMailListBean) {
        refresh.setRefreshing(false); // 关闭动画也就是圈圈消失
        items.clear();
        if (searchMailListBean.size() != 0) {
            whether.setVisibility(View.GONE);
            items.addAll(searchMailListBean);
            multiTypeAdapter.notifyDataSetChanged();
        } else {
            whether.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.fallback, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                finish();
                break;
            case R.id.search:
                search_number = phoneId.getText().toString();
                if (StringUtils.isEmpty(search_number)) {
                    ToastUtils.showLongToast(mContext, getResources().getText(R.string.prompt22));
                    return;
                }
                init();
                break;
        }
    }
}
