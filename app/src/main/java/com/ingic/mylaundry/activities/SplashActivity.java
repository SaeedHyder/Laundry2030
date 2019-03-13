package com.ingic.mylaundry.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.telerApp.TelerActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.imageView_logo)
    ImageView imageViewLogo;
    BasePreferenceHelper basePreferenceHelper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        basePreferenceHelper = new BasePreferenceHelper(this);
        intent=new Intent(SplashActivity.this, MainActivity.class);
        imageViewLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                changeLanguage();

                changeActivity(MainActivity.class,true);

            }
        }, 3000);
    }
    private void changeLanguage() {
        if (basePreferenceHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)) {
            setLocale("ar");
        }
    }
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(locale);
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
}
