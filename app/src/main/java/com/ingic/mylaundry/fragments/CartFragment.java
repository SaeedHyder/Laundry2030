package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.CartItemAdapter;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.models.CartModel;
import com.ingic.mylaundry.models.Item;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CartFragment extends BaseFragment {

    private static final String DRY_CLEANING = "DRY CLEANING";
    private static final String PRESSING = "PRESSING";

    Unbinder unbinder;
    @BindView(R.id.linearLayoutCart)
    LinearLayout linearLayoutCart;
    CartModel cartModel;
    @BindView(R.id.recyclerView_cart_dryClean)
    RecyclerView recyclerViewCartDryClean;
    @BindView(R.id.recyclerView_cart_steamIron)
    RecyclerView recyclerViewCartSteamIron;
    @BindView(R.id.recyclerView_WashIron)
    RecyclerView recyclerViewWashIron;
    int totalPrice = 0;
    @BindView(R.id.recyclerView_cart)
    RecyclerView recyclerViewCart;
    @BindView(R.id.txt_cartDetail)
    TextView txtCartDetail;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.tvPriice)
    TextView tvPriice;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtDryClean)
    TextView txtDryClean;
    @BindView(R.id.txtStreamIroning)
    TextView txtStreamIroning;
    @BindView(R.id.txtWashIron)
    TextView txtWashIron;
    private CartItemAdapter cartdrycleanAdapter;
    private CartItemAdapter cartSteamIronAdapter;
    private CartItemAdapter cartWashIronAdapter;
    private TitleBar titleBar;
    private boolean isService = false;
    private boolean isGuest;

    public void setCartModel(CartModel cartModel) {
        this.cartModel = cartModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerViewCartDryClean.setNestedScrollingEnabled(false);
        recyclerViewCartSteamIron.setNestedScrollingEnabled(false);
        recyclerViewWashIron.setNestedScrollingEnabled(false);
        setRetainInstance(true);
        titleBar = activityReference.getTitleBar();
        setFonts();
        init();
        txtCartDetail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        return view;
    }

    public void setFonts() {
        txtTotal.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtDryClean.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtStreamIroning.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtWashIron.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    public void fromService(boolean isService) {
        this.isService = isService;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getString(R.string.services));
        titleBar.showCartButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityReference.addFragments(new CartFragment());
            }
        });
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
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

    @Override
    public void onCustomBackPressed() {
        if (isGuest)
            activityReference.cartClearDialog();
        else
            activityReference.onPageBack();
    }

    @OnClick(R.id.linearLayoutCart)
    public void onViewClicked() {
        if (totalPrice >= 10)
            activityReference.addFragments(new OrderInformationFragment());
        else
            Toast.makeText(activityReference, getString(R.string._40_00_aed) + " " + getString(R.string.needed_to_reach_minimum_order), Toast.LENGTH_SHORT).show();
    }

    private void init() {

        if (preferenceHelper.getCartData() != null && preferenceHelper.getCartData().getItems().size() > 0) {
            HashMap<String, Item> allitems = preferenceHelper.getCartData().getItems();
            ArrayList<Item> dryCleanitems = new ArrayList<>();
            ArrayList<Item> steamIronitems = new ArrayList<>();
            ArrayList<Item> washIronitems = new ArrayList<>();
//        Dry Clean
//        Steam Iron
//        Wash & Iron

            totalPrice = 0;
            for (Item val : allitems.values()) {
                totalPrice += (Integer.parseInt(val.getAmount()) * val.getQuantity());
                if (val.getParent().equals(activityReference.getString(R.string.dry_clean))) {
                    dryCleanitems.add(val);
                } else if (val.getParent().equals(activityReference.getString(R.string.steam_ironing))) {
                    steamIronitems.add(val);
                } else {
                    washIronitems.add(val);
                }
            }

//            totalPrice = 0;
//            for (Item val : allitems.values()) {
//                totalPrice += (Integer.parseInt(val.getAmount()) * val.getQuantity());
//                if (val.getId().equals("1")) {
//                    dryCleanitems.add(val);
//                } else if (val.getId().equals("2")) {
//                    steamIronitems.add(val);
//                } else if (val.getId().equals("3")){
//                    washIronitems.add(val);
//                }
//            }

            txtPrice.setText(AppConstant.AED + " " + totalPrice);
            preferenceHelper.setStringPrefrence(AppConstant.TOTAL, totalPrice + "");
            cartdrycleanAdapter = new CartItemAdapter(activityReference, dryCleanitems);
            recyclerViewCartDryClean.setLayoutManager(new LinearLayoutManager(activityReference));
            recyclerViewCartDryClean.setAdapter(cartdrycleanAdapter);

            cartSteamIronAdapter = new CartItemAdapter(activityReference, steamIronitems);
            recyclerViewCartSteamIron.setLayoutManager(new LinearLayoutManager(activityReference));
            recyclerViewCartSteamIron.setAdapter(cartSteamIronAdapter);

            cartWashIronAdapter = new CartItemAdapter(activityReference, washIronitems);
            recyclerViewWashIron.setLayoutManager(new LinearLayoutManager(activityReference));
            recyclerViewWashIron.setAdapter(cartWashIronAdapter);
        }
    }

    public void setIsGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }
}
