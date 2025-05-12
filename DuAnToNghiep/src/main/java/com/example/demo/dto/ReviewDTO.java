package com.example.demo.dto;

import java.util.Date;

public class ReviewDTO {
    private int reviewId; // PK
    private int rating;
    private String comment;
    private String imageUser1;
    private String imageUser2;
    private String imageUser3;
    private Date reviewDate;
    private UserDisplayDTO user; // Sử dụng UserDisplayDTO để đại diện cho User
    private OrderItemDTO orderItem; // Sử dụng OrderItemDTO để đại diện cho OrderItem

    // Constructor không tham số
    public ReviewDTO() {
        this.user = new UserDisplayDTO(); // Khởi tạo mặc định cho user
        this.orderItem = new OrderItemDTO(); // Khởi tạo mặc định cho orderItem
    }

    // Constructor có tham số
    public ReviewDTO(int reviewId, int rating, String comment, String imageUser1, String imageUser2, String imageUser3, 
                     Date reviewDate, UserDisplayDTO user, OrderItemDTO orderItem) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.imageUser1 = imageUser1;
        this.imageUser2 = imageUser2;
        this.imageUser3 = imageUser3;
        this.reviewDate = reviewDate;
        this.user = user != null ? user : new UserDisplayDTO(); // Xử lý null cho user
        this.orderItem = orderItem != null ? orderItem : new OrderItemDTO(); // Xử lý null cho orderItem
    }

    // Getters và Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUser1() {
        return imageUser1;
    }

    public void setImageUser1(String imageUser1) {
        this.imageUser1 = imageUser1;
    }

    public String getImageUser2() {
        return imageUser2;
    }

    public void setImageUser2(String imageUser2) {
        this.imageUser2 = imageUser2;
    }

    public String getImageUser3() {
        return imageUser3;
    }

    public void setImageUser3(String imageUser3) {
        this.imageUser3 = imageUser3;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public UserDisplayDTO getUser() {
        return user;
    }

    public void setUser(UserDisplayDTO user) {
        this.user = user != null ? user : new UserDisplayDTO();
    }

    public OrderItemDTO getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemDTO orderItem) {
        this.orderItem = orderItem != null ? orderItem : new OrderItemDTO();
    }
}