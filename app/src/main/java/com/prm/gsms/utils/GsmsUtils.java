package com.prm.gsms.utils;

import android.content.Intent;

import com.google.gson.Gson;
import com.prm.gsms.activities.MainActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GsmsUtils {
    public static <T> T fetchData(String url, String method,
                                  String authToken, Object body, Class<T> objectClass)
            throws IOException, RuntimeException {
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Method must be given");
        }
        URL endpoint = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod(method);
        connection.connect();

        // Response code
        int responseCode = connection.getResponseCode();
        if (responseCode != 200 && responseCode != 201) {
            throw new RuntimeException("HttpResponseCode " + responseCode + "\nEndpoint: " + url);
        }
        String inline = "";
        Scanner scanner = new Scanner(endpoint.openStream());

        // Write all the JSON data into a string
        while (scanner.hasNext()){
            inline += scanner.nextLine();
        }

        // Close scanner
        scanner.close();

        // Parse JSON string
        Gson gson = new Gson();
        T object = gson.fromJson(inline, objectClass);

        return object;
    }
}
