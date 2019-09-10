package com.ergou.hailiao.mvp.bean;

import java.util.List;

/**
 * Created by LuoCY on 2019/9/6.
 */
public class RedEnvelopeGrabBean {


    /**
     * send : {"nick_name":"狗大爷只嘛","user_header_img":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=311097710,965735535&fm=26&gp=0.jpg","money":"200.00","num":7,"thunder_num":"1","create_time":"2019-09-10 22:37:25","done_rob":"00分22秒"}
     * memberRob : []
     * allRob : [{"nick_name":"来啊,踩雷啊","user_header_img":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1801471742,960458455&fm=26&gp=0.jpg","money":"17.71","is_bomb":2,"create_time":"22:37:47","luck":0},{"nick_name":"我是谁我在哪","user_header_img":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=285838770,346577304&fm=11&gp=0.jpg","money":"12.01","is_bomb":2,"create_time":"22:37:45","luck":0},{"nick_name":"运气帮帮de","user_header_img":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4208386305,57701306&fm=26&gp=0.jpg","money":"8.93","is_bomb":1,"create_time":"22:37:44","luck":0},{"nick_name":"狗闪闪","user_header_img":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2542141335,292865164&fm=26&gp=0.jpg","money":"6.02","is_bomb":1,"create_time":"22:37:43","luck":0},{"nick_name":"爱老虎油","user_header_img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2534898382,1324912624&fm=26&gp=0.jpg","money":"52.33","is_bomb":1,"create_time":"22:37:42","luck":0},{"nick_name":"楼房无心","user_header_img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4080336380,3661090732&fm=26&gp=0.jpg","money":"19.89","is_bomb":1,"create_time":"22:37:41","luck":0},{"nick_name":"免死","user_header_img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2735637762,1977390426&fm=26&gp=0.jpg","money":"83.11","is_bomb":2,"create_time":"22:37:25","luck":1}]
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
         * nick_name : 狗大爷只嘛
         * user_header_img : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=311097710,965735535&fm=26&gp=0.jpg
         * money : 200.00
         * num : 7
         * thunder_num : 1
         * create_time : 2019-09-10 22:37:25
         * done_rob : 00分22秒
         */

        private String nick_name;
        private String user_header_img;
        private String money;
        private String num;
        private String thunder_num;
        private String create_time;
        private String done_rob;

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

        public String getDone_rob() {
            return done_rob;
        }

        public void setDone_rob(String done_rob) {
            this.done_rob = done_rob;
        }
    }

    public static class AllRobBean {
        /**
         * nick_name : 来啊,踩雷啊
         * user_header_img : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1801471742,960458455&fm=26&gp=0.jpg
         * money : 17.71
         * is_bomb : 2
         * create_time : 22:37:47
         * luck : 0
         */

        private String nick_name;
        private String user_header_img;
        private String money;
        private String is_bomb;
        private String create_time;
        private String luck;

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

        public String getLuck() {
            return luck;
        }

        public void setLuck(String luck) {
            this.luck = luck;
        }
    }
}
