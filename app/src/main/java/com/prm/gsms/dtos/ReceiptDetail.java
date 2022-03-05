package com.prm.gsms.dtos;

import java.sql.Timestamp;

public class ReceiptDetail {
    private String id, receiptId, productId;
    private int quantity;
    private Timestamp createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public ReceiptDetail(String id, String receiptId, String productId, int quantity, Timestamp createdDate) {
        this.id = id;
        this.receiptId = receiptId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }
}
