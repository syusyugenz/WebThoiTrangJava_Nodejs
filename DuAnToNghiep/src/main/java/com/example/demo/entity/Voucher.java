package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voucherId; // PK

    private String discountType;
    private double minimumMoneyValue; // Minimum value for voucher application
    private double maxDiscountValue; // Maximum discount value
    
    private Date startDate;
    private Date lateDate;


    public Voucher() {
        // Constructor không tham số
    }

    // Getters and Setters
    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getMinimumMoneyValue() {
        return minimumMoneyValue;
    }

    public void setMinimumMoneyValue(double minimumMoneyValue) {
        this.minimumMoneyValue = minimumMoneyValue;
    }

    public double getMaxDiscountValue() {
        return maxDiscountValue;
    }

    public void setMaxDiscountValue(double maxDiscountValue) {
        this.maxDiscountValue = maxDiscountValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLateDate() {
        return lateDate;
    }

    public void setLateDate(Date lateDate) {
        this.lateDate = lateDate;
    }
}