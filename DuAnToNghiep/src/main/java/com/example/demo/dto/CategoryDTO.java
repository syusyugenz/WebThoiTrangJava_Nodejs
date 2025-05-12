package com.example.demo.dto;

public class CategoryDTO {
    private int categoryId;
    private String categoryName;

    // Constructor không tham số
    public CategoryDTO() {
        // Constructor không tham số
    }

    // Constructor với tham số
    public CategoryDTO(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}