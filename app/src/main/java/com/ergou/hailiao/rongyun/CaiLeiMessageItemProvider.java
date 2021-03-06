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
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

/**
 * Created by LuoCY on 2019/8/28.
 */
@ProviderTag(messageContent = CaiLeiMessage.class , showPortrait = false ,showSummaryWithName = false , centerInHorizontal = true)
public class CaiLeiMessageItemProvider extends TextMessageItemProvider.MessageProvider<CaiLeiMessage> {

    class ViewHolder {
        TextView text;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cailei, null);
        ViewHolder holder = new ViewHolder();
        holder.text = (TextView) view.findViewById(R.id.text);//内容
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, CaiLeiMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(customizeMessage.getContent());
    }

    @Override
    public Spannable getContentSummary(CaiLeiMessage data) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }

    @Override
    public void onItemClick(View view, int i, CaiLeiMessage customizeMessage, UIMessage uiMessage) {

    }


}
