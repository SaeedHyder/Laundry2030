package com.ingic.mylaundry.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.models.Item;
import com.ingic.mylaundry.models.OrderDetailWraper;
import com.ingic.mylaundry.models.OrderInstructionWraper;
import com.ingic.mylaundry.models.OrderPostWraper;
import com.ingic.mylaundry.models.OrderWrapper;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;
import com.ingic.telerApp.TelerActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PaymentMethodFragment extends BaseFragment {

    @BindView(R.id.tv_cashOnDelivery)
    TextView tvCashOnDelivery;
    Unbinder unbinder;
    @BindView(R.id.tv_payByCard)
    TextView tvPayByCard;
    private TitleBar titleBar;
    String totalAmountPayment;
    ScheduleOrderModel schedule;
    private BroadcastReceiver broadcastReceiver;
    String transactionId = "";
    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        unbinder = ButterKnife.bind(this, view);
        setFonts();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        onPaymentRecivedReciver();
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getResources().getString(R.string.payment_method));
        titleBar.showHeader(true);
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    public void setFonts() {
        tvCashOnDelivery.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPayByCard.setTypeface(TextUtility.setPoppinsRegular(activityReference));
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

    @OnClick({R.id.tv_payByCard, R.id.tv_cashOnDelivery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_payByCard:
                Intent intent = new Intent(activityReference, TelerActivity.class);
                intent.putExtra("obj", getPaymentDetail("card"));
                intent.putExtra("objSchedule", schedule);
                startActivity(intent);
                break;
            case R.id.tv_cashOnDelivery:
                paymentService("Cash");
                break;
        }
    }

    private void paymentService(String paymentType) {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).SaveOrderFragment(
                getPaymentDetail(paymentType),
                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {
                        loadingFinished();
                        activityReference.emptyBackStack();
                        preferenceHelper.putCartData(null);
                        preferenceHelper.putScheduleOrder(new ScheduleOrderModel());
                        activityReference.initFragments(new HomeFragment());
                        OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
                        double surCharge =0;
                        if (schedule.getPickupSurcharge()!=null) {
                             surCharge = Double.parseDouble(schedule.getPickupSurcharge());
                        }

                        orderSummaryFragment.setSummary((OrderWrapper) JsonHelpers.convertToModelClass(response.getResult(), OrderWrapper.class),surCharge);
                        activityReference.initFragments(orderSummaryFragment);
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

    private OrderPostWraper getPaymentDetail(String type) {
        ArrayList<OrderDetailWraper> order_detail = new ArrayList<>();

        for (Item val : preferenceHelper.getCartData().getItems().values()) {
            order_detail.add(new OrderDetailWraper(val));
        }

        OrderInstructionWraper orderInstructionWraper = new OrderInstructionWraper();

        if (preferenceHelper.getUser().getUserInstruction() != null) {
            orderInstructionWraper.setAt_my_door(preferenceHelper.getUser().getUserInstruction().getAt_my_door() + "");
            orderInstructionWraper.setCall_me_before_delivery(preferenceHelper.getUser().getUserInstruction().getCall_me_before_delivery() + "");
            orderInstructionWraper.setCall_me_before_pickup(preferenceHelper.getUser().getUserInstruction().getCall_me_before_pickup() + "");
            orderInstructionWraper.setIs_fold(preferenceHelper.getUser().getUserInstruction().getIs_fold() + "");
            orderInstructionWraper.setOther(preferenceHelper.getUser().getUserInstruction().getOther());
        } else {
            orderInstructionWraper.setAt_my_door("0");
            orderInstructionWraper.setCall_me_before_delivery("0");
            orderInstructionWraper.setCall_me_before_pickup("0");
            orderInstructionWraper.setIs_fold("0");
            orderInstructionWraper.setOther("");
        }

        schedule = preferenceHelper.getSchedule();
        OrderPostWraper orderPostWraper = new OrderPostWraper();
        orderPostWraper.setUser_id(preferenceHelper.getUser().getId() + "");
        orderPostWraper.setAmount(Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)));
        orderPostWraper.setPickup_location(schedule.getPickupAddress().toString());
        orderPostWraper.setPick_type(schedule.getPickupTypeService());
        orderPostWraper.setDrop_location(schedule.getDeliveryAddress());
        orderPostWraper.setPickup_latitude(schedule.getPickupLat());
        orderPostWraper.setPickup_longitude(schedule.getPickupLong());
        orderPostWraper.setDropup_latitude(schedule.getDeliveryLat());
        orderPostWraper.setDropup_longitude(schedule.getDeliveryLong());
        orderPostWraper.setDrop_type(schedule.getDeliveryTypeService());
        orderPostWraper.setSlot(schedule.getPickupTime());
        orderPostWraper.setDelivery_slot(schedule.getDeliveryTime());
        orderPostWraper.setPickup_date(schedule.getPickupDate());
        orderPostWraper.setDelivery_date(schedule.getDeliveryDate());
        orderPostWraper.setDelivery_amount(Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)));
        if (schedule.getPickupSurcharge() != null) {
            orderPostWraper.setDelivery_amount(Double.valueOf(schedule.getPickupSurcharge())); // Constant rakhi hae change kerni hae
        }
        orderPostWraper.setTotal_amount(schedule.getTotalPayment());
        // orderPostWraper.setPayment_type("cash");
        orderPostWraper.setPayment_type(type);
        orderPostWraper.setOrder_detail(order_detail);
        orderPostWraper.setOrder_instruction(orderInstructionWraper);
        orderPostWraper.setRedeemed_code(schedule.getIs_redeemed_code());
        orderPostWraper.setTransaction_id(transactionId);
        orderPostWraper.setPayment_date(date);
        orderPostWraper.setRedeemed_code_amount(schedule.getIs_redeemed_amount());

        if (schedule.getTotalPayment() <= 0)
            if (schedule.getPickupSurcharge() != null) {
                orderPostWraper.setTotal_amount((Double.parseDouble(schedule.getPickupSurcharge()) + Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL))));
            }
        return orderPostWraper;
    }

    @Override
    public void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("fromTeler");
        activityReference.registerReceiver(broadcastReceiver, filter);
        super.onResume();

    }

    // for payment recived
    private void onPaymentRecivedReciver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                transactionId = intent.getStringExtra("transactionId");
                paymentService("Card");
            }
        };
    }
}
