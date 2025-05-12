package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class VoucherUserDTO {
    private List<VoucherDTO> vouchers; // Danh sách các voucher
    private List<UserDisplayDTO> users; // Danh sách các user
    private String status; // Trạng thái
    private int quantity; // Số lượng (có thể là tổng số voucher hoặc số user)

    // Constructor không tham số
    public VoucherUserDTO() {
        this.vouchers = new ArrayList<>(); // Khởi tạo danh sách voucher rỗng
        this.users = new ArrayList<>(); // Khởi tạo danh sách user rỗng
    }

    // Constructor đầy đủ tham số
    public VoucherUserDTO( List<VoucherDTO> vouchers, List<UserDisplayDTO> users, String status, int quantity) {
        this.vouchers = (vouchers != null) ? vouchers : new ArrayList<>(); // Khởi tạo nếu null
        this.users = (users != null) ? users : new ArrayList<>(); // Khởi tạo nếu null
        this.status = status;
        this.quantity = quantity;
    }

    // Getters và Setters

    public List<VoucherDTO> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<VoucherDTO> vouchers) {
        this.vouchers = (vouchers != null) ? vouchers : new ArrayList<>();
    }

    public List<UserDisplayDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDisplayDTO> users) {
        this.users = (users != null) ? users : new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Phương thức tiện ích để thêm voucher
    public void addVoucher(VoucherDTO voucher) {
        if (voucher != null) {
            this.vouchers.add(voucher);
        }
    }

    // Phương thức tiện ích để thêm user
    public void addUser(UserDisplayDTO user) {
        if (user != null) {
            this.users.add(user);
        }
    }
}