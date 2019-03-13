package com.ingic.mylaundry.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adnanahmed on 2/15/2018.
 */

public class OrderPostWraper implements Serializable {
    String user_id;
    Double amount;
    String pickup_location;
    String pick_type;
    String drop_location;
    String pickup_latitude;
    String pickup_longitude;
    String dropup_latitude;
    String dropup_longitude;
    String drop_type;
    String slot;
    String delivery_slot;
    String pickup_date;
    String delivery_date;

    public void setRedeemed_code_amount(Double redeemed_code_amount) {
        this.redeemed_code_amount = redeemed_code_amount;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    Double delivery_amount;
    Double total_amount;
    String payment_type;
    String subCharge;
    String redeemed_code;
    Double redeemed_code_amount;
    String payment_date;
    String transaction_id;

    ArrayList<OrderDetailWraper> order_detail;
    OrderInstructionWraper order_instructions;

    public OrderInstructionWraper getOrder_instruction() {
        return order_instructions;
    }

    public void setOrder_instruction(OrderInstructionWraper order_instruction) {
        this.order_instructions = order_instruction;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
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

    public String getDropup_latitude() {
        return dropup_latitude;
    }

    public void setDropup_latitude(String dropup_latitude) {
        this.dropup_latitude = dropup_latitude;
    }

    public String getDropup_longitude() {
        return dropup_longitude;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public OrderInstructionWraper getOrder_instructions() {
        return order_instructions;
    }

    public void setOrder_instructions(OrderInstructionWraper order_instructions) {
        this.order_instructions = order_instructions;
    }

    public void setDropup_longitude(String dropup_longitude) {
        this.dropup_longitude = dropup_longitude;
    }

    public String getDrop_type() {
        return drop_type;
    }

    public void setDrop_type(String drop_type) {
        this.drop_type = drop_type;
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

    public Double getDelivery_amount() {
        return delivery_amount;
    }

    public void setDelivery_amount(Double delivery_amount) {
        this.delivery_amount = delivery_amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public ArrayList<OrderDetailWraper> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(ArrayList<OrderDetailWraper> order_detail) {
        this.order_detail = order_detail;
    }

    public String getSubCharge() {
        return subCharge;
    }

    public void setSubCharge(String subCharge) {
        this.subCharge = subCharge;
    }

    public String getRedeemed_code() {
        return redeemed_code;
    }

    public void setRedeemed_code(String redeemed_code) {
        this.redeemed_code = redeemed_code;
    }

    public Double getRedeemed_code_amount() {
        return redeemed_code_amount;
    }

    public void setRedeemed_code_amount(double redeemed_code_amount) {
        this.redeemed_code_amount = redeemed_code_amount;
    }
}
