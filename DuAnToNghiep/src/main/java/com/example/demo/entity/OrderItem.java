package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId; // PK

    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order; // Ánh xạ đến đối tượng Order

    @ManyToOne
    @JoinColumn(name = "variantId", referencedColumnName = "variantId")
    private Variant variant; // Ánh xạ đến đối tượng Variant

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL) // Quan hệ một đến nhiều với Review
    private Set<Review> reviews;

    public OrderItem() {
        // Constructor không tham số
    }

    // Getters and Setters
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}