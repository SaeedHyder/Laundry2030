package com.ingic.mylaundry.adapters;


import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.TextUtility;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.listener.TotalPriceListener;
import com.ingic.mylaundry.models.Item;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class MySection extends StatelessSection {
    private final int pos;
    String title;
    ArrayList<Item> list;
    MainActivity mainActivity;
    int total = 0, cartCount = 0;
    private TotalPriceListener mlistener;
    TitleBar titleBar;


    public MySection(String title, ArrayList<Item> list, int pos, MainActivity activityReference, TitleBar titleBar, TotalPriceListener mlistener) {
        super(new SectionParameters.Builder(R.layout.item_services)
                .headerResourceId(R.layout.item_header)
                .build());


        this.title = title;
        this.mainActivity = activityReference;

        this.list = list;
        this.mlistener = mlistener;
        this.pos = pos;
        this.titleBar = titleBar;

    }


    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText(title);
    }

    public void clear() {
        this.list.clear();
    }

    @Override
    public void onBindItemViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        String name = list.get(position).getTitle();

        itemHolder.tvItem.setText(name);
        itemHolder.tvItem.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
        Utils.setImageWithGlide(mainActivity, itemHolder.imgItem, AppConstant.ServerAPICalls.BASE_URL + AppConstant.ServerAPICalls.API + "resize-service/" + list.get(position).getImage());

        if (mainActivity.prefHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE))
        {
            itemHolder.tv_price.setGravity(Gravity.RIGHT);
            itemHolder.tv_price.setText(AppConstant.AED + " " + list.get(position).getAmount() + "");
        }
        else {
            itemHolder.tv_price.setText(AppConstant.AED + " " + list.get(position).getAmount() + "");
        }

        itemHolder.tv_quantity.setText(list.get(position).getQuantity() + "");

        itemHolder.tv_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quan = list.get(position).getQuantity();
                if (quan > 0) {
                    list.get(position).setQuantity(quan - 1);
                    itemHolder.tv_quantity.setText(list.get(position).getQuantity() + "");

                    mlistener.setTotal(false, Integer.parseInt(list.get(position).getAmount()),
                            list.get(position), Integer.parseInt(itemHolder.tv_quantity.getText().toString()), pos, position);
                }
            }
        });

        itemHolder.tv_addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position).setQuantity(list.get(position).getQuantity() + 1);
                itemHolder.tv_quantity.setText(list.get(position).getQuantity() + "");

                mlistener.setTotal(true, Integer.parseInt(list.get(position).getAmount()),
                        list.get(position), Integer.parseInt(itemHolder.tv_quantity.getText().toString()), pos, position);
            }
        });
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tv_headersection);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;

        ImageView imgItem;
        TextView tvItem, tv_price, tv_subtract, tv_quantity, tv_addition;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgItem = view.findViewById(R.id.imgView_item);
            tvItem = view.findViewById(R.id.tv_Title);
            tv_price = view.findViewById(R.id.tv_price);
            tv_subtract = view.findViewById(R.id.tv_subtract);
            tv_quantity = view.findViewById(R.id.tv_quantity);
            tv_addition = view.findViewById(R.id.tv_addition);
        }
    }
}
