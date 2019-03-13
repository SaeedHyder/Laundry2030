package com.ingic.mylaundry.models;

/**
 * Created by adnanahmed on 4/13/2018.
 */

public class InstructionWraper {
    int id;
    int user_id;
    int is_fold;
    int call_me_before_delivery;
    int at_my_door;
    int call_me_before_pickup;

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    boolean check;



    String other;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_fold() {
        return is_fold;
    }

    public void setIs_fold(int is_fold) {
        this.is_fold = is_fold;
    }

    public int getCall_me_before_delivery() {
        return call_me_before_delivery;
    }

    public void setCall_me_before_delivery(int call_me_before_delivery) {
        this.call_me_before_delivery = call_me_before_delivery;
    }

    public int getAt_my_door() {
        return at_my_door;
    }

    public void setAt_my_door(int at_my_door) {
        this.at_my_door = at_my_door;
    }

    public int getCall_me_before_pickup() {
        return call_me_before_pickup;
    }

    public void setCall_me_before_pickup(int call_me_before_pickup) {
        this.call_me_before_pickup = call_me_before_pickup;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
