package com.ingic.mylaundry.helpers;

import com.ingic.mylaundry.models.OrderPostWraper;
import com.ingic.mylaundry.models.ScheduleOrderModel;

public class Singleton {
    public OrderPostWraper getOrderPostWraper() {
        return orderPostWraper;
    }

    public void setOrderPostWraper(OrderPostWraper orderPostWraper) {
        this.orderPostWraper = orderPostWraper;
    }

    OrderPostWraper orderPostWraper;
    ScheduleOrderModel scheduleOrderModel;

    public ScheduleOrderModel getScheduleOrderModel() {
        return scheduleOrderModel;
    }

    public void setScheduleOrderModel(ScheduleOrderModel scheduleOrderModel) {
        this.scheduleOrderModel = scheduleOrderModel;
    }

    private static Singleton singleton = null;

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    private Singleton() {

    }
}
