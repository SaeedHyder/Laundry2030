package com.ingic.telerApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.DockActivity;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.fragments.HomeFragment;
import com.ingic.mylaundry.fragments.OrderSummaryFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.models.Item;
import com.ingic.mylaundry.models.OrderDetailWraper;
import com.ingic.mylaundry.models.OrderInstructionWraper;
import com.ingic.mylaundry.models.OrderPostWraper;
import com.ingic.mylaundry.models.OrderWrapper;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.response.status.StatusResponse;

import java.util.ArrayList;

public class SuccessTransationActivity extends DockActivity {

    private TextView mTextView;
    OrderPostWraper orderPostWraper;
    double amount;
    String transactionId;
    private ScheduleOrderModel schedule;
    String date;

    @Override
    public int getDockFrameLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successtransaction);
        TextView textView = (TextView) findViewById(R.id.text_payment_result);


//        Singleton.getInstance().getOrderPostWraper().getAmount();
//
//        Utils.showToast(this, String.valueOf(Singleton.getInstance().getOrderPostWraper().getAmount()));
//        finish();
//        OrderSummaryFragment fragment = new OrderSummaryFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
//        fragmentTransaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        StatusResponse status = (StatusResponse) intent.getParcelableExtra(WebviewActivity.PAYMENT_RESPONSE);
//        TextView textView = (TextView)findViewById(R.id.text_payment_result);
        // textView.setText(textView.getText() +" : " + status.getTrace());
//        amount = orderPostWraper.getAmount();
//        textView.setText((int) amount);
        if (status != null) {
            if (status.getAuth() != null) {
                status.getAuth().getStatus();   // Authorisation status. A indicates an authorised transaction. H also indicates an authorised transaction, but where the transaction has been placed on hold. Any other value indicates that the request could not be processed.
                status.getAuth().getAvs();      /* Result of the AVS check:
                                            Y = AVS matched OK
                                            P = Partial match (for example, post-code only)
                                            N = AVS not matched
                                            X = AVS not checked
                                            E = Error, unable to check AVS */
                status.getAuth().getCode();     // If the transaction was authorised, this contains the authorisation code from the card issuer. Otherwise it contains a code indicating why the transaction could not be processed.
                status.getAuth().getMessage();  // The authorisation or processing error message.
                status.getAuth().getCa_valid();
                status.getAuth().getCardcode(); // Code to indicate the card type used in the transaction. See the code list at the end of the document for a list of card codes.
                status.getAuth().getCardlast4();// The last 4 digits of the card number used in the transaction. This is supplied for all payment types (including the Hosted Payment Page method) except for PayPal.
                status.getAuth().getCvv();      /* Result of the CVV check:
                                           Y = CVV matched OK
                                           N = CVV not matched
                                           X = CVV not checked
                                           E = Error, unable to check CVV */
                transactionId = status.getAuth().getTranref(); //The payment gateway transaction reference allocated to this request.
                // Utils.showToast(this, transactionId);
                Log.e("hany", status.getAuth().getTranref());
                status.getAuth().getCardfirst6(); // The first 6 digits of the card number used in the transaction, only for version 2 is submitted in Tran -> Version

                setTransactionDetails(status.getAuth().getTranref(), status.getAuth().getCardlast4());

                Intent intent2 = new Intent();
                intent2.setAction("fromTeler");
                intent2.putExtra("transactionId", transactionId);
                sendBroadcast(intent2);
                finish();
//                paymentService("card");
//
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<Task>>() {}.getType();
//                String json = gson.toJson(status, type);
//                Log.e("json",json);
            }
        }

    }

    private void setTransactionDetails(String ref, String last4) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ref", ref);
        editor.putString("last4", last4);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        // this.finish();
    }

    public void closeWindow(View view) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        prefHelper.setBooleanPrefrence("PAYMENT_DONE", true);
    }

