package com.ergou.hailiao.mvp.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ergou.hailiao.R;
import com.ergou.hailiao.utils.ToastUtils;

import java.util.Map;

import io.rong.imkit.RongExtension;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;
import io.rong.push.RongPushClient;


/**
 * Created by LuoCY on 2019/8/10.
 */
public class TestDuihuaList extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);
        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragement.setUri(uri);
//        // 构造 TextMessage 实例
//        TextMessage myTextMessage = TextMessage.obtain("二狗子");
//
//        /* 生成 Message 对象。
//         * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、群组 Id 或聊天室 Id。
//         * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组。
//         */
//        Message myMessage = Message.obtain("65373", Conversation.ConversationType.PRIVATE, myTextMessage);
//        /**
//         * <p>发送消息。
//         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
//         * 中的方法回调发送的消息状态及消息体。</p>
//         *
//         * @param message     将要发送的消息体。
//         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
//         *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
//         *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
//         * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
//         * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
//         */
//        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
//            @Override
//            public void onAttached(Message message) {
//                //消息本地数据库存储成功的回调
//                Log.e("main","二狗："+"消息本地数据库存储成功的回调");
//            }
//
//            @Override
//            public void onSuccess(Message message) {
//                //消息通过网络发送成功的回调
//                Log.e("main","二狗："+"消息通过网络发送成功的回调");
//            }
//
//            @Override
//            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                //消息发送失败的回调
//                Log.e("main","二狗："+"消息发送失败的回调");
//            }
//        });

//        ConversationListAdapter conversationListAdapter  = new ConversationListAdapter(TestDuihuaList.this);

//        RongIM.getInstance().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//            @Override
//            public boolean onReceived(Message message, int i) {
//                return false;
//            }
//        });
//        RongIM.getInstance().setOnReceiveMessageListener(new OnReceiveMessageListener())


    }


    /**
     * <p>启动会话界面。</p>
     * <p>使用时，可以传入多种会话类型 {@link Conversation.ConversationType} 对应不同的会话类型，开启不同的会话界面。
     * 如果传入的是 {@link Conversation.ConversationType#CHATROOM}，sdk 会默认调用

     * 如果你的逻辑是，只允许加入已存在的聊天室，请使用接口 {@link #(Context, String, boolean)} 并且第三个参数为 true</p>
     *
     * @param context          应用上下文。
     * @param conversationType 会话类型。
     * @param targetId         根据不同的 conversationType，可能是用户 Id、群组 Id 或聊天室 Id。
     * @param title            聊天的标题，开发者可以在聊天界面通过 intent.getData().getQueryParameter("title") 获取该值, 再手动设置为标题。
     */
    public void startConversation(Context context, Conversation.ConversationType conversationType, String targetId, String title) {

    }

    /**
     * 启动会话列表界面。
     *
     * @param context               应用上下文。
     * @param supportedConversation 定义会话列表支持显示的会话类型，及对应的会话类型是否聚合显示。
     *                              例如：supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false) 非聚合式显示 private 类型的会话。
     */
    public void startConversationList(Context context, Map<String, Boolean> supportedConversation) {

    }

    /**
     * 启动聚合后的某类型的会话列表。<br> 例如：如果设置了单聊会话为聚合，则通过该方法可以打开包含所有的单聊会话的列表。
     *
     * @param context          应用上下文。
     * @param conversationType 会话类型。
     */
    public void startSubConversationList(Context context, Conversation.ConversationType conversationType) {

    }

}
