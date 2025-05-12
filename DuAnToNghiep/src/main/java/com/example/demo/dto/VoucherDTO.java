package com.example.demo.dto;

import java.util.Date;

public class VoucherDTO {
    private int voucherId; // PK
    private String discountType;
    private double minimumMoneyValue; // Minimum value for voucher application
    private double maxDiscountValue; // Maximum discount value
    private Date startDate;
    private Date lateDate;

    // Constructor không tham số
    public VoucherDTO() {
    }

    // Constructor đầy đủ tham số
    public VoucherDTO(int voucherId, String discountType, double minimumMoneyValue, double maxDiscountValue, Date startDate, Date lateDate) {
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.minimumMoneyValue = minimumMoneyValue;
        this.maxDiscountValue = maxDiscountValue;
        this.startDate = startDate;
        this.lateDate = lateDate;
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