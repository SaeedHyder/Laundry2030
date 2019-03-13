package com.ingic.mylaundry.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ingic.mylaundry.Interface.setInstantCheck;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.CouponsDetail;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.ui.CouponCodeDialoge;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderInformationFragment extends BaseFragment implements Utils.Utilinterface, setInstantCheck {

    @BindView(R.id.tv_schedule)
    TextView tvSchedule;
    @BindView(R.id.tv_ProvideInstructions)
    TextView tvProvideInstructions;
    @BindView(R.id.tv_subTotal)
    TextView tvSubTotal;
    Unbinder unbinder;
    @BindView(R.id.tv_checkOut)
    TextView tvCheckOut;
    @BindView(R.id.linearLayout_Checkout)
    LinearLayout linearLayoutCheckout;
    @BindView(R.id.txtSubPrice)
    TextView txtSubPrice;
    @BindView(R.id.txtPayment)
    TextView txtPayment;
    @BindView(R.id.txtCheckoutPrice)
    TextView txtCheckoutPrice;
    @BindView(R.id.txt_CouponCode)
    EditText txtCouponCode;
    @BindView(R.id.txtSurcharge)
    TextView txtSurcharge;
    @BindView(R.id.linearLayout_surcharge)
    LinearLayout linearLayoutSurcharge;
    @BindView(R.id.txt_instanceSurcharge)
    TextView txtInstanceSurcharge;
    @BindView(R.id.tv_payment)
    TextView tvPayment;
    String couponCode;
    double discount;
    double sum, vat;
    String is_redeemed_code;
    String is_redeemed_amount;
    CouponsDetail couponsWrapper;
    ScheduleOrderModel scheduleOrderModel;
    CouponCodeDialoge couponCodeDialoge;
    private TitleBar titleBar;
    double subTotalInstant;
    boolean checkIntant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        setFonts();
        setValue();
        guestUser();
        titleBar = activityReference.getTitleBar();
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
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

    public void setFonts() {
        tvSchedule.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvProvideInstructions.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSubTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvCheckOut.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtCouponCode.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtSurcharge.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (titleBar != null)
            titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getString(R.string.cart));
        titleBar.showHeader(true);
        titleBar.hideHomeLogo();
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCustomBackPressed();
            }
        });
    }

    public void getCoupons(String code, int user_id) {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).getCoupons(
                code,
                user_id,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        couponsWrapper = (CouponsDetail) JsonHelpers.convertToModelClass(response.getResult(), CouponsDetail.class);
                        String pickupSurcharge;
                        if (preferenceHelper.getSchedule().getPickupSurcharge() != null) {
                            linearLayoutSurcharge.setVisibility(View.VISIBLE);
                            pickupSurcharge = preferenceHelper.getSchedule().getPickupSurcharge();
                            txtSurcharge.setText(getString(R.string.aed) + " " + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge()));
                        } else {
                            pickupSurcharge = "0";
                            linearLayoutSurcharge.setVisibility(View.GONE);
                        }


                        if (couponsWrapper.getType().equals("percentage")) {

//                            if (preferenceHelper.getSchedule().getPickupSurcharge() != null) {
//                                subTotalInstant = (Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge()));
//                            }
                            double total = Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL));
                            double promoCoupon = couponsWrapper.getAmount();

                            discount = ((total) + Double.parseDouble(pickupSurcharge)) * promoCoupon / 100;
                            double totalAfterDis = total - discount;
                            vat = (totalAfterDis * 5) / 100;
                            double afterVatDiscount = vat + totalAfterDis;


                            txtPayment.setText(getString(R.string.aed) + " " + new DecimalFormat("##.##").format(vat));
                            txtCheckoutPrice.setText(getString(R.string.aed) + " " + new DecimalFormat("##.##").format(afterVatDiscount));


//                            double vatWithoutDiscount = (total * 5) / 100;
//                            couponCodeDialoge = CouponCodeDialoge.newInstance(activityReference, couponsWrapper,   (total + vatWithoutDiscount) + "", Double.valueOf(pickupSurcharge),promoCoupon,subTotal);
                            couponCodeDialoge = CouponCodeDialoge.newInstance(activityReference, couponsWrapper,
                                    total + "", pickupSurcharge, promoCoupon, afterVatDiscount,vat);

                            couponCodeDialoge.show((activityReference).getSupportFragmentManager(), null);

