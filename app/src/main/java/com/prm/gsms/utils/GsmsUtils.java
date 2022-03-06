package com.prm.gsms.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prm.gsms.R;
import com.prm.gsms.activities.customer.CustomerPreferenceActivity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class GsmsUtils {
    private static final String BASE_URL = "https://gsms-api-prm.azurewebsites.net/api/";
    private static final String bearer = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjE4NmFmNjY2LWMzM2EtNGZjMy05OGJlLTU4MWVjYzdiNTk0OCIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJwaG9uZ250IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiQURNSU4iLCJqdGkiOiI3ZWUwYzZjYS0yZjFjLTRlZGMtYjlkNy0zMjU1M2Q0NGYxZGUiLCJleHAiOjE2NDY1NzcyNTUsImlzcyI6Imh0dHBzOi8vZ3Ntcy1hcGktcHJtLmF6dXJld2Vic2l0ZXMubmV0IiwiYXVkIjoiaHR0cHM6Ly9nc21zLWFwaS1wcm0uYXp1cmV3ZWJzaXRlcy5uZXQifQ.j98_87gCxdOOswPOytHsM7DbcuDipTnA11AyJ2_PBbQ";

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();
        return gson;
    }

    public static void apiUtils(Context context, int method, String url, VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strReq = null;
        switch (method) {
            case Request.Method.GET: {
                strReq = new StringRequest(Request.Method.GET, BASE_URL + url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "onErrorResponse: " + error.getMessage());
                    }
                }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", "GSMS-app");
                        headers.put("Authorization", "Bearer " + bearer);
                        return headers;
                    }
                };
            }
        }
        queue.add(strReq);
    }
}
