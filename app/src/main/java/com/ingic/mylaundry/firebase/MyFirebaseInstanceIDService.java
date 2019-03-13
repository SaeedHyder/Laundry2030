package com.ingic.mylaundry.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String TAG = "MyFirebase";
    BasePreferenceHelper prefHelper;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
        prefHelper = new BasePreferenceHelper(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        prefHelper.putDeviceToken(token);

    }
}

