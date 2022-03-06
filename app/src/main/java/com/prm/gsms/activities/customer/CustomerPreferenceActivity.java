package com.prm.gsms.activities.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

import com.prm.gsms.R;
import com.prm.gsms.dtos.Customer;
import com.prm.gsms.services.CustomerService;
import com.prm.gsms.utils.GsmsUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class CustomerPreferenceActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    static Customer curCustomer = null;

    EditTextPreference edtId;
    EditTextPreference edtPassword;
    EditTextPreference edtPhone;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String customerId = null;
        try {
            fis = openFileInput("customer.txt");
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String s = null;
            while ((s = br.readLine()) != null) {
                customerId = s;
            }
            if (customerId != null) curCustomer = CustomerService.getCustomerInfoById(customerId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (isr != null) isr.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        addPreferencesFromResource(R.xml.customerpreference);
        SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);

        edtId = (EditTextPreference) findPreference("IdCustomerId");
        edtPassword = (EditTextPreference) findPreference("IdCustomerPassword");
        edtPhone = (EditTextPreference) findPreference("IdCustomerPhone");

        if (curCustomer != null) {
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
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
