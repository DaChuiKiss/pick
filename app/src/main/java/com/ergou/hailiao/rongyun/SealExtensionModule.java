package com.ergou.hailiao.rongyun;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.RongExtension;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by LuoCY on 2019/8/22.
 */
public class SealExtensionModule extends DefaultExtensionModule {

    public SealExtensionModule(Context context) {
        super(context);
    }

    @Override
    public void onInit(String appKey) {
        super.onInit(appKey);
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
    }

    @Override
    public void onConnect(String token) {
        super.onConnect(token);
    }

    @Override
    public void onAttachedToExtension(RongExtension extension) {
        super.onAttachedToExtension(extension);
    }

    @Override
    public void onDetachedFromExtension() {
        super.onDetachedFromExtension();
    }

    @Override
    public void onReceivedMessage(Message message) {
        super.onReceivedMessage(message);
    }

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModuleList = new ArrayList<>();
//        IPluginModule transferAccounts = new TransferAccounts();
        IPluginModule redEnvelopes = new RedEnvelopes();//红包
        KeFu keFu = new KeFu();//客服
//        pluginModuleList.add(transferAccounts);
        pluginModuleList.add(redEnvelopes);
        pluginModuleList.add(keFu);

        return pluginModuleList;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return super.getEmoticonTabs();
    }
}
