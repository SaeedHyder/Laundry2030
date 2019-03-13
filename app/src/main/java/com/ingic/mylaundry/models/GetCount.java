package com.ingic.mylaundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khanubaid on 4/12/2018.
 */

public class GetCount {

    @SerializedName("count")
    @Expose
    Integer count;
}
