package com.ergou.hailiao.mvp.bean;

public class SearchMailListBean {

    /**
     * user_id : 90625
     * nick_name : 你妈喊你吃饭
     * user_header_img : https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=511793291,3622256371&fm=26&gp=0.jpg
     */

    private String user_id;
    private String nick_name;
    private String user_header_img;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

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
}
