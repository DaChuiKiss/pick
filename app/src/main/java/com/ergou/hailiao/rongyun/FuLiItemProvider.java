package com.ergou.hailiao.rongyun;

/**
 * Created by LuoCY on 2019/8/28.
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
import com.ergou.hailiao.mvp.ui.activity.FuLiGrabActivity;
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
        messageContent = FuliMessage.class, showPortrait = false, showSummaryWithName = false, centerInHorizontal = true
)
public class FuLiItemProvider extends IContainerItemProvider.MessageProvider<FuliMessage> {

    private String sign;
    private String cmd;
    private String code;
    private String timestamp;
    private String version;
    private String timeStamp = "";
    private String device_token = "";

    private PopupWindow popupWindow;

    Handler handler = new Handler();

    public FuLiItemProvider() {
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        //这就是展示在会话界面的自定义的消息的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_fuli, null);
        ViewHolder holder = new ViewHolder();
        holder.money = (TextView) view.findViewById(R.id.money);//红包
        holder.lei_number = (TextView) view.findViewById(R.id.lei_number);//雷号
        holder.receive_type = (TextView) view.findViewById(R.id.receive_type);//是否领取
        holder.remarks = (TextView) view.findViewById(R.id.remarks);//备注
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, FuliMessage fuliMessage, UIMessage message) {
        //根据需求，适配数据
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.money.setText(fuliMessage.getMoney());
        holder.lei_number.setText(fuliMessage.getNum());
        holder.remarks.setText(fuliMessage.getContent());
    }

    @Override
    public Spannable getContentSummary(Context context, FuliMessage fuliMessage) {

        return null;
    }


    @Override
    public Spannable getContentSummary(FuliMessage redPackageMessage) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, FuliMessage fuliMessage, UIMessage uiMessage) {

        Context context = view.getContext();
        getRedPackag( fuliMessage.getOrderId(), context, view);
    }

    @Override
    public void onItemLongClick(View view, int i, FuliMessage fuliMessage, UIMessage uiMessage) {
//        ToastUtils.showLongToast(view.getContext(), "长按红包");
    }

    private static class ViewHolder {
        TextView money, lei_number, receive_type, remarks;
    }

    public void getRedPackag(final String orderid, final Context context, final View view) {//
        device_token = ApiInterface.deviceToken(context);//设备号
        version = AppUtils.getAppVersionName(context);//版本号
        code = InterfaceInteraction.getUUID();//32位随机字符串
        timestamp = timeStamp + "";//时间戳

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_type", "android");
        map.put("client_version", version);
        map.put("device_token", device_token);//
        map.put("timestamp", timestamp);
        map.put("order_id", orderid);//红包唯一订单ID
        map.put("mobile", SPUtilsData.getPhoneNumber());//手机号
        map.put("type", "2");//类型（1:踩雷红包；2:福利红包）


        cmd = InterfaceInteraction.getCmdValue(map);
        sign = EncryptUtils.encryptMD5ToString(InterfaceInteraction.getSign(code, cmd));

        LogUtils.e("code=" + code);
        LogUtils.e("sign=" + sign);
        LogUtils.e("cmd=" + cmd);
        Thread thread = new Thread() {
            public void run() {
                try {
                    String utlString = ApiInterface.API + "redstatus";

                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。

                    FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                    formBody.add("code", code);//传递键值对参数
                    formBody.add("sign", sign);//传递键值对参数
                    formBody.add("cmd", cmd);//传递键值对参数
                    Request request = new Request.Builder()//创建Request 对象。
                            .url(utlString)
                            .post(formBody.build())//传递请求体
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("hailiao", "获取数据失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String bodyString = response.body().string();
                            Gson gson = new Gson();
                            final RedTypeBean typeBean = gson.fromJson(bodyString, RedTypeBean.class);
                            handler.post(new Runnable() {
                                public void run() {
                                    redPackageWindow( orderid, context, view, typeBean.getData().getStatus());
                                }
                            });
                        }
                    });//回调方法的使用与get异步请求相同，此时略。
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }


    public void redPackageWindow( final String order_id, final Context mContext, View view, String redType) {//红包状态
        final Activity activity = (Activity) view.getContext();
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_fuli, null);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setContentView(contentView);
        //3、获取屏幕的默认分辨率
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        popupWindow.setWidth(width - 280);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
// 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = activity.getWindow().getAttributes();
        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
        activity.getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                activity.getWindow().setAttributes(wlBackground);
            }
        });

        ImageView head_img = (ImageView) contentView.findViewById(R.id.head_img);//头像
        TextView name = (TextView) contentView.findViewById(R.id.name);//名字
        TextView text = (TextView) contentView.findViewById(R.id.text);//内容
        TextView open = (TextView) contentView.findViewById(R.id.open);//打开红包
        TextView chakan = (TextView) contentView.findViewById(R.id.chakan);//查看手气
        TextView red_delete = (TextView) contentView.findViewById(R.id.red_delete);//关闭
        if (redType.equals("4")) {//4:可抢
            open.setVisibility(View.VISIBLE);
            chakan.setVisibility(View.GONE);
            text.setText(activity.getResources().getText(R.string.prompt41));
        } else if (redType.equals("1")) {//已过期
            open.setVisibility(View.GONE);
            chakan.setVisibility(View.VISIBLE);
            text.setText(activity.getResources().getText(R.string.prompt42));
        } else if (redType.equals("2")) {//红包已抢完
            open.setVisibility(View.GONE);
            chakan.setVisibility(View.VISIBLE);
            text.setText(activity.getResources().getText(R.string.prompt43));
        } else if (redType.equals("3")) {//已抢过
            open.setVisibility(View.GONE);
            chakan.setVisibility(View.VISIBLE);
            text.setText(activity.getResources().getText(R.string.prompt44));
        }
        name.setText( activity.getResources().getText(R.string.prompt50));
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//开红包
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.setClass(activity, FuLiGrabActivity.class);
                intent.putExtra("order_id", order_id);//
                intent.putExtra("dataType", "kai");//
                activity.startActivity(intent);
            }
        });
        chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//查看手气
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.setClass(activity, FuLiGrabActivity.class);
                intent.putExtra("order_id", order_id);//
                intent.putExtra("dataType", "chakan");//
                activity.startActivity(intent);
            }
        });
        red_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//关闭
                popupWindow.dismiss();
            }
        });
        //设置PopupWindow进入和退出动画
//        popupWindow.setAnimationStyle(R.style.pop_window_anim);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 20);
    }
}
