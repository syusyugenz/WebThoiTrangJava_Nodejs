package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    private String provinceName;
    private int provinceId;
    private String districtName;
    private int districtId;
    private String wardName;
    private String wardId;
    private String addressLine;
    private String receiverPhone; // Đổi từ int sang String
    private String receiverName;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Address() {
        // Constructor mặc định
    }

    public Address(int addressId, String provinceName, int provinceId, 
                  String districtName, int districtId, String wardName, 
                  String wardId, String addressLine, String receiverPhone,
                  String receiverName, User user) {
        this.addressId = addressId;
        this.provinceName = provinceName;
        this.provinceId = provinceId;
        this.districtName = districtName;
        this.districtId = districtId;
        this.wardName = wardName;
        this.wardId = wardId;
        this.addressLine = addressLine;
        this.receiverPhone = receiverPhone;
        this.receiverName = receiverName;
        this.user = user;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getReceiverPhone() { // Đổi từ int sang String
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) { // Đổi từ int sang String
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}