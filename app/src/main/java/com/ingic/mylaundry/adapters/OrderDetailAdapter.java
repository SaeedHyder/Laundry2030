package com.ingic.mylaundry.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.models.CartModel;
import com.ingic.mylaundry.models.NewOrderDetailWraper;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/23/2018.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.orderDetailHolder> {

    MainActivity mainActivity;
    LayoutInflater inflater;
    ArrayList<NewOrderDetailWraper> cartArrayList;
    SubCartAdapter subCartAdapter;


    public OrderDetailAdapter(MainActivity activityReference, ArrayList<NewOrderDetailWraper> cartArrayList) {
        this.mainActivity = activityReference;
        inflater = LayoutInflater.from(mainActivity);
        this.cartArrayList = cartArrayList;
    }

    @Override
    public orderDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        orderDetailHolder detailHolder = new orderDetailHolder(view);
        return detailHolder;
    }

    @Override
    public void onBindViewHolder(orderDetailHolder holder, int position) {

        subCartAdapter = new SubCartAdapter(mainActivity);
        holder.recyclerView_cart_subItem.setLayoutManager(new LinearLayoutManager(mainActivity));
        holder.recyclerView_cart_subItem.setAdapter(subCartAdapter);
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    class orderDetailHolder extends RecyclerView.ViewHolder {


        TextView tv_cartTitle;
        RecyclerView recyclerView_cart_subItem;
        ImageView cartItem;


        public orderDetailHolder(View itemView) {
            super(itemView);
            cartItem= itemView.findViewById(R.id.imageView_itemCart);
            recyclerView_cart_subItem= itemView.findViewById(R.id.recyclerView_cart_subItem);
            tv_cartTitle= itemView.findViewById(R.id.tv_cartTitle);
        }
    }
}
