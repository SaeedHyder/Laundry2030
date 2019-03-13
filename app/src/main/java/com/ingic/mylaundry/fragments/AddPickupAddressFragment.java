package com.ingic.mylaundry.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ingic.mylaundry.Interface.GooglePlaceDataInterface;
import com.ingic.mylaundry.Interface.OnActivityResultInterface;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.GooglePlaceHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.AddressWraper;
import com.ingic.mylaundry.models.AdressData;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPickupAddressFragment extends BaseFragment implements GooglePlaceDataInterface, OnActivityResultInterface {

    @BindView(R.id.radio_home)
    RadioButton radioHome;
    @BindView(R.id.radio_office)
    RadioButton radioOffice;
    @BindView(R.id.radio_other)
    RadioButton radioOther;
    @BindView(R.id.radiogroup_address)
    RadioGroup radiogroupAddress;
    Unbinder unbinder;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_streetName)
    EditText etStreetName;
    @BindView(R.id.et_buildingName)
    EditText etBuildingName;
    @BindView(R.id.et_floor)
    EditText etFloor;
    @BindView(R.id.et_apartment)
    EditText etApartment;
    @BindView(R.id.et_nearestLandmark)
    EditText etNearestLandmark;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_country)
    EditText etCountry;
    @BindView(R.id.btn_saveAddress)
    Button btnSaveAddress;
    @BindView(R.id.streetNameInputLayout)
    CustomTextInputLayout streetNameInputLayout;
    @BindView(R.id.buildingNameInputLayout)
    CustomTextInputLayout buildingNameInputLayout;
    @BindView(R.id.locationInputLayout)
    CustomTextInputLayout locationInputLayout;
    String longitude, latitude;
    String type;
    String pickupType;
    String pickup, delivery;
    String pickupAddress, deliveryAddress;
    ScheduleOrderModel scheduleOrderModel;
    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            streetNameInputLayout.errorEnable(false);
            buildingNameInputLayout.errorEnable(false);
            locationInputLayout.errorEnable(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    @BindView(R.id.txtSelectAddress)
    TextView txtSelectAddress;
    private GooglePlaceHelper googlePlaceHelper;
    private ScheduleFragment scheduleFragment;
    private EditProfileFragment editProfileFragment;
    private TitleBar titleBar;
    private AdressData isEdit;
    private Boolean state = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_pickup_address, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (preferenceHelper != null && preferenceHelper.getSchedule() != null)
            scheduleOrderModel = preferenceHelper.getSchedule();
        else
            scheduleOrderModel = new ScheduleOrderModel();
        streetNameInputLayout.setErrorEnabled();
        buildingNameInputLayout.setErrorEnabled();
        locationInputLayout.setErrorEnabled();

        etStreetName.addTextChangedListener(txtWatcher);
        etBuildingName.addTextChangedListener(txtWatcher);
        etCity.addTextChangedListener(txtWatcher);
        etCountry.addTextChangedListener(txtWatcher);
        titleBar = activityReference.getTitleBar();
        setRadiobutton();
        googlePlaceHelper = new GooglePlaceHelper(activityReference, GooglePlaceHelper.PLACE_PICKER, AddPickupAddressFragment.this, AddPickupAddressFragment.this);
        activityReference.setOnActivityResultInterface(this);
        setFonts();
        fillDetails();
        return view;
    }

    public void setFonts() {
        txtSelectAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioHome.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioOther.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioOffice.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvLocation.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etStreetName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etBuildingName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etFloor.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etApartment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etNearestLandmark.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etCity.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etCountry.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnSaveAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {

        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        if (!Utils.isEmptyOrNull(type) && type.equals(AppConstant.PICKUP))
            titleBar.setTitle(activityReference.getResources().getString(R.string.add_pickup_address));
        else
            titleBar.setTitle(activityReference.getResources().getString(R.string.add_delivery_address));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
        this.titleBar = titleBar;
    }

    public void addAddress(int user_id, final double longitude, final double latitude, final String location, final String address) {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).AddAddress(
                user_id,
                longitude,
                latitude,
                location + "|" + address,
                address,
                pickupType,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        if (scheduleFragment != null && !Utils.isEmptyOrNull(type) && type.equals(AppConstant.PICKUP)) {
                            AddressWraper addressWraper = (AddressWraper) response.getResult();
                            scheduleFragment.setScheduleAddress(addressWraper.getAddress().getAddress(), type);
                            scheduleOrderModel.setPickupLat(addressWraper.getAddress().getLatitude() + "");
                            scheduleOrderModel.setPickupLong(addressWraper.getAddress().getLongitude() + "");
                            scheduleOrderModel.setPickupLocation(addressWraper.getAddress().getLocation());
                            scheduleOrderModel.setPickupTypeService(type);
                            scheduleOrderModel.setPickupType(addressWraper.getAddress().getType());
                            preferenceHelper.putScheduleOrder(scheduleOrderModel);

                        } else if (scheduleFragment != null && !Utils.isEmptyOrNull(type) && type.equals(AppConstant.DELIVERY)) {
                            AddressWraper addressWraper = (AddressWraper) response.getResult();
                            scheduleFragment.setScheduleAddress(addressWraper.getAddress().getAddress(), type);
                            scheduleOrderModel.setDeliveryLat(addressWraper.getAddress().getLatitude() + "");
                            scheduleOrderModel.setDeliveryLong(addressWraper.getAddress().getLongitude() + "");
                            scheduleOrderModel.setDeliveryLocation(addressWraper.getAddress().getLocation());
                            scheduleOrderModel.setDeliveryTypeService(type);
                            scheduleOrderModel.setDeliveryType(addressWraper.getAddress().getType());
                            preferenceHelper.putScheduleOrder(scheduleOrderModel);
                        }

                        if (scheduleFragment != null)
                            scheduleFragment.fillDetails();

                        if (editProfileFragment!=null)
                            editProfileFragment.fillEditAddress();
                        //  activityReference.onPageBack();
                        MyAddressFragment addPickupAddressFragment = new MyAddressFragment();
                        addPickupAddressFragment.setType(type, null);
                        //add
                        getDockActivity().popFragment();
                        activityReference.addFragments(addPickupAddressFragment);
                        //   getDockActivity().replaceDockableSupportFragment(new MyAddressFragment(),false);

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

    public void UpdateAddress(int user_id, double longitude, double latitude, String location, String address, int id) {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).UpdateAddress(
                user_id,
                longitude,
                latitude,
                location + "|" + address,
                address,
                id,
                pickupType,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {
                        if (editProfileFragment != null) {
                            editProfileFragment.tvAddress.setText("");
                        }
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
                }
        );
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

        Utils.hideKeyboard(activityReference);
        titleBar.showHeader(true);
        if (state) {
            titleBar.setTitle(activityReference.getResources().getString(R.string.my_address));
            titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCustomBackPressed();
                }
            });
            titleBar.showAddButtonAndBindClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddPickupAddressFragment pickupAddressFragment = new AddPickupAddressFragment();
                    pickupAddressFragment.setType(type, null);

                    activityReference.addFragments(pickupAddressFragment);
                }
            });
        } else {
            titleBar.setTitle(activityReference.getResources().getString(R.string.schedule_a_wash));
            titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCustomBackPressed();
                }
            });
        }
        unbinder.unbind();
    }

    public void setRadiobutton() {
        radiogroupAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_home) {
                    radioHome.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.home), null, null);
                    radioOffice.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.office), null, null);
                    radioOther.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.location), null, null);
                } else if (i == R.id.radio_office) {
                    radioHome.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.home2), null, null);
                    radioOffice.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.office2), null, null);
                    radioOther.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.location), null, null);

                } else if (i == R.id.radio_other) {
                    radioHome.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.home2), null, null);
                    radioOffice.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.office), null, null);
                    radioOther.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.location2), null, null);

                }
            }
        });
    }

    @OnClick({R.id.radio_home, R.id.radio_office, R.id.radio_other, R.id.radiogroup_address, R.id.tv_location/* R.id.et_streetName, R.id.et_buildingName*/, R.id.et_floor, R.id.et_apartment, R.id.et_nearestLandmark, R.id.et_city, R.id.et_country, R.id.btn_saveAddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_home:
                pickupType = AppConstant.HOME;
                break;
            case R.id.radio_office:
                pickupType = AppConstant.OFFICE;
                break;
            case R.id.radio_other:
                pickupType = AppConstant.OTHER;
                break;
            case R.id.radiogroup_address:
                break;
            case R.id.tv_location:
                loadingStarted();
                googlePlaceHelper.openAutocompleteActivity();
                break;
//            case R.id.et_streetName:
//                break;
//            case R.id.et_buildingName:
//                break;
            case R.id.et_floor:
                break;
            case R.id.et_apartment:
                break;
            case R.id.et_nearestLandmark:
                break;
            case R.id.et_city:
                break;
            case R.id.et_country:
                break;
            case R.id.btn_saveAddress:
                if (validate()) {

                    pickupAddress = etStreetName.getText().toString() + "|" + etBuildingName.getText().toString() + "|" + etFloor.getText().toString() + "|" + etApartment.getText().toString() + "|" + etNearestLandmark.getText().toString() + "|" + etCity.getText().toString() + "|" + etCountry.getText().toString();

                    if (type.equals("pickup")) {
                        pickup = tvLocation.getText().toString();
//                        preferenceHelper.putScheduleOrder(scheduleOrderModel);

                        if (isEdit == null) {
                            addAddress(preferenceHelper.getUser().getId(), Double.parseDouble(longitude),
                                    Double.parseDouble(latitude), pickup, pickupAddress);
                        } else {
                            UpdateAddress(preferenceHelper.getUser().getId(), Double.parseDouble(longitude == null
                                            ? isEdit.getLongitude() : longitude),
                                    Double.parseDouble(latitude == null ? isEdit.getLatitude() : latitude),
                                    pickup, pickupAddress, isEdit.getId());
                        }
                    } else {
                        delivery = tvLocation.getText().toString();

                        if (isEdit == null) {
                            addAddress(preferenceHelper.getUser().getId(), Double.parseDouble(longitude), Double.parseDouble(latitude),
                                    delivery, pickupAddress);
                        } else {
                            UpdateAddress(preferenceHelper.getUser().getId(), Double.parseDouble(longitude == null
                                    ? isEdit.getLongitude() : longitude), Double.parseDouble(latitude == null ?
                                    isEdit.getLatitude() : latitude), delivery, pickupAddress, isEdit.getId());
                        }
                    }
                   /* if (scheduleFragment != null)
                        scheduleFragment.fillDetails();*/
                    // activityReference.onBackPressed();
                }
                break;
        }
    }

    @Override
    public void onPlaceActivityResult(double longitude, double latitude, String address, String city, String country) {
        tvLocation.setText(address);
        this.latitude = String.valueOf(latitude + "");
        this.longitude = String.valueOf(longitude + "");
        etCity.setText(city);
        etCountry.setText(country);
    }

    @Override
    public void onError(String error) {
    }

    @Override
    public void onActivityResultInterface(int requestCode, int resultCode, Intent data) {
        loadingFinished();
        googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validate() {
        if (customValidation.validateLength(tvLocation, locationInputLayout, activityReference.getString(R.string.field_required), "2", "500"))
            if (customValidation.validateLength(etStreetName, streetNameInputLayout, activityReference.getString(R.string.field_required), "4", "100")) {
                if (customValidation.validateLength(etBuildingName, buildingNameInputLayout, activityReference.getString(R.string.field_required), "4", "100"))
                    return true;
            }
        return false;
    }

    public void setType(String type, ScheduleFragment scheduleFragment) {
        this.type = type;
        this.scheduleFragment = scheduleFragment;
    }

    public void setProfileType(String type, EditProfileFragment editProfileFragment) {
        this.type = type;
        this.editProfileFragment = editProfileFragment;
    }

    public void setCondition(AdressData b) {
        this.isEdit = b;
    }

    private void fillDetails() {
        if (isEdit != null && isEdit.getLocation() != null) {
            String[] separated = isEdit.getLocation().split("\\|");
            if (separated.length >= 1)
                tvLocation.setText(separated[0]);
            if (separated.length >= 2)
                etStreetName.setText(separated[1]);
            if (separated.length >= 3)
                etBuildingName.setText(separated[2]);
            if (separated.length >= 4)
                etFloor.setText(separated[3]);
            if (separated.length >= 5)
                etApartment.setText(separated[4]);
            if (separated.length >= 6)
                etNearestLandmark.setText(separated[5]);
            if (separated.length >= 7)
                etCity.setText(separated[6]);
            if (separated.length >= 8)
                etCountry.setText(separated[7]);
            if (isEdit.getType().equals(AppConstant.HOME)) {
                radioHome.setChecked(true);
                pickupType = AppConstant.HOME;
            } else if (isEdit.getType().equals(AppConstant.OTHER)) {
                radioOther.setChecked(true);
                pickupType = AppConstant.OTHER;
            } else {
                radioOffice.setChecked(true);
                pickupType = AppConstant.OFFICE;
            }
        } else
            pickupType = AppConstant.HOME;
    }
}
