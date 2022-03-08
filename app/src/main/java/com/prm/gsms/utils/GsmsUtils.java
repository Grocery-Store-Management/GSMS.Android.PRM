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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class GsmsUtils {
    private static final String BASE_URL = "https://gsms-api-prm.azurewebsites.net/api/";
    private static final String bearer = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjE4NmFmNjY2LWMzM2EtNGZjMy05OGJlLTU4MWVjYzdiNTk0OCIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJwaG9uZ250IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiQURNSU4iLCJqdGkiOiIzNjI2ZmUyNi0wMThjLTRhY2ItYTQ2Ny05ZjJjYTdmZTc4NTciLCJleHAiOjE2NDY3NDMwNDcsImlzcyI6Imh0dHBzOi8vZ3Ntcy1hcGktcHJtLmF6dXJld2Vic2l0ZXMubmV0IiwiYXVkIjoiaHR0cHM6Ly9nc21zLWFwaS1wcm0uYXp1cmV3ZWJzaXRlcy5uZXQifQ.O0gc4M4IObLGRiRDR9KmGpVdcxCZZptpTECvcmGB90c";

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();
        return gson;
    }

    public static void apiUtils (Context context, int method,
                                 String url, VolleyCallback callback) {
        apiUtils(context, method, url, "", callback);
    }
    public static void apiUtils(Context context, int method,
                                String url, String body,
                                VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strReq = null;
        strReq = new StringRequest(method,
                BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",
                        "URL: " + BASE_URL + url + "\n" +
                                "Response Code: " + error.networkResponse.statusCode + "\n" +
                                "onErrorResponse: " + error.getMessage());
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

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.isEmpty() ? null : body.getBytes(StandardCharsets.UTF_8);
            }
        };
        queue.add(strReq);
    }
}
