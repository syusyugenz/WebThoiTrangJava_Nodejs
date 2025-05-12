//package com.example.demo.dto;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DetailDTO {
//    private int productId; // ID sản phẩm
//    private String productName; // Tên sản phẩm
//    private String brand; // Thương hiệu
//    private String description; // Mô tả
//    private String codeProduct; // Mã sản phẩm
//    private List<VariantDTO> variants; // Danh sách biến thể
//    private List<CategoryDTO> categories; // Danh sách danh mục
//    private List<WareHouseDTO> warehouses; // Danh sách kho hàng
//    private List<AttributeDTO> attributes; // Danh sách thuộc tính
//    private List<AttributeOptionDTO> attributeOptions; // Danh sách tùy chọn thuộc tính
//    private List<ReviewDTO> reviews; // Thêm danh sách đánh giá
//
//    // Constructor không tham số
//    public DetailDTO() {
//        this.variants = new ArrayList<>();
//        this.categories = new ArrayList<>();
//        this.warehouses = new ArrayList<>();
//        this.attributes = new ArrayList<>();
//        this.attributeOptions = new ArrayList<>();
//        this.reviews = new ArrayList<>(); // Khởi tạo danh sách đánh giá
//    }
//
//    // Constructor có tham số
//    public DetailDTO(int productId, String productName, String brand, String description,
//                     String codeProduct, List<VariantDTO> variants, List<CategoryDTO> categories,
//                     List<WareHouseDTO> warehouses, List<AttributeDTO> attributes,
//                     List<AttributeOptionDTO> attributeOptions, List<ReviewDTO> reviews) {
//        this.productId = productId;
//        this.productName = productName;
//        this.brand = brand;
//        this.description = description;
//        this.codeProduct = codeProduct;
//        this.variants = variants != null ? variants : new ArrayList<>();
//        this.categories = categories != null ? categories : new ArrayList<>();
//        this.warehouses = warehouses != null ? warehouses : new ArrayList<>();
//        this.attributes = attributes != null ? attributes : new ArrayList<>();
//        this.attributeOptions = attributeOptions != null ? attributeOptions : new ArrayList<>();
//        this.reviews = reviews != null ? reviews : new ArrayList<>(); // Khởi tạo danh sách đánh giá
//    }
//
//    // Getters và Setters
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
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCodeProduct() {
//        return codeProduct;
//    }
//
//    public void setCodeProduct(String codeProduct) {
//        this.codeProduct = codeProduct;
//    }
//
//    public List<VariantDTO> getVariants() {
//        return variants;
//    }
//
//    public void setVariants(List<VariantDTO> variants) {
//        this.variants = variants != null ? variants : new ArrayList<>();
//    }
//
//    public List<CategoryDTO> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(List<CategoryDTO> categories) {
//        this.categories = categories != null ? categories : new ArrayList<>();
//    }
//
//    public List<WareHouseDTO> getWarehouses() {
//        return warehouses;
//    }
//
//    public void setWarehouses(List<WareHouseDTO> warehouses) {
//        this.warehouses = warehouses != null ? warehouses : new ArrayList<>();
//    }
//
//    public List<AttributeDTO> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(List<AttributeDTO> attributes) {
//        this.attributes = attributes != null ? attributes : new ArrayList<>();
//    }
//
//    public List<AttributeOptionDTO> getAttributeOptions() {
//        return attributeOptions;
//    }
//
//    public void setAttributeOptions(List<AttributeOptionDTO> attributeOptions) {
//        this.attributeOptions = attributeOptions != null ? attributeOptions : new ArrayList<>();
//    }
//
//    public List<ReviewDTO> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(List<ReviewDTO> reviews) {
//        this.reviews = reviews != null ? reviews : new ArrayList<>();
//    }
//}

package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class DetailDTO {
    private int productId; // ID sản phẩm
    private String productName; // Tên sản phẩm
    private String brand; // Thương hiệu
    private String description; // Mô tả
    private String codeProduct; // Mã sản phẩm
    private List<VariantDTO> variants; // Danh sách biến thể
    private List<CategoryDTO> categories; // Danh sách danh mục
    private List<WareHouseDTO> warehouses; // Danh sách kho hàng
    private List<AttributeDTO> attributes; // Danh sách thuộc tính
    private List<AttributeOptionDTO> attributeOptions; // Danh sách tùy chọn thuộc tính
    private List<ReviewDTO> reviews; // Danh sách đánh giá
    private int quantitySold; // Tổng số lượng đã bán của sản phẩm

    // Constructor không tham số
    public DetailDTO() {
        this.variants = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.warehouses = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.attributeOptions = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.quantitySold = 0; // Khởi tạo quantitySold
    }

    // Constructor có tham số
    public DetailDTO(int productId, String productName, String brand, String description,
                     String codeProduct, List<VariantDTO> variants, List<CategoryDTO> categories,
                     List<WareHouseDTO> warehouses, List<AttributeDTO> attributes,
                     List<AttributeOptionDTO> attributeOptions, List<ReviewDTO> reviews, int quantitySold) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.description = description;
        this.codeProduct = codeProduct;
        this.variants = variants != null ? variants : new ArrayList<>();
        this.categories = categories != null ? categories : new ArrayList<>();
        this.warehouses = warehouses != null ? warehouses : new ArrayList<>();
        this.attributes = attributes != null ? attributes : new ArrayList<>();
        this.attributeOptions = attributeOptions != null ? attributeOptions : new ArrayList<>();
        this.reviews = reviews != null ? reviews : new ArrayList<>();
        this.quantitySold = quantitySold;
    }

    // Getters và Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDTO> variants) {
        this.variants = variants != null ? variants : new ArrayList<>();
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories != null ? categories : new ArrayList<>();
    }

    public List<WareHouseDTO> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WareHouseDTO> warehouses) {
        this.warehouses = warehouses != null ? warehouses : new ArrayList<>();
    }

    public List<AttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDTO> attributes) {
        this.attributes = attributes != null ? attributes : new ArrayList<>();
    }

    public List<AttributeOptionDTO> getAttributeOptions() {
        return attributeOptions;
    }

    public void setAttributeOptions(List<AttributeOptionDTO> attributeOptions) {
        this.attributeOptions = attributeOptions != null ? attributeOptions : new ArrayList<>();
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews != null ? reviews : new ArrayList<>();
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}