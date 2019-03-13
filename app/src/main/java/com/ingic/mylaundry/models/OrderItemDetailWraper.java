package com.ingic.mylaundry.models;

/**
 * Created by adnanahmed on 3/6/2018.
 */

public class OrderItemDetailWraper {

    private int id;
    private String title;
    private String type_id;
    private String image;
    private String amount;
    private String status;
    private String created_at;
    private String updated_at;
    private OrderParentTypeWraper parenttype;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeId() {
        return this.type_id;
    }

    public void setTypeId(String type_id) {
        this.type_id = type_id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getUpdatedAt() {
        return this.updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public OrderParentTypeWraper getParenttype() {
        return this.parenttype;
    }

    public void setParenttype(OrderParentTypeWraper parenttype) {
        this.parenttype = parenttype;
    }

}
