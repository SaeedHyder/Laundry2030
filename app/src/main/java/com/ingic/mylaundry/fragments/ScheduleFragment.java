package com.ingic.mylaundry.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.ingic.mylaundry.Interface.setInstantCheck;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.MyAddressAdapter;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.CustomTextInputLayout;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.customViews.customValidation;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.DateFormatHelper;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.listener.Pickuplistener;
import com.ingic.mylaundry.models.Address;
import com.ingic.mylaundry.models.AvailableTimeSlotWrapper;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.ui.InstantOrderDialog;
import com.ingic.mylaundry.ui.PickupTimeDialog;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class ScheduleFragment extends BaseFragment implements Pickuplistener, Utils.Utilinterface {
    ScheduleFragment thisInstance;
    @BindView(R.id.tv_schedulePickupAddres)
    public TextView tvSchedulePickupAddres;
    @BindView(R.id.tv_scheduleDeliveryAddres)
    public TextView tvScheduleDeliveryAddres;
    @BindView(R.id.tv_schedulePickupDate)
    TextView tvSchedulePickupDate;
    @BindView(R.id.tv_schedulePickupTime)
    TextView tvSchedulePickupTime;
    @BindView(R.id.tv_headersection)
    TextView tvHeadersection;
    @BindView(R.id.tv_scheduleDeliveryDate)
    TextView tvScheduleDeliveryDate;
    @BindView(R.id.tv_scheduleDeliveryTime)
    TextView tvScheduleDeliveryTime;
    @BindView(R.id.btn_saveAddress)
    Button btnSaveAddress;
    Unbinder unbinder;
    PickupTimeDialog pickupTimeDialog;
    InstantOrderDialog instantOrderDialog;
    DatePickerDialog.OnDateSetListener datePicker;
    DatePickerDialog.OnDateSetListener datePicker1;
    Calendar myCalendar;
    SimpleDateFormat sdf;
    Calendar c;
    String type = "";
    String pickupDate;
    String deliveryDate;
    String pickupSurcharge;

    int offset = 0;
    int limit = 50;
    String instantText;
    setInstantCheck setInstantCheck;
    ScheduleOrderModel scheduleOrderModel;
    MyAddressAdapter addressAdapter;
    AvailableTimeSlotWrapper availableTimeSlotWrapper;
    String pickup_selectedLocation, delivery_selectedLocation;
    @BindView(R.id.tv_instantOrder)
    TextView tvInstantOrder;
    @BindView(R.id.pickupAddressInputLayout)
    CustomTextInputLayout pickupAddressInputLayout;
    @BindView(R.id.PickupTimeInputLayout)
    CustomTextInputLayout PickupTimeInputLayout;
    @BindView(R.id.pickupDeliveryInputLayout)
    CustomTextInputLayout pickupDeliveryInputLayout;
    @BindView(R.id.DeliveryTimeInputLayout)
    CustomTextInputLayout DeliveryTimeInputLayout;
    String date;
    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            pickupAddressInputLayout.errorEnable(false);
            pickupDeliveryInputLayout.errorEnable(false);
            PickupTimeInputLayout.errorEnable(false);
            DeliveryTimeInputLayout.errorEnable(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    @BindView(R.id.chkInstant)
    CheckBox chkInstant;
    @BindView(R.id.tv_headersectiondelivery)
    TextView tvHeadersectiondelivery;
    private ScheduleFragment scheduleFragment;
    private TitleBar titleBar;
    private AvailableTimeSlotWrapper availableDeliverTimeSlotWrapper;

    @SuppressLint("ValidFragment")
    public ScheduleFragment(setInstantCheck setInstantCheck) {
        this.thisInstance = this;
        this.setInstantCheck = setInstantCheck;
    }

    public void setSelectedPickupLocation(String pickup_selectedLocation) {
        this.pickup_selectedLocation = pickup_selectedLocation;
    }

    public void setSelectedDeliveryLocation(String delivery_selectedLocation) {
        this.delivery_selectedLocation = delivery_selectedLocation;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, view);
        arabicContent();
        setFonts();

        if (preferenceHelper.getSchedule() != null)
            scheduleOrderModel = preferenceHelper.getSchedule();
        else
            scheduleOrderModel = new ScheduleOrderModel();

        myCalendar = Calendar.getInstance();

        pickupAddressInputLayout.setErrorEnabled();
        pickupDeliveryInputLayout.setErrorEnabled();
        PickupTimeInputLayout.setErrorEnabled();
        DeliveryTimeInputLayout.setErrorEnabled();

        tvSchedulePickupAddres.addTextChangedListener(txtWatcher);
        tvScheduleDeliveryAddres.addTextChangedListener(txtWatcher);
        tvSchedulePickupTime.addTextChangedListener(txtWatcher);
        tvScheduleDeliveryTime.addTextChangedListener(txtWatcher);

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        c = Calendar.getInstance();
        tvSchedulePickupDate.setText(DateFormatHelper.changeServerToShowFormatTime(sdf.format(c.getTime())));
        pickupDate = sdf.format(c.getTime());

        setPickupDate();
        setDeliveryDate();
        fillDetails();
        setTitleBar(activityReference.mTitleBar);
        return view;
    }

    public void setFonts() {
        tvInstantOrder.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvHeadersection.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvHeadersectiondelivery.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSchedulePickupAddres.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSchedulePickupDate.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSchedulePickupTime.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvScheduleDeliveryAddres.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvScheduleDeliveryDate.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvScheduleDeliveryTime.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnSaveAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    public void fillDetails() {
        if (preferenceHelper.getSchedule() != null && preferenceHelper.getSchedule().getPickupAddress() != null) {
            scheduleOrderModel = preferenceHelper.getSchedule();
            if (preferenceHelper.getSchedule().getPickupAddress() != null)
                tvSchedulePickupAddres.setText(preferenceHelper.getSchedule().getPickupAddress().replaceAll("\\|", " "));
            if (preferenceHelper.getSchedule().getDeliveryDate() != null)
                tvSchedulePickupDate.setText(DateFormatHelper.changeServerToShowFormatTime(preferenceHelper.getSchedule().getPickupDate()));
            if (preferenceHelper.getSchedule().getPickupTime() != null)
                tvSchedulePickupTime.setText(DateFormatHelper.formatTime(preferenceHelper.getSchedule().getPickupTime()));
            if (preferenceHelper.getSchedule().getDeliveryAddress() != null)
                tvScheduleDeliveryAddres.setText(preferenceHelper.getSchedule().getDeliveryAddress().replaceAll("\\|", " "));
            if (preferenceHelper.getSchedule().getDeliveryDate() != null)
                tvScheduleDeliveryDate.setText(DateFormatHelper.changeServerToShowFormatTime(preferenceHelper.getSchedule().getDeliveryDate()));
            if (preferenceHelper.getSchedule().getDeliveryTime() != null)
                tvScheduleDeliveryTime.setText(DateFormatHelper.formatTime(preferenceHelper.getSchedule().getDeliveryTime()));
            chkInstant.setChecked(preferenceHelper.getSchedule().isInstant());
            availableTimeSlot(pickupDate, pickupDate, chkInstant.isChecked() == true ? "1" : "0", "pickup", false);
            availableTimeSlot(pickupDate, Utils.formatDatewithoututc(tvScheduleDeliveryDate.getText().toString(), "dd MMM yyyy", "yyyy-MM-dd"), chkInstant.isChecked() == true ? "1" : "0", "delivery", false);
        } else {
            availableTimeSlot(pickupDate, pickupDate, chkInstant.isChecked() == true ? "1" : "0", "pickup", true);
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getResources().getString(R.string.schedule_a_wash));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    private void availableTimeSlot(String pickupDate, String date, String instant, final String slot_type, final boolean isReset) {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).
                AvailableTimeSlot(pickupDate.equals(getString(R.string.select_pickup_date)) ? null : pickupDate,
                        date,
                        instant,
                        slot_type,
                        tvSchedulePickupTime.getText().toString(), new WebApiRequest.APIRequestDataCallBack() {
                            @Override
                            public void onSuccess(Api_Response response) {
                                // setTitleBar(activityReference.mTitleBar);
                                if (slot_type.equals("pickup")) {
                                    availableTimeSlotWrapper = (AvailableTimeSlotWrapper) JsonHelpers.convertToModelClass(response.getResult(), AvailableTimeSlotWrapper.class);
                                    if (isReset)

                                        tvSchedulePickupTime.setText(activityReference.getString(R.string.pickupTime));
                                }

                                if (slot_type.equals("delivery")) {
                                    availableDeliverTimeSlotWrapper = (AvailableTimeSlotWrapper) JsonHelpers.convertToModelClass(response.getResult(), AvailableTimeSlotWrapper.class);
                                    if (isReset)
                                        tvScheduleDeliveryTime.setText(activityReference.getString(R.string.delivery_time_slot));
                                    //  setTitleBar(activityReference.mTitleBar);
                                }
                                loadingFinished();
                            }

                            @Override
                            public void onError() {
                                loadingFinished();
                                tvScheduleDeliveryTime.setText(getString(R.string.delivery_time_slot));
                                availableDeliverTimeSlotWrapper = null;
                            }

                            @Override
                            public void onNoNetwork() {
                                loadingFinished();
                            }
                        });
    }

    public void setPickupDate() {

        datePicker1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                pickupDate = sdf.format(c.getTime());
                tvSchedulePickupDate.setText(DateFormatHelper.changeServerToShowFormatTime(sdf.format(c.getTime())));
                tvScheduleDeliveryDate.setText(getString(R.string.select_delivery_date));

                availableTimeSlot(pickupDate, pickupDate, chkInstant.isChecked() == true ? "1" : "0", "pickup", true);//DateFormatHelper.changeToServerFormatTime(tvSchedulePickupDate.getText().toString()), chkInstant.isChecked() == true ? "1" : "0", "pickup");
            }
        };
    }

    public void setDeliveryDate() {
        datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);

                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                deliveryDate = sdf.format(c.getTime());

                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                deliveryDate = sdf.format(c.getTime());
                if (pickupDate.equals(deliveryDate)) {
                    deliveryDate = sdf.format(c.getTime());
                }
                tvScheduleDeliveryDate.setText(DateFormatHelper.changeServerToShowFormatTime(sdf.format(c.getTime())));

                availableTimeSlot(pickupDate, deliveryDate, chkInstant.isChecked() == true ? "1" : "0", "delivery", true);
            }

        };
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (titleBar != null) {
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
        }
