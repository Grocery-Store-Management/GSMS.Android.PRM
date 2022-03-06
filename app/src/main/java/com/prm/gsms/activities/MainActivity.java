package com.prm.gsms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.prm.gsms.R;
import com.prm.gsms.activities.customer.CustomerActivity;
import com.prm.gsms.activities.customer.CustomerPreferenceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToCustomer(View view) {
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }
}