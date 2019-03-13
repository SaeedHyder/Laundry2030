
package com.ingic.mylaundry.models;

import java.io.Serializable;

/**
 * Created by khanubaid on 2/8/2018.
 */

public class ScheduleOrderModel implements Serializable {

    String pickupAddress;
    String pickupDate;
    String pickupTime;
    String pickupLat;
    String pickupLong;
    String pickupLocation;
    String pickupSurcharge;
    String pickupType;
    String pickupTypeService;

    String deliveryLat;
    String deliveryLong;
    String deliveryType;
    String deliveryLocation;
    String deliveryAddress;
    String deliveryDate;
    String deliveryTime;
    String deliveryTypeService;

    String deliverySurcharge;
    String is_redeemed_code;
    double is_redeemed_amount;
    double totalPayment;

    String EditProfleAddress;

    public String getEditProfleAddress() {
        return EditProfleAddress;
    }

    public void setEditProfleAddress(String editProfleAddress) {
        EditProfleAddress = editProfleAddress;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getIs_redeemed_code() {
        return is_redeemed_code;
    }

    public void setIs_redeemed_code(String is_redeemed_code) {
        this.is_redeemed_code = is_redeemed_code;
    }

    public String getPickupTypeService() {
        return pickupTypeService;
    }

    public void setPickupTypeService(String pickupTypeService) {
        this.pickupTypeService = pickupTypeService;
    }

    public String getDeliveryTypeService() {
        return deliveryTypeService;
    }

    public void setDeliveryTypeService(String deliveryTypeService) {
        this.deliveryTypeService = deliveryTypeService;
    }

    public double getIs_redeemed_amount() {
        return is_redeemed_amount;
    }

    public void setIs_redeemed_amount(double is_redeemed_amount) {
        this.is_redeemed_amount = is_redeemed_amount;
    }

    boolean isInstant;

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public boolean isInstant() {
        return isInstant;
    }

    public void setInstant(boolean instant) {
        isInstant = instant;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(String pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getPickupLong() {
        return pickupLong;
    }

    public void setPickupLong(String pickupLong) {
        this.pickupLong = pickupLong;
    }

    public String getDeliveryLat() {
        return deliveryLat;
    }

    public void setDeliveryLat(String deliveryLat) {
        this.deliveryLat = deliveryLat;
    }

    public String getDeliveryLong() {
        return deliveryLong;
    }

    public void setDeliveryLong(String deliveryLong) {
        this.deliveryLong = deliveryLong;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPickupSurcharge() {
        return pickupSurcharge;
    }

    public void setPickupSurcharge(String pickupSurcharge) {
        this.pickupSurcharge = pickupSurcharge;
    }

    public String getDeliverySurcharge() {
        return deliverySurcharge;
    }

    public void setDeliverySurcharge(String deliverySurcharge) {
        this.deliverySurcharge = deliverySurcharge;
    }
}
