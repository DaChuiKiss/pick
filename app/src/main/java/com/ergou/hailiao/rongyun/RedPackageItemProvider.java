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

//        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
//        } else {
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
//        }
        //AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
        //holder.tvTitle.setText(redPackageMessage.getTitle());
        holder.money.setText(redPackageMessage.getMoneye());
        holder.lei_number.setText(redPackageMessage.getBoom());
        //holder.tvDesc1.setText(redPackageMessage.getDesc1());
        //holder.tvDesc2.setText(redPackageMessage.getDesc2());
    }

    @Override
    public Spannable getContentSummary(Context context, RedPackageMessage redPackageMessage) {
//        if (redPackageMessage != null && !TextUtils.isEmpty(redPackageMessage.getHongbao())
//                && !TextUtils.isEmpty(redPackageMessage.getHongbao())) {
//            if (redPackageMessage.getOrderId().equals(RongIM.getInstance().getCurrentUserId())) {
////                String str_RecommendClause = context.getResources().getString(R.string.rc_recommend_clause_to_others);
//                String str_RecommendClause = "臭狗子";
//                return new SpannableString(String.format(str_RecommendClause, redPackageMessage.getMoneye()));
//            } else {
////                String str_RecommendClause = context.getResources().getString(R.string.rc_recommend_clause_to_me);
//                String str_RecommendClause = "狗大爷";
//                return new SpannableString(String.format(str_RecommendClause, redPackageMessage.getMoneye(), redPackageMessage.getBoom()));
//            }
//        }
//        return new SpannableString(redPackageMessage.getMoneye());

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
//        //实现长按删除等功能，咱们直接复制融云其他provider的实现
//        String[] items1;//复制，删除
//        items1 = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_copy), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete)};
//
//        OptionsPopupDialog.newInstance(view.getContext(), items1).setOptionsPopupDialogListener(new OptionsPopupDialog.OnOptionsItemClickedListener() {
//            public void onOptionsItemClicked(int which) {
//                if (which == 0) {
//                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                    clipboard.setText(content.getContent());//这里是自定义消息的消息属性
//                } else if (which == 1) {
//                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, (RongIMClient.ResultCallback) null);
//                }
//            }
//        }).show();

    }

    private static class ViewHolder {
        TextView money, lei_number, receive_type, remarks;
    }
}
