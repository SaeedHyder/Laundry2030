package com.ingic.mylaundry.models;

/**
 * Created by adnanahmed on 3/6/2018.
 */

public class NewOrderDetailWraper {

    private int id;
    private String order_id;
    private String item_id;
    private String user_id;
    private int quantity;
    private String amount;
    private String status;
    private String created_at;
    private OrderItemDetailWraper item_detail;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return this.order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public String getItemId() {
        return this.item_id;
    }

    public void setItemId(String item_id) {
        this.item_id = item_id;
    }

    public String getUserId() {
        return this.user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public OrderItemDetailWraper getItemDetail() {
        return this.item_detail;
    }

    public void setItemDetail(OrderItemDetailWraper item_detail) {
        this.item_detail = item_detail;
    }

}
