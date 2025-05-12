package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId; // PK

    private int rating;
    private String comment;
    private String imageUser1;
    private String imageUser2;
    private String imageUser3;
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user; // Ánh xạ đến User

    @ManyToOne
    @JoinColumn(name = "orderItemId", referencedColumnName = "orderItemId", nullable = false)
    private OrderItem orderItem; // Ánh xạ đến OrderItem

    public Review() {
        // Constructor không tham số
    }

    // Getters and Setters
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

    public java.util.Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(java.util.Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}