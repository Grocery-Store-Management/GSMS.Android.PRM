package com.prm.gsms.utils;

import com.android.volley.VolleyError;

//
//int GET = 0;
//        int POST = 1;
//        int PUT = 2;
//        int DELETE = 3;
//        int HEAD = 4;
//        int OPTIONS = 5;
//        int TRACE = 6;
//        int PATCH = 7;
public interface VolleyCallback {
    void onSuccess(String result);
    void onErrorResponse(VolleyError error);
}
