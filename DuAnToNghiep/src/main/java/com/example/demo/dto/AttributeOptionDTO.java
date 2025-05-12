package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class AttributeOptionDTO {
    private int attributeOptionId; // ID của tùy chọn thuộc tính
    private String value; // Giá trị của tùy chọn thuộc tính
    private List<AttributeDTO> attributes; // Danh sách các thuộc tính

    // Constructor không tham số
    public AttributeOptionDTO() {
        this.attributes = new ArrayList<>(); // Khởi tạo danh sách
    }

    // Constructor
    public AttributeOptionDTO(int attributeOptionId, String value, List<AttributeDTO> attributes) {
        this.attributeOptionId = attributeOptionId;
        this.value = value;
        this.attributes = attributes != null ? attributes : new ArrayList<>(); // Kiểm tra null
    }

    // Getters and Setters
    public int getAttributeOptionId() {
        return attributeOptionId;
    }

    public void setAttributeOptionId(int attributeOptionId) {
        this.attributeOptionId = attributeOptionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<AttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDTO> attributes) {
        this.attributes = attributes != null ? attributes : new ArrayList<>(); // Kiểm tra null
    }
}