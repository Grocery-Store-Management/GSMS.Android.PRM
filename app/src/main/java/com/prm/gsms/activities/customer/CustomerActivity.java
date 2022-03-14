package com.prm.gsms.activities.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.prm.gsms.R;
import com.prm.gsms.dtos.Customer;
import com.prm.gsms.services.CustomerService;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;
    private String customerId = "";
    private Customer curCustomer;
    private CodeScanner codeScanner = null;

    private TextView txtWelcome;
    private TextView txtCurrentPoints;
    private ProgressDialog progressDialog;

    private void refreshData() {

        progressDialog = GsmsUtils.showLoading(this, "Loading data... Please wait...");

        txtWelcome = findViewById(R.id.txtWelcome);
        txtCurrentPoints = findViewById(R.id.txtCurrentPoints);
                try {
                    customerId = GsmsUtils.getCurrentCustomerId(CustomerActivity.this);
                    GsmsUtils.apiUtils(CustomerActivity.this, Request.Method.GET, "customers/" + customerId, "", new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            curCustomer = CustomerService.getCustomerInfoById(result);
                            txtWelcome.setText("Welcome: " + curCustomer.getPhoneNumber());
                            txtCurrentPoints.setText("Total points accumulated: " + curCustomer.getPoint());
                            try {
                                if (codeScanner == null) {
                                    setupPermissions();
                                    codeScanner();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }finally {
                                progressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
    }

    private void codeScanner() throws UnsupportedEncodingException, JSONException {
        codeScanner = new CodeScanner(this, findViewById(R.id.scanner_view));
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.SINGLE);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String data = result.getText();
                            int idIndex = data.indexOf("\"id\":");
                            String beg = data.substring(0, idIndex + 5);
                            String end = data.substring(idIndex + 5);
                            String realData = beg + "\"" + customerId + "\"" + end;

                            int pointIndex = realData.indexOf("\"point\":\"");
                            String addPoints = realData.substring(pointIndex + 9, realData.lastIndexOf("\""));
                            try{
                            String totalPoints = String.valueOf(Integer.parseInt(curCustomer.getPoint()) + Integer.parseInt(addPoints));
                                beg = realData.substring(0, pointIndex + 9);
                                realData = beg + totalPoints + "\"}";
                            }catch (NumberFormatException ex){
                                ex.printStackTrace();
                                TextView txtError = findViewById(R.id.txtError);
                                txtError.setText("An error occurred! Please try again!");
                                Toast.makeText(CustomerActivity.this,
                                        "An error occurred! Please try again!", Toast.LENGTH_SHORT).show();
                            }
                            GsmsUtils.apiUtils(CustomerActivity.this, Request.Method.PUT, "customers/" + customerId, realData, new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    Toast.makeText(CustomerActivity.this,
                                            "Points updated successfully!!", Toast.LENGTH_SHORT).show();
                                    refreshData();
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    TextView txtError = findViewById(R.id.txtError);
                                    txtError.setText("An error occurred! Please try again!");
                                    Toast.makeText(CustomerActivity.this,
                                            "An error occurred! Please try again!", Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("QR", "Camera init error:  " + thrown.getMessage());
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private void setupPermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        String perm = Manifest.permission.CAMERA;
        String[] perms = new String[]{perm};
        ActivityCompat.requestPermissions(this, perms, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You need the camera permission to be able to use this app!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clickToEdit(View view) {
        Intent intent = new Intent(this, CustomerPreferenceActivity.class);
        startActivity(intent);
    }

    public void clickToEditPoints(View view) {
        TextView txtError = findViewById(R.id.txtError);
        txtError.setText("");
        codeScanner.startPreview();
    }
}