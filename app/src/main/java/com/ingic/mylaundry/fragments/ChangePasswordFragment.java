package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangePasswordFragment extends BaseFragment {

    @BindView(R.id.btn_done)
    Button btnDone;
    Unbinder unbinder;
    @BindView(R.id.currentPassInputLayout)
    CustomTextInputLayout currentPassInputLayout;
    @BindView(R.id.newPassInputLayout)
    CustomTextInputLayout newPassInputLayout;
    @BindView(R.id.confirmPassInputLayout)
    CustomTextInputLayout confirmPassInputLayout;
    @BindView(R.id.et_currentPassword)
    EditText etCurrentPassword;
    @BindView(R.id.et_newPassword)
    EditText etNewPassword;
    @BindView(R.id.et_confirmPassword)
    EditText etConfirmPassword;
    TitleBar titleBar;

    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            currentPassInputLayout.errorEnable(false);
            newPassInputLayout.errorEnable(false);
            confirmPassInputLayout.errorEnable(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleBar=activityReference.getTitleBar();
        currentPassInputLayout.setErrorEnabled();
        newPassInputLayout.setErrorEnabled();
        confirmPassInputLayout.setErrorEnabled();

        etCurrentPassword.addTextChangedListener(txtWatcher);
        etNewPassword.addTextChangedListener(txtWatcher);
        etConfirmPassword.addTextChangedListener(txtWatcher);
        setFonts();
        return view;
    }

    public void setFonts() {
        etCurrentPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etNewPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etConfirmPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnDone.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        if (validate())
            updatePasswordService();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getResources().getString(R.string.change_password));
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

    private boolean validate() {
        if (customValidation.validateLength(etCurrentPassword, currentPassInputLayout, activityReference.getString(R.string.minLengthGreather5), "6", "20")) {
            if (customValidation.validateLength(etNewPassword, newPassInputLayout, activityReference.getString(R.string.minLengthGreather5), "6", "20"))
                if (customValidation.isValidPassword(etNewPassword, etConfirmPassword, confirmPassInputLayout, activityReference.getString(R.string.doesnt_match)))
                    return true;
        }
        return false;
    }

    private void updatePasswordService() {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).resetPassword(preferenceHelper.getUser().getId() + "", etCurrentPassword.getText().toString(), etNewPassword.getText().toString(), new WebApiRequest.APIRequestDataCallBack() {

            @Override
            public void onSuccess(Api_Response response) {

                Utils.showToast(activityReference, getString(R.string.password_update));
                activityReference.onPageBack();
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
