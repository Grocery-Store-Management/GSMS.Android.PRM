package com.prm.gsms.dtos;

import java.sql.Timestamp;

public class Employee {
    private String id, name, password, storeId, role;
    private Timestamp createdDate;
    private boolean isDeleted;

    public Employee(String id, String name, String password, String storeId, String role, Timestamp createdDate, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.storeId = storeId;
        this.role = role;
        this.createdDate = createdDate;
        this.isDeleted = isDeleted;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
