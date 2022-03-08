package com.prm.gsms.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

public class ImportOrderDetail implements Serializable {
    private String id, orderId, name, distributor, productId;
    private int quantity;
    private boolean isDeleted;
    private BigDecimal price;

    public ImportOrderDetail(String id, String orderId, String name, String distributor, String productId, int quantity, boolean isDeleted, BigDecimal price) {
        this.id = id;
        this.orderId = orderId;
        this.name = name;
        this.distributor = distributor;
        this.productId = productId;
        this.quantity = quantity;
        this.isDeleted = isDeleted;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price.setScale(1, BigDecimal.ROUND_HALF_EVEN);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
