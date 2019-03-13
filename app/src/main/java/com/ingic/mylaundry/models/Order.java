package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by khanubaid on 2/12/2018.
 */

public class Order implements Serializable {
    OrderInstructionWraper order_instructions;
    ArrayList<NewOrderDetailWraper> order_detail;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("amount")

    @Expose
    private Double amount;
    @SerializedName("pickup_location")
    @Expose
    private String pickupLocation;
    @SerializedName("pick_type")
    @Expose
    private String pickType;
    @SerializedName("drop_location")
    @Expose
    private String dropLocation;
    @SerializedName("pickup_latitude")
    @Expose
    private Double pickupLatitude;
    @SerializedName("pickup_longitude")
    @Expose
    private Double pickupLongitude;
    @SerializedName("dropup_latitude")
    @Expose
    private Double dropupLatitude;
    @SerializedName("dropup_longitude")
    @Expose
    private Double dropupLongitude;
    @SerializedName("drop_type")
    @Expose
    private String dropType;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("delivery_slot")
    @Expose
    private String delivery_slot;
    @SerializedName("pickup_date")
    @Expose
    private String pickupDate;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("delivery_amount")
    @Expose
    private Integer deliveryAmount;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("order_status")
    @Expose
    private int orderStatus;



    private String redeemed_code_amount;

    private String redeemed_code;

    public String getRedeem_amount() {
        return redeemed_code_amount;
    }

    public void setRedeem_amount(String redeem_amount) {
        this.redeemed_code_amount = redeem_amount;
    }

    public String getReedem_code() {
        return redeemed_code;
    }

    public void setReedem_code(String reedem_code) {
        this.redeemed_code = reedem_code;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<NewOrderDetailWraper> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(ArrayList<NewOrderDetailWraper> order_detail) {
        this.order_detail = order_detail;
    }

    public OrderInstructionWraper getOrder_instructions() {
        return order_instructions;
    }

    public void setOrder_instructions(OrderInstructionWraper order_instructions) {
        this.order_instructions = order_instructions;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getPickType() {
        return pickType;
    }

    public void setPickType(String pickType) {
        this.pickType = pickType;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(Double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(Double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public Double getDropupLatitude() {
        return dropupLatitude;
    }

    public void setDropupLatitude(Double dropupLatitude) {
        this.dropupLatitude = dropupLatitude;
    }

    public Double getDropupLongitude() {
        return dropupLongitude;
    }

    public void setDropupLongitude(Double dropupLongitude) {

        this.dropupLongitude = dropupLongitude;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public String getDropType() {
        return dropType;
    }

    public void setDropType(String dropType) {
        this.dropType = dropType;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(Integer deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
