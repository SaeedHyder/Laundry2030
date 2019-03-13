package com.ingic.mylaundry.ui;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.fragments.PaymentMethodFragment;
import com.ingic.mylaundry.models.CouponsDetail;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class CouponCodeDialoge extends DialogFragment {
    private final Double promoCodeDiscount;
    private final Double paymentAmount;
    private final Double vat;
    MainActivity activityMain;
    @BindView(R.id.txtSubTotal)
    TextView txtSubTotal;
    @BindView(R.id.txtPromoCode)
    TextView txtPromoCode;
    @BindView(R.id.txtPayment)
    TextView txtPayment;
    @BindView(R.id.btn_CouponOk)
    Button btnCouponOk;
    Unbinder unbinder;
    CouponsDetail couponsDetail;
    double subTotal;
    double discount;
    double percentDiscount;
    double afterDiscount;
    double subDiscount;
    String surCharge;
    @BindView(R.id.tv_subtotal)
    TextView tvSubtotal;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_payment)
    TextView tvPayment;
    @BindView(R.id.txt_instanceSurcharge)
    TextView txtInstanceSurcharge;
    @BindView(R.id.txtSurcharge)
    TextView txtSurcharge;
    @BindView(R.id.linearLayout_surcharge)
    LinearLayout linearLayoutSurcharge;
    @BindView(R.id.tv_vat)
    TextView tvVat;
    @BindView(R.id.txtVat)
    TextView txtVat;

    public static CouponCodeDialoge newInstance(MainActivity activityMain, CouponsDetail couponsDetail, String subTotal, String surcharge, Double promoCodeDiscount, Double paymentAmount, Double vat) {

        CouponCodeDialoge fragment = new CouponCodeDialoge(activityMain, couponsDetail, subTotal, surcharge, promoCodeDiscount, paymentAmount, vat);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    public CouponCodeDialoge(MainActivity activityMain, CouponsDetail couponsDetail, String subTotal, String surcharge, Double promoCodeDiscount, Double paymentAmount, Double vat) {
        this.activityMain = activityMain;
        this.couponsDetail = couponsDetail;
        this.subTotal = Double.parseDouble(subTotal);
        this.surCharge = surcharge;
        this.promoCodeDiscount = promoCodeDiscount;
        this.paymentAmount = paymentAmount;
        this.vat = vat;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.DialogTheme);
        setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_coupon_code_dialoge, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (!surCharge.equals("0")) {
            linearLayoutSurcharge.setVisibility(View.VISIBLE);
        } else {
            linearLayoutSurcharge.setVisibility(View.GONE);
        }
        if (couponsDetail.getType().equals(getString(R.string.percentage))) {
            txtPromoCode.setText("-" + promoCodeDiscount + "%");
        } else {
            txtPromoCode.setText(getString(R.string.aedmins) + " " + promoCodeDiscount);
        }

        txtSurcharge.setText(getString(R.string.aed) + " " + surCharge);
        txtVat.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(vat)));
        txtSubTotal.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(subTotal)));
        txtPayment.setText(getString(R.string.aed) + " " + (new DecimalFormat("##.##").format(paymentAmount)));

        setFonts();

        return view;
    }

    public void setFonts() {
        tvSubtotal.setTypeface(TextUtility.setPoppinsRegular(activityMain));
        txtSubTotal.setTypeface(TextUtility.setPoppinsRegular(activityMain));
        tvDiscount.setTypeface(TextUtility.setPoppinsRegular(activityMain));
        txtPromoCode.setTypeface(TextUtility.setPoppinsRegular(activityMain));
        txtPayment.setTypeface(TextUtility.setPoppinsRegular(activityMain));
        tvPayment.setTypeface(TextUtility.setPoppinsRegular(activityMain));
        btnCouponOk.setTypeface(TextUtility.setPoppinsRegular(activityMain));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() != null)
            getActivity().setTheme(R.style.AppTheme);
    }

    @OnClick(R.id.btn_CouponOk)
    public void onViewClicked() {

        dismiss();
        // activityMain.initFragments(new PaymentMethodFragment());
        activityMain.addFragments(new PaymentMethodFragment());
    }


}
