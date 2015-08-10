package com.tom.atm;

/**
 * Created by Administrator on 2015/8/10.
 */
public class History {
    String date;
    String userid;
    int amount;
    public History(){

    }

    public History(String date, String userid, int amount) {
        this.date = date;
        this.userid = userid;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
