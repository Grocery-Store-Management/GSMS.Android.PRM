package com.prm.gsms.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

public class ImportOrder implements Serializable {
    private String id, name, storeId;
    private boolean isDeleted;
    private Timestamp createdDate;
    private Store store;

    public ImportOrder(String id, String name, String storeId, boolean isDeleted, Timestamp createdDate) {
        this.id = id;
        this.name = name;
        this.storeId = storeId;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
    }

    public ImportOrder(String id, String name, String storeId, boolean isDeleted, Timestamp createdDate, Store store) {
        this.id = id;
        this.name = name;
        this.storeId = storeId;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
