package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ForgotEmailFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.emailInputLayout)
    CustomTextInputLayout emailInputLayout;
    @BindView(R.id.txtemailAddress)
    TextView txtemailAddress;
    @BindView(R.id.txtEmailDetail)
    TextView txtEmailDetail;
    TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_email, container, false);
        unbinder = ButterKnife.bind(this, view);
        emailInputLayout.setErrorEnabled();
        etEmail.addTextChangedListener(txtWatcher);
        setFonts();
        titleBar=activityReference.getTitleBar();
        return view;
    }

    public void setFonts()
    {
        txtemailAddress.setTypeface(TextUtility.setPoppinsSemiBold(activityReference));
        txtEmailDetail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etEmail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnSubmit.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getString(R.string.forgot_password));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
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

    public void emailVerify(final String email) {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).EmailPasswordCode(
                email,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        String code = ((LinkedTreeMap) response.getResult()).get("verification_code").toString();
                        ForgotPinFragment forgotPasswordPinFragment = new ForgotPinFragment(code, email);
                        forgotPasswordPinFragment.setEmail(email);

                        activityReference.initFragments(forgotPasswordPinFragment);

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

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validate())
            emailVerify(etEmail.getText().toString());
    }

    private boolean validate() {
        return customValidation.validateEmail(etEmail, emailInputLayout, getString(R.string.invalid_email));
    }

    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            emailInputLayout.errorEnable(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
