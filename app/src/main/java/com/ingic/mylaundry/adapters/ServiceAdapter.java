package com.ingic.mylaundry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;

import java.util.ArrayList;

/**
 * Created by khanubaid on 1/17/2018.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.myServiceHolder> {

    MainActivity mainActivity;
    LayoutInflater inflater;

    public ServiceAdapter(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity);
    }

    @Override
    public myServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_services, parent, false);
        myServiceHolder serviceHolder = new myServiceHolder(view);
        return serviceHolder;
    }

    @Override
    public void onBindViewHolder(myServiceHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }
    class myServiceHolder extends RecyclerView.ViewHolder {
        public myServiceHolder(View itemView) {
            super(itemView);
        }
    }
}
