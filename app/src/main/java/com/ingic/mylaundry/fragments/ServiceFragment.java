package com.ingic.mylaundry.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.MySection;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.listener.TotalPriceListener;
import com.ingic.mylaundry.models.Item;
import com.ingic.mylaundry.models.MultipleItems;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.models.ServicesWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

//public interface SetTitlebarInterface {
//    void setTitlebarFromInterface(TitleBar titleBar);
//}

public class ServiceFragment extends BaseFragment implements Utils.Utilinterface {

    @BindView(R.id.radio_dry)
    RadioButton radioDry;
    @BindView(R.id.radio_steam)
    RadioButton radioSteam;
    @BindView(R.id.radio_wash)
    RadioButton radioWash;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    Unbinder unbinder;
    @BindView(R.id.recyclerView_Services)
    RecyclerView recyclerViewServices;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.linearLayout_Total)
    LinearLayout linearLayoutTotal;
    MultipleItems multipleItems;
    MySection mySection;
    Parcelable dryState;
    Parcelable steamState;
    Parcelable washState;
    Bundle stateBundle;
    CartFragment cartFragment = new CartFragment();
    int fullTotal = 0;
    @BindView(R.id.swipeRefresh_service)
    SwipeRefreshLayout swipeRefreshService;
    @BindView(R.id.txtTotalName)
    TextView txtTotalName;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private ServicesWrapper serviceWrapper;
    private TitleBar titleBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        unbinder = ButterKnife.bind(this, view);
        multipleItems = new MultipleItems();
        stateBundle = new Bundle();
        setRadiobutton();
        getServices();
        setFonts();

        titleBar = activityReference.getTitleBar();
        sectionAdapter = new SectionedRecyclerViewAdapter();

        swipeRefreshService.setColorSchemeResources(
                R.color.color_button);
        swipeRefreshService.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServices();
                swipeRefreshService.setRefreshing(false);
            }
        });

//

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setFonts() {
        txtTotalName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioDry.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioSteam.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioWash.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    public void setRadiobutton() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_dry) {
                    radioDry.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.dry), null, null);
                    radioSteam.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.iron), null, null);
                    radioWash.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.wash), null, null);
                } else if (i == R.id.radio_steam) {
                    radioDry.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.dry2), null, null);
                    radioSteam.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.iron2), null, null);
                    radioWash.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.wash), null, null);
                } else if (i == R.id.radio_wash) {
                    radioDry.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.dry2), null, null);
                    radioSteam.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.iron), null, null);
                    radioWash.setCompoundDrawablesWithIntrinsicBounds(null, activityReference.getResources().getDrawable(R.drawable.wash2), null, null);
                }
            }
        });
    }

    public void setData(final int position) {
        sectionAdapter.removeAllSections();
        if (serviceWrapper != null && serviceWrapper.getServices() != null && serviceWrapper.getServices().get(position) != null) {
            for (int i = 0; i < serviceWrapper.getServices().get(position).getServiceType().size(); i++) {
                if (serviceWrapper.getServices().get(position).getServiceType().get(i).getItem().size() > 0) {
                    ArrayList<Item> list = serviceWrapper.getServices().get(position).getServiceType().get(i).getItem();

                    mySection = new MySection(serviceWrapper.getServices().get(position).getServiceType().get(i).getTitle(),
                            list, position,
                            activityReference,
                            activityReference.getTitleBar(),
                            new TotalPriceListener() {
                                @Override
                                public void setTotal(boolean isAdd, int total, Item item, int quantity, int pos, int itemPosition) {
                                    maintainCart(isAdd, total, item, quantity, pos);
                                }
                            });
                    sectionAdapter.addSection(mySection);
                }
            }

            recyclerViewServices.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewServices.setAdapter(sectionAdapter);
            int resId = R.anim.layout_animation_right_slide;
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(activityReference, resId);
            recyclerViewServices.setLayoutAnimation(animation);
        }
    }

    public void getServices() {
        loadingStarted();

//        mySection.clear();
        WebApiRequest.getInstance(activityReference, true).GetServices(

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {
                        loadingFinished();
                        serviceWrapper = (ServicesWrapper) JsonHelpers.convertToModelClass(response.getResult(), ServicesWrapper.class);

                        setDataCart();
                        setData(0);
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

    private void setDataCart() {

        if (preferenceHelper.getCartData() != null && preferenceHelper.getCartData().getItems().size() > 0) {
            HashMap<String, Item> allitems = preferenceHelper.getCartData().getItems();
            ArrayList<Item> dryCleanitems = new ArrayList<>();
            ArrayList<Item> steamIronitems = new ArrayList<>();
            ArrayList<Item> washIronitems = new ArrayList<>();
//        Dry Clean
//        Steam Iron
//        Wash & Iron

            fullTotal = 0;
            for (Item val : allitems.values()) {
                fullTotal += (Integer.parseInt(val.getAmount()) * val.getQuantity());
                if (val.getParent().equals(activityReference.getString(R.string.dry_clean))) {
                    dryCleanitems.add(val);
                } else if (val.getParent().equals(activityReference.getString(R.string.steam_ironing))) {
                    steamIronitems.add(val);
                } else {
                    washIronitems.add(val);
                }
            }
            tvTotal.setText(AppConstant.AED + " " + fullTotal);

            if (serviceWrapper != null && serviceWrapper.getServices() != null && serviceWrapper.getServices().get(0) != null) {

                for (int k = 0; k < serviceWrapper.getServices().size(); k++) {

                    for (int i = 0; i < serviceWrapper.getServices().get(k).getServiceType().size(); i++) {
                        if (serviceWrapper.getServices().get(k).getServiceType().get(i).getItem().size() > 0) {
                            //serviceWrapper.getServices().get(k).getServiceType().get(i).getItem();

                            for (int j = 0; j < serviceWrapper.getServices().get(k).getServiceType().get(i).getItem().size(); j++) {
                                Item item = serviceWrapper.getServices().get(k).getServiceType().get(i).getItem().get(j);

                                for (Item val : allitems.values()) {
                                    if (val.getId().equals(item.getId())) {
                                        item.setQuantity(val.getQuantity());
                                    }
                                }

                            }

                        }
                    }
                }
            }


        }


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getString(R.string.services));

        titleBar.showCartButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferenceHelper.getCartData() != null && preferenceHelper.getCartData().getItems().size() > 0) {
                    cartFragment.fromService(true);
                    activityReference.addFragments(cartFragment);
                } else {
                    activityReference.initFragments(new MyCartFragment(), false);

                }
            }
        });
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferenceHelper.getCartData() != null && preferenceHelper.getCartData().getItems().size() > 0) {
                    Utils.alertDialog(getString(R.string.are_you_exit_cart), ServiceFragment.this, activityReference, getString(R.string.yes), getString(R.string.no));
                } else
                    activityReference.onPageBack();
            }
        });
    }

    public void setForceTitlebar(TitleBar titlebar) {
        setTitleBar(titlebar);
    }

    @Override
    public void onCustomBackPressed() {
        if (preferenceHelper.getCartData() != null && preferenceHelper.getCartData().getItems().size() > 0) {
            Utils.alertDialog(getString(R.string.are_you_exit_cart), ServiceFragment.this, activityReference, getString(R.string.yes), getString(R.string.no));
        } else
            activityReference.onPageBack();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Do whatever you want to do when the application stops.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.radio_dry, R.id.radio_steam, R.id.radio_wash, R.id.linearLayout_Total})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_dry:
                sectionAdapter.removeAllSections();
                setData(0);
                break;
            case R.id.radio_steam:
                sectionAdapter.removeAllSections();
                setData(1);
                break;
            case R.id.radio_wash:
                sectionAdapter.removeAllSections();
                setData(2);
                break;
            case R.id.linearLayout_Total:

                linearLayoutTotal.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayoutTotal.setEnabled(true);
                    }
                }, 2000);
                if (preferenceHelper.getCartData() != null && preferenceHelper.getCartData().getItems().size() > 0) {
                    cartFragment.fromService(true);
                    activityReference.addFragments(cartFragment);
                } else {
                    Utils.showToast(activityReference, getString(R.string.select_service));
                    // Toast.makeText(activityReference, getString(R.string.select_service), Toast.LENGTH_SHORT);
                }
