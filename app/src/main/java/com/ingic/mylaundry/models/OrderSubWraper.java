package com.ingic.mylaundry.models;

import java.util.ArrayList;

/**
 * Created by adnanahmed on 3/3/2018.
 */

public class OrderSubWraper {
    // OrderSubSubWraper order;
    ArrayList<Order> data;
    int pages;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    //    public OrderSubSubWraper getOrder() {
//        return order;
//    }

//    public void setOrder(OrderSubSubWraper order) {
//        this.order = order;
//    }

    public ArrayList<Order> getData() {
        return data;
    }

    public void setData(ArrayList<Order> data) {
        this.data = data;
    }
}
