package com.eiadmreh.listviewwithimg;

public class Frouit {
    private String Name;
    private long Code;
    private double Price;
    private String imageUrl;

    public Frouit() {
    }

    public Frouit(String tName, long tCode, double tPrice, String imageUrl) {
        this.Name = tName;
        this.Code = tCode;
        this.Price = tPrice;
        this.imageUrl=imageUrl;
    }

    public String gettName() {
        return Name;
    }

    public void settName(String tName) {
        this.Name = tName;
    }

    public long gettCode() {
        return Code;
    }

    public void settCode(long tCode) {
        this.Code = tCode;
    }

    public double gettPrice() {
        return Price;
    }

    public void settPrice(double tPrice) {
        this.Price = tPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "(Frouits)-> Code=" + Code +
                "\nName=" + Name +
                "\nPrice=" + Price +
                '}';
    }
}
