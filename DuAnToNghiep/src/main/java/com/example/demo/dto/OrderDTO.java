package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private Date orderDate;
    private String status;
    private String shippingAddress;
    private String phoneNumber;
    private String paymentStatus;
    private String content;
    private double shippingFee;
    private double totalAmount;
    private VoucherDTO voucher;
    private List<UserRegisterDTO> users;
    private ShoppingCartDTO shoppingCart;
    private List<OrderItemDTO> orderItems;
    private String receiverName; // Thêm receiverName

    // Constructor không tham số
    public OrderDTO() {
        this.users = new ArrayList<>();
        this.shoppingCart = new ShoppingCartDTO(0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.orderItems = new ArrayList<>();
    }

    // Constructor có tham số
    public OrderDTO(int orderId, Date orderDate, String status, String shippingAddress,
                    String phoneNumber, String paymentStatus, String content,
                    double shippingFee, double totalAmount, VoucherDTO voucher,
                    List<UserRegisterDTO> users, ShoppingCartDTO shoppingCart,
                    List<OrderItemDTO> orderItems, String receiverName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.phoneNumber = phoneNumber;
        this.paymentStatus = paymentStatus;
        this.content = content;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
        this.voucher = voucher;
        this.users = users != null ? users : new ArrayList<>();
        this.shoppingCart = shoppingCart != null ? shoppingCart : new ShoppingCartDTO(0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
        this.receiverName = receiverName;
    }

    // Getters và Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    public List<UserRegisterDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserRegisterDTO> users) {
        this.users = users != null ? users : new ArrayList<>();
    }

    public ShoppingCartDTO getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartDTO shoppingCart) {
        this.shoppingCart = shoppingCart != null ? shoppingCart : new ShoppingCartDTO(0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}