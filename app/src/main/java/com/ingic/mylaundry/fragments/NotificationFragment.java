package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.NotificationAdapter;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.NotificationWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationFragment extends BaseFragment {

    @BindView(R.id.recyclerview_notification)
    RecyclerView recyclerviewNotification;
    NotificationAdapter notificationAdapter;
    Unbinder unbinder;
    //     ArrayList<Notification> getAllNotifications = new ArrayList<>();
    @BindView(R.id.swipeRefresh_notification)
    SwipeRefreshLayout swipeRefreshNotification;
    @BindView(R.id.notfoundTxt)
    TextView notfoundTxt;
    @BindView(R.id.noDataLayout)
    LinearLayout noDataLayout;
    LinearLayoutManager linearManager;
    LayoutAnimationController animation;
    TitleBar titleBar;
    int limit = 10, offset = 1, pages = 11;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleBar = activityReference.getTitleBar();

        linearManager = new LinearLayoutManager(activityReference, LinearLayoutManager.VERTICAL, false);
        recyclerviewNotification.setLayoutManager(linearManager);
        notificationAdapter = new NotificationAdapter(activityReference/*, getAllNotifications*/);
        animation = AnimationUtils.loadLayoutAnimation(activityReference, R.anim.layout_animation_right_slide);
        recyclerviewNotification.setAdapter(notificationAdapter);

        recyclerviewNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastvisibleitemposition = linearManager.findLastVisibleItemPosition();

                if (lastvisibleitemposition == notificationAdapter.getItemCount() - 1) {
                    if (offset <= pages) {
                        offset++;
                        if (preferenceHelper.getUser() != null)
                            getNotification(preferenceHelper.getUser().getId(), offset, limit);
                    }
                }
            }
        });
        if (preferenceHelper.getUser() != null)
            getNotification(preferenceHelper.getUser().getId(), offset, limit);

        swipeRefreshNotification.setColorSchemeResources(
                R.color.color_button);
        swipeRefreshNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 1;
                if (preferenceHelper.getUser().getId() != null)
                    getNotification(preferenceHelper.getUser().getId(), offset, limit);
                swipeRefreshNotification.setRefreshing(false);
            }
        });
    }

    public void getNotification(int user_id, final int offset, int limit) {
        loadingStarted();
        // hideView();
        WebApiRequest.getInstance(activityReference, true).getNotification(
                user_id,
                offset,
                limit,
                //searchBar.getText().toString(), size, from,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {
                        loadingFinished();
                        NotificationWrapper notificationWrapper = (NotificationWrapper) JsonHelpers.convertToModelClass(response.getResult(), NotificationWrapper.class);
                        pages = Integer.parseInt(response.getPages());
                        if (offset == 1)
                            notificationAdapter.clear();
                        if (notificationWrapper.getNotifications() != null) {
                            notificationAdapter.addAllList(notificationWrapper.getNotifications());
                            notificationAdapter.notifyDataSetChanged();

                            visibleView();

                        } else {
                            hideView();
                            Utils.showSnackBar(activityReference, activityReference.findViewById(R.id.main_container),
                                    response.getMessage(), ContextCompat.getColor(activityReference, R.color.color_button));
                        }
                        notificationAdapter.notifyDataSetChanged();

                        setTitleBar(activityReference.getTitleBar());

                    }

                    @Override
                    public void onError() {
                        loadingFinished();
                        if (notificationAdapter.getItemCount() > 0) {
                            visibleView();
                        } else {
                            hideView();
                        }

                    }

                    @Override
                    public void onNoNetwork() {
                        loadingFinished();
                        if (notificationAdapter.getItemCount() > 0) {
                            visibleView();
                        } else {
                            hideView();
                        }
                    }
                }
        );
    }

    public void visibleView() {
        recyclerviewNotification.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        offset = 1;
    }

    public void hideView() {

        recyclerviewNotification.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.VISIBLE);
        notfoundTxt.setText(activityReference.getResources().getString(R.string.sorry_no_data_found));
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getResources().getString(R.string.notifications));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
