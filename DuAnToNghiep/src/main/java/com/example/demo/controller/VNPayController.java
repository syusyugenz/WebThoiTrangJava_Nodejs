//
//package com.example.demo.controller;
//
//import com.example.demo.config.VNPayConfig;
//import com.example.demo.entity.Order;
//import com.example.demo.repository.OrderRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@RestController
//@CrossOrigin(value = {"*"})
//@RequestMapping("/api/vnpay")
//public class VNPayController {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @PostMapping("/create-payment")
//    public ResponseEntity<Map<String, String>> createPayment(HttpServletRequest req) throws UnsupportedEncodingException {
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", "2.1.0");
//        vnp_Params.put("vnp_Command", "pay");
//        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
//
//        int amount = Integer.parseInt(req.getParameter("amount")) * 100; // Số tiền từ frontend (totalAmount)
//        String orderId = req.getParameter("orderId"); // Lấy orderId từ frontend
//        String orderInfo = req.getParameter("orderInfo"); // Nội dung từ frontend
//
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_TxnRef", orderId); // Sử dụng orderId làm vnp_TxnRef
//        vnp_Params.put("vnp_OrderInfo", orderInfo);
//        vnp_Params.put("vnp_OrderType", req.getParameter("orderType"));
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
//        vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress(req));
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
//        cld.add(Calendar.MINUTE, 15);
//        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));
//
//        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//
//        for (String fieldName : fieldNames) {
//            String fieldValue = vnp_Params.get(fieldName);
//            if (fieldValue != null && !fieldValue.isEmpty()) {
//                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
//                     .append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
//                    query.append('&');
//                    hashData.append('&');
//                }
//            }
//        }
//
//        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
//        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;
//
//        Map<String, String> response = new HashMap<>();
//        response.put("code", "00");
//        response.put("message", "success");
//        response.put("data", paymentUrl);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/return")
//    public ResponseEntity<String> paymentReturn(HttpServletRequest req) throws UnsupportedEncodingException {
//        Map<String, String> fields = new HashMap<>();
//        for (Enumeration<String> params = req.getParameterNames(); params.hasMoreElements();) {
//            String fieldName = params.nextElement();
//            String fieldValue = req.getParameter(fieldName);
//            if (fieldValue != null && !fieldValue.isEmpty()) {
//                fields.put(fieldName, fieldValue);
//            }
//        }
//
//        String vnp_SecureHash = req.getParameter("vnp_SecureHash");
//        fields.remove("vnp_SecureHashType");
//        fields.remove("vnp_SecureHash");
//
//        String signValue = hashAllFields(fields);
//        if (signValue.equals(vnp_SecureHash)) {
//            String responseCode = req.getParameter("vnp_ResponseCode");
//            String orderId = req.getParameter("vnp_TxnRef"); // Lấy orderId từ vnp_TxnRef
//
//            Optional<Order> orderOpt = orderRepository.findById(Integer.parseInt(orderId));
//            if (!orderOpt.isPresent()) {
//                return ResponseEntity.badRequest().body("Không tìm thấy đơn hàng với ID: " + orderId);
//            }
//
//            Order order = orderOpt.get();
//            if ("00".equals(responseCode)) {
//                // Thanh toán thành công
//                order.setPaymentStatus("2"); // "2" = Thanh toán thành công
//                orderRepository.save(order);
//                return ResponseEntity.ok("Giao dịch thành công");
//            } else {
//                // Thanh toán thất bại
//                order.setPaymentStatus("3"); // "3" = Thanh toán thất bại
//                order.setStatus("2"); // "2" = Hủy Đơn
//                orderRepository.save(order);
//                return ResponseEntity.ok("Giao dịch không thành công");
//            }
//        } else {
//            return ResponseEntity.badRequest().body("Chữ ký không hợp lệ");
//        }
//    }
//
//    private String hashAllFields(Map<String, String> fields) throws UnsupportedEncodingException {
//        List<String> fieldNames = new ArrayList<>(fields.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder sb = new StringBuilder();
//        for (String fieldName : fieldNames) {
//            String fieldValue = fields.get(fieldName);
//            if (fieldValue != null && !fieldValue.isEmpty()) {
//                sb.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
//                    sb.append('&');
//                }
//            }
//        }
//        return VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, sb.toString());
//    }
//}

package com.example.demo.controller;

import com.example.demo.config.VNPayConfig;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(value = {"*"})
@RequestMapping("/api/vnpay")
public class VNPayController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create-payment")
    public ResponseEntity<Map<String, String>> createPayment(HttpServletRequest req) throws UnsupportedEncodingException {
        System.out.println("orderId nhận được: " + req.getParameter("orderId"));
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);

        int amount = Integer.parseInt(req.getParameter("amount")) * 100;
        String orderId = req.getParameter("orderId");
        String orderInfo = req.getParameter("orderInfo");

        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderId);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", req.getParameter("orderType"));
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress(req));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                     .append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;

        Map<String, String> response = new HashMap<>();
        response.put("code", "00");
        response.put("message", "success");
        response.put("data", paymentUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ipn")
    public ResponseEntity<String> ipnCallback(HttpServletRequest req) throws UnsupportedEncodingException {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = req.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = req.getParameter(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = req.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        String signValue = hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            String responseCode = req.getParameter("vnp_ResponseCode");
            String orderId = req.getParameter("vnp_TxnRef");

            Optional<Order> orderOpt = orderRepository.findById(Integer.parseInt(orderId));
            if (!orderOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Không tìm thấy đơn hàng với ID: " + orderId);
            }

            Order order = orderOpt.get();
            if ("00".equals(responseCode)) {
                order.setPaymentStatus("2");
                orderRepository.save(order);
                return ResponseEntity.ok("Giao dịch thành công");
            } else {
                order.setPaymentStatus("3");
                order.setStatus("2");
                orderRepository.save(order);
                return ResponseEntity.ok("Giao dịch không thành công");
            }
        } else {
            return ResponseEntity.badRequest().body("Chữ ký không hợp lệ");
        }
    }

    private String hashAllFields(Map<String, String> fields) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                sb.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    sb.append('&');
                }
            }
        }
        return VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, sb.toString());
    }
}