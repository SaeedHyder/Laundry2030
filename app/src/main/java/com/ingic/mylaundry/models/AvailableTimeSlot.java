package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by khanubaid on 2/6/2018.
 */

public class AvailableTimeSlot implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("slot_time")
    @Expose
    private String slotTime;
    @SerializedName("order_per_slot")
    @Expose
    private String orderPerSlot;
    @SerializedName("instance_surcharge")
    @Expose
    private String instance_surcharge;

    public String getInstance_surcharge() {
        return instance_surcharge;
    }

    public void setInstance_surcharge(String instance_surcharge) {
        this.instance_surcharge = instance_surcharge;
    }

    public AvailableTimeSlot(Integer id, String slotTime, String orderPerSlot) {
        this.id = id;
        this.slotTime = slotTime;
        this.orderPerSlot = orderPerSlot;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getOrderPerSlot() {
        return orderPerSlot;
    }

    public void setOrderPerSlot(String orderPerSlot) {
        this.orderPerSlot = orderPerSlot;
    }
}
