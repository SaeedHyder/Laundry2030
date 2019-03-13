package com.ingic.mylaundry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.helpers.DateFormatHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.listener.Pickuplistener;
import com.ingic.mylaundry.models.AvailableTimeSlot;
import com.ingic.mylaundry.ui.PickupTimeDialog;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/25/2018.
 */

public class PickupTimeAdapter extends RecyclerView.Adapter<PickupTimeAdapter.pickupTimeHolder> {

    private final boolean is_PickupTime;
    MainActivity mainActivity;
    LayoutInflater inflater;
    private Pickuplistener mlistener;
    PickupTimeDialog fragmentdismiss;
    ArrayList<AvailableTimeSlot> availableTimeSlots;
    int charges;
    String surcharge;


    public PickupTimeAdapter(MainActivity mainActivity, Pickuplistener listener, PickupTimeDialog fragmentdismiss, boolean is_pickuptime, ArrayList<AvailableTimeSlot> availableTimeSlots) {
        this.mainActivity = mainActivity;
        this.mlistener = listener;
        this.fragmentdismiss = fragmentdismiss;
        this.is_PickupTime = is_pickuptime;
        this.availableTimeSlots = availableTimeSlots;
        inflater = LayoutInflater.from(mainActivity);
    }


    @Override
    public pickupTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pickup_time, parent, false);
        pickupTimeHolder timeHolder = new pickupTimeHolder(view);
        return timeHolder;
    }

    @Override
    public void onBindViewHolder(final pickupTimeHolder holder, final int position) {

        holder.tv_pickupTime.setText(DateFormatHelper.formatTime(availableTimeSlots.get(position).getSlotTime()));
        surcharge = availableTimeSlots.get(position).getInstance_surcharge()== null ? null : String.valueOf(availableTimeSlots.get(position).getInstance_surcharge());
        holder.tv_pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mlistener.setTime(holder.tv_pickupTime.getText().toString(), is_PickupTime, surcharge);

                fragmentdismiss.dismiss();
            }
        });
    }

    public void setListener(Pickuplistener listener) {
        this.mlistener = listener;

    }

    @Override
    public int getItemCount() {
        return availableTimeSlots.size();
    }

    class pickupTimeHolder extends RecyclerView.ViewHolder {

        TextView tv_pickupTime;

        public pickupTimeHolder(View itemView) {
            super(itemView);
            tv_pickupTime = itemView.findViewById(R.id.tv_pickupTime);
        }
    }
}
