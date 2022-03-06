package com.prm.gsms.utils;

import android.content.Intent;

import com.google.gson.Gson;
import com.prm.gsms.activities.MainActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GsmsUtils {
    public static final String BASE_URL = "https://gsms-api.azurewebsites.net/api/v1.0/";

    public static <T> T fetchData(String url, String method, Class<T> objectClass
            , Object body, String authToken)
            throws IOException, RuntimeException {
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Method must be given");
        }
        URL endpoint = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.addRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestMethod(method);
        if (body != null) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }
        connection.connect();

        // Response code
        int responseCode = connection.getResponseCode();
        if (responseCode != 200 && responseCode != 201) {
            throw new RuntimeException("HttpResponseCode " + responseCode + "\nEndpoint: " + url);
        }
        String inline = "";
        Scanner scanner = new Scanner(endpoint.openStream());

        // Write all the JSON data into a string
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }

        // Close scanner
        scanner.close();
        connection.disconnect();
        // Parse JSON string
        Gson gson = new Gson();
        T object = gson.fromJson(inline, objectClass);

        return object;
    }
}
