package com.prm.gsms.activities.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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

    private CodeScanner codeScanner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
    }

    private void codeScanner() throws UnsupportedEncodingException, JSONException {
        SharedPreferences loginPreferences = getApplicationContext().getSharedPreferences("LoginPreferences", MODE_PRIVATE);
        String token = loginPreferences.getString("token", null);
        token = token.substring(token.indexOf(".") + 1, token.lastIndexOf("."));

        byte[] data = Base64.decode(token, Base64.DEFAULT);
        String tokenData = new String(data, "UTF-8");

        JSONObject customerJSONObject = new JSONObject(tokenData);
        String customerId = customerJSONObject.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");
        codeScanner = new CodeScanner(this, findViewById(R.id.scanner_view));
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("res", result.getText());
                        try {
                            Gson gson = new Gson();
                            String data = gson.toJson(result.getText());
                            GsmsUtils.apiUtils(CustomerActivity.this, Request.Method.PUT, "customers/" + customerId, data, new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    Toast.makeText(CustomerActivity.this,
                                            "Points updated successfully!!", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });
        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("QR", "Camera init error:  " + thrown.getMessage());

                    }
                }).start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
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
        String[] perms = new String[Integer.parseInt(Manifest.permission.CAMERA)];
        ActivityCompat.requestPermissions(this, perms, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_REQUEST_CODE){
            if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "You need the camera permission to be able to use this app!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clickToEdit(View view) {
        Intent intent = new Intent(this, CustomerPreferenceActivity.class);
        startActivity(intent);
    }

    public void clickToEditPoints(View view) {
        setupPermissions();
        try {
            codeScanner();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}