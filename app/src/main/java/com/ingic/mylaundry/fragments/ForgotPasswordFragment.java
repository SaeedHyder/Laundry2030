package com.ingic.mylaundry.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForgotPasswordFragment extends BaseFragment {

    @BindView(R.id.btn_resetPassword)
    Button btnResetPassword;
    Unbinder unbinder;
    @BindView(R.id.txtForgotPassword)
    TextView txtForgotPassword;
    @BindView(R.id.txt_forgotDetail)
    TextView txtForgotDetail;
    TitleBar titleBar;

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
//        activityReference.onPageBack();
        activityReference.onPageBackSignup();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] abc = new String[5];

        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        setFonts();
        titleBar = activityReference.getTitleBar();
        return view;
    }

    public void setFonts() {

        txtForgotPassword.setTypeface(TextUtility.setPoppinsSemiBold(activityReference));
        txtForgotDetail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnResetPassword.setTypeface(TextUtility.setPoppinsRegular(activityReference));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_resetPassword)
    public void onViewClicked() {
        activityReference.initFragments(new ForgotEmailFragment());
    }
}
