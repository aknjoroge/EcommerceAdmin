package com.example.porkpitadmin;

public class forusers {
    public forusers(){ }

    String name,phone,mail,location;

    public String getName() {
        return name;
    }

    public forusers(String name, String phone, String mail, String location) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getmail() {
        return mail;
    }

    public void setmail(String mail) {
        this.mail = mail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
