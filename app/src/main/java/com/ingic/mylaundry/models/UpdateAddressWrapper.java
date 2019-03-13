package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by khanubaid on 4/11/2018.
 */

public class UpdateAddressWrapper implements Serializable {

    @SerializedName("address")
    @Expose
    AddAddress addAddress;

    public AddAddress getAddAddress() {
        return addAddress;
    }

    public void setAddAddress(AddAddress addAddress) {
        this.addAddress = addAddress;
    }
}
