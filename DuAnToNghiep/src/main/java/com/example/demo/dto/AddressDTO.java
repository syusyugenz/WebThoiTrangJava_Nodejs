package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class AddressDTO {
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
    private List<UserRegisterDTO> users;

    public AddressDTO() {
        this.users = new ArrayList<>();
    }

    public AddressDTO(int addressId, String provinceName, int provinceId, 
                      String districtName, int districtId, String wardName, 
                      String wardId, String addressLine, String receiverPhone, 
                      String receiverName, List<UserRegisterDTO> users) {
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
        this.users = users;
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

    public List<UserRegisterDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserRegisterDTO> users) {
        this.users = users;
    }
}