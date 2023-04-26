package com.aysegulyilmaz.barkodproje;


public class RVModal {
    private String name,country,currency,price;



    public RVModal(String name, String country, String currency, String price) {
        this.name = name;
        this.country = country;
        this.currency = currency;
        this.price = price;



    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }




}


