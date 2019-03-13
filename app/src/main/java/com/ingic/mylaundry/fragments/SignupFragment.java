package com.ingic.mylaundry.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ingic.mylaundry.Interface.CountryPickerListenerInterface;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.UserWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SignupFragment extends BaseFragment implements CountryPickerListenerInterface {


    @BindView(R.id.nameInputLayout)
    CustomTextInputLayout nameInputLayout;
    @BindView(R.id.emailInputLayout)
    CustomTextInputLayout emailInputLayout;
    @BindView(R.id.passwordInputLayout)
    CustomTextInputLayout passwordInputLayout;
    @BindView(R.id.confirmPasswordInputLayout)
    CustomTextInputLayout confirmPasswordInputLayout;
    Unbinder unbinder;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_confirmPass)
    EditText etConfirmPass;
    @BindView(R.id.btn_signUp)
    Button btnSignUp;
    String number;
    String email;
    @BindView(R.id.chkboxTermCondition)
    CheckBox chkboxTermCondition;
    @BindView(R.id.txtTermandCondition)
    TextView txtTermandCondition;
    @BindView(R.id.tv_editNumber)
    TextView tvEditNumber;
    @BindView(R.id.et_mobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.mobileInputLayout)
    CustomTextInputLayout mobileInputLayout;
    private boolean isGuest, fromGuest;

    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            confirmPasswordInputLayout.errorEnable(false);
            nameInputLayout.errorEnable(false);
            passwordInputLayout.errorEnable(false);
            emailInputLayout.errorEnable(false);
            mobileInputLayout.errorEnable(false);
        }


        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public SignupFragment(){}

    @SuppressLint("ValidFragment")
    public SignupFragment(boolean fromGuest){
        this.fromGuest = fromGuest;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        nameInputLayout.setErrorEnabled();
        emailInputLayout.setErrorEnabled();
        passwordInputLayout.setErrorEnabled();
        mobileInputLayout.setErrorEnabled();
        confirmPasswordInputLayout.setErrorEnabled();

        etMobileNumber.addTextChangedListener(txtWatcher);
        etName.addTextChangedListener(txtWatcher);
        etEmail.addTextChangedListener(txtWatcher);
        etPass.addTextChangedListener(txtWatcher);
        etConfirmPass.addTextChangedListener(txtWatcher);

        setFonts();
        return view;
    }

    public void setFonts() {
        etName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etEmail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etPass.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etConfirmPass.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvEditNumber.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etMobileNumber.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnSignUp.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtTermandCondition.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        chkboxTermCondition.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {

        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(getString(R.string.sign_up));
        titleBar.showHeader(true);
        titleBar.hideHomeLogo();
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCustomBackPressed();
            }
        });

    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBackSignup();
      // activityReference.onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean validate() {
        if (customValidation.validateLength(etName, nameInputLayout, getString(R.string.minlenght3) + " 3 ", "3", "25")) {

            if (customValidation.validateEmail(etEmail, emailInputLayout, getString(R.string.invalidEmail)))
                if (customValidation.validateLength(etPass, passwordInputLayout, getString(R.string.minLengthGreather5), "6", "30"))
                    if (customValidation.isValidPassword(etPass, etConfirmPass, confirmPasswordInputLayout, getString(R.string.doesnt_match))) {

                        if (customValidation.validateLength(tvEditNumber.getText().toString(), mobileInputLayout, getString(R.string.country_code_error), "1", "20") && customValidation.validateLength(etMobileNumber.getText().toString(), mobileInputLayout, getString(R.string.min_length7), "7", "20")) {
                        } else {
                            return false;
                        }

                        if (!chkboxTermCondition.isChecked()) {
                            Utils.showToast(activityReference, getString(R.string.accept_terms_conditions));
                            return false;
                        }
                        return true;
                    }
        }
        return false;
    }


    public void signUp() {

        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).UserRegister(

                etName.getText().toString(),
                email = etEmail.getText().toString(),
                etConfirmPass.getText().toString(),
                etPass.getText().toString(),
                preferenceHelper.getDeviceToken(),
                number = tvEditNumber.getText().toString() + "-" + etMobileNumber.getText().toString(),

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {

                        UserWrapper userWarpper = (UserWrapper) JsonHelpers.convertToModelClass(response.getResult(), UserWrapper.class);

                        signupPin signupPin = new signupPin();
                        signupPin.setEmail(email);
                        activityReference.initFragments(signupPin);

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

    @OnClick({R.id.btn_signUp, R.id.txtTermandCondition, R.id.tv_editNumber})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signUp:
                if (validate()) {
                    signUp();
                }
                break;
            case R.id.txtTermandCondition:
                activityReference.addFragments(new TermConditionFragment());
                break;
            case R.id.tv_editNumber:
                activityReference.showCountryPicker(true, SignupFragment.this);
                break;
        }
    }

    @Override
    public void onCountrySelected(boolean isDialCodeShow, String name, String code, String dialCode, int flagDrawableResID) {
        if (isDialCodeShow) {
            tvEditNumber.setText(dialCode);
        }
    }


}
