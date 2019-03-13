package com.ingic.mylaundry.models;

import java.io.Serializable;

/**
 * Created by khanubaid on 12/29/2017.
 */

public class UserWrapper implements Serializable {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
