package com.ergou.hailiao.mvp.bean;

/**
 * Created by KissDa on 2018/8/7.
 */

public class MagicianBean {

    /**
     * name : BTC
     * is_open : 0
     * open_num : 0
     * icon : http://image.studyqian.top/BTC.png
     */

    private String name;//币种名称
    private String is_open;//是否开启（0:关闭；1:开启）
    private String open_num;//开启数量
    private String icon;//图标

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getOpen_num() {
        return open_num;
    }

    public void setOpen_num(String open_num) {
        this.open_num = open_num;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
