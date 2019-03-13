package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by khanubaid on 4/4/2018.
 */

public class OrderWrapperNotification implements Serializable{
    @SerializedName("order")
    @Expose
    Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
