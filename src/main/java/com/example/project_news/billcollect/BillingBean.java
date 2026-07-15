package com.example.project_news.billcollect;

public class BillingBean {
    String dos,doe,days,lessdays,bill,status;

    public BillingBean(String dos, String doe, String days, String lessdays, String bill) {
        this.dos = dos;
        this.doe = doe;
        this.days = days;
        this.lessdays = lessdays;
        this.bill = bill;
    }
    public BillingBean(String dos, String doe, String days, String lessdays, String bill, String status) {
        this.dos = dos;
        this.doe = doe;
        this.days = days;
        this.lessdays = lessdays;
        this.bill = bill;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getDoe() {
        return doe;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLessdays() {
        return lessdays;
    }

    public void setLessdays(String lessdays) {
        this.lessdays = lessdays;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
