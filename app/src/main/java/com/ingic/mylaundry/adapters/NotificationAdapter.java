package com.ingic.mylaundry.adapters;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.fragments.OrderDetailFragment;
import com.ingic.mylaundry.helpers.DateFormatHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.Notification;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import java.util.ArrayList;

/**
 * Created by khanubaid on 3/21/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.myNotificationHolder> implements Utils.Utilinterface {

    ArrayList<Notification> notifications = new ArrayList<>();
    MainActivity mainActivity;
    LayoutInflater inflater;
    int pos;

    public NotificationAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity);
    }

    @Override
    public myNotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_notification, parent, false);
        myNotificationHolder myNotificationHolder = new myNotificationHolder(view);
        return myNotificationHolder;
    }

    public void addAllList(ArrayList<Notification> list) {
        notifications.addAll(list);
        // notifyDataSetChanged();
    }

    public void clear() {
        this.notifications.clear();

    }

    @Override
    public void onBindViewHolder(myNotificationHolder holder, final int position) {
        holder.notificationDetail.setText(notifications.get(position).getMessage());
        holder.notificationDate.setText(DateFormatHelper.changeEventFormatTime(notifications.get(position).getCreatedAt()));
        holder.linear_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (notifications.get(position).getActionType().equals("order_status")) {
                    OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
                    orderDetailFragment.setOrderId(notifications.get(position).getRefId());
                    mainActivity.initFragments(orderDetailFragment);
                }

            }
        });

        holder.linear_notification.setBackgroundColor(mainActivity.getResources().getColor(R.color.color_white));
        holder.linear_notification.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                pos = position;
                Utils.alertDialog(mainActivity.getString(R.string.are_you_delete_order), NotificationAdapter.this, mainActivity, mainActivity.getString(R.string.yes), mainActivity.getString(R.string.no));

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        deleteNotification(mainActivity.prefHelper.getUser().getId(), notifications.get(pos).getId(), pos);
    }

    public void deleteNotification(int user_id, int notification_id, final int position) {
        // loadingStarted();
        WebApiRequest.getInstance(mainActivity, true).deleteNotification(
                user_id,
                notification_id,


                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        notifications.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError() {
                    }

                    @Override
                    public void onNoNetwork() {

                    }
                }
        );
    }

    class myNotificationHolder extends RecyclerView.ViewHolder {

        TextView notificationDetail, notificationDate;
        LinearLayout linear_notification;

        public myNotificationHolder(View itemView) {
            super(itemView);
            linear_notification = itemView.findViewById(R.id.linear_notification);
            notificationDetail = itemView.findViewById(R.id.txt_notificationDetail);
            notificationDate = itemView.findViewById(R.id.txt_notificationDate);

            notificationDetail.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
            notificationDate.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(mainActivity));
        }
    }
}
