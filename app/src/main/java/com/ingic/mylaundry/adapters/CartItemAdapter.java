package com.ingic.mylaundry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.models.Item;

import java.util.ArrayList;

/**
 * Created by adnanahmed on 2/8/2018.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder> {

    private final ArrayList<Item> entity;
    MainActivity mainActivity;
    LayoutInflater inflater;

    public CartItemAdapter(MainActivity activityReference, ArrayList<Item> entity) {
        this.mainActivity = activityReference;
        inflater = LayoutInflater.from(mainActivity);
        this.entity = entity;
    }

    @Override
    public CartItemAdapter.CartItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_subcart, parent, false);
        CartItemAdapter.CartItemHolder cartHolder = new CartItemAdapter.CartItemHolder(view);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(CartItemAdapter.CartItemHolder holder, int position) {
        holder.txtNameQuantity.setText(entity.get(position).getQuantity() + " x " + entity.get(position).getTitle());
        holder.txtPrice.setText("AED " + (Integer.parseInt(entity.get(position).getAmount()) * entity.get(position).getQuantity()));

        holder.txtNameQuantity.setTypeface(TextUtility.setPoppinsRegular(mainActivity));
        holder.txtPrice.setTypeface(TextUtility.setPoppinsRegular(mainActivity));
    }

    @Override
    public int getItemCount() {
        return entity.size();
    }

    class CartItemHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem;
        TextView txtNameQuantity;
        TextView txtPrice;

        public CartItemHolder(View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            txtNameQuantity = itemView.findViewById(R.id.txtNameQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}
