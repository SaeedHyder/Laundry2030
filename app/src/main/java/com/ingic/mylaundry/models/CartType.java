package com.ingic.mylaundry.models;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/30/2018.
 */

public class CartType {

    String Title;
    String image;
    ArrayList<Item> itemArrayList = new ArrayList<>();

    public CartType(String title, String image, ArrayList<Item> itemArrayList) {
        Title = title;
        this.image = image;
        this.itemArrayList = itemArrayList;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }
}
