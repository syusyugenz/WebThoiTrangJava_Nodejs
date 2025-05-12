//package com.example.demo.controller;
//
//import com.example.demo.dto.OrderItemDTO;
//import com.example.demo.dto.ReviewDTO;
//import com.example.demo.dto.UserDisplayDTO;
//import com.example.demo.service.ReviewService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/reviews")
//@CrossOrigin(origins = "*")
//public class ReviewController {
//
//    @Autowired
//    private ReviewService reviewService;
//
//    // Lấy tất cả đánh giá
//    @GetMapping
//    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
//        List<ReviewDTO> reviews = reviewService.getAllReviews();
//        return ResponseEntity.ok(reviews);
//    }
//
//    // Lấy tất cả người dùng
//    @GetMapping("/users")
//    public ResponseEntity<List<UserDisplayDTO>> getAllUsers() {
//        List<UserDisplayDTO> users = reviewService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    // Lấy tất cả OrderItem
//    @GetMapping("/orderitems")
//    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
//        List<OrderItemDTO> orderItems = reviewService.getAllOrderItems();
//        return ResponseEntity.ok(orderItems);
//    }
//
//    // Thêm đánh giá
//    @PostMapping
//    public ResponseEntity<ReviewDTO> addReview(
//            @RequestPart("review") ReviewDTO reviewDTO,
//            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
//        System.out.println("Received addReview request with " + (images != null ? images.length : 0) + " images");
//        ReviewDTO createdReview = reviewService.addReview(reviewDTO, images);
//        return ResponseEntity.ok(createdReview);
//    }
//
//    // Cập nhật đánh giá
//    @PutMapping("/{id}")
//    public ResponseEntity<ReviewDTO> updateReview(
//            @PathVariable("id") int reviewId,
//            @RequestPart("review") ReviewDTO reviewDTO,
//            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
//        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO, images);
//        return updatedReview != null ? ResponseEntity.ok(updatedReview) : ResponseEntity.notFound().build();
//    }
//
//    // Xóa đánh giá
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteReview(@PathVariable("id") int reviewId) {
//        reviewService.deleteReview(reviewId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Lấy đánh giá theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("id") int reviewId) {
//        ReviewDTO reviewDTO = reviewService.getReviewById(reviewId);
//        return reviewDTO != null ? ResponseEntity.ok(reviewDTO) : ResponseEntity.notFound().build();
//    }
//
//    // Kiểm tra xem người dùng đã đánh giá OrderItem chưa
//    @GetMapping("/check")
//    public ResponseEntity<Boolean> checkReviewExists(
//            @RequestParam("userId") int userId,
//            @RequestParam("orderItemId") int orderItemId) {
//        boolean hasReviewed = reviewService.hasUserReviewed(userId, orderItemId);
//        return ResponseEntity.ok(hasReviewed);
//    }
//
//    // Lấy danh sách đánh giá theo productId
//    @GetMapping("/product/{productId}")
//    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable("productId") int productId) {
//        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
//        return ResponseEntity.ok(reviews);
//    }
//}

package com.example.demo.controller;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.UserDisplayDTO;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDisplayDTO>> getAllUsers() {
        List<UserDisplayDTO> users = reviewService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/orderitems")
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
        List<OrderItemDTO> orderItems = reviewService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(
            @RequestPart("review") ReviewDTO reviewDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        System.out.println("Received addReview request with " + (images != null ? images.length : 0) + " images");
        ReviewDTO createdReview = reviewService.addReview(reviewDTO, images);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable("id") int reviewId,
            @RequestPart("review") ReviewDTO reviewDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO, images);
        return updatedReview != null ? ResponseEntity.ok(updatedReview) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") int reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("id") int reviewId) {
        ReviewDTO reviewDTO = reviewService.getReviewById(reviewId);
        return reviewDTO != null ? ResponseEntity.ok(reviewDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkReviewExists(
            @RequestParam("userId") int userId,
            @RequestParam("orderItemId") int orderItemId) {
        boolean hasReviewed = reviewService.hasUserReviewed(userId, orderItemId);
        return ResponseEntity.ok(hasReviewed);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable("productId") int productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    // Thêm endpoint mới để lấy trung bình rating
    @GetMapping("/product/{productId}/average-rating")
    public ResponseEntity<Map<String, Object>> getAverageRatingByProductId(@PathVariable("productId") int productId) {
        Map<String, Object> averageRating = reviewService.calculateAverageRatingByProductId(productId);
        return ResponseEntity.ok(averageRating);
    }
}