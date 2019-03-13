package com.ingic.mylaundry.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
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
import com.ingic.mylaundry.helpers.CountDownInterval;
import com.ingic.mylaundry.helpers.NetworkUtils;
import com.ingic.mylaundry.helpers.PinEntryEditText;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class ForgotPinFragment extends BaseFragment {


    @BindView(R.id.pinView)
    PinEntryEditText pinView;
    @BindView(R.id.btn_resetPassword)
    Button btnResetPassword;
    Unbinder unbinder;
    @BindView(R.id.pinInputLayout)
    CustomTextInputLayout pinInputLayout;
    String email;
    @BindView(R.id.txtcodetimer)
    TextView txtcodetimer;
    @BindView(R.id.txt_resendCode)
    TextView txtResendCode;
    @BindView(R.id.txtEnterCode)
    TextView txtEnterCode;
    @BindView(R.id.txtcodehere)
    TextView txtcodehere;
    private CountDownTimer counttimer;
    String code;
    TitleBar titleBar;
    String memail;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressLint("ValidFragment")
    public ForgotPinFragment(String code, String memail) {
        this.code = code;
        this.memail = memail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_pin, container, false);
        unbinder = ButterKnife.bind(this, view);
        pinInputLayout.setErrorEnabled();
        setFonts();
        setTimer();
        titleBar=activityReference.getTitleBar();
        return view;
    }

    public void setFonts() {
        txtEnterCode.setTypeface(TextUtility.setPoppinsSemiBold(activityReference));
        txtcodehere.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtcodetimer.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnResetPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {

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


    private boolean validate() {
        return customValidation.validateLength(pinView, pinInputLayout, getString(R.string.enter_a_code), "4", "4");
    }

    public void VerifyPasswordCode(String verification_code) {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).VerifyPasswordCode(
                verification_code,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {
                        ForgotChangePasswordFragment forgotChangePasswordFragment = new ForgotChangePasswordFragment();
                        forgotChangePasswordFragment.setCurrentEmail(email);
                        activityReference.initFragments(forgotChangePasswordFragment);
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
                }
        );
    }

    private void setTimer() {
        if (counttimer != null)
            counttimer.cancel();
        counttimer = new CountDownInterval(txtcodetimer, 2 * 60 * 1000, 1000, activityReference) {
            @Override
            public void onFinish() {
                if (txtcodetimer != null) {

                    txtcodetimer.setVisibility(View.GONE);
                    txtResendCode.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    public void ResentCode() {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).ResentForgotCode(memail, new WebApiRequest.APIRequestDataCallBack() {
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

    @OnClick({R.id.txt_resendCode, R.id.btn_resetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_resendCode:
                ResentCode();
                break;
            case R.id.btn_resetPassword:
                if (validate())
                    VerifyPasswordCode(pinView.getText().toString());
                break;
        }
    }
}
