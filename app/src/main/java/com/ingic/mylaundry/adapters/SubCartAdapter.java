package com.ingic.mylaundry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;

/**
 * Created by khanubaid on 1/24/2018.
 */

public class SubCartAdapter extends RecyclerView.Adapter<SubCartAdapter.subCartHolder> {

    MainActivity mainActivity;
    LayoutInflater inflater;

    public SubCartAdapter(MainActivity activityReference)
    {
        this.mainActivity = activityReference;
        inflater = LayoutInflater.from(mainActivity);
    }

    @Override
    public subCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_subcart, parent, false);
        subCartHolder cartHolder = new subCartHolder(view);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(subCartHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class subCartHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView_cart_subItem;
        public subCartHolder(View itemView) {
            super(itemView);
        }
    }
}
