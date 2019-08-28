package com.ergou.hailiao.rongyun;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ergou.hailiao.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * Created by LuoCY on 2019/8/28.
 */
@ProviderTag(messageContent = RedPackageMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<RedPackageMessage> {

    class ViewHolder {
        TextView money;
        TextView lei_number;
        TextView receive_type;
        TextView remarks;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hongbao, null);
        ViewHolder holder = new ViewHolder();
        holder.money = (TextView) view.findViewById(R.id.money);//金额
        holder.lei_number = (TextView) view.findViewById(R.id.lei_number);//雷号
        holder.receive_type = (TextView) view.findViewById(R.id.receive_type);//是否领取
        holder.remarks = (TextView) view.findViewById(R.id.remarks);//备注
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, RedPackageMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();

//        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
//        } else {
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
//        }
//        holder.money.setText(content.getContent());
//        AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }

    @Override
    public Spannable getContentSummary(RedPackageMessage data) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }

    @Override
    public void onItemClick(View view, int i, RedPackageMessage customizeMessage, UIMessage uiMessage) {

    }


}