//    private void paymentService() {
//        WebApiRequest.getInstance(this, true).SaveOrder(
//                getPaymentDetail(),
//                new WebApiRequest.APIRequestDataCallBack() {
//                    @Override
//                    public void onSuccess(Api_Response response) {
//
//                        emptyBackStack();
//                        prefHelper.putCartData(null);
//                        prefHelper.putScheduleOrder(new ScheduleOrderModel());
//                        prefHelper.setBooleanPrefrence("PAYMENT_DONE", true);
//                        OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
//                        orderSummaryFragment.setSummary((OrderWrapper) JsonHelpers.convertToModelClass(response.getResult(), OrderWrapper.class), prefHelper.getSchedule().getPickupSurcharge());
//                        prefHelper.setBooleanPrefrence("PAYMENT_DONE", true);
//
//                        finish();
//                    }
//
//                    @Override
//                    public void onError() {
//                        //  loadingFinished();
//                    }
//
//                    @Override
//                    public void onNoNetwork() {
//                        //  loadingFinished();
//                    }
//                });
//    }

//    private OrderPostWraper getPaymentDetail() {
//        ArrayList<OrderDetailWraper> order_detail = new ArrayList<>();
//
//        for (Item val : prefHelper.getCartData().getItems().values()) {
//            order_detail.add(new OrderDetailWraper(val));
//        }
//
//        OrderInstructionWraper orderInstructionWraper = new OrderInstructionWraper();
//
//        if (prefHelper.getUser().getUserInstruction() != null) {
//            orderInstructionWraper.setAt_my_door(prefHelper.getUser().getUserInstruction().getAt_my_door() + "");
//            orderInstructionWraper.setCall_me_before_delivery(prefHelper.getUser().getUserInstruction().getCall_me_before_delivery() + "");
//            orderInstructionWraper.setCall_me_before_pickup(prefHelper.getUser().getUserInstruction().getCall_me_before_pickup() + "");
//            orderInstructionWraper.setIs_fold(prefHelper.getUser().getUserInstruction().getIs_fold() + "");
//            orderInstructionWraper.setOther(prefHelper.getUser().getUserInstruction().getOther());
//        } else {
//            orderInstructionWraper.setAt_my_door("0");
//            orderInstructionWraper.setCall_me_before_delivery("0");
//            orderInstructionWraper.setCall_me_before_pickup("0");
//            orderInstructionWraper.setIs_fold("0");
//            orderInstructionWraper.setOther("");
//        }
//
//        ScheduleOrderModel schedule = prefHelper.getSchedule();
//        OrderPostWraper orderPostWraper = new OrderPostWraper();
//        orderPostWraper.setUser_id(String.valueOf(prefHelper.getUser().getId()));
//        orderPostWraper.setAmount(Double.parseDouble(prefHelper.getStringPrefrence(AppConstant.TOTAL)));
//        orderPostWraper.setPickup_location(Singleton.getInstance().getOrderPostWraper().getPickup_location());
//        orderPostWraper.setPick_type(Singleton.getInstance().getOrderPostWraper().getPick_type());
//        orderPostWraper.setDrop_location(Singleton.getInstance().getOrderPostWraper().getDrop_location());
//        orderPostWraper.setPickup_latitude(Singleton.getInstance().getOrderPostWraper().getPickup_latitude());
//        orderPostWraper.setPickup_longitude(Singleton.getInstance().getOrderPostWraper().getPickup_longitude());
//        orderPostWraper.setDropup_latitude(Singleton.getInstance().getOrderPostWraper().getDropup_latitude());
//        orderPostWraper.setDropup_longitude(Singleton.getInstance().getOrderPostWraper().getDropup_longitude());
//        orderPostWraper.setDrop_type(Singleton.getInstance().getOrderPostWraper().getDrop_type());
//        orderPostWraper.setSlot(Singleton.getInstance().getOrderPostWraper().getSlot());
//        orderPostWraper.setDelivery_slot(Singleton.getInstance().getOrderPostWraper().getDelivery_slot());
//        orderPostWraper.setPickup_date(Singleton.getInstance().getOrderPostWraper().getPickup_date());
//        orderPostWraper.setDelivery_date(Singleton.getInstance().getOrderPostWraper().getDelivery_date());
//        if (schedule.getPickupSurcharge() != null)
//            orderPostWraper.setDelivery_amount(Double.valueOf(Singleton.getInstance().getOrderPostWraper().getDelivery_amount())); // Constant rakhi hae change kerni hae
//        orderPostWraper.setTotal_amount(Singleton.getInstance().getOrderPostWraper().getTotal_amount());
//        // orderPostWraper.setPayment_type("cash");
//        orderPostWraper.setOrder_detail(order_detail);
//        orderPostWraper.setOrder_instruction(orderInstructionWraper);
//        orderPostWraper.setRedeemed_code(Singleton.getInstance().getOrderPostWraper().getRedeemed_code());
//        orderPostWraper.setPayment_type(Singleton.getInstance().getOrderPostWraper().getPayment_type());
//        orderPostWraper.setTransaction_id(transactionId);
//        orderPostWraper.setRedeemed_code_amount(Singleton.getInstance().getOrderPostWraper().getRedeemed_code_amount());
//
//        if (schedule.getTotalPayment() <= 0)
//            orderPostWraper.setTotal_amount((Double.parseDouble(schedule.getPickupSurcharge()) + Double.parseDouble(prefHelper.getStringPrefrence(AppConstant.TOTAL))));
//
//        return orderPostWraper;
//    }


    private void paymentService(String paymentType) {
        WebApiRequest.getInstance(SuccessTransationActivity.this, true).SaveOrder(
                getPaymentDetail(paymentType),
                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {


                        finish();
                        emptyBackStack();
                        prefHelper.putCartData(null);
                        prefHelper.putScheduleOrder(new ScheduleOrderModel());
                        addDockableSupportFragment(new HomeFragment(), HomeFragment.class.getName());
                        OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
                        double surCharge = Double.parseDouble(prefHelper.getSchedule().getPickupSurcharge());
                        orderSummaryFragment.setSummary((OrderWrapper) JsonHelpers.convertToModelClass(response.getResult(), OrderWrapper.class), surCharge);
                        addDockableSupportFragment(orderSummaryFragment, OrderSummaryFragment.class.getName());

//                    emptyBackStack();
//                        prefHelper.putCartData(null);
//                        prefHelper.putScheduleOrder(new ScheduleOrderModel());
//                        prefHelper.setBooleanPrefrence("PAYMENT_DONE", true);
//                        OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
//                        orderSummaryFragment.setSummary((OrderWrapper) JsonHelpers.convertToModelClass(response.getResult(), OrderWrapper.class), prefHelper.getSchedule().getPickupSurcharge());
//                        prefHelper.setBooleanPrefrence("PAYMENT_DONE", true);
//
//                        finish();
                    }

                    @Override
                    public void onError() {
//                        loadingFinished();
                    }

                    @Override
                    public void onNoNetwork() {
//                        loadingFinished();
                    }
                });
    }


    private OrderPostWraper getPaymentDetail(String type) {
        ArrayList<OrderDetailWraper> order_detail = new ArrayList<>();

        for (Item val : prefHelper.getCartData().getItems().values()) {
            order_detail.add(new OrderDetailWraper(val));
        }

        OrderInstructionWraper orderInstructionWraper = new OrderInstructionWraper();

        if (prefHelper.getUser().getUserInstruction() != null) {
            orderInstructionWraper.setAt_my_door(prefHelper.getUser().getUserInstruction().getAt_my_door() + "");
            orderInstructionWraper.setCall_me_before_delivery(prefHelper.getUser().getUserInstruction().getCall_me_before_delivery() + "");
            orderInstructionWraper.setCall_me_before_pickup(prefHelper.getUser().getUserInstruction().getCall_me_before_pickup() + "");
            orderInstructionWraper.setIs_fold(prefHelper.getUser().getUserInstruction().getIs_fold() + "");
            orderInstructionWraper.setOther(prefHelper.getUser().getUserInstruction().getOther());
        } else {
            orderInstructionWraper.setAt_my_door("0");
            orderInstructionWraper.setCall_me_before_delivery("0");
            orderInstructionWraper.setCall_me_before_pickup("0");
            orderInstructionWraper.setIs_fold("0");
            orderInstructionWraper.setOther("");
        }

        schedule = prefHelper.getSchedule();
        OrderPostWraper orderPostWraper = new OrderPostWraper();
        orderPostWraper.setUser_id(prefHelper.getUser().getId() + "");
        orderPostWraper.setAmount(Double.parseDouble(prefHelper.getStringPrefrence(AppConstant.TOTAL)));
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
        if (schedule.getPickupSurcharge() != null)
            orderPostWraper.setDelivery_amount(Double.valueOf(schedule.getPickupSurcharge())); // Constant rakhi hae change kerni hae
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
            orderPostWraper.setTotal_amount((Double.parseDouble(schedule.getPickupSurcharge()) + Double.parseDouble(prefHelper.getStringPrefrence(AppConstant.TOTAL))));

        return orderPostWraper;
    }
}
