package com.example.demo.util;

import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.entity.OrderItem;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.UserDisplayDTO;
import com.example.demo.dto.OrderItemDTO;

public class ReviewMapper {

    // Chuyển từ Review entity sang ReviewDTO
    public static ReviewDTO toDto(Review review) {
        if (review == null) {
            return null;
        }

        // Ánh xạ User sang UserDisplayDTO
        UserDisplayDTO userDTO = toUserDto(review.getUser());

        // Ánh xạ OrderItem sang OrderItemDTO (giản lược, chỉ lấy các trường cơ bản)
        OrderItemDTO orderItemDTO = toOrderItemDto(review.getOrderItem());

        return new ReviewDTO(
            review.getReviewId(),
            review.getRating(),
            review.getComment(),
            review.getImageUser1(),
            review.getImageUser2(),
            review.getImageUser3(),
            review.getReviewDate(),
            userDTO,
            orderItemDTO
        );
    }

    // Chuyển từ ReviewDTO sang Review entity
    public static Review toEntity(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }

        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setImageUser1(reviewDTO.getImageUser1());
        review.setImageUser2(reviewDTO.getImageUser2());
        review.setImageUser3(reviewDTO.getImageUser3());
        review.setReviewDate(reviewDTO.getReviewDate());

        // Ánh xạ UserDisplayDTO sang User
        review.setUser(toUserEntity(reviewDTO.getUser()));

        // Ánh xạ OrderItemDTO sang OrderItem
        review.setOrderItem(toOrderItemEntity(reviewDTO.getOrderItem()));

        return review;
    }

    // Chuyển từ User entity sang UserDisplayDTO
    public static UserDisplayDTO toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDisplayDTO(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getPoint(),
            user.getImage()
        );
    }

    // Chuyển từ UserDisplayDTO sang User entity
    public static User toUserEntity(UserDisplayDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setPoint(userDTO.getPoint());
        user.setImage(userDTO.getImage());
        return user;
    }

    // Chuyển từ OrderItem entity sang OrderItemDTO (giản lược, chỉ lấy các trường cơ bản)
    public static OrderItemDTO toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return new OrderItemDTO(
            orderItem.getOrderItemId(),
            orderItem.getQuantity(),
            orderItem.getPrice(),
            null, // Không ánh xạ orders chi tiết
            null  // Không ánh xạ variants chi tiết
        );
    }

    // Chuyển từ OrderItemDTO sang OrderItem entity (mới thêm)
    public static OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(orderItemDTO.getOrderItemId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        return orderItem;
    }
}