package com.ergou.hailiao.rongyun;

/**
 * Created by LuoCY on 2019/8/28.
 *
 * 红包
 *
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Spannable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ergou.hailiao.NetworkRequest.InterfaceInteraction;
import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.bean.RedTypeBean;
import com.ergou.hailiao.mvp.http.ApiInterface;
import com.ergou.hailiao.mvp.ui.activity.RedEnvelopeGrabActivity;
import com.ergou.hailiao.utils.AppUtils;
import com.ergou.hailiao.utils.EncryptUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.ToastUtils;
import com.ergou.hailiao.utils.dataUtils.SPUtilsData;
import com.ergou.hailiao.utils.glide.GlideManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by longShun on 2017/2/24.
 * desc新建一个消息类继承 IContainerItemProvider.MessageProvider 类，实现对应接口方法，
 * 1.注意开头的注解！
 * 2.注意泛型！
 */
@ProviderTag(
        messageContent = ZhuanZhangMessage.class,//(自定义的消息实体)
        showReadState = true
)
public class ZhuanZhangItemProvider extends IContainerItemProvider.MessageProvider<ZhuanZhangMessage> {

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private PopupWindow popupWindow;

    Handler handler = new Handler();

    public ZhuanZhangItemProvider() {
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        //这就是展示在会话界面的自定义的消息的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_zhuanzhang, null);
        ViewHolder holder = new ViewHolder();
        holder.money = (TextView) view.findViewById(R.id.money);//红包

        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, ZhuanZhangMessage redPackageMessage, UIMessage message) {
        //根据需求，适配数据
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.money.setText(redPackageMessage.getMoneye());

    }

    @Override
    public Spannable getContentSummary(Context context, ZhuanZhangMessage redPackageMessage) {

        return null;
    }


    @Override
    public Spannable getContentSummary(ZhuanZhangMessage redPackageMessage) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, ZhuanZhangMessage redPackageMessage, UIMessage uiMessage) {

    }

    @Override
    public void onItemLongClick(View view, int i, ZhuanZhangMessage redPackageMessage, UIMessage uiMessage) {
//        ToastUtils.showLongToast(view.getContext(), "长按红包");
    }

    private static class ViewHolder {
        TextView money;
    }

}
