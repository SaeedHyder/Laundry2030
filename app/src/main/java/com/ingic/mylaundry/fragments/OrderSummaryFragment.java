package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.OrderWrapper;
import com.ingic.mylaundry.models.ScheduleOrderModel;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderSummaryFragment extends BaseFragment {

    @BindView(R.id.btn_orderHistory)
    Button btnOrderHistory;
    @BindView(R.id.btn_continueLaundry)
    Button btnContinueLaundry;
    Unbinder unbinder;
    @BindView(R.id.txtOrderId)
    TextView txtOrderId;
    @BindView(R.id.txtPickupDateTime)
    TextView txtPickupDateTime;
    @BindView(R.id.txtDeliveryDateTime)
    TextView txtDeliveryDateTime;
    @BindView(R.id.txtInstruction)
    TextView txtInstruction;
    @BindView(R.id.txtDeliveryAddress)
    TextView txtDeliveryAddress;
    @BindView(R.id.txtPaymentMethod)
    TextView txtPaymentMethod;
    @BindView(R.id.txtPickupAddress)
    TextView txtPickupAddress;
    @BindView(R.id.txtDeliveryAddress2)
    TextView txtDeliveryAddress2;
    @BindView(R.id.txtSubTotal)
    TextView txtSubTotal;
    @BindView(R.id.txtPromoCode)
    TextView txtPromoCode;
    @BindView(R.id.txtPayment)
    TextView txtPayment;
    TitleBar titleBar;
    @BindView(R.id.txtSurcharge)
    TextView txtSurcharge;
    @BindView(R.id.linear_promo)
    LinearLayout linearPromo;
    @BindView(R.id.linearSummarySurcharge)
    LinearLayout linearSummarySurcharge;
    @BindView(R.id.tv_summaryDetail)
    TextView tvSummaryDetail;
    @BindView(R.id.tv_orderPlaced)
    TextView tvOrderPlaced;
    @BindView(R.id.tv_pickup)
    TextView tvPickup;
    @BindView(R.id.tv_delivery)
    TextView tvDelivery;
    @BindView(R.id.tv_provideInstruction)
    TextView tvProvideInstruction;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_payment)
    TextView tvPayment;
    @BindView(R.id.tv_pickupAdd)
    TextView tvPickupAdd;
    @BindView(R.id.tv_delivry)
    TextView tvDelivry;
    @BindView(R.id.tv_subTotal)
    TextView tvSubTotal;
    @BindView(R.id.tv_surcharge)
    TextView tvSurcharge;
    @BindView(R.id.tv_promoCode)
    TextView tvPromoCode;
    @BindView(R.id.tv_paymment)
    TextView tvPaymment;
    double surcharge;
    @BindView(R.id.tv_GrandTotal)
    TextView tvGrandTotal;
    @BindView(R.id.txtGrandTotal)
    TextView txtGrandTotal;
    private OrderWrapper summary;
    double vat;
    Double promo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        unbinder = ButterKnife.bind(this, view);
        setSummaryDetail();
        setFonts();
        preferenceHelper.putCartData(null);
        return view;
    }

    public void setFonts() {
        tvSummaryDetail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnContinueLaundry.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnOrderHistory.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvOrderPlaced.setTypeface(TextUtility.setPoppinsSemiBold(activityReference));
        txtOrderId.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPickupAdd.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPickupDateTime.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvDelivery.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvDelivry.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvProvideInstruction.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtInstruction.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtDeliveryAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPaymentMethod.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPickupAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtDeliveryAddress2.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSubTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtSubTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSurcharge.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtSurcharge.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPromoCode.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPromoCode.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPaymment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void onResume() {
        super.onResume();
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle(activityReference.getResources().getString(R.string.thankyou));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
        titleBar.showHeader(true);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @OnClick({R.id.btn_orderHistory, R.id.btn_continueLaundry})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_orderHistory:
                activityReference.initFragments(new MyOrderFragment());
                break;
            case R.id.btn_continueLaundry:
                preferenceHelper.putScheduleOrder(new ScheduleOrderModel());
                activityReference.initFragments(new ServiceFragment());
                break;
        }
    }

    public void setSummary(OrderWrapper summary, double Surcharge) {
        this.summary = summary;
        this.surcharge = Surcharge;
    }

    private void setSummaryDetail() {

        if (summary != null) {
            txtOrderId.setText(activityReference.getString(R.string.order_id_1245) + summary.getOrder().getId());
            txtPickupDateTime.setText
                    (Utils.formatDatewithoututc(summary.getOrder().getPickupDate(), "yyyy-MM-dd", "dd MMM yy")
                            + " " + Utils.formatDatewithoututc(summary.getOrder().getSlot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " - " + Utils.formatDatewithoututc(summary.getOrder().getSlot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase());
            txtDeliveryDateTime.setText(Utils.formatDatewithoututc(summary.getOrder().getDeliveryDate(), "yyyy-MM-dd", "dd MMM yy") + " " + Utils.formatDatewithoututc(summary.getOrder().getDelivery_slot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " - " + Utils.formatDatewithoututc(summary.getOrder().getDelivery_slot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase());
            String instruction = "";
            if (summary.getOrder().getOrder_instructions().getIs_fold().equals("1")) {
                instruction += getString(R.string.fold_my_clothes);
            }
            if (summary.getOrder().getOrder_instructions().getAt_my_door().equals("1")) {
                if (!Utils.isEmptyOrNull(instruction))
                    instruction += "\n";
                instruction += getString(R.string.leave_it_at_my_door);
            }
            if (summary.getOrder().getOrder_instructions().getCall_me_before_pickup().equals("1")) {
                if (!Utils.isEmptyOrNull(instruction))
                    instruction += "\n";
                instruction += getString(R.string.call_me_before);
            }
            if (summary.getOrder().getOrder_instructions().getCall_me_before_delivery().equals("1")) {
                if (!Utils.isEmptyOrNull(instruction))
                    instruction += "\n";
                instruction += getString(R.string.call_me_before_delivery);
            }
            if (!Utils.isEmptyOrNull(summary.getOrder().getOrder_instructions().getOther())) {
                if (!Utils.isEmptyOrNull(instruction))
                    instruction += "\n";
                instruction += "" + summary.getOrder().getOrder_instructions().getOther();
            }
            txtInstruction.setText(instruction);
            txtDeliveryAddress.setText(summary.getOrder().getDropLocation());
            txtDeliveryAddress2.setText(summary.getOrder().getDropLocation());
            txtPickupAddress.setText(summary.getOrder().getPickupLocation());
            //if (summary.getOrder().getPaymentType().equals("cash")) {
            //  txtPaymentMethod.setText(activityReference.getString(R.string.cash_on_delivery));
//            } else {
            txtPaymentMethod.setText(summary.getOrder().getPaymentType());
//            }
            txtSubTotal.setText(getString(R.string.aed) + " " + summary.getOrder().getAmount());
            if (summary.getOrder().getRedeem_amount() != null) {
                promo = Double.valueOf(summary.getOrder().getRedeem_amount());
                txtPromoCode.setText(getString(R.string.aedmins) + " " + (new DecimalFormat("##.##").format(promo)));
                linearPromo.setVisibility(View.VISIBLE);
                double grandTotal = summary.getOrder().getAmount() - promo;
                txtGrandTotal.setText(getString(R.string.aed) + " " + grandTotal + "");

            } else {
                linearPromo.setVisibility(View.GONE);
            }
            if (surcharge != 0) {
                txtSurcharge.setText(getString(R.string.aed) + " " + surcharge + "");
                linearSummarySurcharge.setVisibility(View.VISIBLE);
            } else {
                linearSummarySurcharge.setVisibility(View.GONE);
            }
            double addvat = summary.getOrder().getAmount();
            double totalAmout = ((summary.getOrder().getAmount() + surcharge));
            double sumTotalAmount = (totalAmout - promo);

            vat = (sumTotalAmount * 5) / 100;

            txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format( vat)));
            //  vat = (summary.getOrder().getAmount()*5)/100;
           /* if (summary.getOrder().getTotalAmount().equals(0.0)) {

                if (surcharge == 0) {
                    txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format( vat)));
                   // txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(sumTotalAmount + vat)));
                    // txtPayment.setText(getString(R.string.aed) + " " +  vat);
                } else {
                    txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(sumTotalAmount + surcharge + vat)));
                    // txtPayment.setText(getString(R.string.aed) + " " +  (vat));
                }
            } else {
                txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(sumTotalAmount + vat)));
                // txtPayment.setText(getString(R.string.aed) + " " + ( vat));
            }*/
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}