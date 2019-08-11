package com.ergou.hailiao.utils.dataUtils;


import com.ergou.hailiao.utils.UserInfoSPUtils;

public class SPUtilsData {
    /**
     * 获取用户token
     */
    public static String getRongToken() {
        String ticket = UserInfoSPUtils.getInstance().getString("rong_token");
        return ticket;
    }

    /**
     * 获取用户昵称
     */
    public static String getNickName() {
        String ticket = UserInfoSPUtils.getInstance().getString("nick_name");
        return ticket;
    }

    /**
     * 获取用户头像
     */
    public static String getUserHeaderImg() {
        String ticket = UserInfoSPUtils.getInstance().getString("user_header_img");
        return ticket;
    }

    /**
     * 获取融云ID
     */
    public static String getUserId() {
        String ticket = UserInfoSPUtils.getInstance().getString("user_id");
        return ticket;
    }
}
