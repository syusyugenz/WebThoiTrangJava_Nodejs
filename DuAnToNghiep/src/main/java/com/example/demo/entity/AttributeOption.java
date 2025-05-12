package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attribute_options")
public class AttributeOption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attributeOptionId; // PK

    private String value; // Giá trị của thuộc tính

    @ManyToOne
    @JoinColumn(name = "attributeId", referencedColumnName = "attributeId")
    private Attribute attribute; // Ánh xạ đến đối tượng Attribute

    @OneToMany(mappedBy = "attributeOption", cascade = CascadeType.ALL) // Quan hệ một đến nhiều với AttributeOptionVariant
    private Set<AttributeOptionVariant> attributeOptionVariants = new HashSet<>();

    public AttributeOption() {
        // Constructor không tham số
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

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Set<AttributeOptionVariant> getAttributeOptionVariants() {
        return attributeOptionVariants;
    }

    public void setAttributeOptionVariants(Set<AttributeOptionVariant> attributeOptionVariants) {
        this.attributeOptionVariants = attributeOptionVariants;
    }
}