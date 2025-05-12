package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.demo.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    // Lấy tất cả giỏ hàng của một người dùng dựa trên userId
    List<ShoppingCart> findByUser_UserId(int userId);
    
    // Tìm giỏ hàng theo cartId
    ShoppingCart findByCartId(int cartId);
}