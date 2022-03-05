package com.prm.gsms.dtos;

import java.sql.Timestamp;

public class Store {
    private String id, name;
    private Timestamp createdDate;
    private boolean isDeleted;

    public Store(String id, String name, Timestamp createdDate, boolean isDeleted) {
        this.id = id;
        this.name = name;
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
