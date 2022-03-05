package com.prm.gsms.dtos;

import java.sql.Timestamp;

public class Receipt {
    private String id, customerId, employeeId, storeId;
    private Timestamp createdDate;
    private boolean isDeleted;

    public Receipt(String id, String customerId, String employeeId, String storeId, Timestamp createdDate, boolean isDeleted) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.storeId = storeId;
        this.createdDate = createdDate;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
