package com.prm.gsms.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.prm.gsms.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickToLoginCustomer(View view) {
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