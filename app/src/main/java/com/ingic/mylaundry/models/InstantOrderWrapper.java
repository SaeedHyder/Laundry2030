package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khanubaid on 3/30/2018.
 */

public class InstantOrderWrapper {
    @SerializedName("instanceText")
    @Expose
    private String instanceText;
}
