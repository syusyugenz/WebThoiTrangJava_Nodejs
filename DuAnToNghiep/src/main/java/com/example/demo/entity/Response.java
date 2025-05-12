package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Responses")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int responseId; // PK, tự động sinh ID

    private String content;
    private Date responseDate;

    @OneToOne
    @JoinColumn(name = "reviewId", referencedColumnName = "reviewId", nullable = false)
    private Review review; // Liên kết 1-1 với Review

    public Response() {
    }

    public int getResponseId() { return responseId; }
    public void setResponseId(int responseId) { this.responseId = responseId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getResponseDate() { return responseDate; }
    public void setResponseDate(Date responseDate) { this.responseDate = responseDate; }
    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }
}