package com.ergou.hailiao.mvp.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ergou.hailiao.R;
import com.ergou.hailiao.app.AppManager;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.fragment.DialogueFragment;
import com.ergou.hailiao.mvp.ui.fragment.GameFragment;
import com.ergou.hailiao.mvp.ui.fragment.MailListFragment;
import com.ergou.hailiao.mvp.ui.fragment.MyFragment;
import com.ergou.hailiao.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.dialogue_img)
    ImageView dialogueImg;
    @BindView(R.id.mail_list_img)
    ImageView mailListImg;
    @BindView(R.id.game_img)
    ImageView gameImg;
    @BindView(R.id.my_img)
    ImageView myImg;


    private FragmentTransaction transaction;
    private FragmentManager manager;

    private DialogueFragment dialogueFragment;//对话
    private MailListFragment mailListFragment;//通讯录
    private GameFragment gameFragment;//游戏
    private MyFragment myFragment;//我的

    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public void showError(String msg) {
        ApiInterface.disPro(mContext);
        ToastUtils.showLongToast(mContext, msg);
    }

    @Override
    public void timeShowError(String time) {
    }

    @Override
    public void codeTypeError(int code) {
        ApiInterface.disPro(mContext);
//        ToastUtils.showLongToast(mContext, msg);
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
        manager = getSupportFragmentManager();
        selectedtab(1);
    }

    public void selectedtab(int selectid) {
        transaction = manager.beginTransaction();
        hideFragments();
        switch (selectid) {
            case 1:
                if (dialogueFragment == null) {
                    dialogueFragment = new DialogueFragment();
                    transaction.add(R.id.home_home, dialogueFragment); // 如果为空加入FragmentTransaction管理器
                }
                transaction.show(dialogueFragment);
                dialogueImg();//切换字体颜色与图片
                break;
            case 2:
                if (mailListFragment == null) {
                    mailListFragment = new MailListFragment();
                    transaction.add(R.id.home_home, mailListFragment);
                }
                transaction.show(mailListFragment);
                mailListImg();//切换字体颜色与图片
                break;
            case 3:
                if (gameFragment == null) {
                    gameFragment = new GameFragment();
                    transaction.add(R.id.home_home, gameFragment);
                }
                transaction.show(gameFragment);
                gameImg();//切换字体颜色与图片
                break;
            case 4:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.home_home, myFragment);
                }
                transaction.show(myFragment);
                myImg();//切换字体颜色与图片
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments() {
        if (dialogueFragment != null) {
            transaction.hide(dialogueFragment);
        }

        if (mailListFragment != null) {
            transaction.hide(mailListFragment);
        }

        if (gameFragment != null) {
            transaction.hide(gameFragment);
        }

        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    public void dialogueImg() {//对话
        dialogueImg.setImageResource(R.drawable.ic_launcher_background);
        mailListImg.setImageResource(R.drawable.ic_launcher_background);
        gameImg.setImageResource(R.drawable.ic_launcher_background);
        myImg.setImageResource(R.drawable.ic_launcher_background);
    }

    public void mailListImg() {//通讯录
        dialogueImg.setImageResource(R.drawable.ic_launcher_background);
        mailListImg.setImageResource(R.drawable.ic_launcher_background);
        gameImg.setImageResource(R.drawable.ic_launcher_background);
        myImg.setImageResource(R.drawable.ic_launcher_background);
    }

    public void gameImg() {//游戏
        dialogueImg.setImageResource(R.drawable.ic_launcher_background);
        mailListImg.setImageResource(R.drawable.ic_launcher_background);
        gameImg.setImageResource(R.drawable.ic_launcher_background);
        myImg.setImageResource(R.drawable.ic_launcher_background);
    }

    public void myImg() {//我的
        dialogueImg.setImageResource(R.drawable.ic_launcher_background);
        mailListImg.setImageResource(R.drawable.ic_launcher_background);
        gameImg.setImageResource(R.drawable.ic_launcher_background);
        myImg.setImageResource(R.drawable.ic_launcher_background);
    }

    @OnClick({R.id.dialogue_ll, R.id.mail_list_ll, R.id.game_ll, R.id.my_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialogue_ll://对话
                dialogueImg();
                break;
            case R.id.mail_list_ll://通讯录
                mailListImg();
                break;
            case R.id.game_ll://游戏
                gameImg();
                break;
            case R.id.my_ll://我的
                myImg();
                break;
        }
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
