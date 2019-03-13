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
import com.ingic.mylaundry.adapters.ActiveOrderAdapter;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.models.OrderSubWraper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ActiveOrderFragment extends BaseFragment {

    @BindView(R.id.recyclerView_activeOrder)
    RecyclerView recyclerViewActiveOrder;
    Unbinder unbinder;
    ActiveOrderAdapter activeOrderAdapter;
    OrderSubWraper order;
    @BindView(R.id.swipeRefresh_activeOrder)
    SwipeRefreshLayout swipeRefreshActiveOrder;
    LinearLayoutManager linearManager;
    int limit = 50, offset = 1, pages = 11;
    @BindView(R.id.notfoundTxt)
    TextView notfoundTxt;
    @BindView(R.id.noDataLayout)
    LinearLayout noDataLayout;

    @Override
    public void onStop() {
        offset = 1;
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        activeHistory();

        swipeRefreshActiveOrder.setColorSchemeResources(
                R.color.color_button);
        swipeRefreshActiveOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                offset = 1;
                activeHistory();
                swipeRefreshActiveOrder.setRefreshing(false);
            }
        });

        recyclerViewActiveOrder.setHasFixedSize(true);
        linearManager = new LinearLayoutManager(activityReference, LinearLayoutManager.VERTICAL, false);
        recyclerViewActiveOrder.setLayoutManager(linearManager);
        activeOrderAdapter = new ActiveOrderAdapter(activityReference/*, orders*/);
        recyclerViewActiveOrder.setAdapter(activeOrderAdapter);

        recyclerViewActiveOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastvisibleitemposition = linearManager.findLastVisibleItemPosition();

                if (lastvisibleitemposition == activeOrderAdapter.getItemCount() - 1) {
                    if (offset <= pages) {
                        offset++;
                        activeHistory();
                    }
                }
            }
        });

        return view;
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
    }

    public void visibleView() {
        if (recyclerViewActiveOrder != null)
            recyclerViewActiveOrder.setVisibility(View.VISIBLE);
        if (noDataLayout != null)
            noDataLayout.setVisibility(View.GONE);
    }

    public void hideView() {
        if (recyclerViewActiveOrder != null)
            recyclerViewActiveOrder.setVisibility(View.GONE);
        if (noDataLayout != null)
            noDataLayout.setVisibility(View.VISIBLE);
        if (notfoundTxt != null)
            notfoundTxt.setText(activityReference.getResources().getString(R.string.sorry_no_data_found));
    }

    private void activeHistory() {

        orderloadingStarted();
        WebApiRequest.getInstance(activityReference, true).OrderHistory(
                preferenceHelper.getUser().getId(),
                "active", offset, limit,
                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(Api_Response response) {

                        order = (OrderSubWraper) JsonHelpers.convertToModelClass(response.getResult(), OrderSubWraper.class);

//                        if (order != null && order.getData() != null && recyclerViewActiveOrder != null) {
//                            pages = Integer.parseInt(response.getPages());
//
//                            if (offset == 1)
//                                activeOrderAdapter.clear();
//                            recyclerViewActiveOrder.setLayoutManager(linearManager);
//
//                            if (order.getData() != null)
//                                activeOrderAdapter.addAll(order.getData());
//                            activeOrderAdapter.notifyDataSetChanged();
//                            //  visibleView();
//                            orderloadingFinished();
//
//                            if (order.getData().size() > 0) {
//                                visibleView();
//                            } else {
//
//                                hideView();
//                            }
//
//
//                        } else {
//                            //   hideView();
//                            orderloadingFinished();
//                        }
                        if (recyclerViewActiveOrder != null) {
                            pages = Integer.parseInt(response.getPages());
                            recyclerViewActiveOrder.setLayoutManager(linearManager);

                            if (offset == 1)
                                activeOrderAdapter.clear();
                            if (order.getData() != null) {
                                if (order.getData().size() > 0) {
                                    if (order.getData() != null) {
                                        visibleView();
                                        if (activeOrderAdapter != null)
                                            activeOrderAdapter.addAll(order.getData());
                                    } else {
                                        hideView();
                                    }
                                } else if (activeOrderAdapter.getItemCount() > 0) {

                                    visibleView();
                                }
                            } else {
                                hideView();
                            }
                        }
                        orderloadingFinished();
                        if (activeOrderAdapter != null)
                            activeOrderAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError() {
                        orderloadingFinished();
                        hideView();
                    }

                    @Override
                    public void onNoNetwork() {
                        orderloadingFinished();

                    }
                }
        );
    }
}
