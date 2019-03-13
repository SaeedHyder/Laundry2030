package com.ingic.mylaundry.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;
    private NotificationManager mNotificationManager;

    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            // sendNotification(remoteMessage);
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message NotificationFragment Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification(true, remoteMessage);
    }
    private void sendNotification(boolean isAdded, RemoteMessage extras) {
        if (!isAdded) {
            return;
        }
        Bundle extra = new Bundle();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        GcmDataObject gcmObject = new GcmDataObject();
        Intent intent = new Intent(this, MainActivity.class);
        gcmObject.setTitle(getString(R.string.app_name));
        gcmObject.setMessage(extras.getNotification().getBody().toString());
        gcmObject.setAction_type(extras.getData().get("action_type"));
        gcmObject.setAction_id(extras.getData().get("ref_id"));

        String str = GsonFactory.getConfiguredGson().toJson(gcmObject);
        extra.putString("gcmObject", str);
        intent.putExtras(extra);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Flag added to resume
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Flag added to resume

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
// TODO PEnding IOntent
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle  = bigTextStyle.bigText(extras.getNotification().getBody().toString());
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this)
                .setSmallIcon(R.drawable.w_logo)
                .setContentTitle(extras.getNotification().getTitle())
                .setContentText(extras.getNotification().getBody())
                .setSound(uri).setStyle(bigTextStyle)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        NOTIFICATION_ID++;

    }
}

