package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by khanubaid on 1/29/2018.
 */

public class Services implements Serializable {
    String id;
    String title;
    int status;
    String created_at;
    String updated_at;
    @SerializedName("service_type")
    @Expose
    private ArrayList<ServiceType> serviceType = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<ServiceType> getServiceType() {
        return serviceType;
    }

    public void setServiceType(ArrayList<ServiceType> serviceType) {
        this.serviceType = serviceType;
    }
}
