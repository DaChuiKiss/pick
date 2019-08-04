package com.ergou.hailiao.mvp.bean;

/**
 * Created by KissDa on 2018/8/13.
 */

public class ProfitBean {

    /**
     * invest_earnings : 2.91532320
     * recommend_earnings : 0.40325967
     * chained_earnings : 0.13441989
     * team_earnings : 0.08850705
     * today_earnings : 3.54150981
     * history_earnings : 3.54150981
     */

    private String invest_earnings;//用户今日投资收益
    private String recommend_earnings;//用户今日直推好友收益
    private String chained_earnings;//用户今日链接收益
    private String team_earnings;//用户今日团队收益
    private String today_earnings;//用户今日总收益
    private String history_earnings;//用户历史总收益

    public String getInvest_earnings() {
        return invest_earnings;
    }

    public void setInvest_earnings(String invest_earnings) {
        this.invest_earnings = invest_earnings;
    }

    public String getRecommend_earnings() {
        return recommend_earnings;
    }

    public void setRecommend_earnings(String recommend_earnings) {
        this.recommend_earnings = recommend_earnings;
    }

    public String getChained_earnings() {
        return chained_earnings;
    }

    public void setChained_earnings(String chained_earnings) {
        this.chained_earnings = chained_earnings;
    }

    public String getTeam_earnings() {
        return team_earnings;
    }

    public void setTeam_earnings(String team_earnings) {
        this.team_earnings = team_earnings;
    }

    public String getToday_earnings() {
        return today_earnings;
    }

    public void setToday_earnings(String today_earnings) {
        this.today_earnings = today_earnings;
    }

    public String getHistory_earnings() {
        return history_earnings;
    }

    public void setHistory_earnings(String history_earnings) {
        this.history_earnings = history_earnings;
    }
}
