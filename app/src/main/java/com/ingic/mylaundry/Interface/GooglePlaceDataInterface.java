package com.ingic.mylaundry.Interface;

/**
 * Created by khanubaid on 1/19/2018.
 */

public interface GooglePlaceDataInterface {
    void onPlaceActivityResult(double longitude, double latitude, String address,String city,String country);
    void onError(String error);
}
