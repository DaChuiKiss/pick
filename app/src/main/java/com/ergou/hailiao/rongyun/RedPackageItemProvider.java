package com.ergou.hailiao.rongyun;

/**
 * Created by LuoCY on 2019/8/28.
 */

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.bean.RedPackageBean;
import com.ergou.hailiao.utils.ToastUtils;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utilities.OptionsPopupDialog;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by longShun on 2017/2/24.
 * desc新建一个消息类继承 IContainerItemProvider.MessageProvider 类，实现对应接口方法，
 * 1.注意开头的注解！
 * 2.注意泛型！
 */
@ProviderTag(
        messageContent = RedPackageMessage.class,//(自定义的消息实体)
        showReadState = true
)
public class RedPackageItemProvider extends IContainerItemProvider.MessageProvider<RedPackageMessage> {

    public RedPackageItemProvider() {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        //这就是展示在会话界面的自定义的消息的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_hongbao, null);
        ViewHolder holder = new ViewHolder();
        holder.money = (TextView) view.findViewById(R.id.money);//红包
        holder.lei_number = (TextView) view.findViewById(R.id.lei_number);//雷号
        holder.receive_type = (TextView) view.findViewById(R.id.receive_type);//是否领取
        holder.remarks = (TextView) view.findViewById(R.id.remarks);//备注
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, RedPackageMessage redPackageMessage, UIMessage message) {

        //根据需求，适配数据
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.money.setText(redPackageMessage.getMoneye());
        holder.lei_number.setText(redPackageMessage.getBoom());
        holder.remarks.setText(redPackageMessage.getContent());

    }

    @Override
    public Spannable getContentSummary(Context context, RedPackageMessage redPackageMessage) {

        return null;
    }

    @Override
    public Spannable getContentSummary(RedPackageMessage redPackageMessage) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, RedPackageMessage redPackageMessage, UIMessage uiMessage) {
        ToastUtils.showLongToast(view.getContext(), "点击红包");
    }

    @Override
    public void onItemLongClick(View view, int i, RedPackageMessage redPackageMessage, UIMessage uiMessage) {
        ToastUtils.showLongToast(view.getContext(), "长按红包");
    }

    private static class ViewHolder {
        TextView money, lei_number, receive_type, remarks;
    }
}
