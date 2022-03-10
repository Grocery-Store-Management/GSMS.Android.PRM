package com.prm.gsms.services;

import static com.prm.gsms.utils.GsmsUtils.*;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.R;
import com.prm.gsms.dtos.Customer;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.utils.GsmsUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    private static final String BASE_URL = "https://gsms-api-prm.azurewebsites.net/api/";
    private static final String bearer = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjE4NmFmNjY2LWMzM2EtNGZjMy05OGJlLTU4MWVjYzdiNTk0OCIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJwaG9uZ250IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiQURNSU4iLCJqdGkiOiI3ZWUwYzZjYS0yZjFjLTRlZGMtYjlkNy0zMjU1M2Q0NGYxZGUiLCJleHAiOjE2NDY1NzcyNTUsImlzcyI6Imh0dHBzOi8vZ3Ntcy1hcGktcHJtLmF6dXJld2Vic2l0ZXMubmV0IiwiYXVkIjoiaHR0cHM6Ly9nc21zLWFwaS1wcm0uYXp1cmV3ZWJzaXRlcy5uZXQifQ.j98_87gCxdOOswPOytHsM7DbcuDipTnA11AyJ2_PBbQ";

    private static Customer foundCustomer = null;

    public static Customer getCustomerInfoById(String customerJson) {
        Gson gson = GsmsUtils.createGson();
        Type type = new TypeToken<Customer>() {
        }.getType();
        foundCustomer = gson.fromJson(customerJson, type);
        return foundCustomer;
    }

    public static void updateCustomerInfo(String id, Customer customer) {

    }
}
