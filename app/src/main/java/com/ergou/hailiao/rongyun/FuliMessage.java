package com.ergou.hailiao.rongyun;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by LuoCY on 2019/8/28.
 */
@MessageTag(value = "fuli", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class FuliMessage extends MessageContent {
    //自定义的属性
    private String nick_name;//昵称
    private String header;//头像
    private String content;
    private String money;//金额
    private String num;//个数
    private String order_id;//ID

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<FuliMessage> CREATOR = new Creator<FuliMessage>() {

        @Override
        public FuliMessage createFromParcel(Parcel source) {
            return new FuliMessage(source);
        }

        @Override
        public FuliMessage[] newArray(int size) {
            return new FuliMessage[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("nick_name", this.getNick_name());
            jsonObj.put("header", this.getHeader());
            jsonObj.put("content", this.getContent());
            jsonObj.put("money", this.getMoney());
            jsonObj.put("num", this.getNum());
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

    public FuliMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("nick_name"))
                this.setNick_name(jsonObj.optString("nick_name"));
            if (jsonObj.has("header"))
                this.setHeader(jsonObj.optString("header"));
            if (jsonObj.has("content"))
                this.setContent(jsonObj.optString("content"));
            if (jsonObj.has("money"))
                this.setMoney(jsonObj.optString("money"));
            if (jsonObj.has("num"))
                this.setNum(jsonObj.optString("num"));
            if (jsonObj.has("order_id"))
                this.setOrderId(jsonObj.optString("order_id"));
        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }

    }

    //给消息赋值。
    public FuliMessage(Parcel in) {

        setNick_name(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        setHeader(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        setContent(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        //这里可继续增加你消息的属性
        setMoney(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        setNum(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
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
        ParcelUtils.writeToParcel(dest, this.getNick_name());
        ParcelUtils.writeToParcel(dest, this.getHeader());
        ParcelUtils.writeToParcel(dest, this.getContent());
        ParcelUtils.writeToParcel(dest, this.getMoney());
        ParcelUtils.writeToParcel(dest, this.getNum());
        ParcelUtils.writeToParcel(dest, this.getOrderId());
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

}
