package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Variants")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variantId; // PK

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product; // Ánh xạ đến đối tượng Product

    private String code;
    private String image;
    private double price;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems; // Quan hệ một đến nhiều với OrderItem

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL) // Quan hệ một đến nhiều với WareHouse
    private Set<WareHouse> wareHouses;

    public Variant() {
        // Constructor không tham số
    }

    // Getters and Setters
    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Set<WareHouse> getWareHouses() {
        return wareHouses;
    }

    public void setWareHouses(Set<WareHouse> wareHouses) {
        this.wareHouses = wareHouses;
    }
}