//package com.example.demo.controller;
//
//import com.example.demo.config.VNPayConfig;
//import com.example.demo.dto.OrderDTO;
//import com.example.demo.dto.UserRegisterDTO;
//import com.example.demo.entity.Order;
//import com.example.demo.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.stream.Collectors;
//
//// Lớp hỗ trợ để trả về orderId
//class OrderIdResponse {
//    private int orderId;
//
//    public OrderIdResponse(int orderId) {
//        this.orderId = orderId;
//    }
//
//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }
//}
//
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/api/orders")
//public class OrderController {
//
//    @Autowired
//    private OrderService orderService;
//
//    @GetMapping("/users")
//    public ResponseEntity<List<UserRegisterDTO>> getAllUsers() {
//        List<UserRegisterDTO> users = orderService.getAllUsersAsDTO();
//        return ResponseEntity.ok(users);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
//        try {
//            int orderId = orderService.createOrderAndGetId(orderDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(new OrderIdResponse(orderId));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<OrderDTO>> getAllOrders() {
//        List<Order> allOrders = orderService.getOrderRepository().findAll();
//        List<OrderDTO> orders = allOrders.stream()
//                .map(orderService::convertToDTO)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(orders);
//    }
//
//    @GetMapping("/{orderId}")
//    public ResponseEntity<?> getOrderById(@PathVariable int orderId) {
//        try {
//            Order order = orderService.getOrderById(orderId);
//            OrderDTO orderDTO = orderService.convertToDTO(order);
//            return ResponseEntity.ok(orderDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(
//            @PathVariable Integer userId,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "15") int size) {
//        List<OrderDTO> orders = orderService.getOrdersByUserId(userId, page, size);
//        return ResponseEntity.ok(orders);
//    }
//
//    @GetMapping("/user/{userId}/total")
//    public ResponseEntity<Long> getTotalOrdersByUserId(@PathVariable Integer userId) {
//        long total = orderService.getTotalOrdersByUserId(userId);
//        return ResponseEntity.ok(total);
//    }
//
//    @PutMapping("/admin/confirm/{orderId}")
//    public ResponseEntity<?> adminConfirmOrder(@PathVariable int orderId) {
//        try {
//            Order confirmedOrder = orderService.confirmOrder(orderId);
//            OrderDTO orderDTO = orderService.convertToDTO(confirmedOrder);
//            return ResponseEntity.ok(orderDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @PutMapping("/admin/shipping/{orderId}")
//    public ResponseEntity<?> adminShippingOrder(@PathVariable int orderId) {
//        try {
//            Order shippingOrder = orderService.shippingOrder(orderId);
//            OrderDTO orderDTO = orderService.convertToDTO(shippingOrder);
//            return ResponseEntity.ok(orderDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @PutMapping("/admin/delivered/{orderId}")
//    public ResponseEntity<?> adminDeliveredOrder(@PathVariable int orderId) {
//        try {
//            Order deliveredOrder = orderService.deliveredOrder(orderId);
//            OrderDTO orderDTO = orderService.convertToDTO(deliveredOrder);
//            return ResponseEntity.ok(orderDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @PutMapping("/admin/cancel/{orderId}")
//    public ResponseEntity<?> adminCancelOrder(@PathVariable int orderId) {
//        try {
//            Order canceledOrder = orderService.adminCancelOrder(orderId);
//            OrderDTO orderDTO = orderService.convertToDTO(canceledOrder);
//            return ResponseEntity.ok(orderDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @PutMapping("/user/receive/{orderId}")
//    public ResponseEntity<?> userReceiveOrder(@PathVariable int orderId) {
//        try {
//            Order receivedOrder = orderService.receiveOrder(orderId);
//            OrderDTO orderDTO = orderService.convertToDTO(receivedOrder);
//            return ResponseEntity.ok(orderDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PutMapping("/user/cancel/{orderId}")
//    public ResponseEntity<?> userCancelOrder(@PathVariable int orderId) {
//        try {
//            Order canceledOrder = orderService.userCancelOrder(orderId);
//            OrderDTO orderDTO = orderService.convertToDTO(canceledOrder);
//            return ResponseEntity.ok(orderDTO);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/vnpay/return")
//    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> params) {
//        try {
//            String vnp_TxnRef = params.get("vnp_TxnRef");
//            String vnp_ResponseCode = params.get("vnp_ResponseCode");
//            String vnp_SecureHash = params.get("vnp_SecureHash");
//
//            if (vnp_TxnRef == null || vnp_ResponseCode == null || vnp_SecureHash == null) {
//                return ResponseEntity.badRequest().body("Thiếu tham số VNPay cần thiết");
//            }
//
//            int orderId;
//            try {
//                orderId = Integer.parseInt(vnp_TxnRef);
//            } catch (NumberFormatException e) {
//                return ResponseEntity.badRequest().body("Mã đơn hàng không hợp lệ");
//            }
//
//            Map<String, String> hashParams = new TreeMap<>(params);
//            hashParams.remove("vnp_SecureHash");
//            StringBuilder hashData = new StringBuilder();
//            for (Map.Entry<String, String> entry : hashParams.entrySet()) {
//                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
//                    hashData.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString()))
//                            .append('=')
//                            .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()))
//                            .append('&');
//                }
//            }
//            if (hashData.length() > 0) {
//                hashData.setLength(hashData.length() - 1);
//            }
//
//            String calculatedHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
//
//            if (!calculatedHash.equalsIgnoreCase(vnp_SecureHash)) {
//                return ResponseEntity.badRequest().body("Chữ ký VNPay không hợp lệ");
//            }
//
//            Order updatedOrder = orderService.handleVNPayCallback(orderId, vnp_ResponseCode);
//            OrderDTO orderDTO = orderService.convertToDTO(updatedOrder);
//            return ResponseEntity.ok(orderDTO);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Lỗi khi xử lý callback VNPay: " + e.getMessage());
//        }
//    }
//}

