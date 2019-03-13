package com.ingic.mylaundry.firebase;

import java.io.Serializable;

/**
 * Created by khanubaid on 1/3/2018.
 */

public class GcmDataObject implements Serializable {

    String title = "";
    String message = "";
    String action_type = "";
    String action_id = "";

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
