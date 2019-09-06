package com.ergou.hailiao.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.RedEnvelopeGrab;
import com.ergou.hailiao.mvp.bean.TimeStampBean;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopeGrabContract;
import com.ergou.hailiao.mvp.homepresenter.RedEnvelopesGrabPerson;
import com.ergou.hailiao.utils.glide.GlideManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LuoCY on 2019/9/6.
 * <p>
 * <p>
 * 抢红包
 */
public class RedEnvelopeGrabActivity extends BaseActivity<RedEnvelopesGrabPerson> implements RedEnvelopeGrabContract.MainView{
    @BindView(R.id.head_img)
    ImageView headImg;//头像
    @BindView(R.id.name)
    TextView name;//你猜
    @BindView(R.id.money)
    TextView money;//红包
    @BindView(R.id.money_number)
    TextView moneyNumber;//红包个数
    @BindView(R.id.leihao)
    TextView leihao;//雷号
    @BindView(R.id.time)
    TextView time;//时间
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private String nickame = "";
    private String header = "";
    private String order_id = "";

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_red_envelope_grab;
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
        Intent intent = getIntent();
        nickame = intent.getStringExtra("nickame");//
        header = intent.getStringExtra("header");//
        order_id = intent.getStringExtra("order_id");//
        name.setText(nickame +getResources().getText(R.string.prompt40));
        GlideManager.loadRoundImageView(mContext, header,
                headImg, R.mipmap.ic_launcher);//头像
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {

    }

    @OnClick({R.id.fallback, R.id.red_envelope_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fallback:
                break;
            case R.id.red_envelope_record:
                break;
        }
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
    public void getRedEnvelopeGrabTos(RedEnvelopeGrab redEnvelopeGrab) {

    }
}
