package com.ingic.mylaundry.fragments;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.models.OrderWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderTrackingStatusFragment extends BaseFragment {

    Unbinder unbinder;
    int orderId;
    @BindView(R.id.image_orderPlace)
    ImageView imageOrderPlace;
    @BindView(R.id.image_orderPlaceline)
    ImageView imageOrderPlaceline;
    @BindView(R.id.image_orderPending)
    ImageView imageOrderPending;
    @BindView(R.id.image_orderPendingline)
    ImageView imageOrderPendingline;
    @BindView(R.id.image_pickupProcess)
    ImageView imagePickupProcess;
    @BindView(R.id.image_pickupProcessline)
    ImageView imagePickupProcessline;
    @BindView(R.id.image_deliveryProcess)
    ImageView imageDeliveryProcess;
    @BindView(R.id.img_laundry_process)
    ImageView imageLaundryProcess;
    @BindView(R.id.image_deliveryProcessline)
    ImageView imageDeliveryProcessline;
    @BindView(R.id.image_orderComplete)
    ImageView imageOrderComplete;
    @BindView(R.id.image_circle)
    ImageView imageCircle;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.img_place)
    ImageView imgPlace;
    @BindView(R.id.img_pending)
    ImageView imgPending;
    @BindView(R.id.img_process)
    ImageView imgProcess;
    @BindView(R.id.img_delivery_process)
    ImageView imgDeliveryProcess;
    @BindView(R.id.img_completed)
    ImageView imgCompleted;
    @BindView(R.id.txt_place)
    TextView txtPlace;
    @BindView(R.id.txt_pending)
    TextView txtPending;
    @BindView(R.id.txt_pickup)
    TextView txtPickup;
    @BindView(R.id.delivery)
    TextView delivery;
    @BindView(R.id.txt_complete)
    TextView txtComplete;
    private View view;
    int intValue = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_oder_tracking_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        orderDetail();

        Drawable progressDrawable = progress.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getResources().getColor(R.color.color_button), PorterDuff.Mode.SRC_IN);
        progress.setProgressDrawable(progressDrawable);
        progress.getProgress();
        setProgresss(0);
        setFonts();
        if (progress.getProgress() == 28) {
        }
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getResources().getString(R.string.order_tracking_status));
        titleBar.showHeader(true);
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    public void setFonts() {
        txtPlace.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPending.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtPickup.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        delivery.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        txtComplete.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    public void setProgresss(final int p) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (intValue < p) {
                    intValue++;

                    activityReference.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            if (intValue == 20) {
                                if (imgPending!=null)
                                imgPending.setImageResource(R.drawable.circle_indicator_color);
                            } else if (intValue == 40) {
                                if (imgProcess!=null)
                                imgProcess.setImageResource(R.drawable.circle_indicator_color);
                            } else if (intValue == 60) {
                                if (imageLaundryProcess!=null)
                                imageLaundryProcess.setImageResource(R.drawable.circle_indicator_color);
                             } else if (intValue ==80) {
                            if (imgDeliveryProcess!=null)
                                imgDeliveryProcess.setImageResource(R.drawable.circle_indicator_color);
                        } else if (intValue == 100) {
                                if (imgCompleted != null)
                                    imgCompleted.setImageResource(R.drawable.circle_indicator_color); }
                            if (progress != null) {
                                progress.setProgress(intValue);
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    private void orderDetail() {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).
                OrderDetail(orderId,

                        new WebApiRequest.APIRequestDataCallBack() {

                            @Override
                            public void onSuccess(Api_Response response) {

                                OrderWrapper orderWrapper = (OrderWrapper) response.getResult();

                                if (orderWrapper.getOrder().getOrderStatus() == (0)) {
                                    setProgresss(0);
                                    imageCircle.setImageResource(R.drawable.place_circle);
                                } else if (orderWrapper.getOrder().getOrderStatus() == (1)) {
                                    setProgresss(20);
                                    imageCircle.setImageResource(R.drawable.pending_circle);

                                } else if (orderWrapper.getOrder().getOrderStatus() == (2)) {
                                    setProgresss(40);
                                    imageCircle.setImageResource(R.drawable.order_pickup);

                                } else if (orderWrapper.getOrder().getOrderStatus() == (7)) {
                                    setProgresss(60);
                                    imageCircle.setImageResource(R.drawable.laundry_in_process);

                                } else if (orderWrapper.getOrder().getOrderStatus() == (3)) {
                                    setProgresss(80);
                                    imageCircle.setImageResource(R.drawable.delivery_in_process);

                                } else if (orderWrapper.getOrder().getOrderStatus() == (4)) {
                                    setProgresss(100);

                                    imageCircle.setImageResource(R.drawable.order_completed);

                                }

                                loadingFinished();
                            }

                            @Override
                            public void onError() {
                                loadingFinished();
                            }

                            @Override
                            public void onNoNetwork() {
                                loadingFinished();
                            }
                        });
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
