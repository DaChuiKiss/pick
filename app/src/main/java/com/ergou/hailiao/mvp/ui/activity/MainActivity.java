package com.ergou.hailiao.mvp.ui.activity;

import android.view.KeyEvent;
import android.widget.Toast;


import com.ergou.hailiao.R;
import com.ergou.hailiao.app.AppManager;
import com.ergou.hailiao.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public void showError(String msg) {

    }

    @Override
    public void timeShowError(String time) {
    }

    @Override
    public void codeTypeError(int code) {

    }

    @Override
    protected void initInject() {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //拦截返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //判断触摸UP事件才会进行返回事件处理
            if (event.getAction() == KeyEvent.ACTION_UP) {
                onBackPressed();
            }
            //只要是返回事件，直接返回true，表示消费掉
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            Toast.makeText(this, getResources().getText(R.string.prompt1), Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录

                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {//退出程序
//            singOut();
            AppManager.getAppManager().AppExit(mContext);
        }
    }

}
