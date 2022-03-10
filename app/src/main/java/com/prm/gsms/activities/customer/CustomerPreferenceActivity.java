package com.prm.gsms.activities.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Base64;
import android.util.Log;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.prm.gsms.R;
import com.prm.gsms.dtos.Customer;
import com.prm.gsms.services.CustomerService;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class CustomerPreferenceActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    static Customer curCustomer = null;

    EditTextPreference edtId;
    EditTextPreference edtPassword;
    EditTextPreference edtPhone;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String customerId = "";
        try {
            SharedPreferences loginPreferences = getApplicationContext().getSharedPreferences("LoginPreferences", MODE_PRIVATE);
            String token = loginPreferences.getString("token", null);
            Log.d("condilztranphong1", token);
            byte[] data = Base64.decode(token, Base64.DEFAULT);

            String tokenData = new String(data, "UTF-8");
            String customerObjectData = tokenData.substring(
                    tokenData.lastIndexOf("{")
            );
            JSONObject customerJSONObject = new JSONObject(customerObjectData);
            customerId = customerJSONObject.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");
            Log.d("condilztranphong2", customerId);
            addPreferencesFromResource(R.xml.customerpreference);
            SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);

            if (customerId != null)
                GsmsUtils.apiUtils(this, Request.Method.GET, "customers/" + customerId, "", new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        curCustomer = CustomerService.getCustomerInfoById(result);
                        if (curCustomer != null) {
                            edtId = (EditTextPreference) findPreference("IdCustomerId");
                            edtPassword = (EditTextPreference) findPreference("IdCustomerPassword");
                            edtPhone = (EditTextPreference) findPreference("IdCustomerPhone");
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putString("IdCustomerId", curCustomer.getId());
                            editor.putString("IdCustomerPassword", curCustomer.getPassword());
                            editor.putString("IdCustomerPhone", curCustomer.getPhoneNumber());
                            editor.commit();
                            edtId.setSummary(curCustomer.getId());
                            edtPassword.setSummary(curCustomer.getPassword());
                            edtPhone.setSummary(curCustomer.getPhoneNumber());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO
                    }
                });
            if (curCustomer != null) {
                edtId = (EditTextPreference) findPreference("IdCustomerId");
                edtPassword = (EditTextPreference) findPreference("IdCustomerPassword");
                edtPhone = (EditTextPreference) findPreference("IdCustomerPhone");
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("IdCustomerId", curCustomer.getId());
                editor.putString("IdCustomerPassword", curCustomer.getPassword());
                editor.putString("IdCustomerPhone", curCustomer.getPhoneNumber());
                editor.commit();
                edtId.setSummary(curCustomer.getId());
                edtPassword.setSummary(curCustomer.getPassword());
                edtPhone.setSummary(curCustomer.getPhoneNumber());
            } else {
                for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
                    initData(getPreferenceScreen().getPreference(i), sharedPrefs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initData(Preference p, SharedPreferences sharedPreferences) {
        if (p instanceof PreferenceCategory) {
            PreferenceCategory category = (PreferenceCategory) p;
            for (int i = 0; i < category.getPreferenceCount(); i++) {
                initData(category.getPreference(i), sharedPreferences);
            }
        } else {
            updatePreference(p, sharedPreferences);
        }
    }

    private void updatePreference(Preference p, SharedPreferences sharedPreferences) {
        if (p instanceof EditTextPreference) {
            EditTextPreference edt = (EditTextPreference) p;
            p.setSummary(edt.getText());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        updatePreference(findPreference(s), sharedPreferences);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);
        String newPhoneNumber = sharedPrefs.getString("IdCustomerPhone", null);
        String newPassword = sharedPrefs.getString("IdCustomerPassword", null);

        if (newPhoneNumber != null && newPassword != null) {
            curCustomer.setPhoneNumber(newPhoneNumber);
            curCustomer.setPassword(newPassword);
        }
        try {
            Gson gson = new Gson();
            String curCustomerJson = gson.toJson(curCustomer);
            GsmsUtils.apiUtils(this, Request.Method.POST, "customers/" + curCustomer.getId(), curCustomerJson, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
