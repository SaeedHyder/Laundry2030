package com.ingic.mylaundry.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.adapters.ActiveOrderAdapter;
import com.ingic.mylaundry.adapters.PickupTimeAdapter;
import com.ingic.mylaundry.listener.Pickuplistener;
import com.ingic.mylaundry.models.AvailableTimeSlot;
import com.ingic.mylaundry.models.AvailableTimeSlotWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PickupTimeDialog extends DialogFragment {

    MainActivity mainActivity;
    @BindView(R.id.reyclerView_pickUpTime)
    RecyclerView reyclerViewPickUpTime;
    Unbinder unbinder;
    PickupTimeDialog fragmentdismiss;
    PickupTimeAdapter timeAdapter;
    Pickuplistener mListener;
    private boolean is_pickup;

    AvailableTimeSlotWrapper availableTimeSlotWrapper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.DialogTimeTheme);
        setCancelable(true);
    }

    public static PickupTimeDialog newInstance(MainActivity mainActivity, Pickuplistener listener, boolean is_pickup) {
        PickupTimeDialog fragment = new PickupTimeDialog();
        fragment.mainActivity = mainActivity;
        fragment.mListener = listener;
        fragment.fragmentdismiss = fragment;
        fragment.is_pickup = is_pickup;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pickup_time_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            availableTimeSlotWrapper = (AvailableTimeSlotWrapper) bundle.getSerializable("key_available_time");
        }

        if (availableTimeSlotWrapper != null) {
            timeAdapter = new PickupTimeAdapter(mainActivity, mListener, fragmentdismiss, is_pickup, availableTimeSlotWrapper.getAvailableTimeSlot());
            reyclerViewPickUpTime.setLayoutManager(new LinearLayoutManager(mainActivity));
            reyclerViewPickUpTime.setAdapter(timeAdapter);
        }
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
