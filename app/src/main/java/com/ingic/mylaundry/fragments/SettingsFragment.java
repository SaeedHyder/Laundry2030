package com.ingic.mylaundry.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.models.User;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

public class SettingsFragment extends BaseFragment implements Utils.Utilinterface {

    @BindView(R.id.tv_ChangePassword)
    TextView tvChangePassword;
    @BindView(R.id.tv_Logout)
    TextView tvLogout;
    Unbinder unbinder;
    @BindView(R.id.cb_notification)
    CheckBox cbNotification;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    boolean notification;
    @BindView(R.id.tv_pushNotification)
    TextView tvPushNotification;
    Call<Api_Response> req;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (preferenceHelper != null)
            if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER)) {
                tvLogout.setText(activityReference.getString(R.string.login));
            }
        setValue();
        setFonts();
        guestUser();
        return view;
    }

    public void setFonts() {
        tvPushNotification.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvChangePassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvLanguage.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvLogout.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getResources().getString(R.string.settings));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    @Override
    public void onStop() {
        if (req != null)
            req.cancel();
        super.onStop();
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_ChangePassword, R.id.tv_Logout, R.id.cb_notification, R.id.tv_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ChangePassword:
                activityReference.initFragments(new ChangePasswordFragment());
                break;
            case R.id.tv_Logout:
                Utils.alertDialog(getString(R.string.are_you_logout), this, activityReference, getString(R.string.yes), getString(R.string.no));
                break;
            case R.id.cb_notification:

                if (!cbNotification.isChecked()) {
                    notificationStatus(preferenceHelper.getUser().getId(), 0);
                } else {
                    notificationStatus(preferenceHelper.getUser().getId(), 1);
                }
                break;
            case R.id.tv_language:
                if (tvLanguage.getText().equals(getString(R.string.english))) {
                    preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.LANGUAGE, false);
                    tvLanguage.setText(R.string.arabic);
                    getMainActivity().setLocale("en");
                } else {
                    preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.LANGUAGE, true);
                    tvLanguage.setText(R.string.english);
                    getMainActivity().setLocale("ar");
                }
                break;
        }
    }

    public void setValue() {

        if (preferenceHelper.getUser() != null && preferenceHelper.getUser().getNotificationStatus() != null) {
            if (preferenceHelper.getUser().getNotificationStatus().equals("1")) {
                cbNotification.setChecked(true);
                notification = true;
            } else {
                cbNotification.setChecked(false);
                notification = false;
            }

        }

        if (!preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)) {
            tvLanguage.setText(R.string.arabic);
        }
    }

    public void notificationStatus(int user_id, final int status) {
        loadingStarted();
        req = WebApiRequest.getInstance(activityReference, true).notificationStatus(
                user_id,
                status,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        User user = preferenceHelper.getUser();
                        if (status == 1) {
                            user.setNotificationStatus(String.valueOf(1));
                            cbNotification.setChecked(true);
                        } else {
                            user.setNotificationStatus(String.valueOf(0));
                            cbNotification.setChecked(false);
                        }
                        preferenceHelper.putUser(user);
                        loadingFinished();
                    }

                    @Override
                    public void onError() {
                        loadingFinished();
                        //  hideView();
                    }

                    @Override
                    public void onNoNetwork() {
                        loadingFinished();
                        cbNotification.performClick();
                        //   hideView();
                    }
                }
        );
    }

    private void logOut() {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).logout(preferenceHelper.getUserToken(), new WebApiRequest.APIRequestDataCallBack() {
            @Override
            public void onSuccess(Api_Response response) {

                activityReference.emptyBackStack();
                preferenceHelper.putUser(null);
                preferenceHelper.putScheduleOrder(new ScheduleOrderModel());
                preferenceHelper.putCartData(null);
                preferenceHelper.setLoginStatus(false);
                preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
                activityReference.initFragments(new LoginFragment());
                loadingFinished();
            }

            @Override
            public void onError() {
                loadingFinished();
                activityReference.emptyBackStack();
                preferenceHelper.putUser(null);
                preferenceHelper.putScheduleOrder(new ScheduleOrderModel());
                preferenceHelper.putCartData(null);
                preferenceHelper.setLoginStatus(false);
                preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
                activityReference.initFragments(new LoginFragment());
            }

            @Override
            public void onNoNetwork() {
                loadingFinished();
            }
        });
    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER)) {
            activityReference.emptyBackStack();
            preferenceHelper.putUser(null);
            preferenceHelper.putScheduleOrder(new ScheduleOrderModel());
            preferenceHelper.setLoginStatus(false);
            preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
            activityReference.initFragments(new LoginFragment());
            loadingFinished();
        } else
            logOut();
    }

    private void guestUser() {

        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER)) {
            cbNotification.setOnClickListener(guestListner());
            tvChangePassword.setOnClickListener(guestListner());
            tvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activityReference.emptyBackStack();
                    preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
                    preferenceHelper.setLoginStatus(false);
                    activityReference.initFragments(new LoginFragment());
                }
            });
        }
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public void setLocale(String lang) {
//        Locale locale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.setLocale(locale);
//        conf.locale = locale;
//        conf.setLayoutDirection(locale);
//        res.updateConfiguration(conf, dm);
//        activityReference.finish();
//        Intent refresh = new Intent(activityReference, TelerMainActivity.class);
//        startActivity(refresh);
//    }
}