package com.example.demo.controller;

import com.example.demo.config.VNPayConfig;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Lớp hỗ trợ để trả về orderId
class OrderIdResponse {
    private int orderId;

    public OrderIdResponse(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users")
    public ResponseEntity<List<UserRegisterDTO>> getAllUsers() {
        List<UserRegisterDTO> users = orderService.getAllUsersAsDTO();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            int orderId = orderService.createOrderAndGetId(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OrderIdResponse(orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderDTO> orders = orderService.getAllOrdersAsDTO(page, size);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable int orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(order);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderDTO>> getOrdersByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderDTO> orders = orderService.getOrdersByUserId(userId, page, size);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Long> getTotalOrdersByUserId(@PathVariable Integer userId) {
        long total = orderService.getTotalOrdersByUserId(userId);
        return ResponseEntity.ok(total);
    }

    @PutMapping("/admin/confirm/{orderId}")
    public ResponseEntity<?> adminConfirmOrder(@PathVariable int orderId) {
        try {
            Order confirmedOrder = orderService.confirmOrder(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(confirmedOrder);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/shipping/{orderId}")
    public ResponseEntity<?> adminShippingOrder(@PathVariable int orderId) {
        try {
            Order shippingOrder = orderService.shippingOrder(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(shippingOrder);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/delivered/{orderId}")
    public ResponseEntity<?> adminDeliveredOrder(@PathVariable int orderId) {
        try {
            Order deliveredOrder = orderService.deliveredOrder(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(deliveredOrder);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/cancel/{orderId}")
    public ResponseEntity<?> adminCancelOrder(@PathVariable int orderId) {
        try {
            Order canceledOrder = orderService.adminCancelOrder(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(canceledOrder);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/user/receive/{orderId}")
    public ResponseEntity<?> userReceiveOrder(@PathVariable int orderId) {
        try {
            Order receivedOrder = orderService.receiveOrder(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(receivedOrder);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/user/cancel/{orderId}")
    public ResponseEntity<?> userCancelOrder(@PathVariable int orderId) {
        try {
            Order canceledOrder = orderService.userCancelOrder(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(canceledOrder);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/vnpay/return")
    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> params) {
        try {
            String vnp_TxnRef = params.get("vnp_TxnRef");
            String vnp_ResponseCode = params.get("vnp_ResponseCode");
            String vnp_SecureHash = params.get("vnp_SecureHash");

            if (vnp_TxnRef == null || vnp_ResponseCode == null || vnp_SecureHash == null) {
                return ResponseEntity.badRequest().body("Thiếu tham số VNPay cần thiết");
            }

            int orderId;
            try {
                orderId = Integer.parseInt(vnp_TxnRef);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Mã đơn hàng không hợp lệ");
            }

            Map<String, String> hashParams = new TreeMap<>(params);
            hashParams.remove("vnp_SecureHash");
            StringBuilder hashData = new StringBuilder();
            for (Map.Entry<String, String> entry : hashParams.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    hashData.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString()))
                            .append('=')
                            .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()))
                            .append('&');
                }
            }
            if (hashData.length() > 0) {
                hashData.setLength(hashData.length() - 1);
            }

            String calculatedHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());

            if (!calculatedHash.equalsIgnoreCase(vnp_SecureHash)) {
                return ResponseEntity.badRequest().body("Chữ ký VNPay không hợp lệ");
            }

            Order updatedOrder = orderService.handleVNPayCallback(orderId, vnp_ResponseCode);
            OrderDTO orderDTO = orderService.convertToDTO(updatedOrder);
            return ResponseEntity.ok(orderDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xử lý callback VNPay: " + e.getMessage());
        }
    }
}