//                            double subTotalPromo = (total - promoCoupon);
//                            double subTotal = (subTotalInstant - promoCoupon);
//                            vat = (subTotal * 5) / 100;
//                            txtPayment.setText((getString(R.string.aed) + " " + subTotal + vat) + "");
//                            txtCheckoutPrice.setText((getString(R.string.aed) + " " + subTotalPromo + vat) + "");
//
//                            discount = ((total) + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge())) * couponsWrapper.getAmount() / 100;


//                            Double dis = Double.valueOf((new DecimalFormat("##.##").format(discount)));
//                            Double payment = ((Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge())) - dis);
//                            vat = (payment * 5) / 100;
//                            txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(vat)));
//                            txtCheckoutPrice.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(vat)));

                            scheduleOrderModel = preferenceHelper.getSchedule();
                            scheduleOrderModel.setIs_redeemed_code(couponsWrapper.getCode());
                            scheduleOrderModel.setIs_redeemed_amount(discount);
                            // double surCharge = Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge()+"");
//                            if (preferenceHelper.getSchedule().getPickupSurcharge() != null) {
//                                scheduleOrderModel.setTotalPayment(((Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge())) - promoCoupon));
//                            } else {
//                                scheduleOrderModel.setTotalPayment(((Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) + 0) - promoCoupon));
//                            }

                            preferenceHelper.putScheduleOrder(scheduleOrderModel);

                        } else {

                            double total = Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL));
                            double totalAmount = Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL));
                            double promoCoupon = couponsWrapper.getAmount();

                            total = (total + Double.parseDouble(pickupSurcharge));
                            double subTotal = (total - promoCoupon);
//                            double subTotal = (subTotalInstant - promoCoupon);
                            vat = (subTotal * 5) / 100;
                            subTotal += vat;
                           // txtPayment.setText((getString(R.string.aed) + " " + subTotal) + "");
                            txtPayment.setText((getString(R.string.aed) + " " + vat) + "");
                            txtCheckoutPrice.setText((getString(R.string.aed) + " " + subTotal + ""));

                            couponCodeDialoge = CouponCodeDialoge.newInstance(activityReference, couponsWrapper,
                                    totalAmount+"" ,pickupSurcharge, promoCoupon, subTotal,vat);
//                            double vatWithoutDiscount = (total * 5) / 100;
//                            couponCodeDialoge = CouponCodeDialoge.newInstance(activityReference, couponsWrapper,   (total + vatWithoutDiscount) + "", Double.valueOf(pickupSurcharge),promoCoupon,subTotal);

                            couponCodeDialoge.show((activityReference).getSupportFragmentManager(), null);


//                            double coupon = couponsWrapper.getAmount();
//                            double total = Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL));
//                           // double surCharge = Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge());
//                            double SuBTotal;
//
//                            if (preferenceHelper.getSchedule().getPickupSurcharge() != null) {
//                                double surCharge = Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge());
//                                 SuBTotal = (total + surCharge);
//                            }
//                            else
//                            {
//                                SuBTotal = (total);
//                            }
//
//
//
//                            double totalDiscount = SuBTotal + couponsWrapper.getAmount();
//                            discount = ((Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge())) * couponsWrapper.getAmount());
//                            Double dis = Double.valueOf((new DecimalFormat("##.##").format(totalDiscount)));
//                            Double payment = ((Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge())) - dis);
//                            vat = (payment * 5) / 100;
//                            txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(vat)));
//                            txtCheckoutPrice.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(vat)));
                            scheduleOrderModel = preferenceHelper.getSchedule();
                            scheduleOrderModel.setIs_redeemed_code(couponsWrapper.getCode());
                            scheduleOrderModel.setIs_redeemed_amount(couponsWrapper.getAmount());
                            preferenceHelper.putScheduleOrder(scheduleOrderModel);
