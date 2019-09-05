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
@ProviderTag(messageContent = DuoLeiMessage.class)
public class DuoLeiMessageItemProvider extends IContainerItemProvider.MessageProvider<DuoLeiMessage> {

    class ViewHolder {
        TextView money;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_duolei, null);
        ViewHolder holder = new ViewHolder();
        holder.money = (TextView) view.findViewById(R.id.money);//金额
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, DuoLeiMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.money.setText("四狗子");

    }

    @Override
    public Spannable getContentSummary(DuoLeiMessage data) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }

    @Override
    public void onItemClick(View view, int i, DuoLeiMessage customizeMessage, UIMessage uiMessage) {

    }


}
