package com.prm.gsms.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.dtos.ImportOrderCartItem;
import com.prm.gsms.dtos.Product;
import com.prm.gsms.utils.GsmsUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImportOrderService {
    public static String CART_FILE_NAME = "import-order-cart.txt";
    public static List<ImportOrder> getImportOrders(String importOrderJson){
        Gson gson = GsmsUtils.createGson();
        List<ImportOrder> importOrders = new ArrayList<>();
        Type type = new TypeToken<ArrayList<ImportOrder>>(){}.getType();
        importOrders = gson.fromJson(importOrderJson, type);
        return importOrders;
    }

    public static String getJsonString(ImportOrder importOrder) {
        Gson gson = GsmsUtils.createGson();
        return gson.toJson(importOrder);
    }

    public static List<ImportOrderCartItem> getCart(Context context) throws Exception {
        List<ImportOrderCartItem> products = null;
        String s = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            FileInputStream fis = context.openFileInput(CART_FILE_NAME);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            s = br.readLine();
            Gson gson = GsmsUtils.createGson();
            products = new ArrayList<>();
            Type type = new TypeToken<ArrayList<ImportOrderCartItem>>(){}.getType();
            products = gson.fromJson(s, type);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return products;
    }

    public static void saveToCart(Context context, List<ImportOrderCartItem> cart) throws Exception {
        OutputStreamWriter osw = null;
        try {
            FileOutputStream fos = context.openFileOutput(CART_FILE_NAME, Context.MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            Gson gson = GsmsUtils.createGson();
            String result = gson.toJson(cart);
            osw.write(result);
            osw.flush();
        } finally {
            try {
                if (osw != null){
                    osw.close();
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
