package com.ergou.hailiao.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Base64编码解码工具
 */
public class Base64Manage {

    /**
     * 异步编码状态监听
     * @author Administrator
     */
    public interface Base64compileListener{
        public void complete(String basevalue);
    }

    private Base64compileListener base64listener;

    /**
     * 设置点击监听
     */
    public void Setistener(Base64compileListener base64listener){
        this.base64listener = base64listener;
    }


    /**
     * @author ln
     * created at 2017/4/11 10:41
     * 对参数进行base64编码
     */
    public static String EncodeToString(String params) {
        String encodedString = Base64.encodeToString(params.getBytes(), Base64.DEFAULT);
        try {
            String encode = URLEncoder.encode(encodedString, "utf-8");
            return encode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @author ln
     * created at 2017/4/11 10:41
     * 对参数进行base64编码
     */
    public static String base64UrlSafeEncode(String params) {

        String ss = String.valueOf(EncodeUtils.base64UrlSafeEncode(params));
        return ss;
    }

    /**
     * @author ln
     * created at 2017/4/11 10:43
     * 进行base64的解码操作
     */
    public static String Decoding(String str) {
        try {
            String data = URLDecoder.decode(str, "utf-8");
            return new String(Base64.decode(data.getBytes("utf-8"), Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * TODO:将图片以Base64方式编码为字符串
     *
     * @param imgUrl 图片的绝对路径（例如：D:\\jsontest\\abc.jpg）
     * @return 编码后的字符串
     * @throws IOException
     */
    public void encodeImage(String imgUrl) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(imgUrl);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        try {
            String encode = URLEncoder.encode(imageEncoded, "utf-8");
            //将编译完成
            base64listener.complete(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //将编译完成
            base64listener.complete("");
        }
    }
}
