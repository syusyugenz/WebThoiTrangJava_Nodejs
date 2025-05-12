package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private int cartId; // ID của giỏ hàng
    private int quantity; // Số lượng sản phẩm trong giỏ
    private List<UserRegisterDTO> users; // Danh sách người dùng
    private List<VariantDTO> variants; // Danh sách biến thể sản phẩm
    private List<ProductDTO> products; // Danh sách sản phẩm

    // Constructor không tham số
    public ShoppingCartDTO() {
        this.cartId = 0; // Giá trị mặc định
        this.quantity = 0; // Giá trị mặc định
        this.users = new ArrayList<>(); // Khởi tạo danh sách người dùng
        this.variants = new ArrayList<>(); // Khởi tạo danh sách biến thể
        this.products = new ArrayList<>(); // Khởi tạo danh sách sản phẩm
    }

    // Constructor có tham số
    public ShoppingCartDTO(int cartId, int quantity, List<UserRegisterDTO> users,
                           List<VariantDTO> variants, List<ProductDTO> products) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.users = users != null ? users : new ArrayList<>();
        this.variants = variants != null ? variants : new ArrayList<>();
        this.products = products != null ? products : new ArrayList<>();
    }

    // Getter và Setter
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<UserRegisterDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserRegisterDTO> users) {
        this.users = users;
    }

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDTO> variants) {
        this.variants = variants;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}