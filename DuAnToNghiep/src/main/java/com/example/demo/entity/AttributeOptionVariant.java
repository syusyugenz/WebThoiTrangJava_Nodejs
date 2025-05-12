package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "AttributeOptionVariants")
public class AttributeOptionVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variantAttributeId; // PK

    @ManyToOne
    @JoinColumn(name = "attributeOptionId", referencedColumnName = "attributeOptionId")
    private AttributeOption attributeOption; // Ánh xạ đến đối tượng AttributeOption

    @ManyToOne
    @JoinColumn(name = "variantId", referencedColumnName = "variantId")
    private Variant variant; // Ánh xạ đến đối tượng Variant

    public AttributeOptionVariant() {
        // Constructor không tham số
    }

    // Getters and Setters
    public int getVariantAttributeId() {
        return variantAttributeId;
    }

    public void setVariantAttributeId(int variantAttributeId) {
        this.variantAttributeId = variantAttributeId;
    }

    public AttributeOption getAttributeOption() {
        return attributeOption;
    }

    public void setAttributeOption(AttributeOption attributeOption) {
        this.attributeOption = attributeOption;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }
}