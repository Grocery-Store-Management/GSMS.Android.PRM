package com.prm.gsms.services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("newToken", s);
        getSharedPreferences("firebasePreferences", MODE_PRIVATE).edit().putString("firebaseToken", s).commit();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("firebasePreferences", MODE_PRIVATE).getString("firebaseToken", "empty");
    }
}
