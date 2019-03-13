package com.ingic.mylaundry.models;

/**
 * Created by khanubaid on 1/20/2018.
 */

public class ServiceModel {

    int image;
    String Title;
    int price;
    int quantity;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ServiceModel(int image, String title, int price, int quantity) {

        this.image = image;
        Title = title;
        this.price = price;
        this.quantity = quantity;
    }
}
