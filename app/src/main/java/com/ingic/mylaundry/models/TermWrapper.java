package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khanubaid on 2/6/2018.
 */

public class TermWrapper  {

    public Crm getCrm() {
        return crm;
    }

    public void setCrm(Crm crm) {
        this.crm = crm;
    }

    @SerializedName("crm")
    @Expose
    private Crm crm;
}
