package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class WareHouseDTO {
    private int warehouseId; // ID kho
    private double price; // Giá của biến thể
    private int quantity; // Số lượng lưu trữ
    private List<VariantDTO> variants; // Danh sách VariantDTO
    private List<ProductDTO> products; // Danh sách ProductDTO

    // Constructor không tham số
    public WareHouseDTO() {
        // Khởi tạo danh sách variants và products
        this.variants = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    // Constructor có tham số
    public WareHouseDTO(int warehouseId, double price, int quantity, List<VariantDTO> variants, List<ProductDTO> products) {
        this.warehouseId = warehouseId;
        this.price = price;
        this.quantity = quantity;
        this.variants = variants != null ? variants : new ArrayList<>(); // Kiểm tra null
        this.products = products != null ? products : new ArrayList<>(); // Kiểm tra null
    }

    // Getters và Setters
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

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDTO> variants) {
        this.variants = variants != null ? variants : new ArrayList<>(); // Kiểm tra null
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products != null ? products : new ArrayList<>(); // Kiểm tra null
    }

    // Phương thức lấy tên sản phẩm
    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        for (ProductDTO product : products) {
            productNames.add(product.getProductName());
        }
        return productNames;
    }
}