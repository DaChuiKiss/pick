package com.ergou.hailiao.mvp.bean;

import java.util.List;

/**
 * Created by LuoCY on 2019/9/6.
 */
public class RedEnvelopeGrab {

    /**
     * send : {"nick_name":"我去","user_header_img":"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3146277763,293920617&fm=11&gp=0.jpg","money":"88.00","num":7,"thunder_num":"6","create_time":"2019-08-29 08:08:57"}
     * memberRob : {"money":"32.10","is_bomb":1}
     * allRob : [{"nick_name":"come on","user_header_img":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2542141335,292865164&fm=26&gp=0.jpg","money":"32.77","is_bomb":1,"create_time":"11:20:17"},{"nick_name":"我去","user_header_img":"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3146277763,293920617&fm=11&gp=0.jpg","money":"32.10","is_bomb":1,"create_time":"08:09:00"},{"nick_name":"嗨聊","user_header_img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4080336380,3661090732&fm=26&gp=0.jpg","money":"2.07","is_bomb":1,"create_time":"08:08:57"}]
     */

    private SendBean send;
    private MemberRobBean memberRob;
    private List<AllRobBean> allRob;

    public SendBean getSend() {
        return send;
    }

    public void setSend(SendBean send) {
        this.send = send;
    }

    public MemberRobBean getMemberRob() {
        return memberRob;
    }

    public void setMemberRob(MemberRobBean memberRob) {
        this.memberRob = memberRob;
    }

    public List<AllRobBean> getAllRob() {
        return allRob;
    }

    public void setAllRob(List<AllRobBean> allRob) {
        this.allRob = allRob;
    }

    public static class SendBean {
        /**
         * nick_name : 我去
         * user_header_img : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3146277763,293920617&fm=11&gp=0.jpg
         * money : 88.00
         * num : 7
         * thunder_num : 6
         * create_time : 2019-08-29 08:08:57
         */

        private String nick_name;
        private String user_header_img;
        private String money;
        private String num;
        private String thunder_num;
        private String create_time;

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

        public String getThunder_num() {
            return thunder_num;
        }

        public void setThunder_num(String thunder_num) {
            this.thunder_num = thunder_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class MemberRobBean {
        /**
         * money : 32.10
         * is_bomb : 1
         */

        private String money;
        private String is_bomb;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getIs_bomb() {
            return is_bomb;
        }

        public void setIs_bomb(String is_bomb) {
            this.is_bomb = is_bomb;
        }
    }

    public static class AllRobBean {
        /**
         * nick_name : come on
         * user_header_img : https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2542141335,292865164&fm=26&gp=0.jpg
         * money : 32.77
         * is_bomb : 1
         * create_time : 11:20:17
         */

        private String nick_name;
        private String user_header_img;
        private String money;
        private String is_bomb;
        private String create_time;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getIs_bomb() {
            return is_bomb;
        }

        public void setIs_bomb(String is_bomb) {
            this.is_bomb = is_bomb;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
