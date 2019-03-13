package com.ingic.mylaundry.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.CountDownInterval;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.PinEntryEditText;
import com.ingic.mylaundry.models.UserWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class signupPin extends BaseFragment {

    @BindView(R.id.pinView)
    PinEntryEditText pinView;
    @BindView(R.id.pinInputLayout)
    CustomTextInputLayout pinInputLayout;
    @BindView(R.id.txtcodetimer)
    TextView txtcodetimer;
    @BindView(R.id.btn_resetPassword)
    Button btnResetPassword;
    String number;
    String email;
    Unbinder unbinder;
    @BindView(R.id.txt_resendCode)
    TextView txtResendCode;
    @BindView(R.id.txtEnterCode)
    TextView txtEnterCode;
    @BindView(R.id.txtcoderhere)
    TextView txtcoderhere;
    private CountDownTimer counttimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_pin, container, false);
        unbinder = ButterKnife.bind(this, view);
        pinInputLayout.setErrorEnabled();
        setTimer();
        setFonts();

        return view;
    }

    public void setFonts()
    {
        txtEnterCode.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtcoderhere.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtcodetimer.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnResetPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void setTimer() {
        if (counttimer != null)
            counttimer.cancel();
        counttimer = new CountDownInterval(txtcodetimer, 2 * 60 * 1000, 1000, activityReference) {
            @Override
            public void onFinish() {
                if (txtcodetimer != null) {
                    //service hit asd
                    // ResentCode();
                    txtcodetimer.setVisibility(View.GONE);
                    txtResendCode.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    public void ResentCode() {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).ResentCode(email, new WebApiRequest.APIRequestDataCallBack() {
            @Override
            public void onSuccess(Api_Response response) {
                txtcodetimer.setVisibility(View.VISIBLE);
                txtResendCode.setVisibility(View.GONE);
                setTimer();
                String verificationCode = ((LinkedTreeMap) response.getResult()).get("verification_code").toString();

                loadingFinished();
            }

            @Override
            public void onError() {
                loadingFinished();
            }

            @Override
            public void onNoNetwork() {
                loadingFinished();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getResources().getString(R.string.verification_code));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    public void VerifyPasswordCode(String email, int verification_code) {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).verifyRegCode(
                email,
                verification_code,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        UserWrapper userWarpper = (UserWrapper) JsonHelpers.convertToModelClass(response.getResult(), UserWrapper.class);

                       // preferenceHelper.putUserToken(userWarpper.getUser().getToken());
                       // preferenceHelper.putUser(userWarpper.getUser());
                        //activityReference.emptyBackStack();
                        //preferenceHelper.setLoginStatus(true);
                       // HomeFragment homeFragment = new HomeFragment();
                        //activityReference.initFragments(homeFragment);
                        loadingFinished();




                        preferenceHelper.putUserToken(userWarpper.getUser().getToken());
                        preferenceHelper.putUser(userWarpper.getUser());
                        preferenceHelper.setLoginStatus(true);
                        preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
                        activityReference.emptyBackStack();
                        activityReference.initFragments(new HomeFragment());
                        if (preferenceHelper.getCartData() != null) {
                            CartFragment cartFragment = new CartFragment();
                         //   cartFragment.setIsGuest(true);
                            activityReference.initFragments(cartFragment);
                        }

                    }

                    @Override
                    public void onError() {
                        loadingFinished();
                    }

                    @Override
                    public void onNoNetwork() {
                        loadingFinished();
                    }
                }
        );
    }

    private boolean validate() {
        return customValidation.validateLength(pinView, pinInputLayout, getString(R.string.enter_a_code), "4", "4");
    }

    @OnClick({R.id.txt_resendCode, R.id.btn_resetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_resendCode:
                ResentCode();
                break;
            case R.id.btn_resetPassword:
                if (validate())
                    VerifyPasswordCode(email, Integer.parseInt(pinView.getText().toString()));
                break;
        }
    }
}
