package com.ergou.hailiao.mvp.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.utils.glide.GlideManager;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class MaintainActivity extends BaseActivity {
    @BindView(R.id.maintain_img)
    ImageView maintain_img;
    private String url = "";

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_maintain;
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
        url = intent.getStringExtra("url");//图片
//        maintain_img.setImageResource(R.mipmap.bg_img);
        if (!url.equals("")) {
            GlideManager.loadImageView(MaintainActivity.this, url, maintain_img, R.color.colorWhite);
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {

    }

}
