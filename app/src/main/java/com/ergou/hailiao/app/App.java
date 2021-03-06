package com.ergou.hailiao.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.ergou.hailiao.di.component.AppComponent;
import com.ergou.hailiao.di.component.DaggerAppComponent;
import com.ergou.hailiao.di.module.AppMoudle;
import com.ergou.hailiao.mvp.ui.activity.MainActivity;
import com.ergou.hailiao.rongyun.CaiLeiMessage;
import com.ergou.hailiao.rongyun.CaiLeiMessageItemProvider;
import com.ergou.hailiao.rongyun.ContactNotificationMessageData;
import com.ergou.hailiao.rongyun.DuoLeiMessage;
import com.ergou.hailiao.rongyun.DuoLeiMessageItemProvider;
import com.ergou.hailiao.rongyun.FuLiItemProvider;
import com.ergou.hailiao.rongyun.FuliMessage;
import com.ergou.hailiao.rongyun.RedPackageItemProvider;
import com.ergou.hailiao.rongyun.RedPackageMessage;
import com.ergou.hailiao.rongyun.SealExtensionModule;
import com.ergou.hailiao.rongyun.IntentExtra;
import com.ergou.hailiao.rongyun.SealExtensionOneModule;
import com.ergou.hailiao.rongyun.TeShuMessage;
import com.ergou.hailiao.rongyun.TeShuMessageItemProvider;
import com.ergou.hailiao.rongyun.ZhuanZhangItemProvider;
import com.ergou.hailiao.rongyun.ZhuanZhangMessage;
import com.ergou.hailiao.utils.CrashUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.Utils;
import com.google.gson.Gson;
import com.sobot.chat.SobotApi;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.ImageMessage;


/**
 * Created by LuoCy on 2017/11/06.
 */

public class App extends Application {
    private static App instance;
    private static SLHandler handler;
    public static MainActivity mainActivity;


    public static StringBuilder payloadData = new StringBuilder();

    public static synchronized App getInstance() {
        return instance;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {

        super.onCreate();

        if (handler == null) {
            handler = new SLHandler();
        }
        instance = this;
        //Realm初始化

        //初始化错误收集
        CrashUtils.getInstance().init(getApplicationContext());

        // android 7.0系统解决拍照的问题
        LogUtils.e("系版本=" + Build.VERSION.RELEASE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        Utils.init(instance);

        RongIM.init(instance, "82hegw5u8x73x", true);
        setMyExtensionModule("1");

        SobotApi.initSobotSDK(instance, "bb85ba9496ad4e5a9e9e848a5288dd77", "");
        // 注册一个自定义消息类型。
        /**
         * hongbao(发红包消息)；cailei（红包中雷提示）；award（抢红包特殊号码奖励提示）；duolei（发红包多雷奖励提示）；fuli（发送福利红包消息）
         * */
        RongIM.registerMessageType(RedPackageMessage.class);//hongbao
        RongIM.registerMessageType(CaiLeiMessage.class);//cailei
        RongIM.registerMessageType(FuliMessage.class);//fuli
        RongIM.registerMessageType(TeShuMessage.class);//award
        RongIM.registerMessageType(DuoLeiMessage.class);//duolei
        RongIM.registerMessageType(ZhuanZhangMessage.class);//zhuangzhang
        RongIM.registerMessageTemplate(new RedPackageItemProvider());
        RongIM.registerMessageTemplate(new CaiLeiMessageItemProvider());
        RongIM.registerMessageTemplate(new FuLiItemProvider());
        RongIM.registerMessageTemplate(new TeShuMessageItemProvider());
        RongIM.registerMessageTemplate(new DuoLeiMessageItemProvider());
        RongIM.registerMessageTemplate(new ZhuanZhangItemProvider());

//        initConversation();
//        initConversationList();
    }

//    public static String getCurProcessName(Context context) {
//        int pid = Process.myPid();
//        ActivityManager mActivityManager = (ActivityManager)context.getSystemService("activity");
//        List<RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
//        if (runningAppProcessInfos == null) {
//            return null;
//        } else {
//            Iterator var4 = runningAppProcessInfos.iterator();
//
//            RunningAppProcessInfo appProcess;
//            do {
//                if (!var4.hasNext()) {
//                    return null;
//                }
//
//                appProcess = (RunningAppProcessInfo)var4.next();
//            } while(appProcess.pid != pid);
//
//            return appProcess.processName;
//        }
//    }

    /**
     * 初始化会话相关
     */
    private void initConversation() {
        // 启用会话界面新消息提示
        RongIM.getInstance().enableNewComingMessageIcon(true);
        // 启用会话界面未读信息提示
        RongIM.getInstance().enableUnreadMessageIcon(true);

        // 添加会话界面点击事件
        RongIM.setConversationClickListener(new RongIM.ConversationClickListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(IntentExtra.STR_TARGET_ID, userInfo.getUserId());
                if (conversationType == Conversation.ConversationType.GROUP) {
                    Group groupInfo = RongUserInfoManager.getInstance().getGroupInfo(s);
                    if (groupInfo != null) {
                        intent.putExtra(IntentExtra.STR_GROUP_NAME, groupInfo.getName());
                    }
                }
                context.startActivity(intent);
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, io.rong.imlib.model.Message message) {
                if (message.getContent() instanceof ImageMessage) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.setPackage(view.getContext().getPackageName());
                    intent.putExtra("message", message);
                    view.getContext().startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s, io.rong.imlib.model.Message message) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, io.rong.imlib.model.Message message) {
                return false;
            }
        });

    }

    /**
     * 初始化会话列表相关事件
     */
    private void initConversationList() {
        // 设置会话列表行为监听
        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                /*
                 * 当点击会话列表中通知添加好友消息时，判断是否已成为好友
                 * 已成为好友时，跳转到私聊界面
                 * 非好友时跳转到新的朋友界面查看添加好友状态
                 */
                MessageContent messageContent = uiConversation.getMessageContent();
                if (messageContent instanceof ContactNotificationMessage) {
                    ContactNotificationMessage contactNotificationMessage = (ContactNotificationMessage) messageContent;
                    if (contactNotificationMessage.getOperation().equals("AcceptResponse")) {
                        // 被加方同意请求后
                        if (contactNotificationMessage.getExtra() != null) {
                            ContactNotificationMessageData bean = null;
                            try {
                                Gson gson = new Gson();
                                bean = gson.fromJson(contactNotificationMessage.getExtra(), ContactNotificationMessageData.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            RongIM.getInstance().startPrivateChat(context, uiConversation.getConversationSenderId(), bean.getSourceUserNickname());
                        }
                    } else {
                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appMoudle(new AppMoudle(instance))
                .build();
    }

    public static class SLHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mainActivity != null) {
                        payloadData.append((String) msg.obj);
                        payloadData.append("\n");
//                        if (MainActivity.tLogView != null) {
//                            MainActivity.tLogView.append(msg.obj + "\n");
//                        }
                        LogUtils.e("心好累");
                    }
                    break;

                case 1:
                    if (mainActivity != null) {
                        LogUtils.e("心好累AA");
//                        if (MainActivity.tLogView != null) {
//                            MainActivity.tView.setText((String) msg.obj);
//                        }
                    }
                    break;
            }
        }
    }

    public static  void setMyExtensionModule(String type) {
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
        if (type.equals("1")){
            RongExtensionManager.getInstance().registerExtensionModule(new SealExtensionModule(instance));
        }else {
            RongExtensionManager.getInstance().registerExtensionModule(new SealExtensionOneModule(instance));
        }

    }


}
