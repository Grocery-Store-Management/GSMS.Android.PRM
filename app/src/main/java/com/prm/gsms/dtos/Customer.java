package com.prm.gsms.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

public class Customer implements Serializable {
    private String id;
    private String point;
    private String phoneNumber;
    private String password;
    private Timestamp createdDate;
    private boolean isDeleted;

    public Customer(String id, String point, String phoneNumber, String password, Timestamp createdDate, boolean isDeleted) {
        this.id = id;
        this.point = point;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.createdDate = createdDate;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
