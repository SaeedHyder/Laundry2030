package com.ingic.mylaundry.listener;

import com.ingic.mylaundry.models.Item;

/**
 * Created by khanubaid on 1/20/2018.
 */

public  interface TotalPriceListener {
    void setTotal(boolean isAdd, int total,Item item,int quantity,int pos,int itemPosition);
}
