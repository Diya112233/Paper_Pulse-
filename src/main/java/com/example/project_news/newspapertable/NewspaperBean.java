package com.example.project_news.newspapertable;

public class NewspaperBean {
    String paper,language,price;

    public NewspaperBean(String paper, String language, String price) {
        this.paper = paper;
        this.language = language;
        this.price = price;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
