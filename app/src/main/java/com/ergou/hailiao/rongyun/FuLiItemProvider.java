package com.ergou.hailiao.rongyun;

/**
 * Created by LuoCY on 2019/8/28.
 */

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ergou.hailiao.R;
import com.ergou.hailiao.utils.ToastUtils;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * Created by longShun on 2017/2/24.
 * desc新建一个消息类继承 IContainerItemProvider.MessageProvider 类，实现对应接口方法，
 * 1.注意开头的注解！
 * 2.注意泛型！
 */
@ProviderTag(
        messageContent = FuliMessage.class
)
public class FuLiItemProvider extends IContainerItemProvider.MessageProvider<FuliMessage> {

    public FuLiItemProvider() {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        //这就是展示在会话界面的自定义的消息的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_fuli, null);
        ViewHolder holder = new ViewHolder();
        holder.money = (TextView) view.findViewById(R.id.money);//红包
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, FuliMessage redPackageMessage, UIMessage message) {

        //根据需求，适配数据
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.money.setText("狗大爷");
     ;

    }

    @Override
    public Spannable getContentSummary(Context context, FuliMessage redPackageMessage) {

        return null;
    }

    @Override
    public Spannable getContentSummary(FuliMessage redPackageMessage) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, FuliMessage redPackageMessage, UIMessage uiMessage) {
        ToastUtils.showLongToast(view.getContext(), "点击红包");
    }

    @Override
    public void onItemLongClick(View view, int i, FuliMessage redPackageMessage, UIMessage uiMessage) {
        ToastUtils.showLongToast(view.getContext(), "长按红包");
    }

    private static class ViewHolder {
        TextView money;
    }
}
