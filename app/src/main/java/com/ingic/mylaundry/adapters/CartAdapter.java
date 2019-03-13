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
import com.ingic.mylaundry.models.Item;
import com.ingic.mylaundry.models.MultipleItems;
import com.ingic.mylaundry.ui.InstantOrderDialog;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/17/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.myCartHolder> {
    MainActivity mainActivity;
    LayoutInflater inflater;
    CartItemAdapter cartAdapter;
    ArrayList<Item> zeroList;
    ArrayList<Item> oneList;
    ArrayList<Item> twoList;

    public CartAdapter(MainActivity activityReference) {
        this.mainActivity = activityReference;
        inflater = LayoutInflater.from(mainActivity);
    }

    @Override
    public myCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        myCartHolder cartHolder = new myCartHolder(view);

        return cartHolder;
    }

    @Override
    public void onBindViewHolder(myCartHolder holder, int position) {
        setRespecttoPos(holder,position);


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private void setRespecttoPos(myCartHolder holder, int position) {
        if (position == 0) {
            holder.imageView_itemCart.setImageResource(R.drawable.wash_fold);
            holder.tv_cartTitle.setText(mainActivity.getString(R.string.dry_clean));
        }
        if (position == 1) {
            holder.imageView_itemCart.setImageResource(R.drawable.iron_cart);
            holder.tv_cartTitle.setText(mainActivity.getString(R.string.steam_ironing));
        }
        if (position == 2) {
            holder.imageView_itemCart.setImageResource(R.drawable.wash_iron);
            holder.tv_cartTitle.setText(mainActivity.getString(R.string.wash_amp_iron));
        }
        holder.recyclerView_cart_subItem.setLayoutManager(new LinearLayoutManager(mainActivity));
        holder.recyclerView_cart_subItem.setAdapter(cartAdapter);
    }

    class myCartHolder extends RecyclerView.ViewHolder {

        ImageView imageView_itemCart;
        TextView tv_cartTitle;
        RecyclerView recyclerView_cart_subItem;

        public myCartHolder(View itemView) {
            super(itemView);
            imageView_itemCart = itemView.findViewById(R.id.imageView_itemCart);
            recyclerView_cart_subItem = itemView.findViewById(R.id.recyclerView_cart_subItem);
            tv_cartTitle = itemView.findViewById(R.id.tv_cartTitle);

        }
    }
}
