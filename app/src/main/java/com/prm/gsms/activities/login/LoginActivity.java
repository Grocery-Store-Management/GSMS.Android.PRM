package com.prm.gsms.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.prm.gsms.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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