//package com.example.demo.dto;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VariantDTO {
//    private int variantId; // PK
//    private String code;
//    private String image;
//    private double price;
//    private List<ProductDTO> products; // Danh sách ProductDTO
//    private List<AttributeDTO> attributes; // Danh sách AttributeDTO
//    private List<AttributeOptionDTO> attributeOptions; // Danh sách AttributeOptionDTO
//
//    // Constructor không tham số
//    public VariantDTO() {
//        // Khởi tạo danh sách
//        this.products = new ArrayList<>();
//        this.attributes = new ArrayList<>();
//        this.attributeOptions = new ArrayList<>();
//    }
//
//    // Constructor với tham số
//    public VariantDTO(int variantId, String code, String image, double price, List<ProductDTO> products, 
//                      List<AttributeDTO> attributes, List<AttributeOptionDTO> attributeOptions) {
//        this.variantId = variantId;
//        this.code = code;
//        this.image = image;
//        this.price = price;
//        this.products = products != null ? products : new ArrayList<>(); // Kiểm tra null
//        this.attributes = attributes != null ? attributes : new ArrayList<>(); // Kiểm tra null
//        this.attributeOptions = attributeOptions != null ? attributeOptions : new ArrayList<>(); // Kiểm tra null
//    }
//
//    // Getters and Setters
//    public int getVariantId() {
//        return variantId;
//    }
//
//    public void setVariantId(int variantId) {
//        this.variantId = variantId;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public List<ProductDTO> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<ProductDTO> products) {
//        this.products = products != null ? products : new ArrayList<>(); // Kiểm tra null
//    }
//
//    public List<AttributeDTO> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(List<AttributeDTO> attributes) {
//        this.attributes = attributes != null ? attributes : new ArrayList<>(); // Kiểm tra null
//    }
//
//    public List<AttributeOptionDTO> getAttributeOptions() {
//        return attributeOptions;
//    }
//
//    public void setAttributeOptions(List<AttributeOptionDTO> attributeOptions) {
//        this.attributeOptions = attributeOptions != null ? attributeOptions : new ArrayList<>(); // Kiểm tra null
//    }
//}

package com.example.demo.dto;

import java.util.List;

public class VariantDTO {
    private Integer variantId;
    private String code; // Chuỗi code gốc (ví dụ: "1:1;2:2;3:3")
    private String readableCode; // Chuỗi hiển thị dễ đọc (ví dụ: "Size:Đen, Material:Coton, Color:Trắng")
    private String image;
    private Double price;
    private List<ProductDTO> products;
    private List<AttributeDTO> attributes;
    private List<AttributeOptionDTO> attributeOptions;

    // Constructor
    public VariantDTO(Integer variantId, String code, String image, Double price, List<ProductDTO> products,
                      List<AttributeDTO> attributes, List<AttributeOptionDTO> attributeOptions) {
        this.variantId = variantId;
        this.code = code;
        this.image = image;
        this.price = price;
        this.products = products;
        this.attributes = attributes;
        this.attributeOptions = attributeOptions;
    }

    // Getters và Setters
    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReadableCode() {
        return readableCode;
    }

    public void setReadableCode(String readableCode) {
        this.readableCode = readableCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<AttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public List<AttributeOptionDTO> getAttributeOptions() {
        return attributeOptions;
    }

    public void setAttributeOptions(List<AttributeOptionDTO> attributeOptions) {
        this.attributeOptions = attributeOptions;
    }
}