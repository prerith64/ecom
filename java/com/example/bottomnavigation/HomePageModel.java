package com.example.bottomnavigation;

import java.util.List;

public class HomePageModel {
      public  static final int BANNER_SLIDER=0;
      public static final int STRIP_AD_BANNER=1;
    public static final int HORIZONTAL_PRODUCT_VIEW=2;
    public static final int GRID_PRODUCT_VIEW=3;
    public static final int WISHLIST_VIEW=4;

    private int type;
    private String backgroundColor;
    ///////////////// banner slider
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ///////////////// banner slider


    /////////// strip ad

    private String resource;


    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /////////// strip ad

    /////////// Horizontal product layout  && Grid product Layout
     private String Title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelLIst;
    private List<WishlistModel> viewAllProductList;
    public HomePageModel(int type, String title, String backgroundColor ,List<HorizontalProductScrollModel> horizontalProductScrollModelLIst,List<WishlistModel> viewAllProductList) {
        this.type = type;
        this.viewAllProductList=viewAllProductList;
        this.backgroundColor=backgroundColor;
        Title = title;
        this.horizontalProductScrollModelLIst = horizontalProductScrollModelLIst;
    }

    public List<WishlistModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    public HomePageModel(int type, String title, String backgroundColor , List<HorizontalProductScrollModel> horizontalProductScrollModelLIst) {
        this.type = type;
        this.backgroundColor=backgroundColor;
        Title = title;
        this.horizontalProductScrollModelLIst = horizontalProductScrollModelLIst;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelLIst() {
        return horizontalProductScrollModelLIst;
    }

    public void setHorizontalProductScrollModelLIst(List<HorizontalProductScrollModel> horizontalProductScrollModelLIst) {
        this.horizontalProductScrollModelLIst = horizontalProductScrollModelLIst;
    }
    /////////// Horizontal product layout

////wishlist layout


    private List<WishlistModel> viewAllProduct;
    public HomePageModel(int type,String backgroundColor,List<WishlistModel> viewAllProduct) {
        this.type = type;
        this.viewAllProduct = viewAllProduct;
        this.backgroundColor=backgroundColor;
    }

    public List<WishlistModel> getViewAllProduct() {
        return viewAllProduct;
    }

    public void setViewAllProduct(List<WishlistModel> viewAllProduct) {
        this.viewAllProduct = viewAllProduct;
    }
////wishlist layout

}
