package com.ingic.mylaundry.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ingic.mylaundry.helpers.BasePreferenceHelper;

import java.util.Locale;

/**
 * Created by khanubaid on 4/20/2018.
 */

public class SystemLanuageChangeBroadcastReceiver extends BroadcastReceiver {
    BasePreferenceHelper preferenceHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferenceHelper = new BasePreferenceHelper(context);
      //  Toast.makeText(context, "System Language Changed to: " + Locale.getDefault().getDisplayLanguage(), Toast.LENGTH_SHORT).show();
    }
}