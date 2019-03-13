package com.ingic.mylaundry.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_services)
    TextView tvServices;
    @BindView(R.id.tv_myProfile)
    TextView tvMyProfile;
    @BindView(R.id.tv_myOrders)
    TextView tvMyOrders;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    @BindView(R.id.tv_contactUs)
    TextView tvContactUs;
    Unbinder unbinder;
    @BindView(R.id.txthomeTitle)
    TextView txthomeTitle;
    @BindView(R.id.imageelogo)
    ImageView imageelogo;
    String temp;
    int limit = 50, offset = 1, pages = 11;
    int notiCount;
    private int intValue;
    private Call<Api_Response> webReq;
    private TitleBar titlebar;


    @Override
    public void onStop() {
        if (webReq != null)
            webReq.cancel();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        intValue = 0;
        temp = "";
        loadingFinished();
        Utils.hideKeyboard(activityReference);
        titlebar = activityReference.getTitleBar();
        setTextAnimation(activityReference.getResources().getString(R.string.we_can_do_it));

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1500);
        rotate.setInterpolator(new LinearInterpolator());
        imageelogo.startAnimation(rotate);

        setFonts();
        guestUser();

        if (preferenceHelper != null)
            if (preferenceHelper.getUser() != null)
                getNotificationCount(preferenceHelper.getUser().getId());

        setTitleBar(activityReference.mTitleBar);
        return view;
    }

    public void setTextAnimation(final String str) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (intValue <= str.length()) {

                    activityReference.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            if (intValue < 13) {
                                temp += str.toCharArray()[intValue];
                                if (txthomeTitle != null)
                                    txthomeTitle.setText(temp);
                                intValue++;

                            }
                        }
                    });
                    try {
                        Thread.sleep(280);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                temp = "";
                intValue = 0;
            }
        }).start();
    }

    public void setFonts() {

        txthomeTitle.setTypeface(TextUtility.setPoppinsBold(activityReference));
        tvServices.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvMyProfile.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvMyOrders.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSettings.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvContactUs.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(final TitleBar titleBar) {
        this.titlebar = titleBar;
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.showHomeLogo();
        titleBar.setNotificationCount(0);
        titleBar.showArabicTextBindClickListener(getMainActivity());
        if (preferenceHelper.getCartData() != null)
            titleBar.setCartCount(preferenceHelper.getCartData().getItems().size());
        else
            titleBar.setCartCount(0);

        setTextAnimation(activityReference.getResources().getString(R.string.we_can_do_it));
        titleBar.showNotificationButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityReference.initFragments(new NotificationFragment());
            }
        });
        titleBar.showCartButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferenceHelper.getCartData() != null) {
                    if (preferenceHelper.getCartData().getItems().size() > 0) {
                        activityReference.initFragments(new CartFragment());
                    }
                }else {
                    activityReference.initFragments(new MyCartFragment());
                }

            }
        });
        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER))
            titleBar.showNotificationButtonAndBindClickListener(guestListner());
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    public void getNotificationCount(int user_id) {
        // hideView();
        webReq = WebApiRequest.getInstance(activityReference, true).countNotification(
                user_id,
                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        int count = Integer.parseInt((((LinkedTreeMap) response.getResult()).get("count")).toString().split("\\.")[0]);
                        if (titlebar != null)
                            titlebar.setNotificationCount(count);
                        setTitleBar(activityReference.mTitleBar);
                    }

                    @Override
                    public void onError() {
                    }

                    @Override
                    public void onNoNetwork() {
                        setTitleBar(activityReference.mTitleBar);
                    }
                }
        );
    }

    @OnClick({R.id.tv_services, R.id.tv_myProfile, R.id.tv_myOrders, R.id.tv_settings, R.id.tv_contactUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_services:
                activityReference.initFragments(new ServiceFragment());
                break;
            case R.id.tv_myProfile:
                activityReference.initFragments(new MyProfileFragment());
                break;
            case R.id.tv_myOrders:
                activityReference.initFragments(new MyOrderFragment());
                break;
            case R.id.tv_settings:

                activityReference.initFragments(new SettingsFragment());
                break;
            case R.id.tv_contactUs:
                activityReference.initFragments(new ContactUsFragment());
                break;
        }
    }

    private void guestUser() {
        if (preferenceHelper != null)
            if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER)) {
                tvMyProfile.setOnClickListener(guestListner());
                tvMyOrders.setOnClickListener(guestListner());
            }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(locale);
        conf.locale = locale;
        conf.setLayoutDirection(locale);
        res.updateConfiguration(conf, dm);
        activityReference.finish();
        Intent refresh = new Intent(activityReference, MainActivity.class);
        startActivity(refresh);

    }

    public View.OnClickListener guestListner() {
        View.OnClickListener listner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.alertDialog(getString(R.string.loginGuest), getDockActivity(), activityReference, getString(R.string.yes), getString(R.string.no));
            }
        };
        return listner;
    }
}
