package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
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
 * <p>
 * 推广海报
 */
public class H5Activity extends BaseActivity {
    @BindView(R.id.title_share)
    TextView titleShare;
    @BindView(R.id.webView)
    WebView webView;

    private String article_link = "";
    private String mark = "";


    @Override
    protected void initInject() {
//        getActivityComponent().inject(H5Activity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_promotional_posters;
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        mark = intent.getStringExtra("mark");//标题
        article_link = intent.getStringExtra("game_rule");//
        titleShare.setText(mark);
        webView();

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

    }

}
