package com.ingic.mylaundry.models;

import com.ingic.mylaundry.models.Item;

import java.io.Serializable;

/**
 * Created by adnanahmed on 2/14/2018.
 */

public class OrderDetailWraper implements Serializable{

    String item_id;
    String quantity;
    Double amount;

    public OrderDetailWraper(Item items) {
        setItem_id(items.getId() + "");
        setQuantity(items.getQuantity() + "");
        setAmount((Double.parseDouble(items.getAmount()) * items.getQuantity()));
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
