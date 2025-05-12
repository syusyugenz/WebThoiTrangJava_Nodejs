package com.example.demo.dto;

import java.util.Date;

public class ResponseDTO {
    private int responseId;
    private String content;
    private Date responseDate;
    private ReviewDTO review; // Lọc thông tin từ ReviewDTO

    public ResponseDTO() {
        this.review = new ReviewDTO();
    }

    public ResponseDTO(int responseId, String content, Date responseDate, ReviewDTO review) {
        this.responseId = responseId;
        this.content = content;
        this.responseDate = responseDate;
        this.review = review != null ? review : new ReviewDTO();
    }

    // Getters and Setters
    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public void setReview(ReviewDTO review) {
        this.review = review != null ? review : new ReviewDTO();
    }
}