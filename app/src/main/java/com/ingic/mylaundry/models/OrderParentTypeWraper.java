package com.ingic.mylaundry.models;

/**
 * Created by adnanahmed on 3/6/2018.
 */

public class OrderParentTypeWraper {

    private int id;
    private String title;
    private String service_id;
    private String status;
    private String created_at;
    private OrderParentWraper parent;

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

    public String getServiceId() {
        return this.service_id;
    }

    public void setServiceId(String service_id) {
        this.service_id = service_id;
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

    public OrderParentWraper getParent() {
        return this.parent;
    }

    public void setParent(OrderParentWraper parent) {
        this.parent = parent;
    }

}
