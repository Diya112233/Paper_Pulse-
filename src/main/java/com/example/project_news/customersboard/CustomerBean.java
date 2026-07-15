package com.example.project_news.customersboard;

public class CustomerBean {
    String mobile, cname, address, area, hawkerid, papers;

    public CustomerBean(String mobile, String cname, String address, String area, String hawkerid, String papers) {
        this.mobile = mobile;
        this.cname = cname;
        this.address = address;
        this.area = area;
        this.hawkerid = hawkerid;
        this.papers = papers;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCname() {
        return cname;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getHawkerid() {
        return hawkerid;
    }

    public String getPapers() {
        return papers;
    }
}
