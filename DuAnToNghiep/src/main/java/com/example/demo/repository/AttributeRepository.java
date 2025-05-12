package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    
	// Tìm tất cả các thuộc tính
    List<Attribute> findAll();
    
    // Tìm thuộc tính theo attributeId
    Optional<Attribute> findByAttributeId(int attributeId);
    
    Optional<Attribute> findByName(String name); // Tìm theo tên
    
    Optional<Attribute> findByNameIgnoreCase(String name);
}