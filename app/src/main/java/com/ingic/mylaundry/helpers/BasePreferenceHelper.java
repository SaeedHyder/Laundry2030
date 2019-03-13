package com.ingic.mylaundry.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.ingic.mylaundry.models.Item;
import com.ingic.mylaundry.models.MultipleItems;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class BasePreferenceHelper extends PreferenceHelper {


    private static final String CART_DATA = "cart_data" ;
    public static final String GUEST_USER = "guest_login";
    public static final String LANGUAGE = "language";
    public static final String Ar = "language";
    private Context context;
    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_USER = "user";
    protected static final String KEY_SCHEDULE = "schedule";
    public static final String KEY_DEVICE_TOKEN = "device_token";
    public static final String AUTHENTICATE_USER_TOKEN = "user_token";
    public static final String KEY_SURCHARGE = "surcharge";

    private static final String FILENAME = "preferences";

    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public void setStringPrefrence(String key, String value) {
        putStringPreference(context, FILENAME, key, value);
    }

    public String getStringPrefrence(String key) {
        return getStringPreference(context, FILENAME, key);
    }

    public void setIntegerPrefrence(String key, int value) {
        putIntegerPreference(context, FILENAME, key, value);
    }

    public int getIntegerPrefrence(String key) {
        return getIntegerPreference(context, FILENAME, key);
    }

    public void setBooleanPrefrence(String Key,boolean val) {
        putBooleanPreference(context, FILENAME, Key, val);
    }

    public boolean getBooleanPrefrence(String Key) {
        return getBooleanPreference(context, FILENAME, Key);
    }

    public boolean getLoginStatus() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public void putDeviceToken(String token) {
        putStringPreference(context, FILENAME, KEY_DEVICE_TOKEN, token);
    }

    public String getDeviceToken() {
        return getStringPreference(context, FILENAME, KEY_DEVICE_TOKEN);
    }

    public void putUserToken(String token) {
        putStringPreference(context, FILENAME, AUTHENTICATE_USER_TOKEN, token);
    }

    public String getUserToken() {
        return getStringPreference(context, FILENAME, AUTHENTICATE_USER_TOKEN);
    }

    public void putUser(User user) {
        putStringPreference(context, FILENAME, KEY_USER, new GsonBuilder()
                .create().toJson(user));
    }

    public User getUser() {
        return new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), User.class);
    }

    public void putScheduleOrder(ScheduleOrderModel scheduleOrderModel)
    {
        putStringPreference(context,FILENAME,KEY_SCHEDULE,new GsonBuilder()
        .create().toJson(scheduleOrderModel));
    }

    public ScheduleOrderModel getSchedule()
    {
        return new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_SCHEDULE), ScheduleOrderModel.class);
    }


    public void putSurcharge(String value) {
        putStringPreference(context, FILENAME, KEY_SURCHARGE, value);
    }

    public String getSurcharge() {
        return getStringPreference(context, FILENAME, KEY_SURCHARGE);
    }


    public void putCartData(MultipleItems items) {
        putStringPreference(context, FILENAME, CART_DATA, new GsonBuilder()
                .create().toJson(items));
    }

    public MultipleItems getCartData() {
        return new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, CART_DATA), MultipleItems.class);
    }

    public void removeLoginPreference() {
        setLoginStatus(false);
        removePreference(context, FILENAME, KEY_USER);
        removePreference(context, FILENAME, KEY_LOGIN_STATUS);
    }
}