//                openCart();
                break;
        }
    }

    private void maintainCart(boolean isAdd, int total, final Item item, int quantity, int pos) {
        multipleItems = preferenceHelper.getCartData();

        if (fullTotal >= 0) {
            if (isAdd) {
                fullTotal += total;
            } else {
                fullTotal -= total;
            }
            //init Cart
            if (multipleItems == null || multipleItems.getItems() == null) {
                preferenceHelper.putCartData(new MultipleItems(new HashMap<String, Item>()));
                multipleItems = preferenceHelper.getCartData();
            }
            //if List is empty
            if (multipleItems.getItems().size() == 0) {
                HashMap<String, Item> itemList = new HashMap<>();
                item.setQuantity(quantity);
                item.setParent(serviceWrapper.getServices().get(pos).getTitle());
                itemList.put(item.getId() + "", item);
                preferenceHelper.putCartData(new MultipleItems(itemList));


                //if List is not empty
            } else if (multipleItems.getItems().size() > 0) {
                HashMap<String, Item> items = multipleItems.getItems();

                if (multipleItems.getItems().get(item.getId() + "") != null) {
                    //Replace item
                    if (item.getQuantity() != 0) {
                        items.get(item.getId() + "").setQuantity(item.getQuantity());
                        preferenceHelper.putCartData(new MultipleItems(items));
                    } else {
                        items.remove(item.getId() + "");
                        preferenceHelper.putCartData(new MultipleItems(items));
                    }
                } else {
                    //Add new item
                    if (quantity == 0) {
                        items.remove(item.getId() + "");
                        preferenceHelper.putCartData(new MultipleItems(items));
                    } else {
                        item.setQuantity(quantity);
                        item.setParent(serviceWrapper.getServices().get(pos).getTitle());
                        items.put(item.getId() + "", item);
                        preferenceHelper.putCartData(new MultipleItems(items));
                    }
                }

            }
            preferenceHelper.putStringPreference(activityReference, String.valueOf(fullTotal), item.getAmount(), item.getTitle());

            tvTotal.setText(AppConstant.AED + " " + fullTotal);
            titleBar.setCartCount(preferenceHelper.getCartData().getItems().size());
        }
    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        preferenceHelper.putScheduleOrder(null);
        preferenceHelper.putCartData(null);
        activityReference.onPageBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
