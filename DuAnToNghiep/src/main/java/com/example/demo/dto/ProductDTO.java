package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    private int productId;
    private String productName;
    private String description;
    private String brand;
    private List<CategoryDTO> categories; // Danh sách CategoryDTO

    // Constructor không tham số
    public ProductDTO() {
        // Constructor không tham số, khởi tạo danh sách categories
        this.categories = new ArrayList<>();
    }

    // Constructor với tham số
    public ProductDTO(int productId, String productName, String description, String brand, List<CategoryDTO> categories) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.categories = categories; // Khởi tạo danh sách categories
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}