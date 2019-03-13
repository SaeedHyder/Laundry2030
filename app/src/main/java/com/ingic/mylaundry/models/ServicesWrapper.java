package com.ingic.mylaundry.models;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by khanubaid on 1/26/2018.
 */

public class ServicesWrapper implements Serializable {
    @SerializedName("services")
    @Expose
    private ArrayList<Services>  services;

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }
}
