package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.CartItemAdapter;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.Item;
import com.ingic.mylaundry.models.NewOrderDetailWraper;
import com.ingic.mylaundry.models.Order;
import com.ingic.mylaundry.models.OrderWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderDetailFragment extends BaseFragment {


    private static final String DRY_CLEANING = "DRY CLEANING";
    private static final String PRESSING = "PRESSING";
    public Order order;

    @BindView(R.id.btn_orderTrackingStatus)
    Button btnOrderTrackingStatus;

    Unbinder unbinder;
    ArrayList<NewOrderDetailWraper> cartArrayList;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtNumber)
    TextView txtNumber;
    @BindView(R.id.txtPickupDateTime)
    TextView txtPickupDateTime;
    @BindView(R.id.txtPickupAddress)
    TextView txtPickupAddress;
    @BindView(R.id.txtDeliveryDateTime)
    TextView txtDeliveryDateTime;
    @BindView(R.id.txtDeliveryAddress)
    TextView txtDeliveryAddress;
    @BindView(R.id.txtPaymentMethod)
    TextView txtPaymentMethod;
    @BindView(R.id.txtPaymentDetail)
    TextView txtPaymentDetail;
    @BindView(R.id.layoutPayment)
    LinearLayout layoutPayment;
    @BindView(R.id.txtTotItem)
    TextView txtTotItem;
    @BindView(R.id.txtSubTotal)
    TextView txtSubTotal;
    @BindView(R.id.txtPromoCode)
    TextView txtPromoCode;
    @BindView(R.id.txtPayment)
    TextView txtPayment;
    @BindView(R.id.recyclerView_cart_dryClean)
    RecyclerView recyclerViewCartDryClean;
    @BindView(R.id.recyclerView_cart_steamIron)
    RecyclerView recyclerViewCartSteamIron;
    @BindView(R.id.recyclerView_WashIron)
    RecyclerView recyclerViewWashIron;
    @BindView(R.id.txtinstanceSurcharge)
    TextView txtinstanceSurcharge;
    @BindView(R.id.linear_promoCode)
    LinearLayout linearPromoCode;
    @BindView(R.id.linear_instanceCharge)
    LinearLayout linearInstanceCharge;
    @BindView(R.id.tv_customeDetail)
    TextView tvCustomeDetail;
    @BindView(R.id.tv_fullName)
    TextView tvFullName;
    @BindView(R.id.tv_mobileNumber)
    TextView tvMobileNumber;
    @BindView(R.id.tv_pickupDetail)
    TextView tvPickupDetail;
    @BindView(R.id.tv_pickupDate)
    TextView tvPickupDate;
    @BindView(R.id.tv_pickupAddress)
    TextView tvPickupAddress;
    @BindView(R.id.tv_deliveryDetail)
    TextView tvDeliveryDetail;
    @BindView(R.id.tv_deliveryDate)
    TextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryAddress)
    TextView tvDeliveryAddress;
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.tv_payment)
    TextView tvPayment;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_promoitem)
    TextView tvPromoitem;
    @BindView(R.id.tv_subtotal)
    TextView tvSubtotal;
    @BindView(R.id.tv_orderInstance)
    TextView tvOrderInstance;
    @BindView(R.id.tv_promoCode)
    TextView tvPromoCode;
    @BindView(R.id.tvpayment)
    TextView tvpayment;
    Double promo, vat;

    boolean comingFromNoti = false;
    @BindView(R.id.layoutOrder)
    LinearLayout layoutOrder;
    @BindView(R.id.tv_GrandTotal)
    TextView tvGrandTotal;
    @BindView(R.id.txtGrandTotal)
    TextView txtGrandTotal;
    private CartItemAdapter cartdrycleanAdapter;
    private CartItemAdapter cartSteamIronAdapter;
    private CartItemAdapter cartWashIronAdapter;
    private String id;

    public boolean isComingFromNoti() {
        return comingFromNoti;
    }

    public void setComingFromNoti(boolean comingFromNoti) {
        this.comingFromNoti = comingFromNoti;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        cartArrayList = new ArrayList<>();
        orderDetail();
        setFonts();
        languageLocale();
        setTitleBar(activityReference.getTitleBar());
        return view;
    }

    public void setFonts() {
        btnOrderTrackingStatus.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtinstanceSurcharge.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtDeliveryDateTime.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtDeliveryAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPickupDateTime.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvDeliveryAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPickupAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPaymentMethod.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvDeliveryDetail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvCustomeDetail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvOrderInstance.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPickupAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvDeliveryDate.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvMobileNumber.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPickupDetail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPickupDate.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPromoCode.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPromoitem.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtSubTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvPromoCode.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvFullName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtTotItem.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvSubtotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvpayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtNumber.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvpayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvItem.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvGrandTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtGrandTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getResources().getString(R.string.order_detail));
        titleBar.showHeader(true);
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
        unbinder.unbind();
    }

    @OnClick(R.id.btn_orderTrackingStatus)
    public void onViewClicked() {

        OrderTrackingStatusFragment trackingStatusFragment = new OrderTrackingStatusFragment();
        trackingStatusFragment.setOrderId(order.getId());
        activityReference.initFragments(trackingStatusFragment);

    }

    public void setOrderId(String orderId) {
        this.id = orderId;
    }

    public void setDetail(Order orders) {
        this.order = orders;
        this.id = String.valueOf(orders.getId());
    }

    private void fillDetail(Order order) {
//        orderDetail();
        if (order.getOrderStatus() == 8)
            btnOrderTrackingStatus.setVisibility(View.GONE);
        else
            btnOrderTrackingStatus.setVisibility(View.VISIBLE);
        txtName.setText(preferenceHelper.getUser().getName());
        txtNumber.setText(preferenceHelper.getUser().getPhone());

        txtPaymentMethod.setText(order.getPaymentType());
        txtPickupDateTime.setText(Utils.formatDatewithoututc(order.getPickupDate(), "yyyy-MM-dd", "dd MMM yy")
                + " " + Utils.formatDatewithoututc(order.getSlot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " - " + Utils.formatDatewithoututc(order.getSlot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase());
        if (order.getDelivery_slot() != null)
            txtDeliveryDateTime.setText(Utils.formatDatewithoututc(order.getDeliveryDate(), "yyyy-MM-dd", "dd MMM yy")
                    + " " + Utils.formatDatewithoututc(order.getDelivery_slot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " - " + Utils.formatDatewithoututc(order.getDelivery_slot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase());
        txtPickupAddress.setText(order.getPickupLocation());
        txtDeliveryAddress.setText(order.getDropLocation());

        Double subTotal = Double.valueOf(order.getAmount());
        Double payment = Double.valueOf(order.getTotalAmount());
        txtSubTotal.setText(getString(R.string.aed) + " " + (subTotal));
        vat = (payment * 5) / 100;

        txtPayment.setText(getString(R.string.aed) + " " + changeDecimalLocale(vat));
       /* if (promo != null) {
            txtPayment.setText(getString(R.string.aed) + " " + changeDecimalLocale((payment + vat) - promo));
            txtGrandTotal.setText("AED " + (subTotal - promo )+ "");
        }*/
        if (order.getTotalAmount().equals(0.0)) {
            double total = subTotal + order.getDeliveryAmount();
            vat = (total * 5) / 100;
          //  txtPayment.setText(getString(R.string.aed) + " " + ((total + vat) - promo));
            txtPayment.setText(getString(R.string.aed) + " " + changeDecimalLocale(vat));
        }
        orderloadingFinished();
    }

    private void orderDetail() {
        try {
            orderloadingStarted();

            WebApiRequest.getInstance(activityReference, true).
                    OrderDetail(Integer.parseInt(id),

                            new WebApiRequest.APIRequestDataCallBack() {

                                @Override
                                public void onSuccess(Api_Response response) {
                                    loadingFinished();
                                    OrderWrapper orderWrapper = (OrderWrapper) response.getResult();
                                    ArrayList<Item> dryCleanitems = new ArrayList<>();
                                    ArrayList<Item> steamIronitems = new ArrayList<>();
                                    ArrayList<Item> washIronitems = new ArrayList<>();
                                    layoutOrder.setVisibility(View.VISIBLE);

                                    if (orderWrapper.getOrder() != null) {
                                        if (orderWrapper.getOrder().getRedeem_amount() != null) {
                                            promo = Double.valueOf(orderWrapper.getOrder().getRedeem_amount());
                                            txtPromoCode.setText(getString(R.string.aedmins) + " " + changeDecimalLocale(promo));

                                        }
                                        txtinstanceSurcharge.setText(getString(R.string.aed) + " " + orderWrapper.getOrder().getDeliveryAmount());
                                        order = orderWrapper.getOrder();

                                        fillDetail(orderWrapper.getOrder());
                                    }
                                    setTitleBar(activityReference.getTitleBar());
//
//        Dry Clean
//        Steam Iron
//        Wash & Ironif
                                    if (orderWrapper.getOrder() != null) {

                                        ArrayList<NewOrderDetailWraper> data = orderWrapper.getOrder().getOrder_detail();
                                        for (int i = 0; i < orderWrapper.getOrder().getOrder_detail().size(); i++) {
                                            if (data.get(i).getItemDetail() != null && data.get(i).getItemDetail().getParenttype() != null) {
                                                if (data.get(i).getItemDetail().getParenttype().getParent() != null) {
                                                    if (data.get(i).getItemDetail().getParenttype().getParent().getTitle().equals(activityReference.getString(R.string.dry_clean))) {

                                                        dryCleanitems.add(new Item(data.get(i).getItemDetail().getId(), data.get(i).getItemDetail().getTitle(),
                                                                data.get(i).getItemDetail().getTypeId(), data.get(i).getItemDetail().getImage(), data.get(i).getItemDetail().getAmount(), data.get(i).getStatus(), data.get(i).getCreatedAt(), data.get(i).getCreatedAt(), data.get(i).getQuantity()));
                                                    } else if (data.get(i).getItemDetail().getParenttype().getParent().getTitle().equals(activityReference.getString(R.string.steam_ironing))) {
                                                        steamIronitems.add(new Item(data.get(i).getItemDetail().getId(), data.get(i).getItemDetail().getTitle(),
                                                                data.get(i).getItemDetail().getTypeId(), data.get(i).getItemDetail().getImage(), data.get(i).getItemDetail().getAmount(), data.get(i).getStatus(), data.get(i).getCreatedAt(), data.get(i).getCreatedAt(), data.get(i).getQuantity()));
                                                    } else {
                                                        washIronitems.add(new Item(data.get(i).getItemDetail().getId(), data.get(i).getItemDetail().getTitle(),
                                                                data.get(i).getItemDetail().getTypeId(), data.get(i).getItemDetail().getImage(), data.get(i).getItemDetail().getAmount(), data.get(i).getStatus(), data.get(i).getCreatedAt(), data.get(i).getCreatedAt(), data.get(i).getQuantity()));
                                                    }
                                                }
                                            }
                                        }
                                        cartdrycleanAdapter = new CartItemAdapter(activityReference, dryCleanitems);
                                        recyclerViewCartDryClean.setLayoutManager(new LinearLayoutManager(activityReference));
                                        recyclerViewCartDryClean.setAdapter(cartdrycleanAdapter);

                                        cartSteamIronAdapter = new CartItemAdapter(activityReference, steamIronitems);
                                        recyclerViewCartSteamIron.setLayoutManager(new LinearLayoutManager(activityReference));
                                        recyclerViewCartSteamIron.setAdapter(cartSteamIronAdapter);

                                        cartWashIronAdapter = new CartItemAdapter(activityReference, washIronitems);
                                        recyclerViewWashIron.setLayoutManager(new LinearLayoutManager(activityReference));
                                        recyclerViewWashIron.setAdapter(cartWashIronAdapter);
                                        txtTotItem.setText(orderWrapper.getOrder().getOrder_detail().size() + "");
                                        cartArrayList.addAll(orderWrapper.getOrder().getOrder_detail());

                                    }
                                    if (comingFromNoti) {
//
                                    }
                                    orderloadingFinished();

                                }

                                @Override
                                public void onError() {
                                    orderloadingFinished();
                                }

                                @Override
                                public void onNoNetwork() {
                                    orderloadingFinished();
                                }
                            });
        } catch (Exception ex) {

        }
    }

    private void languageLocale() {
        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)) {
            txtTotItem.setGravity(Gravity.LEFT);
            txtNumber.setTextDirection(View.TEXT_DIRECTION_LTR);
        } else {
            txtTotItem.setGravity(Gravity.START);
        }
    }

    private String changeDecimalLocale(double val) {
        NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.US);
        nf_out.setMaximumFractionDigits(2);
        return nf_out.format(val);
    }
}
