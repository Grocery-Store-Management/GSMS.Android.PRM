package com.prm.gsms.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.prm.gsms.R;
import com.prm.gsms.activities.MainActivity;
import com.prm.gsms.services.CustomFirebaseMessagingService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        }

        String tok = CustomFirebaseMessagingService.getToken(this);
        Log.d("Asd", tok);
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(this.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
        }
    }

    public void clickToLoginCustomer(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);
        sharedPrefs.edit().clear().commit();
        Log.d("liar", sharedPrefs.toString());
        Intent intent = new Intent(this, LoginMainActivity.class);
        intent.putExtra("type", "customer");
        startActivity(intent);
    }

    public void clickToLoginEmployee(View view) {
        Intent intent = new Intent(this, LoginMainActivity.class);
        intent.putExtra("type", "employee");
        startActivity(intent);
    }
}