package com.example.demo.repository;

import com.example.demo.entity.Order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Lấy đơn hàng theo userId với phân trang
    Page<Order> findByUser_UserId(Integer userId, Pageable pageable);

    // Đếm số đơn hàng theo userId
    long countByUser_UserId(Integer userId);
    
    List<Order> findAll();

    // Lấy đơn hàng theo userId
    List<Order> findByUser_UserId(Integer userId);
}