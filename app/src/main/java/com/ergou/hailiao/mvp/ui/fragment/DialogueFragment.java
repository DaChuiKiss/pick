package com.ergou.hailiao.mvp.ui.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * 对话
 */
public class DialogueFragment extends BaseFragment {

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dialogue;
    }

    @Override
    protected void initEventAndData() {
//        FragmentManager fragmentManage = getActivity().getSupportFragmentManager();
//        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
//        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversationlist")
//                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
//                .build();
//        fragement.setUri(uri);
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void timeShowError(String time) {

    }

    @Override
    public void codeTypeError(int code) {

    }
}
