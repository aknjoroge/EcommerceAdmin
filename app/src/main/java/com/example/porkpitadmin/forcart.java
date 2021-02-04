package com.example.porkpitadmin;

public class forcart {

    public forcart(){
    }
String username,phone,time,date,productname,price,amount,productid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public forcart(String username, String phone, String time, String date, String productname, String price, String amount, String productid) {
        this.username = username;
        this.phone = phone;
        this.time = time;
        this.date = date;
        this.productname = productname;
        this.price = price;
        this.amount = amount;
        this.productid = productid;
    }
}
