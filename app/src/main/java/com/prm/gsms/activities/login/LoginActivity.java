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

import com.prm.gsms.R;
import com.prm.gsms.activities.MainActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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