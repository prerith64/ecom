package com.example.bottomnavigation;

public class HorizontalProductScrollModel {

    private String productID;
    private String productImage;
    private String productTitle;
    private String productDiscription;
    private String productPrice;

    public HorizontalProductScrollModel(String productID,String productImage, String productTitle, String productDiscription, String productPrice) {
        this.productImage = productImage;
        this.productID = productID;
        this.productTitle = productTitle;
        this.productDiscription = productDiscription;
        this.productPrice = productPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDiscription() {
        return productDiscription;
    }

    public void setProductDiscription(String productDiscription) {
        this.productDiscription = productDiscription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
