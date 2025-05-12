package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "*")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    // Lưu hoặc cập nhật một OrderItem
    @PostMapping
    public ResponseEntity<OrderItemDTO> saveOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        try {
            OrderItemDTO savedOrderItemDTO = orderItemService.saveOrderItem(orderItemDTO);
            return new ResponseEntity<>(savedOrderItemDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lưu nhiều OrderItems với các Variants trong cùng một Order
    @PostMapping("/multiple")
    public ResponseEntity<List<OrderItemDTO>> saveMultipleOrderItems(@RequestBody OrderItemDTO orderItemDTO) {
        try {
            List<OrderItemDTO> savedOrderItems = orderItemService.save2(orderItemDTO);
            return new ResponseEntity<>(savedOrderItems, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy tất cả OrderItems
    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
        try {
            List<OrderItemDTO> orderItems = orderItemService.getAllOrderItems();
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy OrderItem theo ID
    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable int orderItemId) {
        try {
            OrderItemDTO orderItem = orderItemService.getOrderItemById(orderItemId);
            return new ResponseEntity<>(orderItem, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy danh sách OrderItems theo Order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsByOrderId(@PathVariable int orderId) {
        try {
            List<OrderItemDTO> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy danh sách OrderItems theo danh sách Order IDs
    @PostMapping("/orders")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsByOrderIds(@RequestBody List<Integer> orderIds) {
        try {
            List<OrderItemDTO> orderItems = orderItemService.getOrderItemsByOrderIds(orderIds);
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy danh sách OrderItems theo User ID
    @GetMapping("/user/{userId}/items")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsByUserId(@PathVariable int userId) {
        try {
            List<OrderItemDTO> orderItems = orderItemService.getOrderItemsByUserId(userId);
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Xóa OrderItem theo ID
    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable int orderItemId) {
        try {
            orderItemService.deleteOrderItem(orderItemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy tất cả Variants
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> getAllVariants() {
        try {
            List<VariantDTO> variants = orderItemService.getAllVariants();
            return new ResponseEntity<>(variants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy tất cả Orders
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderItemService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy danh sách Orders theo User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable int userId) {
        try {
            List<OrderDTO> orders = orderItemService.getOrdersByUserId(userId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}