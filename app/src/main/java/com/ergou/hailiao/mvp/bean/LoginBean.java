package com.ergou.hailiao.mvp.bean;


/**
 * Created by LuoCy on 2017/9/26.
 */

public class LoginBean {


    /**
     * token : 4sCNSzqX+SyUdKWCYWLTX303ftBhTVqaQtHwhKZiey91+iJfSlIav5Hjvo6gTW7T2+4IkdmkYdVeSLgOXge+gojVMJBqZthA
     */

    private String nick_name;//用户昵称
    private String user_header_img;//用户头像
    private String rong_token;//用户融云注册token
    private String user_id;//用户融云ID

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_header_img() {
        return user_header_img;
    }

    public void setUser_header_img(String user_header_img) {
        this.user_header_img = user_header_img;
    }

    public String getRong_token() {
        return rong_token;
    }

    public void setRong_token(String rong_token) {
        this.rong_token = rong_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
