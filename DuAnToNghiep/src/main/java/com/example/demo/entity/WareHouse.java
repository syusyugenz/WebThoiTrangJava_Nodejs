package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "WareHouses")
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId; // PK

    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "variantId", referencedColumnName = "variantId", nullable = false)
    private Variant variant; // Ánh xạ đến đối tượng Variant

    // Constructor không tham số
    public WareHouse() {}

    // Constructor có tham số
    public WareHouse(Integer warehouseId, Variant variant, double price, int quantity) {
        this.warehouseId = warehouseId;
        this.variant = variant;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter và Setter
    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }
}