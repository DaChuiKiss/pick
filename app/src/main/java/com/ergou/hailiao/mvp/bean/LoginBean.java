package com.ergou.hailiao.mvp.bean;


/**
 * Created by LuoCy on 2017/9/26.
 */

public class LoginBean  {


    /**
     * phone : 18622223333
     * pwd :
     * grade    （0:普遍用户（灰色）；1:会员（淡红色）；2:精灵（绿色）；3:牧师（蓝色）；4:堡主（金色））

     */

    private String phone;
    private String pwd;
    private String key;
    private String token;
    private String grade;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
