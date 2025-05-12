
//package com.example.demo.dto;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CardDTO {
//    private int productId; // PK
//    private String productName;
//    private String brand;
//    private List<VariantDTO> variants; // Danh sách VariantDTO
//    private List<CategoryDTO> categories; // Danh sách CategoryDTO
//    private boolean isNew; // Trường để đánh dấu sản phẩm mới
//
//    // Constructor không tham số
//    public CardDTO() {
//        this.variants = new ArrayList<>();
//        this.categories = new ArrayList<>();
//        this.isNew = false; // Mặc định không phải sản phẩm mới
//    }
//
//    // Constructor với tham số
//    public CardDTO(int productId, String productName, String brand, List<VariantDTO> variants, List<CategoryDTO> categories, boolean isNew) {
//        this.productId = productId;
//        this.productName = productName;
//        this.brand = brand;
//        this.variants = variants != null ? variants : new ArrayList<>();
//        this.categories = categories != null ? categories : new ArrayList<>();
//        this.isNew = isNew;
//    }
//
//    // Kiểm tra xem CardDTO có thuộc category cụ thể không
//    public boolean belongsToCategory(String categoryName) {
//        return categories.stream()
//                .anyMatch(category -> category.getCategoryName().equalsIgnoreCase(categoryName));
//    }
//
//    // Getters and Setters
//    public int getProductId() {
//        return productId;
//    }
//
//    public void setProductId(int productId) {
//        this.productId = productId;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    public List<VariantDTO> getVariants() {
//        return variants;
//    }
//
//    public void setVariants(List<VariantDTO> variants) {
//        this.variants = variants;
//    }
//
//    public List<CategoryDTO> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(List<CategoryDTO> categories) {
//        this.categories = categories;
//    }
//
//    public boolean isNew() {
//        return isNew;
//    }
//
//    public void setNew(boolean isNew) {
//        this.isNew = isNew;
//    }
//}

package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class CardDTO {
    private int productId; // PK
    private String productName;
    private String brand;
    private List<VariantDTO> variants; // Danh sách VariantDTO
    private List<CategoryDTO> categories; // Danh sách CategoryDTO
    private boolean isNew; // Trường để đánh dấu sản phẩm mới
    private double averageRating; // Thêm trường mới để chứa rating trung bình

    // Constructor không tham số
    public CardDTO() {
        this.variants = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.isNew = false; // Mặc định không phải sản phẩm mới
        this.averageRating = 0.0; // Mặc định rating trung bình là 0
    }

    // Constructor với tham số
    public CardDTO(int productId, String productName, String brand, List<VariantDTO> variants, 
                   List<CategoryDTO> categories, boolean isNew, double averageRating) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.variants = variants != null ? variants : new ArrayList<>();
        this.categories = categories != null ? categories : new ArrayList<>();
        this.isNew = isNew;
        this.averageRating = averageRating;
    }

    // Kiểm tra xem CardDTO có thuộc category cụ thể không
    public boolean belongsToCategory(String categoryName) {
        return categories.stream()
                .anyMatch(category -> category.getCategoryName().equalsIgnoreCase(categoryName));
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDTO> variants) {
        this.variants = variants;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}