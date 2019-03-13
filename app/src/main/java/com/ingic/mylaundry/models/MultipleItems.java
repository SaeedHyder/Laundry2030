package com.ingic.mylaundry.models;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmObject;

/**
 * Created by khanubaid on 1/30/2018.
 */

public class MultipleItems {
    HashMap<String,Item> items;

    public MultipleItems(HashMap<String,Item> items) {
        this.items = items;
    }

    public MultipleItems() {
    }

    public HashMap<String,Item> getItems() {
        return items;
    }

    public void setItems(HashMap<String,Item>  items) {
        this.items = items;
    }
}
