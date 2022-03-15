package com.prm.gsms.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prm.gsms.dtos.Customer;
import com.prm.gsms.dtos.Employee;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class GsmsUtils {
    private static final String BASE_URL = "https://gsms-api-prm.azurewebsites.net/api/";
    private static String bearer = "";

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();
        return gson;
    }

    public static void apiUtils(Context context, int method,
                                String url, VolleyCallback callback) throws JSONException {
        apiUtils(context, method, url, "", callback);
    }

    public static void apiUtils(Context context, int method,
                                String url, String body,
                                VolleyCallback callback) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        String bearerStr = sharedPreferences.getString("token", "");
        bearer = bearerStr;

        if (!bearer.isEmpty()) {
            RequestQueue queue = Volley.newRequestQueue(context);
            Request strReq = null;
            switch (method) {
                case Request.Method.GET: {
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
                                            "onErrorResponse: " + error.getMessage());
                            callback.onErrorResponse(error);
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
                    break;
                }
                case Request.Method.POST: {
                    strReq = new JsonObjectRequest(method,
                            BASE_URL + url,
                            new JSONObject(body),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    callback.onSuccess(response.toString());
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error",
                                    "URL: " + BASE_URL + url + "\n" +
                                            "onErrorResponse: " + error.getMessage());
                            callback.onErrorResponse(error);
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
                    };
                }
                case Request.Method.PUT: {
                    strReq = new JsonRequest(method,
                            BASE_URL + url,
                            body,
                            new Response.Listener() {
                                @Override
                                public void onResponse(Object response) {
                                    if (response != null) {
                                        callback.onSuccess(response.toString());
                                    } else {
                                        callback.onSuccess("");
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", "onErrorResponse: " + error.getMessage());
                            callback.onErrorResponse(error);
                        }
                    }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("User-Agent", "GSMS-app");
                            headers.put("Content-Type", "application/json");
                            headers.put("Authorization", "Bearer " + bearer);
                            return headers;
                        }

                        @Override
                        protected Response parseNetworkResponse(NetworkResponse response) {
                            try {
                                String jsonString = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                                JSONObject result = null;
                                if (jsonString != null && jsonString.length() > 0)
                                    result = new JSONObject(jsonString);
                                return Response.success(result,
                                        HttpHeaderParser.parseCacheHeaders(response));
                            } catch (UnsupportedEncodingException e) {
                                return Response.error(new ParseError(e));
                            } catch (JSONException je) {
                                return Response.error(new ParseError(je));
                            }
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }
                    };
                }

            }
            queue.add(strReq);
        }
    }

    public static void apiUtilsForLogin(Context context, int method, String url,
                                        Object object, String type, VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsBody = new JSONObject();
        if (type.equals("employee")) {
            try {
                jsBody.put("name", ((Employee) object).getName());
                jsBody.put("password", ((Employee) object).getPassword());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                jsBody.put("phoneNumber", ((Customer) object).getPhoneNumber());
                jsBody.put("password", ((Customer) object).getPassword());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        switch (method) {
            case Request.Method.POST: {
                jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST, BASE_URL + url, jsBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                callback.onSuccess(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "onErrorResponse: " + error.getMessage());
                        callback.onErrorResponse(error);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }

                };
            }
        }
        queue.add(jsonObjectRequest);
    }

    public static String getCurrentCustomerId(Context context) throws UnsupportedEncodingException, JSONException {
        SharedPreferences loginPreferences = context.getSharedPreferences("LoginPreferences", context.getApplicationContext().MODE_PRIVATE);
        String token = loginPreferences.getString("token", null);
        token = token.substring(token.indexOf(".") + 1, token.lastIndexOf("."));

        byte[] data = Base64.decode(token, Base64.DEFAULT);
        String tokenData = new String(data, "UTF-8");

        JSONObject customerJSONObject = new JSONObject(tokenData);
        String customerId = customerJSONObject.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");
        return customerId;
    }


    public static ProgressDialog showLoading(Context context, String message){
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage(message);
        progress.setCancelable(false);
        progress.show();
        return progress;
    }

}


