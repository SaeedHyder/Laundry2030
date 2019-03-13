package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.CompleteOrderAdapter;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.models.OrderSubWraper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CompletedOrderFragment extends BaseFragment {

    @BindView(R.id.recyclerView_completeOrder)
    RecyclerView recyclerViewCompleteOrder;
    Unbinder unbinder;
    CompleteOrderAdapter completeOrderAdapter;
    int limit = 50, offset = 1, pages = 11;
    LinearLayoutManager linearManager;
    @BindView(R.id.swipeRefresh_compeleteOrder)
    SwipeRefreshLayout swipeRefreshCompeleteOrder;
    @BindView(R.id.notfoundTxt)
    TextView notfoundTxt;
    @BindView(R.id.noDataLayout)
    LinearLayout noDataLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        completeOrderAdapter = new CompleteOrderAdapter(activityReference);


        recyclerViewCompleteOrder.setAdapter(completeOrderAdapter);
        linearManager = new LinearLayoutManager(activityReference, LinearLayoutManager.VERTICAL, false);

        completeHistory();
        swipeRefreshCompeleteOrder.setColorSchemeResources(
                R.color.color_button);
        swipeRefreshCompeleteOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (completeOrderAdapter != null)
                    offset = 1;
                completeHistory();
                swipeRefreshCompeleteOrder.setRefreshing(false);
            }
        });
        recyclerViewCompleteOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastvisibleitemposition = linearManager.findLastVisibleItemPosition();

                if (lastvisibleitemposition == completeOrderAdapter.getItemCount() - 1) {
                    if (offset <= pages) {
                        offset++;
                        completeHistory();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        offset = 1;
        super.onStop();
    }

    public void visibleView() {
        recyclerViewCompleteOrder.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);

    }

    public void hideView() {
        recyclerViewCompleteOrder.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.VISIBLE);
        notfoundTxt.setText(activityReference.getResources().getString(R.string.sorry_no_data_found));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {

    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    private void completeHistory() {

        orderloadingStarted();
        WebApiRequest.getInstance(activityReference, true).OrderHistory(
                preferenceHelper.getUser().getId(),
                "completed", offset, limit,
                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {

                        OrderSubWraper order = (OrderSubWraper) JsonHelpers.convertToModelClass(response.getResult(), OrderSubWraper.class);
                        if (recyclerViewCompleteOrder != null) {
                            pages = Integer.parseInt(response.getPages());

                            if (offset == 1)
                                completeOrderAdapter.clear();
                            recyclerViewCompleteOrder.setLayoutManager(linearManager);

                            if (order.getData() != null) {
                                visibleView();
                                if (completeOrderAdapter != null)
                                    //recyclerViewCompleteOrder.getRecycledViewPool().clear();
                                    completeOrderAdapter.addAll(order.getData());
                            } else {
                                hideView();
                            }
                        }
                        orderloadingFinished();
                        if (completeOrderAdapter != null)
                            completeOrderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError() {
                        orderloadingFinished();
                        hideView();
                    }

                    @Override
                    public void onNoNetwork() {
                        // hideView();
                        orderloadingFinished();
                    }
                }
        );

    }
}
