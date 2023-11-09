package com.example.bottomnavigation;

public class MyOrderItemModel {

    private int productImage;
    private String productTitle;
    private String deliverystatus;

    public MyOrderItemModel(int productImage, String productTitle, String deliverystatus) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.deliverystatus = deliverystatus;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }
}
