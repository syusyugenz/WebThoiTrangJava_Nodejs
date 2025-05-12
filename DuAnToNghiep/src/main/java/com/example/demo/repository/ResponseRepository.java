package com.example.demo.repository;

import com.example.demo.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Integer> {
    Optional<Response> findByReview_ReviewId(int reviewId);
    boolean existsByReview_ReviewId(int reviewId);
}