package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by khanubaid on 2/12/2018.
 */

public class OrderWrapper implements Serializable {

    @SerializedName("order")
    @Expose

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
