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


public class ForgotChangePasswordFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.et_newPassword)
    EditText etNewPassword;
    @BindView(R.id.newpassInputLayout)
    CustomTextInputLayout newpassInputLayout;
    @BindView(R.id.et_confirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.confirmpassInputLayout)
    CustomTextInputLayout confirmpassInputLayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    String currentEmail;
    @BindView(R.id.txtChangePassword)
    TextView txtChangePassword;
    TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        newpassInputLayout.setErrorEnabled();
        confirmpassInputLayout.setErrorEnabled();
        titleBar=activityReference.getTitleBar();
        etNewPassword.addTextChangedListener(txtWatcher);
        etConfirmPassword.addTextChangedListener(txtWatcher);

        setFonts();
        return view;
    }

    public void
    setFonts() {
        txtChangePassword.setTypeface(TextUtility.setPoppinsSemiBold(activityReference));
        etNewPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etConfirmPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnSubmit.setTypeface(TextUtility.setPoppinsRegular(activityReference));
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

    public void updateForgotPassword(String password, String password_confirmation) {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).UpdateForgotPassword(
                currentEmail,
                password,
                password_confirmation,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        activityReference.emptyBackStack();
                        activityReference.initFragments(new LoginFragment());
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
        if (validate()) {
            updateForgotPassword(etNewPassword.getText().toString(), etConfirmPassword.getText().toString());

        }
    }

    private boolean validate() {
        if (customValidation.validateLength(etNewPassword, newpassInputLayout, "min length should be greater than 5", "5", "20")) {
            if (customValidation.isValidPassword(etNewPassword, etConfirmPassword, confirmpassInputLayout, "Password doesnot match"))
                return true;
        }
        return false;
    }

    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            newpassInputLayout.errorEnable(false);
            confirmpassInputLayout.errorEnable(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void setCurrentEmail(String email) {
        this.currentEmail = email;
    }
}
