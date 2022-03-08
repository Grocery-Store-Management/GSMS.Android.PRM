package com.prm.gsms.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product implements Serializable {
    private String id, name, categoryId;
    private boolean isDeleted;
    private int status;
    private BigDecimal price;
    private Timestamp expiringDate;
    private int storedQuantity;

    public Product(String id, String name, String categoryId, boolean isDeleted, int status, BigDecimal price, Timestamp expiringDate, int storedQuantity) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.isDeleted = isDeleted;
        this.status = status;
        this.price = price;
        this.expiringDate = expiringDate;
        this.storedQuantity = storedQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Timestamp expiringDate) {
        this.expiringDate = expiringDate;
    }

    public int getStoredQuantity() {
        return storedQuantity;
    }

    public void setStoredQuantity(int storedQuantity) {
        this.storedQuantity = storedQuantity;
    }
}
