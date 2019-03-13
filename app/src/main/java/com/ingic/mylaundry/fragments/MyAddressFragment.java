package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.Interface.AddressPickerInterface;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.MyAddressAdapter;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.Address;
import com.ingic.mylaundry.models.AdressData;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyAddressFragment extends BaseFragment {

    Unbinder unbinder;

    @BindView(R.id.notfoundTxt)
    TextView notfoundTxt;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    int offset = 0;
    int limit = 7;
    int resId;
    private String type;
    @BindView(R.id.noDataLayout)
    LinearLayout noDataLayout;
    private TitleBar titleBar;
    AdressData selectedAddress;
    MyAddressAdapter addressAdapter;
    @BindView(R.id.recyclerView_myAddress)
    RecyclerView recyclerViewMyAddress;

    ScheduleOrderModel scheduleOrderModel;
    @BindView(R.id.swipeRefresh_myAddress)
    SwipeRefreshLayout swipeRefreshMyAddress;
    private ScheduleFragment scheduleFragment;
    private EditProfileFragment editProfileFragment;
    ArrayList<AdressData> adressData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_address, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleBar = activityReference.getTitleBar();
        addressAdapter = new MyAddressAdapter(activityReference, adressData, type, scheduleFragment, editProfileFragment, new AddressPickerInterface() {
            @Override
            public void onAddressPicked(AdressData address) {
                selectedAddress = address;
            }

            @Override
            public void editAddress_Click() {
                selectedAddress = null;
            }
        });
        recyclerViewMyAddress.setLayoutManager(new LinearLayoutManager(activityReference));
        recyclerViewMyAddress.setAdapter(addressAdapter);

        getAddresses(preferenceHelper.getUser().getId(), offset, limit);

        swipeRefreshMyAddress.setColorSchemeResources(
                R.color.color_button);
        swipeRefreshMyAddress.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                resId = R.anim.layout_animation_right_slide;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(activityReference, resId);
                recyclerViewMyAddress.setLayoutAnimation(animation);

                getAddresses(preferenceHelper.getUser().getId(), offset, limit);
                swipeRefreshMyAddress.setRefreshing(false);
            }
        });

        return view;
    }

    public void getAddresses(int user_id, int offset, int limit) {
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

                        addressAdapter.clearAllList();
                        addressAdapter.addAllList(addressWrapper.getData());
                    }

                    @Override
                    public void onError() {
                        loadingFinished();
                        hideView();
                        if (addressAdapter.getItemCount() > 0) {
                            visibleView();
                        } else {
                            hideView();
                        }
                    }

                    @Override
                    public void onNoNetwork() {
                        loadingFinished();
                        hideView();
                        if (addressAdapter.getItemCount() > 0) {
                            visibleView();
                        } else {
                            hideView();
                        }
                    }
                }
        );
    }

    public void visibleView() {
        if (recyclerViewMyAddress != null)
            recyclerViewMyAddress.setVisibility(View.VISIBLE);
        if (noDataLayout != null)
            noDataLayout.setVisibility(View.GONE);
    }

    public void hideView() {
        if (recyclerViewMyAddress != null)
            recyclerViewMyAddress.setVisibility(View.GONE);
        if (noDataLayout != null)
            noDataLayout.setVisibility(View.VISIBLE);
        if (notfoundTxt != null)
            notfoundTxt.setText(activityReference.getResources().getString(R.string.sorry_no_data_found));
    }

    @Override
    public void onDestroyView() {
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
        super.onDestroyView();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
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
                selectedAddress = null;
                activityReference.initFragments(pickupAddressFragment);
            }
        });
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    public void setType(String type, ScheduleFragment scheduleFragment) {
        this.type = type;
        this.scheduleFragment = scheduleFragment;
    }

    public void setProfileType(String type, EditProfileFragment editProfileFragment) {
        this.type = type;
        this.editProfileFragment = editProfileFragment;
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        scheduleOrderModel = preferenceHelper.getSchedule();

        if (type.equals(AppConstant.EDIT_PROFILE)) {

            if (selectedAddress == null || Utils.isEmptyOrNull(selectedAddress.getAddress())) {
                Utils.showToast(activityReference, getString(R.string.please_select_address));

                return;
            }
            if (editProfileFragment != null) {
                editProfileFragment.setAddress(selectedAddress.getAddress());
            }

        } else if (type.equals(AppConstant.PICKUP)) {
            if (selectedAddress == null || Utils.isEmptyOrNull(selectedAddress.getAddress())) {
                Utils.showToast(activityReference, getString(R.string.please_select_address));
                return;
            }
            if (scheduleFragment != null) {
                scheduleFragment.setScheduleAddress(selectedAddress.getAddress(), type);

            }
            scheduleOrderModel.setPickupLat(selectedAddress.getLatitude());
            scheduleOrderModel.setPickupLong(selectedAddress.getLongitude());
            //changing
            scheduleOrderModel.setPickupType(scheduleOrderModel.getPickupTypeService());
            scheduleOrderModel.setPickupLocation(selectedAddress.getLocation());
            scheduleOrderModel.setPickupTypeService(selectedAddress.getType());


        } else if (type.equals(AppConstant.DELIVERY)) {
            if (selectedAddress == null || Utils.isEmptyOrNull(selectedAddress.getAddress())) {
                Utils.showToast(activityReference, getString(R.string.please_select_address));
                return;
            }
            if (scheduleFragment != null) {
                scheduleFragment.setScheduleAddress(selectedAddress.getAddress(), type);
            }
            scheduleOrderModel.setDeliveryLat(selectedAddress.getLatitude());
            scheduleOrderModel.setDeliveryLong(selectedAddress.getLongitude());
            scheduleOrderModel.setDeliveryType(scheduleOrderModel.getDeliveryTypeService());
            scheduleOrderModel.setDeliveryLocation(selectedAddress.getLocation());
            scheduleOrderModel.setDeliveryTypeService(selectedAddress.getType());
        }

        preferenceHelper.putScheduleOrder(scheduleOrderModel);
        activityReference.onBackPressed();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (type.equals(AppConstant.EDIT_PROFILE)) {
            titleBar.setTitle(activityReference.getString(R.string.my_profile));
        } else if (type.equals(AppConstant.DELIVERY.equals(type.equals(AppConstant.PICKUP)))) {
            titleBar.setTitle(activityReference.getString(R.string.schedule_a_wash));
        }
    }
}
