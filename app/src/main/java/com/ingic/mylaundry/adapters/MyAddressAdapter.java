package com.ingic.mylaundry.adapters;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ingic.mylaundry.Interface.AddressPickerInterface;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.fragments.AddPickupAddressFragment;
import com.ingic.mylaundry.fragments.EditProfileFragment;
import com.ingic.mylaundry.fragments.ScheduleFragment;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.AdressData;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.util.ArrayList;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.myAddressHolder> implements Utils.Utilinterface {
    private final ScheduleOrderModel scheduleOrderModel;
    ScheduleFragment scheduleFragment;
    EditProfileFragment editProfileFragment;
    MainActivity mainActivity;
    LayoutInflater inflater;
    ArrayList<AdressData> adressData;
    AddressPickerInterface addressPickerInterface;
    String type;
    int id;
    int pos;
    private int lastSelectedPosition = -1;

    public MyAddressAdapter(MainActivity activityReference, ArrayList<AdressData> adressData, String type, ScheduleFragment scheduleFragment, EditProfileFragment editProfileFragment, AddressPickerInterface addressPickerInterface) {
        this.mainActivity = activityReference;
        inflater = LayoutInflater.from(mainActivity);
        this.adressData = adressData;
        this.type = type;
        this.scheduleFragment = scheduleFragment;
        this.editProfileFragment = editProfileFragment;
        this.addressPickerInterface = addressPickerInterface;
        if (mainActivity.prefHelper.getSchedule() != null)
            scheduleOrderModel = mainActivity.prefHelper.getSchedule();
        else
            scheduleOrderModel = new ScheduleOrderModel();
    }

    @Override
    public myAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        myAddressHolder addressHolder = new myAddressHolder(view);
        return addressHolder;
    }

    public void clearAllList() {
        adressData.clear();
    }

    public void addAllList(ArrayList<AdressData> list) {
        adressData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final myAddressHolder holder, final int position) {

        if (type == "pickup") {
            holder.check_item.setText(R.string.ship_to_pickup);
        } else {
            holder.check_item.setText(R.string.ship_to_delivery);
        }
        holder.tv_address.setText(adressData.get(position).getLocation().replaceAll("\\|", ",").replace(",,", ",").replace(",,", ",").replace(",,", ","));

        holder.imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddPickupAddressFragment pickupAddressFragment = new AddPickupAddressFragment();
                pickupAddressFragment.setCondition(adressData.get(position));
                pickupAddressFragment.setType(type, null);
                mainActivity.initFragments(pickupAddressFragment);
                addressPickerInterface.editAddress_Click();
            }
        });

        holder.layoutAddress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                id = adressData.get(position).getId();
                pos = position;
                Utils.alertDialog(mainActivity.getString(R.string.are_you_delete_address), MyAddressAdapter.this, mainActivity, mainActivity.getString(R.string.yes), mainActivity.getString(R.string.no));

                return false;
            }
        });

        holder.check_item.setChecked(lastSelectedPosition == position);
        holder.check_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();
                addressPickerInterface.onAddressPicked(adressData.get(position));
            }
        });

        holder.tv_address.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
        holder.check_item.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        deleteAddress(id, mainActivity.prefHelper.getUser().getId());
    }

    public void deleteAddress(int id, int user_id) {

        WebApiRequest.getInstance(mainActivity, true).deleteAddress(
                id,
                user_id,
                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        //  loadingFinished();
                        notifyItemRemoved(pos);
                        adressData.remove(pos);
                        notifyDataSetChanged();
                        Utils.showSnackBar(mainActivity, mainActivity.findViewById(R.id.main_container),
                                mainActivity.getResources().getString(R.string.delete_succesfuly),
                                ContextCompat.getColor(mainActivity, R.color.color_button));
                    }

                    @Override
                    public void onError() {
                        //  loadingFinished();

                    }

                    @Override
                    public void onNoNetwork() {
                        //      loadingFinished();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return adressData.size();
    }

    class myAddressHolder extends RecyclerView.ViewHolder {
        TextView tv_address;
        RadioButton check_item;
        ImageView imageView_edit;
        LinearLayout layoutAddress;

        public myAddressHolder(View itemView) {
            super(itemView);
            check_item = itemView.findViewById(R.id.check_item);
            tv_address = itemView.findViewById(R.id.tv_address);
            imageView_edit = itemView.findViewById(R.id.imageView_edit);
            layoutAddress = itemView.findViewById(R.id.layoutAddress);
        }
    }
}
