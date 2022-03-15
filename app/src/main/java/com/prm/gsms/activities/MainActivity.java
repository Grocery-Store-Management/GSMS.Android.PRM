package com.prm.gsms.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prm.gsms.R;
import com.prm.gsms.activities.import_order.ImportOrderListActivity;

import com.prm.gsms.activities.customer.CustomerActivity;
import com.prm.gsms.activities.customer.CustomerPreferenceActivity;
import com.prm.gsms.services.CustomFirebaseMessagingService;


public class MainActivity extends AppCompatActivity {

    private Button btnImportOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnImportOrderList = (Button) findViewById(R.id.btnImportOrderList);
        btnImportOrderList.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImportOrderListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String tok = CustomFirebaseMessagingService.getToken(this);
    }

    public void clickToCustomer(View view) {
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }


}