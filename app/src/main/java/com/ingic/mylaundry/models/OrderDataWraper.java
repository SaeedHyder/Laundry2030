package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by adnanahmed on 3/3/2018.
 */

public class OrderDataWraper {

    int id;
    int user_id;
    String pickup_location;
    String pick_type;
    String pickup_latitude;
    String pickup_longitude;
    String drop_location;
    String drop_type;
    String dropup_latitude;
    String dropup_longitude;
    String delivery_slot;
    String slot;
    String pickup_date;
    String delivery_date;
    String delivery_amount;
    String amount;
    String total_amount;
    int order_status;
    String payment_type;
    String payment_responce;
    String status;
    String created_at;
    String updated_at;
    String deleted_at;

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

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getPick_type() {
        return pick_type;
    }

    public void setPick_type(String pick_type) {
        this.pick_type = pick_type;
    }

    public String getPickup_latitude() {
        return pickup_latitude;
    }

    public void setPickup_latitude(String pickup_latitude) {
        this.pickup_latitude = pickup_latitude;
    }

    public String getPickup_longitude() {
        return pickup_longitude;
    }

    public void setPickup_longitude(String pickup_longitude) {
        this.pickup_longitude = pickup_longitude;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getDrop_type() {
        return drop_type;
    }

    public void setDrop_type(String drop_type) {
        this.drop_type = drop_type;
    }

    public String getDropup_latitude() {
        return dropup_latitude;
    }

    public void setDropup_latitude(String dropup_latitude) {
        this.dropup_latitude = dropup_latitude;
    }

    public String getDropup_longitude() {
        return dropup_longitude;
    }

    public void setDropup_longitude(String dropup_longitude) {
        this.dropup_longitude = dropup_longitude;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_amount() {
        return delivery_amount;
    }

    public void setDelivery_amount(String delivery_amount) {
        this.delivery_amount = delivery_amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_responce() {
        return payment_responce;
    }

    public void setPayment_responce(String payment_responce) {
        this.payment_responce = payment_responce;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
