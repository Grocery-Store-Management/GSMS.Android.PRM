package com.prm.gsms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

public class GsmsUtils {
    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();
        return gson;
    }
}
