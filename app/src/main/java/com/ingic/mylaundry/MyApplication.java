package com.ingic.mylaundry;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.telr.mobile.sdk.TelrApplication;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by adnanahmed on 9/28/2017.
 */

public class MyApplication extends TelrApplication {

    @Override
    public void onCreate() {
        super.onCreate();

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("fonts/Poppins-Bold.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build());
        TextUtility.overrideFont(getApplicationContext(), "SERIF", "fonts/Poppins-Regular.ttf"); // font from assets:
        Fabric.with(this, new Crashlytics());
//      MultiDex.install(this);
//
//        TextUtility.overrideFont(getApplicationContext(), "SERIF", "fonts/Montserrat-Regular.otf");
//        TextUtility.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Italic_1.ttf");
        // TextUtility.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Bold.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

       /* registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());*/
    }
}
