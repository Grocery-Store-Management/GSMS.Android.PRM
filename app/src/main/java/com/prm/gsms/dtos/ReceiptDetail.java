package com.prm.gsms.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReceiptDetail implements Serializable {
    private String id, receiptId, productId, name;
    private int quantity;
    private Timestamp createdDate;
    private BigDecimal price;

    public ReceiptDetail(String id, String receiptId, String productId, String name, int quantity, Timestamp createdDate, BigDecimal price) {
        this.id = id;
        this.receiptId = receiptId;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.price = price;
    }

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
}
