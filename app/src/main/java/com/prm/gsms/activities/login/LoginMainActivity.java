package com.prm.gsms.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.prm.gsms.R;
import com.prm.gsms.dtos.Customer;
import com.prm.gsms.dtos.Employee;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

public class LoginMainActivity extends AppCompatActivity {

    private TextView txtLoginAs;
    private String userNamePhonenumber;
    private String password;
    private TextView txtUsernamePhonenumber;
    private EditText edtUsernamePhonenumber;
    private EditText edtPassword;
    private String token;
    private TextView txtLoginError;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        txtLoginAs = findViewById(R.id.txtLoginAs);
        txtUsernamePhonenumber = findViewById(R.id.txtUsernamePhoneNumber);
        edtUsernamePhonenumber = findViewById(R.id.edtUsernamePhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        Intent intent = this.getIntent();
        type = intent.getStringExtra("type");
        if(type.equals("employee")){
            txtLoginAs.setText("Login As Employee");
            txtUsernamePhonenumber.setText("User name");
        } else {
            txtLoginAs.setText("Login As Customer");
            txtUsernamePhonenumber.setText("Phone number");
        }

        txtLoginError = findViewById(R.id.txtLoginError);
    }

    public void clickToLogin(View view) {
        if(type.equals("employee")){
            if(!edtUsernamePhonenumber.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()){
                userNamePhonenumber = edtUsernamePhonenumber.getText().toString();
                password = edtPassword.getText().toString();
            } else {
                Toast.makeText(this, "Please input user name and password", Toast.LENGTH_SHORT).show();
                return;
            }
            Employee employee = new Employee(userNamePhonenumber, password);

            GsmsUtils.apiUtilsForLogin(this, Request.Method.POST, "employees/login/", employee, type, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    token = result;

                    if(token != null && !token.isEmpty()){
                        SharedPreferences loginPreferences = getApplicationContext().getSharedPreferences("LoginPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginPreferences.edit();
                        editor.putString("token", token);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 401){
                        txtLoginError.setText("Incorrect user name or password! \n Please try again.");
                    }
                }
            });
        } else {
            if(!edtUsernamePhonenumber.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()){
                userNamePhonenumber = edtUsernamePhonenumber.getText().toString();
                password = edtPassword.getText().toString();
            } else {
                Toast.makeText(this, "Please input phone number and password", Toast.LENGTH_SHORT).show();
                return;
            }
            Customer customer = new Customer(userNamePhonenumber, password);

            GsmsUtils.apiUtilsForLogin(this, Request.Method.POST, "customers/login/", customer, type, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    token = result;

                    if(token != null && !token.isEmpty()){
                        SharedPreferences loginPreferences = getApplicationContext().getSharedPreferences("LoginPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginPreferences.edit();
                        editor.putString("token", token);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    } 
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 401){
                        txtLoginError.setText("Incorrect phone number or password! \n Please try again.");
                    }
                }
            });
        }
    }
}