package com.ergou.hailiao.NetworkRequest;

import android.util.Base64;
import android.util.Log;

import com.ergou.hailiao.utils.DESEncodeUtil;
import com.ergou.hailiao.utils.EncryptUtils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/16
 *     desc  : 字符串相关工具类
 * </pre>
 */
public class InterfaceInteraction {

    private static String sortFront;
    private static String sortEND;
    private static String pattern = "yyyyMMddHHmmss";

//随机生成32位编码
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

//Sign签名
    public static String getSign(String code,String cmd){

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("code",code);
        hashMap.put("cmd",cmd);
        SortedMap<String,String> sort=new TreeMap<String,String>(hashMap);
        Set<Map.Entry<String,String>> entry1=sort.entrySet();
        Iterator<Map.Entry<String,String>> it=entry1.iterator();
        String str = "code=" + code + "&cmd=" + cmd +"&UNqItA@&AofHFtvt";
        String encryptMD5 = EncryptUtils.encryptMD5ToString(str);
        return encryptMD5;
    }


    /**
     * 获取系统时间戳
     * @return
     */
    public static long getCurTimeLong(){
        long time=System.currentTimeMillis();
        return time;
    }
    /**
     * 获取当前时间
     * @return
     */
    public static String getCurDate(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date());
    }

    /**
     * 时间戳转换成字符窜
     * @param milSecond
     * @return
     */
    public static String getDateToString(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @return
     */
    public static long getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    //
    public static String getJSON(Map map){
        Gson gson=new Gson();
        String s = gson.toJson(map);
        String encodedString = Base64.encodeToString(s.getBytes(), Base64.NO_WRAP);
        return encodedString;
    }

    public static <T> String getCmdValue(T t) {
//        String encodedString = Base64.encodeToString(new Gson().toJson(t).getBytes(), Base64.NO_WRAP);
        Gson gson = new Gson();
        String str = gson.toJson(t);
        Log.e("gson", str + "----gson----");
        String encodedString = null;
        try {
            encodedString = DESEncodeUtil.encode(str);
//            encodedString = DESEncodeUtil.encode("123456");
            Log.e("gson", encodedString + "---------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("gson", encodedString + "---3des----");
        Log.e("encodedString", encodedString+"3des");
        return encodedString;
    }

}