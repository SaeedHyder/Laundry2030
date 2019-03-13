package com.ingic.mylaundry.models;

import java.io.Serializable;

/**
 * Created by adnanahmed on 2/14/2018.
 */

public class OrderInstructionWraper implements Serializable{
    String other;
    String is_fold;
    String at_my_door;
    String call_me_before_pickup;
    String call_me_before_delivery;

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getIs_fold() {
        return is_fold;
    }

    public void setIs_fold(String is_fold) {
        this.is_fold = is_fold;
    }

    public String getAt_my_door() {
        return at_my_door;
    }

    public void setAt_my_door(String at_my_door) {
        this.at_my_door = at_my_door;
    }

    public String getCall_me_before_pickup() {
        return call_me_before_pickup;
    }

    public void setCall_me_before_pickup(String call_me_before_pickup) {
        this.call_me_before_pickup = call_me_before_pickup;
    }

    public String getCall_me_before_delivery() {
        return call_me_before_delivery;
    }

    public void setCall_me_before_delivery(String call_me_before_delivery) {
        this.call_me_before_delivery = call_me_before_delivery;
    }
}
