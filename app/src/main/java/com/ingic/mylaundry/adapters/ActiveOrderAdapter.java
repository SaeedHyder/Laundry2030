package com.ingic.mylaundry.adapters;

import android.content.DialogInterface;
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
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/18/2018.
 */

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.activeOrderHolder> implements Utils.Utilinterface {

    MainActivity mainActivity;
    LayoutInflater inflater;
    int selectedId;
    TextView textView;
    int pos;
    double vat;
    private ArrayList<Order> orders = new ArrayList<>();

    public ActiveOrderAdapter(MainActivity activityReference/*, OrderSubWraper orders*/) {
        this.mainActivity = activityReference;
        inflater = LayoutInflater.from(mainActivity);

    }

    @Override
    public activeOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_activeorder, parent, false);
        activeOrderHolder activeOrderHolder = new activeOrderHolder(view);
        return activeOrderHolder;
    }

    @Override
    public void onBindViewHolder(final activeOrderHolder holder, final int position) {

        if (orders.get(position).getOrderStatus() == 0) {
            holder.txtPickupProcess.setText(R.string.orderplace);
            holder.txtPickupProcess.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_midgray));
        } else if (orders.get(position).getOrderStatus() == 1) {
            holder.txtPickupProcess.setText(R.string.order_pending);
            holder.txtPickupProcess.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_yellow));
        } else if (orders.get(position).getOrderStatus() == 2) {
            holder.txtPickupProcess.setText(R.string.pickup_process);
            holder.txtPickupProcess.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_button));
        } else if (orders.get(position).getOrderStatus() == 3) {
            holder.txtPickupProcess.setText(R.string.delivery_process);
            holder.txtPickupProcess.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_green));
        } else if (orders.get(position).getOrderStatus() == 7) {
            holder.txtPickupProcess.setText(R.string.laundry_in_process);
            holder.txtPickupProcess.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_green));
        } else {
            holder.txtPickupProcess.setText(R.string.cancelled);
            holder.txtPickupProcess.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_midgray));
        }

        holder.txtOrderId.setText(orders.get(position).getId() + "");
        double promo = 0.0;
        if (orders.get(position).getRedeem_amount() != null)
            promo = Double.parseDouble(orders.get(position).getRedeem_amount());
        double addvat = orders.get(position).getAmount();
        vat = (addvat * 5) / 100;
     //   holder.txtPrice.setText("AED " + (orders.get(position).getAmount() + orders.get(position).getDeliveryAmount() - promo));
        holder.txtPrice.setText("AED " + (orders.get(position).getAmount()+vat));

        if (!Utils.isEmptyOrNull(orders.get(position).getPickupDate()) && !Utils.isEmptyOrNull(orders.get(position).getSlot()) &&
                !Utils.isEmptyOrNull(orders.get(position).getDeliveryDate()) &&
                !Utils.isEmptyOrNull(orders.get(position).getDelivery_slot())) {
            holder.txtDateTime.setText((Utils.formatDatewithoututc(orders.get(position).getPickupDate(), "yyyy-MM-dd", "dd MMM, yyyy")
                    + " & " + Utils.formatDatewithoututc(orders.get(position).getSlot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " to " + Utils.formatDatewithoututc(orders.get(position).getSlot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase()));

            holder.deliveryDateTime.setText((Utils.formatDatewithoututc(orders.get(position).getDeliveryDate(), "yyyy-MM-dd", "dd MMM, yyyy")
                    + " & " + Utils.formatDatewithoututc(orders.get(position).getDelivery_slot().split("-")[0], "HH:mm:ss", "hh:mm a").toUpperCase() + " to " + Utils.formatDatewithoututc(orders.get(position).getDelivery_slot().split("-")[1], "HH:mm:ss", "hh:mm a").toUpperCase()));
        }
        holder.linearLayout_mainActiveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
                orderDetailFragment.setDetail(orders.get(position));
                mainActivity.initFragments(orderDetailFragment);
            }
        });

        holder.linearLayout_mainActiveOrder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if ((orders.get(position).getOrderStatus()) == 0) {
                    textView = holder.txtPickupProcess;
                    selectedId = orders.get(position).getId();
                    pos = position;
                    Utils.alertDialog(mainActivity.getString(R.string.are_you_cancel_order), ActiveOrderAdapter.this, mainActivity, mainActivity.getString(R.string.yes), mainActivity.getString(R.string.no));
                }
                return false;
            }
        });


    }

    public void cancelOrder(int selectedId, int user_id, int current_order, int order_status) {

        WebApiRequest.getInstance(mainActivity, true).cancelOrder(
                selectedId,
                user_id,
                current_order,
                order_status,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {
                        orders.remove(pos);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError() {
                    }

                    @Override
                    public void onNoNetwork() {
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void addAll(ArrayList<Order> orders) {

        // for (int pos = 0; pos < orders.size(); pos++) {
        this.orders.addAll(orders);
        // }
    }

    public void clear() {
        this.orders.clear();

    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        cancelOrder(selectedId, mainActivity.prefHelper.getUser().getId(), 0, 7);
    }

    class activeOrderHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout_activeOrder, linearLayout_mainActiveOrder;
        TextView txtOrderId, txtDateTime, deliveryDateTime, txtPrice, txtPickupProcess, tv_orderTitle, tv_DateTime, tv_deliveryDate, tv_price;


        public activeOrderHolder(View itemView) {
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
            linearLayout_activeOrder = itemView.findViewById(R.id.linearLayout_activeOrder);
            linearLayout_mainActiveOrder = itemView.findViewById(R.id.linearLayout_mainActiveOrder);

//        tv_orderTitle.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        txtOrderId.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        tv_DateTime.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        txtDateTime.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        tv_deliveryDate.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        deliveryDateTime.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        tv_price.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        txtPrice.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
//        txtPickupProcess.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));

        }
    }
}
