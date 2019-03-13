package com.ingic.mylaundry.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.models.UserWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.tv_signup)
    TextView tvSignup;
    Unbinder unbinder;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.emailInputLayout)
    CustomTextInputLayout emailInputLayout;
    @BindView(R.id.passwordInputLayout)
    CustomTextInputLayout passwordInputLayout;
    @BindView(R.id.btnLoginGuest)
    Button btnLoginGuest;
    String Email;
    TitleBar titleBar;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            passwordInputLayout.errorEnable(false);
            emailInputLayout.errorEnable(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public LoginFragment() {
    }

    @SuppressLint("ValidFragment")
    public LoginFragment(boolean fromGuest) {
        this.fromGuest = fromGuest;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.showHeader(false);
    }

    @Override
    public void onCustomBackPressed() {
        //  activityReference.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        emailInputLayout.setErrorEnabled();
        passwordInputLayout.setErrorEnabled();
        etEmail.addTextChangedListener(txtWatcher);
        etPassword.addTextChangedListener(txtWatcher);
        titleBar = activityReference.getTitleBar();
        setFonts();
        return view;
    }

    public void setFonts() {
        tvForgotPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etEmail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnLogin.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnLoginGuest.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvAccount.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSignup.setTypeface(TextUtility.setPoppinsSemiBold(activityReference));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_login, R.id.tv_signup, R.id.tv_forgotPassword, R.id.btnLoginGuest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgotPassword:
                activityReference.initFragments(new ForgotPasswordFragment());
                break;
            case R.id.btn_login:
                if (validate()) {
                    signIn();
                }
                break;
            case R.id.tv_signup:
                activityReference.initFragments(new SignupFragment());
                break;

            case R.id.btnLoginGuest:
                preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, true);
                preferenceHelper.setLoginStatus(true);
               // activityReference.emptyBackStack();
                activityReference.initFragments(new HomeFragment());
                break;
        }
    }

    private boolean validate() {
        if (customValidation.validateEmail(etEmail, emailInputLayout, getString(R.string.invalid_email))) {
            if (customValidation.validateLength(etPassword, passwordInputLayout, getString(R.string.minlenght3) + " 6 ", "6", "15"))
                return true;
        }
        return false;


    }

    boolean fromGuest;

    public void setFromGuest(boolean fromGuest) {
        this.fromGuest = fromGuest;
    }

    public void signIn() {

        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).SignIn(

                Email = etEmail.getText().toString(),
                etPassword.getText().toString(),
                AppConstant.DEVICE_TYPE,
                preferenceHelper.getDeviceToken(),

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {

                        UserWrapper userWarpper = (UserWrapper) JsonHelpers.convertToModelClass(response.getResult(), UserWrapper.class);

                        if (userWarpper.getUser().getIsVerified().equals("0")) {

                            signupPin signupPin = new signupPin();
                            signupPin.setEmail(Email);
                            activityReference.initFragments(signupPin);

                        } else {
//                            Log.e(TAG, "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());
                            preferenceHelper.putUserToken(userWarpper.getUser().getToken());
                            preferenceHelper.putUser(userWarpper.getUser());
                            preferenceHelper.setLoginStatus(true);
                            //preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
                            // activityReference.emptyBackStack();
//                            if (preferenceHelper.getCartData() != null) {
//                                //  activityReference.popFragment();
//                                CartFragment cartFragment = new CartFragment();
//                                //   cartFragment.setIsGuest(true);
//                                activityReference.addFragments(cartFragment);
//                            } else {
                            activityReference.addFragments(new HomeFragment());
                            // }

//                            if (fromGuest) {
//                              // preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
//                             //  setFromGuest(true);
//                                activityReference.popFragment();
//                            } else {
//                                activityReference.initFragments(new HomeFragment());
//                            }
                        }
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
}
