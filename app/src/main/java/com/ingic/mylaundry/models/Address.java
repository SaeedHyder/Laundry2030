package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by khanubaid on 2/6/2018.
 */

    public class Address implements Serializable{


    @SerializedName("data")
    @Expose
    ArrayList<AdressData> data = null;



    @SerializedName("pages")
    @Expose
    private Integer pages;

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }



    public ArrayList<AdressData> getData() {
        return data;
    }

    public void setData(ArrayList<AdressData> data) {
        this.data = data;
    }
}
