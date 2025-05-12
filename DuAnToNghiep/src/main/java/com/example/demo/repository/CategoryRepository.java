package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
	List<Category> findAll(); // Tìm tất cả danh mục

    Optional<Category> findById(int id); // Tìm danh mock theo ID

    Optional<List<Category>> findByCategoryName(String categoryName); // Tìm danh mục theo tên danh mục
    
 // Thêm phương thức để tìm danh mục theo categoryName (không phân biệt hoa/thường)
    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);


}