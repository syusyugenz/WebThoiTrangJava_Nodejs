package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.Variant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {
	
	// Tìm kiếm các biến thể theo productId
    List<Variant> findByProduct_ProductId(int productId);

    // Tìm tất cả các biến thể
    List<Variant> findAll();
    
    List<Variant> findByProduct_ProductIdIn(List<Integer> productIds);
    
    @Query("SELECT COUNT(v) > 0 FROM Variant v WHERE v.product.productId = :productId AND v.code = :code")
    boolean existsByProductIdAndCode(Integer productId, String code);


    List<Variant> findByProduct(Product product);
}