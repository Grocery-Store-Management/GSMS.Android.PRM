package com.prm.gsms.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prm.gsms.R;
import com.prm.gsms.activities.customer.CustomerActivity;
import com.prm.gsms.activities.import_order.ImportOrderListActivity;
import com.prm.gsms.activities.report.ReportActivity;

public class DashboardActivity extends AppCompatActivity {
    private TextView txtTitle;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txtTitle = findViewById(R.id.txtDashboardTitle);

        Intent intent = this.getIntent();
        type = intent.getStringExtra("type");

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(type.equals("employee")){
            txtTitle.setText("Employee Dashboard");

            Button btnImportOrder = new Button(this);
            btnImportOrder.setText("Import Order");
            btnImportOrder.setLayoutParams(params);

            Button btnReport = new Button(this);
            btnReport.setText("Report");
            btnReport.setLayoutParams(params);

            linearLayout.addView(btnImportOrder);
            linearLayout.addView(btnReport);

            this.addContentView(linearLayout, params);
            btnImportOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ImportOrderListActivity.class);
                    startActivity(intent);
                }
            });
            btnReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            txtTitle.setText("Customer Dashboard");

            Button btnProfile = new Button(this);
            btnProfile.setText("My Profile");
            btnProfile.setLayoutParams(params);

            TextView txtComingSoon = new TextView(this);
            txtComingSoon.setText("Coming soon ...");

            linearLayout.addView(btnProfile);
            linearLayout.addView(txtComingSoon);

            this.addContentView(linearLayout, params);
            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}