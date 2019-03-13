package com.ingic.mylaundry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.fragments.OrderDetailFragment;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.Order;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/18/2018.
 */

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.CompleteOrderHolder> {

    MainActivity mainActivity;
    LayoutInflater inflater;
    private ArrayList<Order> orderSubWraper = new ArrayList<>();
    double vat;

    public CompleteOrderAdapter(MainActivity activityReference/*, OrderSubWraper orderSubWraper*/) {
        this.mainActivity = activityReference;
        inflater = LayoutInflater.from(mainActivity);

    }

    @Override
    public CompleteOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_completeorder, parent, false);
        CompleteOrderHolder completeOrderHolder = new CompleteOrderHolder(view);
        return completeOrderHolder;
    }

    @Override
    public void onBindViewHolder(CompleteOrderHolder holder, final int position) {


        holder.txtOrderId.setText(orderSubWraper.get(position).getId() + "");
        double addvat = orderSubWraper.get(position).getAmount();
        vat = (addvat * 5) / 100;
        //   holder.txtPrice.setText("AED " + (orders.get(position).getAmount() + orders.get(position).getDeliveryAmount() - promo));
        holder.txtPrice.setText("AED " + (orderSubWraper.get(position).getAmount() + vat));
        //  holder.txtPrice.setText("AED " + (orderSubWraper.get(position).getAmount() + orderSubWraper.get(position).getDeliveryAmount() - Double.parseDouble(orderSubWraper.get(position).getRedeem_amount())));
        if (!Utils.isEmptyOrNull(orderSubWraper.get(position).getPickupDate()) && !Utils.isEmptyOrNull(orderSubWraper.get(position).getSlot()) &&
                !Utils.isEmptyOrNull(orderSubWraper.get(position).getDeliveryDate()) &&
                !Utils.isEmptyOrNull(orderSubWraper.get(position).getDelivery_slot())) {
            holder.txtDateTime.setText((Utils.formatDatewithoututc(orderSubWraper.get(position).getPickupDate(), "yyyy-MM-dd", "dd MMM, yyyy")
                    + " & " + Utils.formatDatewithoututc(orderSubWraper.get(position).getSlot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " to " + Utils.formatDatewithoututc(orderSubWraper.get(position).getSlot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase()));

            holder.deliveryDateTime.setText((Utils.formatDatewithoututc(orderSubWraper.get(position).getDeliveryDate(), "yyyy-MM-dd", "dd MMM, yyyy")
                    + " & " + Utils.formatDatewithoututc(orderSubWraper.get(position).getDelivery_slot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " to " + Utils.formatDatewithoututc(orderSubWraper.get(position).getDelivery_slot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase()));

        }
        holder.linearLayoutActiveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
                orderDetailFragment.setDetail(orderSubWraper.get(position));
                mainActivity.initFragments(orderDetailFragment);
            }
        });
        if (orderSubWraper.get(position).getOrderStatus() <= 5) {
            holder.txtPickupProcess.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_green));
            holder.txtPickupProcess.setText(R.string.completed);
        }


    }

    public void clear() {
        this.orderSubWraper.clear();

    }

    @Override
    public int getItemCount() {
        return orderSubWraper.size();
    }

    public void addAll(ArrayList<Order> orders) {
        this.orderSubWraper.addAll(orders);
    }

    class CompleteOrderHolder extends RecyclerView.ViewHolder {

        TextView txtPrice, deliveryDateTime, txtDateTime, txtOrderId, txtPickupProcess, tv_orderTitle, tv_DateTime, tv_deliveryDate, tv_price;
        LinearLayout linearLayoutActiveOrder;

        public CompleteOrderHolder(View itemView) {
            super(itemView);

            tv_orderTitle = itemView.findViewById(R.id.tv_orderTitle);
            tv_DateTime = itemView.findViewById(R.id.tv_DateTime);
            tv_deliveryDate = itemView.findViewById(R.id.tv_deliveryDate);
            tv_price = itemView.findViewById(R.id.tv_price);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            deliveryDateTime = itemView.findViewById(R.id.deliveryDateTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPickupProcess = itemView.findViewById(R.id.txtPickupProcess);
            linearLayoutActiveOrder = itemView.findViewById(R.id.linearLayout_activeOrder);
        }
    }
}
