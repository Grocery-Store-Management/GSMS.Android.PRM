package com.prm.gsms.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ImportOrderCartItem extends Product {
    private int orderQuantity;
    private String distributor;

    public ImportOrderCartItem(String id, String name, String categoryId, boolean isDeleted, int status, BigDecimal price, Timestamp expiringDate, int storedQuantity, Category category, int orderQuantity, String distributor) {
        super(id, name, categoryId, isDeleted, status, price, expiringDate, storedQuantity, category);
        this.orderQuantity = orderQuantity;
        this.distributor = distributor;
    }

    public ImportOrderCartItem(String id, String name, String categoryId, boolean isDeleted, int status, BigDecimal price, Timestamp expiringDate, int storedQuantity, int orderQuantity, String distributor) {
        super(id, name, categoryId, isDeleted, status, price, expiringDate, storedQuantity);
        this.orderQuantity = orderQuantity;
        this.distributor = distributor;
    }

    public ImportOrderCartItem(Product product, int orderQuantity, String distributor) {
        super(product.getId(), product.getName(), product.getCategoryId(), product.isDeleted(), product.getStatus(), product.getPrice(), product.getExpiringDate(), product.getStoredQuantity());
        this.orderQuantity = orderQuantity;
        this.distributor = distributor;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }
}
