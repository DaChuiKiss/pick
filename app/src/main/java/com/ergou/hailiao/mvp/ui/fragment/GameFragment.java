package com.ergou.hailiao.mvp.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseFragment;
import com.ergou.hailiao.mvp.bean.GameBean;
import com.ergou.hailiao.mvp.bean.GameMarqueelBean;
import com.ergou.hailiao.mvp.bean.LunBoBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.GameContract;
import com.ergou.hailiao.mvp.homepresenter.GamePerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.adapter.GameAdapter;
import com.ergou.hailiao.mvp.ui.adapter.PictureCarouselLoopAdapter;
import com.ergou.hailiao.mvp.ui.adapter.recycleradapter.OnRcvScrollListener;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.view.XMarqueeView;
import com.ergou.hailiao.view.XMarqueeViewAdapter;
import com.ergou.hailiao.widget.recyclerview.multitype.Items;
import com.ergou.hailiao.widget.recyclerview.multitype.MultiTypeAdapter;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * * 游戏——Fragmen
 */
public class GameFragment extends BaseFragment<GamePerson>
        implements GameContract.MainView {
    @BindView(R.id.rollPagerView)
    RollPagerView rollPagerView;
    @BindView(R.id.whether)
    TextView whether;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.marquee1)
    XMarqueeView mMarquee1;

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
    private String[] imgs;
    private List<String> imgDate = new ArrayList<>();//广告
    private List<LunBoBean> lunBoBeanList = new ArrayList<>();
    private List<GameMarqueelBean> gameMarqueelBeanList = new ArrayList<>();
    private XmarQueeAdapter xmarQueeAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(GameFragment.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {//判断在不在此页面
        super.onHiddenChanged(hidden);
        if (hidden) {
            //结束
        } else {
            init();
        }
    }

    private void init() {
        items = new Items();
        items.clear();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        multiTypeAdapter = new MultiTypeAdapter(items);
        GameAdapter gameAdapter = new GameAdapter();
        multiTypeAdapter.register(GameBean.GroupBean.class, gameAdapter);
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
//                refresh.setRefreshing(true);
                getTimeStamp();
                getLunBo();
                getGameMarqueel();
            }
        });
    }

    public void getTimeStamp() {//获取服务器时间
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getGame() {//获取群列表
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
        mPresenter.getGameBean(requestBody);
    }

    public void getLunBo() {//获取广告列表
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
        mPresenter.getLunBoBean(requestBody);
    }


    public void getGameMarqueel() {//获取消息列表
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
        mPresenter.getGameMarqueelBean(requestBody);
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
        getGame();
    }

    @Override
    public void onError(Throwable throwable) {
        refresh.setRefreshing(false);
    }

    @Override
    public void timeOnError(Throwable throwable) {
        getGame();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getGame();
    }

    @Override
    public void getGameTos(GameBean gameBeans) {
        refresh.setRefreshing(false); // 关闭动画也就是圈圈消失
        items.clear();
        if (gameBeans.getGroup().size() != 0) {
            whether.setVisibility(View.GONE);
            items.addAll(gameBeans.getGroup());
            multiTypeAdapter.notifyDataSetChanged();
        } else {
            whether.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getLunBoTos(List<LunBoBean> lunBoBeans) {
        imgDate.clear();
        lunBoBeanList.clear();
        if (lunBoBeans.size() != 0) {
            lunBoBeanList.addAll(lunBoBeans);
            for (int i = 0; i < lunBoBeanList.size(); i++) {
                imgDate.add(lunBoBeanList.get(i).getImages());
            }
        }
        if (imgDate.size() != 0) {
            imgs = imgDate.toArray(new String[imgDate.size()]);
        }
        if (imgs.length != 0) {
            rollPagerView.setPlayDelay(3000);
            rollPagerView.setAdapter(new PictureCarouselLoopAdapter(rollPagerView, imgs, getActivity()));
        }
    }

    @Override
    public void getGameMarqueelTos(List<GameMarqueelBean> gameMarqueelBeans) {
        gameMarqueelBeanList.clear();
        if (gameMarqueelBeans.size() > 0) {//滚动广告条
            gameMarqueelBeanList.addAll(gameMarqueelBeans);
            if (xmarQueeAdapter==null){
                xmarQueeAdapter = new XmarQueeAdapter(gameMarqueelBeanList);
                mMarquee1.setAdapter(xmarQueeAdapter);
            }else {
                xmarQueeAdapter = new XmarQueeAdapter(gameMarqueelBeanList);
            }
            xmarQueeAdapter.notifyDataChanged();
        }
    }

    /**
     * 公告轮播Adapter
     */
    class XmarQueeAdapter extends XMarqueeViewAdapter<GameMarqueelBean> {
        private List<GameMarqueelBean> gameMBeanList = new ArrayList<>();

        public XmarQueeAdapter(List<GameMarqueelBean> listString) {
            super(listString);
            this.gameMBeanList = listString;
        }


        @Override
        public View onCreateView(XMarqueeView parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_marqueel, null);
        }

        @Override
        public void onBindView(View parent, View view, int position) {
            TextView notice = view.findViewById(R.id.tvNotice);
            GameMarqueelBean gameMarqueelBean = gameMBeanList.get(position);
            notice.setText(gameMarqueelBean.getMessage());
        }
    }

}
