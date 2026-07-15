package com.example.project_news.hawkerboard;

public class HawkerBean {

    String hawkerid,name,contact,address,selareas;

    public String getHawkerid() {
        return hawkerid;
    }

    public void setHawkerid(String hawkerid) {
        this.hawkerid = hawkerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSelareas() {
        return selareas;
    }

    public void setSelareas(String selareas) {
        this.selareas = selareas;
    }

    public HawkerBean(String hawkerid, String name, String contact, String address, String selareas) {
        this.hawkerid = hawkerid;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.selareas = selareas;
    }
}
