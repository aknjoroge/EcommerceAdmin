package com.example.porkpitadmin;

import java.util.HashMap;
import java.util.Map;

public class fororders {

public  fororders(){

}


String tobepaid,transactiontype,paidname,location,userphone,mail,time,date,randomkey,userid;

    public String getRandomkey() {
        return randomkey;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setRandomkey(String randomkey) {
        this.randomkey = randomkey;
    }

    public String getTobepaid() {
        return tobepaid;
    }

    public void setTobepaid(String tobepaid) {
        this.tobepaid = tobepaid;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getPaidname() {
        return paidname;
    }

    public void setPaidname(String paidname) {
        this.paidname = paidname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public fororders(String tobepaid, String transactiontype,String userid, String paidname, String location, String userphone, String mail, String time,String randomkey, String date) {
        this.tobepaid = tobepaid;
        this.transactiontype = transactiontype;
        this.paidname = paidname;
        this.location = location;
        this.userphone = userphone;
        this.randomkey = randomkey;
        this.userid = userid;
        this.mail = mail;
        this.time = time;
        this.date = date;
    }
}
