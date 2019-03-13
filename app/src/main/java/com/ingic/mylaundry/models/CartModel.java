package com.ingic.mylaundry.models;

import java.util.ArrayList;



public class CartModel {

    ArrayList<CartType> typeArrayList = new ArrayList<>();

    String count;

    public ArrayList<CartType> getTypeArrayList() {
        return typeArrayList;
    }

    public void setTypeArrayList(ArrayList<CartType> typeArrayList) {
        this.typeArrayList = typeArrayList;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public CartModel(ArrayList<CartType> typeArrayList, String count) {

        this.typeArrayList = typeArrayList;
        this.count = count;
    }
}
