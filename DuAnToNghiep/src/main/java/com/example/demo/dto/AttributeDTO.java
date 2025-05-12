package com.example.demo.dto;

public class AttributeDTO {
    private int attributeId;
    private String name;
    
    public AttributeDTO() {
		// TODO Auto-generated constructor stub
	}

    // Constructor
    public AttributeDTO(int attributeId, String name) {
        this.attributeId = attributeId;
        this.name = name;
    }

    // Getters and Setters
    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}