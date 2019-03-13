package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.models.UserWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ProvideInstructionFragment extends BaseFragment {

    @BindView(R.id.btn_saveInstruction)
    Button btnSaveInstruction;
    Unbinder unbinder;
    @BindView(R.id.chkfold)
    CheckBox chkfold;
    @BindView(R.id.chkatDoor)
    CheckBox chkatDoor;
    @BindView(R.id.chkCallPickup)
    CheckBox chkCallPickup;
    @BindView(R.id.chkCallDelivery)
    CheckBox chkCallDelivery;
    @BindView(R.id.chksave)
    CheckBox chksave;
    @BindView(R.id.txtInstruction)
    EditText txtInstruction;

    private TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_provide_instruction, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleBar = activityReference.getTitleBar();
        voifillData();
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getResources().getString(R.string.provide_instructions));
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
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getResources().getString(R.string.order_information));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
        unbinder.unbind();
    }


    private void setDatainPrefrence() {
        if (chksave.isChecked()) {
            webService(txtInstruction.getText().toString().trim(), boolToint(chkfold.isChecked()), boolToint(chkatDoor.isChecked()), boolToint(chkCallPickup.isChecked()), boolToint(chkCallDelivery.isChecked()));
        }
    }

    private void voifillData() {

        if (preferenceHelper.getUser().getUserInstruction() != null) {
            chkfold.setChecked(intTobool(preferenceHelper.getUser().getUserInstruction().getIs_fold()));
            chkatDoor.setChecked(intTobool(preferenceHelper.getUser().getUserInstruction().getAt_my_door()));
            chkCallPickup.setChecked(intTobool(preferenceHelper.getUser().getUserInstruction().getCall_me_before_pickup()));
            chkCallDelivery.setChecked(intTobool(preferenceHelper.getUser().getUserInstruction().getCall_me_before_delivery()));
            chksave.setChecked(true);
            txtInstruction.setText(preferenceHelper.getUser().getUserInstruction().getOther());
        }
    }

    private void webService(String other, int is_fold, int at_my_door, int call_me_before_pickup, int call_me_before_delivery) {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).saveInstruction(
                preferenceHelper.getUser().getId(),
                other,
                is_fold,
                at_my_door,
                call_me_before_pickup,
                call_me_before_delivery,
                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {
                        UserWrapper user = (UserWrapper) JsonHelpers.convertToModelClass(response.getResult(), UserWrapper.class);
                        preferenceHelper.putUser(user.getUser());
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

    private int boolToint(boolean val) {
        if (val)
            return 1;
        else
            return 0;
    }

    private boolean intTobool(int val) {
        return val == 1;
    }


    @OnClick({R.id.chksave, R.id.btn_saveInstruction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chksave:

                break;
            case R.id.btn_saveInstruction:
                        setDatainPrefrence();
        activityReference.onPageBack();
                break;
        }
    }
}
