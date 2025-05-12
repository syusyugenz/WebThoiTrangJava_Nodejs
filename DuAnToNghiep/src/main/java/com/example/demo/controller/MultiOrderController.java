//
//
//package com.example.demo.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.dto.ShoppingCartDTO;
//import com.example.demo.entity.Order;
//import com.example.demo.service.MultiOrderService;
//
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/api/multi-orders")
//public class MultiOrderController {
//
//    @Autowired
//    private MultiOrderService multiOrderService;
//
//    @PostMapping
//    public ResponseEntity<List<Order>> saveMultiOrder(@RequestBody MultiOrderRequest request) {
//        try {
//            List<Order> savedOrders = multiOrderService.saveMultiOrder(
//                request.getShoppingCartDTO(),
//                request.getShippingAddress(),
//                request.getPhoneNumber(),
//                request.getPaymentStatus(),
//                request.getContent(),
//                request.getShippingFee(),
//                request.getVoucherId(),
//                request.getUserId(),
//                request.getTotalAmount() // Truyền thêm totalAmount
//            );
//            return new ResponseEntity<>(savedOrders, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//}
//
//// DTO để nhận dữ liệu từ body
//class MultiOrderRequest {
//    private ShoppingCartDTO shoppingCartDTO;
//    private String shippingAddress;
//    private String phoneNumber;
//    private String paymentStatus;
//    private String content;
//    private double shippingFee;
//    private Integer voucherId; // Giữ nguyên là Integer để có thể null
//    private int userId;
//    private double totalAmount; // Thêm trường totalAmount
//
//    // Getters and Setters
//    public ShoppingCartDTO getShoppingCartDTO() {
//        return shoppingCartDTO;
//    }
//
//    public void setShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
//        this.shoppingCartDTO = shoppingCartDTO;
//    }
//
//    public String getShippingAddress() {
//        return shippingAddress;
//    }
//
//    public void setShippingAddress(String shippingAddress) {
//        this.shippingAddress = shippingAddress;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getPaymentStatus() {
//        return paymentStatus;
//    }
//
//    public void setPaymentStatus(String paymentStatus) {
//        this.paymentStatus = paymentStatus;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public double getShippingFee() {
//        return shippingFee;
//    }
//
//    public void setShippingFee(double shippingFee) {
//        this.shippingFee = shippingFee;
//    }
//
//    public Integer getVoucherId() {
//        return voucherId;
//    }
//
//    public void setVoucherId(Integer voucherId) {
//        this.voucherId = voucherId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//}

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ShoppingCartDTO;
import com.example.demo.entity.Order;
import com.example.demo.service.MultiOrderService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/multi-orders")
public class MultiOrderController {

    @Autowired
    private MultiOrderService multiOrderService;

    @PostMapping
    public ResponseEntity<?> saveMultiOrder(@RequestBody MultiOrderRequest request) {
        try {
            List<Order> savedOrders = multiOrderService.saveMultiOrder(
                request.getShoppingCartDTO(),
                request.getShippingAddress(),
                request.getPhoneNumber(),
                request.getPaymentStatus(),
                request.getContent(),
                request.getShippingFee(),
                request.getVoucherId(),
                request.getUserId(),
                request.getTotalAmount() // Truyền thêm totalAmount
            );
            return new ResponseEntity<>(savedOrders, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

// DTO để nhận dữ liệu từ body (không cần thay đổi)
class MultiOrderRequest {
    private ShoppingCartDTO shoppingCartDTO;
    private String shippingAddress;
    private String phoneNumber;
    private String paymentStatus;
    private String content;
    private double shippingFee;
    private Integer voucherId; // Giữ nguyên là Integer để có thể null
    private int userId;
    private double totalAmount; // Thêm trường totalAmount

    // Getters and Setters (giữ nguyên)
    public ShoppingCartDTO getShoppingCartDTO() {
        return shoppingCartDTO;
    }

    public void setShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
        this.shoppingCartDTO = shoppingCartDTO;
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

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}