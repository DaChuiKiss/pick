package com.ergou.hailiao.rongyun;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by LuoCY on 2019/8/28.
 */
@MessageTag(value = "app:custom", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RedPackageMessage extends MessageContent {
    //自定义的属性
    private String hongbao;//操作名
    private String content;
    private String money;//金额
    private String boom;//雷号
    private String order_id;//ID

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<RedPackageMessage> CREATOR = new Creator<RedPackageMessage>() {

        @Override
        public RedPackageMessage createFromParcel(Parcel source) {
            return new RedPackageMessage(source);
        }

        @Override
        public RedPackageMessage[] newArray(int size) {
            return new RedPackageMessage[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("hongbao", this.getHongbao());
            jsonObj.put("content", this.getContent());
            jsonObj.put("money", this.getMoneye());
            jsonObj.put("boom", this.getBoom());
            jsonObj.put("order_id", this.getOrderId());

        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RedPackageMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("hongbao"))
                this.setHongbao(jsonObj.optString("hongbao"));
            if (jsonObj.has("content"))
                this.setContent(jsonObj.optString("content"));
            if (jsonObj.has("money"))
                this.setMoney(jsonObj.optString("money"));
            if (jsonObj.has("boom"))
                this.setBoom(jsonObj.optString("boom"));
            if (jsonObj.has("order_id"))
                this.setOrderId(jsonObj.optString("order_id"));
        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }

    }

    //给消息赋值。
    public RedPackageMessage(Parcel in) {

        setHongbao(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        setContent(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        //这里可继续增加你消息的属性
        setMoney(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        setBoom(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        setOrderId(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
    }


    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getHongbao());
        ParcelUtils.writeToParcel(dest, this.getContent());
        ParcelUtils.writeToParcel(dest, this.getMoneye());
        ParcelUtils.writeToParcel(dest, this.getBoom());
        ParcelUtils.writeToParcel(dest, this.getOrderId());
    }

    public String getHongbao() {
        return hongbao;
    }

    public void setHongbao(String hongbao) {
        this.hongbao = hongbao;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoneye() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBoom() {
        return boom;
    }

    public void setBoom(String boom) {
        this.boom = boom;
    }

    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }


}
