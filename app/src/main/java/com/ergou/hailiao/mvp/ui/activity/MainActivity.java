package com.ergou.hailiao.mvp.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ergou.hailiao.R;
import com.ergou.hailiao.app.AppManager;
import com.ergou.hailiao.base.BaseActivity;
import com.ergou.hailiao.mvp.bean.RongYunInfoBean;
import com.ergou.hailiao.mvp.ui.fragment.DialogueFragment;
import com.ergou.hailiao.mvp.ui.fragment.GameFragment;
import com.ergou.hailiao.mvp.ui.fragment.MailListFragment;
import com.ergou.hailiao.mvp.ui.fragment.MyFragment;
import com.ergou.hailiao.rongyun.SealExtensionModule;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;


public class MainActivity extends BaseActivity {
    @BindView(R.id.dialogue_img)
    ImageView dialogueImg;
    @BindView(R.id.mail_list_img)
    ImageView mailListImg;
    @BindView(R.id.game_img)
    ImageView gameImg;
    @BindView(R.id.my_img)
    ImageView myImg;
    @BindView(R.id.dialogue_text)
    TextView dialogueText;//对话
    @BindView(R.id.mail_list_text)
    TextView mailListText;//通讯录
    @BindView(R.id.game_text)
    TextView gameText;//游戏
    @BindView(R.id.my_text)
    TextView myText;//我的


    private FragmentTransaction transaction;
    private FragmentManager manager;
    //    private MainConversationListFragment mainConversationListFragment;//对话
    private DialogueFragment dialogueFragment;//对话
    private MailListFragment mailListFragment;//通讯录
    private GameFragment gameFragment;//游戏
    private MyFragment myFragment;//我的

    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public void showError() {

    }

    @Override
    public void timeShowError() {
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
        setMyExtensionModule();
        manager = getSupportFragmentManager();
        selectedtab(3);
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
//                if (mainConversationListFragment == null) {
//                    mainConversationListFragment = new MainConversationListFragment();
//                    transaction.add(R.id.home_home, mainConversationListFragment); // 如果为空加入FragmentTransaction管理器
//                }
//                transaction.show(mainConversationListFragment);
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

//        if (mainConversationListFragment != null) {
//            transaction.hide(mainConversationListFragment);
//        }

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
        dialogueText.setTextColor(getResources().getColor(R.color.colorText4295D5));
        mailListText.setTextColor(getResources().getColor(R.color.colorText));
        gameText.setTextColor(getResources().getColor(R.color.colorText));
        myText.setTextColor(getResources().getColor(R.color.colorText));
        dialogueImg.setImageResource(R.drawable.dialogue_y);
        mailListImg.setImageResource(R.drawable.mail_list_n);
        gameImg.setImageResource(R.drawable.game_n);
        myImg.setImageResource(R.drawable.my_n);
    }

    public void mailListImg() {//通讯录
        dialogueText.setTextColor(getResources().getColor(R.color.colorText));
        mailListText.setTextColor(getResources().getColor(R.color.colorText4295D5));
        gameText.setTextColor(getResources().getColor(R.color.colorText));
        myText.setTextColor(getResources().getColor(R.color.colorText));
        dialogueImg.setImageResource(R.drawable.dialogue_n);
        mailListImg.setImageResource(R.drawable.mail_list_y);
        gameImg.setImageResource(R.drawable.game_n);
        myImg.setImageResource(R.drawable.my_n);
    }

    public void gameImg() {//游戏
        dialogueText.setTextColor(getResources().getColor(R.color.colorText));
        mailListText.setTextColor(getResources().getColor(R.color.colorText));
        gameText.setTextColor(getResources().getColor(R.color.colorText4295D5));
        myText.setTextColor(getResources().getColor(R.color.colorText));
        dialogueImg.setImageResource(R.drawable.dialogue_n);
        mailListImg.setImageResource(R.drawable.mail_list_n);
        gameImg.setImageResource(R.drawable.game_y);
        myImg.setImageResource(R.drawable.my_n);
    }

    public void myImg() {//我的
        dialogueText.setTextColor(getResources().getColor(R.color.colorText));
        mailListText.setTextColor(getResources().getColor(R.color.colorText));
        gameText.setTextColor(getResources().getColor(R.color.colorText));
        myText.setTextColor(getResources().getColor(R.color.colorText4295D5));
        dialogueImg.setImageResource(R.drawable.dialogue_n);
        mailListImg.setImageResource(R.drawable.mail_list_n);
        gameImg.setImageResource(R.drawable.game_n);
        myImg.setImageResource(R.drawable.my_y);
    }

    @OnClick({R.id.dialogue_ll, R.id.mail_list_ll, R.id.game_ll, R.id.my_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialogue_ll://对话
                selectedtab(1);
                break;
            case R.id.mail_list_ll://通讯录
                selectedtab(2);
                break;
            case R.id.game_ll://游戏
                selectedtab(3);
                break;
            case R.id.my_ll://我的
                selectedtab(4);
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

    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);

            }
        }
        RongExtensionManager.getInstance().registerExtensionModule(new SealExtensionModule(mContext));
    }

}
