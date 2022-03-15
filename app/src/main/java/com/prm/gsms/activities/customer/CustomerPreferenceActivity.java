package com.prm.gsms.activities.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.widget.Toast;

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


public class CustomerPreferenceActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    static Customer curCustomer = null;

    EditTextPreference edtId;
    EditTextPreference edtPassword;
    EditTextPreference edtPhone;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String customerId = GsmsUtils.getCurrentUserId(this);
            addPreferencesFromResource(R.xml.customerpreference);
            SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            if (customerId != null)
                GsmsUtils.apiUtils(this, Request.Method.GET, "customers/" + customerId, "", new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        curCustomer = CustomerService.getCustomerInfoById(result);
                        if (curCustomer != null) {
                            edtId = (EditTextPreference) findPreference("IdCustomerId");
                            edtPassword = (EditTextPreference) findPreference("IdCustomerPassword");
                            edtPhone = (EditTextPreference) findPreference("IdCustomerPhone");
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
                        error.printStackTrace();
                    }
                });
            if (curCustomer != null) {
                edtId = (EditTextPreference) findPreference("IdCustomerId");
                edtPassword = (EditTextPreference) findPreference("IdCustomerPassword");
                edtPhone = (EditTextPreference) findPreference("IdCustomerPhone");
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
            if (curCustomer == null) {
                p.setSummary("");
            } else {
                p.setSummary(edt.getText());
                SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(p.getKey(), edt.getText());
                editor.commit();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        updatePreference(findPreference(s), sharedPreferences);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().findPreference("IdCustomerId").setEnabled(false);
        getPreferenceScreen().findPreference("IdCustomerPhone").setEnabled(false);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    boolean invalidPassword = false;

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);
        String newPhoneNumber = sharedPrefs.getString("IdCustomerPhone", null);
        String newPassword = sharedPrefs.getString("IdCustomerPassword", null);
        if (newPassword.equals(curCustomer.getPassword())) {
            return;
        }
        if (newPassword.trim().length() < 6 || newPassword.trim().length() > 12) {
            invalidPassword = true;
        }
        if (!invalidPassword) {
            curCustomer.setPhoneNumber(newPhoneNumber);
            curCustomer.setPassword(newPassword);
        }
        try {
            Gson gson = new Gson();
            String curCustomerJson = gson.toJson(curCustomer);
            GsmsUtils.apiUtils(this, Request.Method.PUT, "customers/" + curCustomer.getId(), curCustomerJson, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    if (!invalidPassword) {
                        Toast.makeText(CustomerPreferenceActivity.this,
                                "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                    if (invalidPassword) {
                        Toast.makeText(CustomerPreferenceActivity.this,
                                "Invalid Password! Password must be between 6 to 12 characters! \n Nothing has been changed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CustomerPreferenceActivity.this,
                            "An error occurred! Please try again!", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
