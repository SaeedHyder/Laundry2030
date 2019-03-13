package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by khanubaid on 2/7/2018.
 */

public class Feedback implements Serializable {
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("feedback")
    @Expose
    private String feedback;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
