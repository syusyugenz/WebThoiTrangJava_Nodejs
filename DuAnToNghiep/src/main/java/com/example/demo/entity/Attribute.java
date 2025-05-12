//package com.example.demo.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import java.util.Set;
//
//@Entity
//@Table(name = "attributes")
//public class Attribute {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int attributeId; 
//
//    private String name; 
//
//    @OneToMany(mappedBy = "attribute") 
//    private Set<AttributeOption> attributeOptions;
//
//    public Attribute() {
//        // Constructor không tham số
//    }
//
//    public int getAttributeId() {
//        return attributeId;
//    }
//
//    public void setAttributeId(int attributeId) {
//        this.attributeId = attributeId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Set<AttributeOption> getAttributeOptions() {
//        return attributeOptions;
//    }
//
//    public void setAttributeOptions(Set<AttributeOption> attributeOptions) {
//        this.attributeOptions = attributeOptions;
//    }
//}

package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attributeId;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "attribute")
    private Set<AttributeOption> attributeOptions;

    // Constructor, getters, setters
    public Attribute() {}

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
        this.name = name; // Chuẩn hóa đã được xử lý ở service
    }

    public Set<AttributeOption> getAttributeOptions() {
        return attributeOptions;
    }

    public void setAttributeOptions(Set<AttributeOption> attributeOptions) {
        this.attributeOptions = attributeOptions;
    }
}