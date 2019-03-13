package com.ingic.mylaundry.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.mylaundry.Interface.CountryPickerListenerInterface;
import com.ingic.mylaundry.Interface.GooglePlaceDataInterface;
import com.ingic.mylaundry.Interface.MediaTypePicker;
import com.ingic.mylaundry.Interface.OnActivityResultInterface;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.GooglePlaceHelper;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.Address;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.models.UserWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends BaseFragment implements MediaTypePicker, CountryPickerListenerInterface, GooglePlaceDataInterface, OnActivityResultInterface {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.image_camera)
    ImageView imageCamera;
    @BindView(R.id.et_fullName)
    EditText etFullName;
    @BindView(R.id.namelInputLayout)
    CustomTextInputLayout namelInputLayout;
    @BindView(R.id.tv_emailAddress)
    TextView tvEmailAddress;
    @BindView(R.id.emailAddresslInputLayout)
    CustomTextInputLayout emailAddresslInputLayout;
    @BindView(R.id.tv_Address)
    TextView tvAddress;
    @BindView(R.id.AddresslInputLayout)
    CustomTextInputLayout AddresslInputLayout;
    @BindView(R.id.tv_editNumber)
    TextView tvEditNumber;
    @BindView(R.id.et_mobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.mobileInputLayout)
    CustomTextInputLayout mobileInputLayout;
    File profileImageFile = null;
    Unbinder unbinder;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    String longitude, latitude;
    String number;
    int offset = 0;
    int limit = 50;
    TitleBar titleBar;
    ScheduleOrderModel scheduleOrderModel;
    private GooglePlaceHelper googlePlaceHelper;

    EditProfileFragment thisInstance;

    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            namelInputLayout.errorEnable(false);
            mobileInputLayout.errorEnable(false);
            AddresslInputLayout.errorEnable(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public EditProfileFragment() {
        this.thisInstance = this;
    }



    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();

        return fragment;
    }

    public void setFonts() {
        etFullName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvEmailAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvEditNumber.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etMobileNumber.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

   public void fillEditAddress()
    {
        if (preferenceHelper.getSchedule() != null && preferenceHelper.getSchedule().getPickupAddress() != null) {
            tvAddress.setText(preferenceHelper.getSchedule().getPickupAddress().replaceAll("\\|", " "));
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        arabicContent();
        fillEditAddress();
        namelInputLayout.setErrorEnabled();
        mobileInputLayout.setErrorEnabled();
        AddresslInputLayout.setErrorEnabled();
        titleBar = activityReference.getTitleBar();
        etFullName.addTextChangedListener(txtWatcher);
        etMobileNumber.addTextChangedListener(txtWatcher);
        tvAddress.addTextChangedListener(txtWatcher);

        googlePlaceHelper = new GooglePlaceHelper(activityReference, GooglePlaceHelper.PLACE_PICKER, EditProfileFragment.this, EditProfileFragment.this);
        activityReference.setOnActivityResultInterface(this);

        setUserInfo();
        fillEditProfile();
        setFonts();
        arabicContent();
        return view;
    }

    public void getAddresses(int user_id, int offset, int limit, final String constantType) {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).GetAddresses(
                preferenceHelper.getUser().getId(),
                offset,
                limit,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {
                        loadingFinished();
                        Address addressWrapper = (Address) JsonHelpers.convertToModelClass(response.getResult(), Address.class);
                        if (addressWrapper.getData().size() > 0) {

                            MyAddressFragment addPickupAddressFragment = new MyAddressFragment();
                            addPickupAddressFragment.setProfileType(constantType, thisInstance);
                            //  addPickupAddressFragment.setProfileType(constantType, EditProfileFragment.this);
                            activityReference.addFragments(addPickupAddressFragment);

                        } else {
                          //  activityReference.onPageBack();
                            AddPickupAddressFragment addPickupAddressFragment = new AddPickupAddressFragment();
                            addPickupAddressFragment.setProfileType(constantType, thisInstance);
                            activityReference.addFragments(addPickupAddressFragment);
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

    public void fillEditProfile() {
        if (preferenceHelper != null)
            if (preferenceHelper.getUser().getAddress() != null)
                tvAddress.setText(preferenceHelper.getUser().getAddress());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getString(R.string.my_profile));
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
    }

    @Override
    public void onPhotoClicked(ArrayList<File> file) {
        if (file.size() > 0) {
            profileImageFile = file.get(0);
            profileImage.setImageBitmap(BitmapFactory.decodeFile(profileImageFile.getAbsolutePath()));
        }
    }

    @Override
    public void onDocClicked(ArrayList<String> files) {

    }

    private boolean validate() {
        if (customValidation.validateLength(etFullName, namelInputLayout, "min length should be 4", "4", "25")) {

            if (Utils.isEmptyOrNull(etMobileNumber.getText().toString()) || Utils.isEmptyOrNull(tvEditNumber.getText().toString())) {
                mobileInputLayout.setError(getString(R.string.mobilefieldrequire));
                return false;
            }
            if (customValidation.validateLength(etMobileNumber, mobileInputLayout, "min length should be 7", "7", "15")) {

            } else {
                return false;
            }
            if (!Utils.isEmptyOrNull(tvAddress.getText().toString())) {
                return true;
            } else {
                AddresslInputLayout.setError(getString(R.string.select_address));
                return false;
            }
        }
        return false;
    }

    @Override
    public void onCountrySelected(boolean isDialCodeShow, String name, String code, String dialCode, int flagDrawableResID) {

        if (isDialCodeShow) {
            tvEditNumber.setText(dialCode);
        }
    }

    @OnClick({R.id.profile_image, R.id.tv_editNumber, R.id.tv_Address, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                activityReference.openImagePicker(EditProfileFragment.this);
                break;
            case R.id.tv_editNumber:
                activityReference.showCountryPicker(true, EditProfileFragment.this);
                break;
            case R.id.tv_Address:

                getAddresses(preferenceHelper.getUser().getId(), offset, limit, AppConstant.EDIT_PROFILE);
//                etMobileNumber.requestFocus();
                //  etMobileNumber.setCursorVisible(true);

                break;
            case R.id.btn_update:

                if (validate()) {
                    updateProfile();
                }
                break;
        }
    }

    @Override
    public void onPlaceActivityResult(double longitude, double latitude, String address, String city, String country) {
        tvAddress.setText(address);
        this.latitude = String.valueOf(latitude + "");
        this.longitude = String.valueOf(longitude + "");
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onActivityResultInterface(int requestCode, int resultCode, Intent data) {
        loadingFinished();
        googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void setUserInfo() {

        if (preferenceHelper != null) {
            if (preferenceHelper.getSchedule().getEditProfleAddress() != null) {
                tvAddress.setText(preferenceHelper.getSchedule().getEditProfleAddress());
            }
            if (activityReference.prefHelper.getUser() != null) {

                if (!Utils.isEmptyOrNull(preferenceHelper.getUser().getImage()))
                    Utils.setImageWithGlide(activityReference, profileImage, AppConstant.ServerAPICalls.BASE_URL + AppConstant.ServerAPICalls.API + "resize/" + preferenceHelper.getUser().getImage());

                if (preferenceHelper.getUser().getName() != null) {
                    etFullName.setText(preferenceHelper.getUser().getName().substring(0, 1).toUpperCase() + preferenceHelper.getUser().getName().toString().substring(1));
                }
                tvEmailAddress.setText(preferenceHelper.getUser().getEmail());
                tvAddress.setText(preferenceHelper.getUser().getAddress());
                if (preferenceHelper.getUser().getPhone().contains("-")) {
                    String[] separated = preferenceHelper.getUser().getPhone().split("-");
                    String Code = separated[0];
                    String Number = separated[1];
                    tvEditNumber.setText(Code);
                    etMobileNumber.setText(Number);
                }
            }
        }

    }

    public void updateProfile() {

        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).UpdatedProfile(

                preferenceHelper.getUser().getId() + "",
                etFullName.getText().toString(),
                tvEmailAddress.getText().toString(),
                number = tvEditNumber.getText().toString() + "-" + etMobileNumber.getText().toString(),
                profileImageFile,
                tvAddress.getText().toString(),

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {

                        UserWrapper user = (UserWrapper) JsonHelpers.convertToModelClass(response.getResult(), UserWrapper.class);
                        if (user != null) {
                            preferenceHelper.putUser(user.getUser());
                        }

                        loadingFinished();
                        onCustomBackPressed();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        activityReference = (MainActivity) getActivity();
    }

    public void setAddress(String address) {
        tvAddress.setText(address.replaceAll("\\|", ",").replace(",,", ",").replace(",,", ",").replace(",,", ","));
    }


    private void arabicContent() {
        if (preferenceHelper != null)
            if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)) {
                tvEditNumber.setTextDirection(View.TEXT_DIRECTION_LTR);
            }
    }
}