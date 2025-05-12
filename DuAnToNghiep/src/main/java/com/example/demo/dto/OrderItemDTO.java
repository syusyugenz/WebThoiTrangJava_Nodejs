package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderItemDTO {
    private int orderItemId;
    private int quantity;
    private double price;
    private OrderDTO order; // Thay List<OrderDTO> bằng OrderDTO
    private List<VariantDTO> variants;

    public OrderItemDTO() {
        this.order = null;
        this.variants = new ArrayList<>();
    }

    public OrderItemDTO(int orderItemId, int quantity, double price, OrderDTO order, List<VariantDTO> variants) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.variants = variants != null ? variants : new ArrayList<>();
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDTO> variants) {
        this.variants = variants;
    }
}