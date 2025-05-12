package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // Custom query methods can be added here
	List<Review> findByUserUserId(int userId);
    List<Review> findByOrderItemOrderItemId(int orderItemId);
    boolean existsByUserUserIdAndOrderItemOrderItemId(int userId, int orderItemId);
    
    List<Review> findByOrderItemIn(List<OrderItem> orderItems); // Đúng: Truyền List<OrderItem>
}