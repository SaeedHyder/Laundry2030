package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by khanubaid on 3/22/2018.
 */

public class NotificationWrapper {

    @SerializedName("UnreadCount")
    @Expose
    Integer UnreadCount;

    public Integer getUnreadCount() {
        return UnreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        UnreadCount = unreadCount;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @SerializedName("notification")
    @Expose
    ArrayList<Notification> notifications;
}