//        unbinder.unbind();
    }

    @OnClick({R.id.tv_instantOrder, R.id.tv_schedulePickupAddres, R.id.tv_schedulePickupDate, R.id.tv_schedulePickupTime, R.id.tv_headersection, R.id.tv_scheduleDeliveryAddres, R.id.tv_scheduleDeliveryDate, R.id.tv_scheduleDeliveryTime, R.id.btn_saveAddress, R.id.chkInstant})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_schedulePickupAddres:
                getAddresses(preferenceHelper.getUser().getId(), offset, limit, AppConstant.PICKUP);
                break;
            case R.id.tv_schedulePickupDate:
                DatePickerDialog pickerDialog = new DatePickerDialog(activityReference, datePicker1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                pickerDialog.show();
                break;

            case R.id.tv_schedulePickupTime:
                Bundle bundle = new Bundle();
                bundle.putSerializable("key_available_time", availableTimeSlotWrapper);
                if (availableTimeSlotWrapper != null) {
                    pickupTimeDialog = PickupTimeDialog.newInstance(activityReference, ScheduleFragment.this, true);
                    pickupTimeDialog.show(activityReference.getSupportFragmentManager(), null);
                    pickupTimeDialog.setArguments(bundle);
                } else {
                    Utils.showToast(activityReference, getString(R.string.no_time_slot_error));
                }
                break;

            case R.id.tv_headersection:
                break;
            case R.id.tv_scheduleDeliveryAddres:
                getAddresses(preferenceHelper.getUser().getId(), offset, limit, AppConstant.DELIVERY);

                break;
            case R.id.tv_scheduleDeliveryDate:

                DatePickerDialog pickerDialog2 = new DatePickerDialog(activityReference, datePicker, c
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                if (chkInstant.isChecked() == true) {
                    pickerDialog2.getDatePicker().setMinDate(c.getTimeInMillis());
                    pickerDialog2.getDatePicker().setMaxDate(c.getTimeInMillis());
                } else {
                    pickerDialog2.getDatePicker().setMinDate(c.getTimeInMillis());
                }

                pickerDialog2.show();
                break;
            case R.id.tv_scheduleDeliveryTime:

                Bundle bundles = new Bundle();
                bundles.putSerializable("key_available_time", availableDeliverTimeSlotWrapper);
                if (availableDeliverTimeSlotWrapper != null) {
                    pickupTimeDialog = PickupTimeDialog.newInstance(activityReference, ScheduleFragment.this, false);
                    pickupTimeDialog.show(activityReference.getSupportFragmentManager(), null);
                    pickupTimeDialog.setArguments(bundles);
                } else {
                    Utils.showToast(activityReference, getString(R.string.no_time_slot_error));
                }
                break;
            case R.id.btn_saveAddress:

                if (validate()) {
                    // if (chkInstant.isChecked()) {

                    scheduleOrderModel = preferenceHelper.getSchedule();
                    scheduleOrderModel.setPickupAddress(tvSchedulePickupAddres.getText().toString());
                    scheduleOrderModel.setPickupDate(DateFormatHelper.changeToServerFormatTime(tvSchedulePickupDate.getText().toString()));
                    scheduleOrderModel.setPickupTime(DateFormatHelper.changeformatTime(tvSchedulePickupTime.getText().toString()));
                    scheduleOrderModel.setInstant(chkInstant.isChecked());
                    scheduleOrderModel.setDeliveryAddress(tvScheduleDeliveryAddres.getText().toString());
                    scheduleOrderModel.setDeliveryDate(DateFormatHelper.changeToServerFormatTime(tvScheduleDeliveryDate.getText().toString()));
                    scheduleOrderModel.setDeliveryTime(DateFormatHelper.changeformatTime(tvScheduleDeliveryTime.getText().toString()));


//                    if (pickupSurcharge != null) {
//                        scheduleOrderModel.setPickupSurcharge(pickupSurcharge);
//                    } else

                    if (preferenceHelper.getSchedule().isInstant()) {
                        scheduleOrderModel.setPickupSurcharge(preferenceHelper.getSurcharge());

                        //   pickupSurcharge = scheduleOrderModel.getPickupSurcharge();

                    } else {

                        scheduleOrderModel.setPickupSurcharge(null);
                    }
                    preferenceHelper.putScheduleOrder(scheduleOrderModel);

                    if (chkInstant.isChecked() == true) {
                        setInstantCheck.setInstantCheck(true);
                    } else {
                        setInstantCheck.setInstantCheck(false);
                    }

// chnaging
                    //  activityReference.addFragments(new OrderInformationFragment());
                    activityReference.onBackPressed();


                }

                break;

            case R.id.tv_instantOrder:

                instanceOrder();

                break;

            case R.id.chkInstant:

                tvScheduleDeliveryDate.setText("");
                tvScheduleDeliveryDate.setText(getString(R.string.select_delivery_date));
                tvScheduleDeliveryTime.setText(getString(R.string.delivery_time_slot));

                tvSchedulePickupDate.setText("");
                tvSchedulePickupDate.setText(getString(R.string.selectPickup));
                tvSchedulePickupTime.setText(getString(R.string.pickupTime));

                scheduleOrderModel.setInstant(chkInstant.isChecked());
                preferenceHelper.putScheduleOrder(scheduleOrderModel);

                break;
        }
    }

    public void instanceOrder() {

        WebApiRequest.getInstance(activityReference, true).instanceOrder(

                new WebApiRequest.APIRequestDataCallBack() {

                    @Override
                    public void onSuccess(Api_Response response) {
                        instantText = ((LinkedTreeMap) response.getResult()).get("instanceText").toString();
                        instantOrderDialog = InstantOrderDialog.newInstance(activityReference, instantText);
                        instantOrderDialog.show(activityReference.getSupportFragmentManager(), null);
                    }

                    @Override
                    public void onError() {
                        //  loadingFinished();
                    }

                    @Override
                    public void onNoNetwork() {
                        //      loadingFinished();
                    }
                }
        );
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
                            addPickupAddressFragment.setType(constantType, thisInstance);
                            //add
                            activityReference.addFragments(addPickupAddressFragment);
                        } else {
                            // activityReference.onPageBack();
                            AddPickupAddressFragment addPickupAddressFragment = new AddPickupAddressFragment();
                            addPickupAddressFragment.setType(constantType, thisInstance);
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

    private boolean validate() {

        if (tvScheduleDeliveryDate.getText().toString().equals(getString(R.string.select_delivery_date))) {

            Utils.showToast(activityReference, getString(R.string.deliverydate_require));
            return false;
        } else if (tvScheduleDeliveryTime.getText().toString().equals(getString(R.string.delivery_time_slot))) {
            Utils.showToast(activityReference, getString(R.string.deliverytime_require));
            return false;
        } else if (tvSchedulePickupTime.getText().toString().equals(getString(R.string.pickupTime))) {
            Utils.showToast(activityReference, getString(R.string.pickuptime_require));
            return false;
        }
        if (customValidation.validateTextViewLength(tvSchedulePickupAddres, pickupAddressInputLayout, activityReference.getString(R.string.field_requireds), "4", "500")) {
            if (customValidation.validateTextViewLength(tvScheduleDeliveryAddres, pickupDeliveryInputLayout, activityReference.getString(R.string.field_requireds), "4", "500"))
                if (customValidation.validateTextViewLength(tvSchedulePickupTime, PickupTimeInputLayout, activityReference.getString(R.string.time_required), "4", "500"))
                    if (customValidation.validateTextViewLength(tvScheduleDeliveryTime, DeliveryTimeInputLayout, activityReference.getString(R.string.time_required), "4", "500"))
                        return true;
        }
        return false;
    }

    @Override
    public void setTime(String time, boolean is_pickup, String surcharge) {
        if (is_pickup) {

            this.pickupSurcharge = surcharge;

            tvSchedulePickupTime.setText(time);

            preferenceHelper.putSurcharge(surcharge);

        } else {
            tvScheduleDeliveryTime.setText(time);

        }
    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        dialog.dismiss();
    }

    public void setScheduleAddress(String address, String type) {
        if (type.equals(AppConstant.PICKUP)) {
            tvSchedulePickupAddres.setText(address.replaceAll("\\|", ",").replace(",,", ",").replace(",,", ",").replace(",,", ","));
        } else {
            tvScheduleDeliveryAddres.setText(address.replaceAll("\\|", ",").replace(",,", ",").replace(",,", ",").replace(",,", ","));
        }
    }

    //Arabic Alignment
    private void arabicContent() {
        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)) {
            tvSchedulePickupAddres.setTextDirection(View.TEXT_DIRECTION_RTL);
            tvSchedulePickupDate.setGravity(Gravity.RIGHT);
            tvSchedulePickupTime.setGravity(Gravity.RIGHT);
            tvScheduleDeliveryAddres.setTextDirection(View.TEXT_DIRECTION_RTL);
            tvScheduleDeliveryDate.setGravity(Gravity.RIGHT);
            tvScheduleDeliveryTime.setGravity(Gravity.RIGHT);
        } else {
            tvSchedulePickupDate.setGravity(Gravity.LEFT);
            tvScheduleDeliveryDate.setGravity(Gravity.LEFT);
            tvScheduleDeliveryTime.setGravity(Gravity.LEFT);
            tvSchedulePickupTime.setGravity(Gravity.LEFT);
        }
    }
}
