package com.ergou.hailiao.mvp.bean;

import java.util.List;

/**
 * Created by LuoCY on 2019/9/22.
 */
public class MemberListBean {

    private String total;
    private List<ListsBean> lists;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * user_id : 450920
         * nick_name : 免死
         * user_header_img : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2735637762,1977390426&fm=26&gp=0.jpg
         */

        private int user_id;
        private String nick_name;
        private String user_header_img;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
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
}
