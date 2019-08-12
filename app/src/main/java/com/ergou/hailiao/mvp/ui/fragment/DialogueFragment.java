package com.ergou.hailiao.mvp.ui.fragment;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ergou.hailiao.R;
import com.ergou.hailiao.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * 对话
 */
public class DialogueFragment extends BaseFragment {

    @BindView(R.id.rc_viewpager)
    ViewPager mViewPager;

    /**
     * Fragment的数据适配器
     */
    private FragmentPagerAdapter mFragmentPagerAdapter;
    /**
     * ViewPager中的数据
     */
    private List<Fragment> mFragmentList;

    private Fragment mConversationListFragment;//会话列表的fragment 的声明

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dialogue;
    }

    @Override
    protected void initEventAndData() {
        mConversationListFragment = initConversationListFragment();
        initView();
    }

    private void initView() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.clear();
        mFragmentList.add(mConversationListFragment);

        mFragmentPagerAdapter = new FragmentPagerAdapter(
                getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragmentList.get(i);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
    }


    /**
     * 封装的代码加载融云的会话列表的 fragment
     *
     * @return
     */
    private Fragment initConversationListFragment() {
//        FragmentManager fragmentManage = getActivity().getSupportFragmentManager();
//        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        ConversationListFragment fragement = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragement.setUri(uri);
        return fragement;
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