//                            Double subtotal = (Integer.parseInt(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) - couponsWrapper.getAmount());
//                            vat = (subtotal * 5) / 100;
//                            txtPayment.setText(getString(R.string.aed) + " " + vat);
//                            txtCheckoutPrice.setText(getString(R.string.aed) + " " + vat);
                        }

                        loadingFinished();
                    }

                    @Override
                    public void onError() {
                        loadingFinished();
                        //  hideView();
                    }

                    @Override
                    public void onNoNetwork() {
                        loadingFinished();

                        //   hideView();
                    }
                }
        );
    }

    @OnClick({R.id.tv_schedule, R.id.tv_ProvideInstructions, R.id.linearLayout_Checkout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_schedule:
                //  activityReference.initFragments(new ScheduleFragment());
                activityReference.addFragments(new ScheduleFragment(this));

                break;
            case R.id.tv_ProvideInstructions:
                activityReference.addFragments(new ProvideInstructionFragment());
                break;
            case R.id.linearLayout_Checkout:

                couponCode = txtCouponCode.getText().toString();

                if (preferenceHelper.getSchedule() != null && !Utils.isEmptyOrNull(preferenceHelper.getSchedule().getPickupTime())) {

                    if (!couponCode.equals("")) {

                        // insert specicfic id for using coupons code
                        getCoupons(couponCode, preferenceHelper.getUser().getId());
                    } else {
                        txtCouponCode.setText("");
                        activityReference.addFragments(new PaymentMethodFragment());
                    }

                } else {
                    Toast.makeText(activityReference, getString(R.string.select_schedule), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void setValue() {
        double addvat;
        addvat = Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL));
        vat = (addvat * 5) / 100;
        txtSubPrice.setText(getString(R.string.aed) + " " + Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)));
        //  txtPayment.setText(getString(R.string.aed) + " " + (addvat + vat));
        txtPayment.setText(getString(R.string.aed) + " " + (vat));
        txtCheckoutPrice.setText(getString(R.string.aed) + " " + (addvat + vat));
        if (preferenceHelper.getSchedule() != null && preferenceHelper.getSchedule().getPickupSurcharge() != null) {
            txtSurcharge.setText(getString(R.string.aed) + " " + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge()));
            sum = (Integer.parseInt(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) + (Integer.parseInt(preferenceHelper.getSchedule().getPickupSurcharge())));
            linearLayoutSurcharge.setVisibility(View.VISIBLE);
            vat = (sum * 5) / 100;
            // txtPayment.setText(getString(R.string.aed) + " " + (sum + vat));
            txtPayment.setText(getString(R.string.aed) + " " + (vat));
            txtCheckoutPrice.setText(getString(R.string.aed) + " " + (sum + vat));
            txtSubPrice.setText(getString(R.string.aed) + " " + Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)));
        } else {
            linearLayoutSurcharge.setVisibility(View.GONE);
        }
    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER)) {
//            activityReference.emptyBackStack();
            preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
            preferenceHelper.setLoginStatus(false);

//            activityReference.addFragments(new LoginFragment());
//            activityReference.popFragment();

            activityReference.popBackStackTillEntry(0);
            activityReference.replaceDockableSupportFragment(new LoginFragment(), true);

        } else
            dialog.dismiss();
    }

    private void guestUser() {
        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER)) {
            tvSchedule.setOnClickListener(guestListner());
            tvProvideInstructions.setOnClickListener(guestListner());
            linearLayoutCheckout.setOnClickListener(guestListner());
        }
    }

    @Override
    public void setInstantCheck(boolean instantCheck) {
        checkIntant = instantCheck;
        if (instantCheck == true) {
            linearLayoutSurcharge.setVisibility(View.VISIBLE);
            if (preferenceHelper.getSchedule() != null && preferenceHelper.getSchedule().getPickupSurcharge() != null) {
                txtSurcharge.setText(getString(R.string.aed) + " " + Double.parseDouble(preferenceHelper.getSchedule().getPickupSurcharge()));
                sum = (Integer.parseInt(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)) + (Integer.parseInt(preferenceHelper.getSchedule().getPickupSurcharge())));
                //  linearLayoutSurcharge.setVisibility(View.VISIBLE);
                vat = (sum * 5) / 100;
                // txtPayment.setText(getString(R.string.aed) + " " + (sum + vat));
                txtPayment.setText(getString(R.string.aed) + " " + (vat));
                txtCheckoutPrice.setText(getString(R.string.aed) + " " + (sum + vat));
                txtSubPrice.setText(getString(R.string.aed) + " " + Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)));
            }
        } else {
            linearLayoutSurcharge.setVisibility(View.GONE);

            sum = (Integer.parseInt(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)));
            vat = (sum * 5) / 100;
            //  txtPayment.setText(getString(R.string.aed) + " " + (sum + vat));
            txtPayment.setText(getString(R.string.aed) + " " + (vat));
            txtCheckoutPrice.setText(getString(R.string.aed) + " " + (sum + vat));
            txtSubPrice.setText(getString(R.string.aed) + " " + Double.parseDouble(preferenceHelper.getStringPrefrence(AppConstant.TOTAL)));
        }
    }
}
