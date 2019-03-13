package com.ingic.mylaundry.models;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/22/2018.
 */

public class ServiceAllItem {

    String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<ServiceModel> getServiceModels() {
        return serviceModels;
    }

    public void setServiceModels(ArrayList<ServiceModel> serviceModels) {
        this.serviceModels = serviceModels;
    }

    public ServiceAllItem(String title, ArrayList<ServiceModel> serviceModels) {

        Title = title;
        this.serviceModels = serviceModels;
    }

    ArrayList<ServiceModel> serviceModels= new ArrayList<>();
}
