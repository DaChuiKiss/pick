package com.ergou.hailiao.mvp.ui.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.PromotionalPostersBean;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.PromotionalPostersContract;
import com.ergou.hailiao.mvp.homepresenter.PromotionalPostersPerson;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.ImageUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by LuoCY on 2019/8/10.
 *
 * 推广海报
 *
 */
public class PromotionalPostersActivity extends BaseActivity<PromotionalPostersPerson>
        implements PromotionalPostersContract.MainView {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.webView)
    WebView webView;

    private String article_link = "";

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    final Bitmap[] bitmap = new Bitmap[1];

    @Override
    protected void initInject() {
        getActivityComponent().inject(PromotionalPostersActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_promotional_posters;
    }

    @Override
    protected void initEventAndData() {
        titleShare.setText(getResources().getText(R.string.promotional_posters));
        getTimeStamp();
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(bitmap[0] !=null){
                    ImageUtils.saveImageToGallery(mContext, bitmap[0]);
                }else {
                    ToastUtils.showLongToast(mContext,"bitmap为空");
                }
                return true;
            }
        });

    }

    public void getTimeStamp() {//获取服务器时间

        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "");
        RequestBody requestBody = build.build();
        mPresenter.getTimeStampBean(requestBody);
    }

    public void getPromotionalPosters() {//获取推广地址
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
        mPresenter.getPromotionalPostersBean(requestBody);
    }


    public void webView() {

        webView.loadUrl(article_link);
        // 启用支持JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);

//复写WebViewClient的shouldOverrideUrlLoading()的方法

//如果需要事件处理返回false,否则返回true.这样就可以解决问题了

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                lodurl(view, url);
                return false;
            }
        });

//        this.mWebView.loadUrl(ConstUtils.API+"content.aspx?id="+type);
        webView.loadUrl(article_link);
    }

    public void lodurl(final WebView webView, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
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
    public void showError() {

    }

    @Override
    public void timeShowError() {
        getPromotionalPosters();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void timeOnError(Throwable throwable) {
        getPromotionalPosters();
    }

    @Override
    public void getTimeStampTos(TimeStampBean timeStampBean) {
        getPromotionalPosters();
    }

    @Override
    public void getPromotionalPostersTos(PromotionalPostersBean promotionalPostersBean) {
        article_link = promotionalPostersBean.getImg_url();
        webView();
        Glide.with(mContext).load(promotionalPostersBean.getImg_url()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bitmap[0] = resource;
            }
        });
    }
}
