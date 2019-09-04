package com.ergou.hailiao.mvp.bean;

import java.util.List;

public class GameBean {


    private List<GroupBean> group;

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class GroupBean {
        /**
         * group_id : 10001
         * group_name : 测试群组
         */

        private String group_id;
        private String group_name;
        private String group_img;
        private String type;//1:群组；2:聊天室\
        private String mark;//
        private String button;//是否有交互，0没有，1有
        private String is_fuli;//isfuli是是否为福利房间


        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getGroup_img() {
            return group_img;
        }

        public void setGroup_img(String group_img) {
            this.group_img = group_img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getButton() {
            return button;
        }

        public void setButton(String button) {
            this.button = button;
        }

        public String getIs_fuli() {
            return is_fuli;
        }

        public void setIs_fuli(String is_fuli) {
            this.is_fuli = is_fuli;
        }
    }
}
