package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by khanubaid on 2/6/2018.
 */

public class AvailableTimeSlotWrapper implements Serializable {

    @SerializedName("availableTimeSlot")
    @Expose
    private ArrayList<AvailableTimeSlot> availableTimeSlot = null;

    public AvailableTimeSlotWrapper(ArrayList<AvailableTimeSlot> availableTimeSlot) {
        this.availableTimeSlot = availableTimeSlot;
    }

    public ArrayList<AvailableTimeSlot> getAvailableTimeSlot() {

        return availableTimeSlot;
    }

    public void setAvailableTimeSlot(ArrayList<AvailableTimeSlot> availableTimeSlot) {
        this.availableTimeSlot = availableTimeSlot;
    }
}
