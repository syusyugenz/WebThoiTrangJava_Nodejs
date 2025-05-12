//
//
//package com.example.demo.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.dto.ShoppingCartDTO;
//import com.example.demo.entity.Order;
//import com.example.demo.entity.User;
//import com.example.demo.entity.Voucher;
//import com.example.demo.repository.OrderRepository;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.VoucherRepository;
//
//@Service
//public class MultiOrderService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private VoucherRepository voucherRepository;
//
//    // Lưu nhiều đơn hàng (tạo một đơn hàng tổng hợp từ danh sách sản phẩm)
//    public List<Order> saveMultiOrder(ShoppingCartDTO shoppingCartDTO, String shippingAddress, String phoneNumber, 
//                                     String paymentStatus, String content, double shippingFee, 
//                                     Integer voucherId, int userId, double totalAmount) { // Thêm totalAmount vào tham số
//        List<Order> orders = new ArrayList<>();
//
//        // Tạo một đơn hàng tổng hợp
//        Order order = new Order();
//        order.setOrderDate(new Date());
//        order.setStatus("Đặt Hàng");
//        order.setShippingAddress(shippingAddress);
//        order.setPhoneNumber(phoneNumber);
//        order.setPaymentStatus(paymentStatus);
//        order.setContent(content);
//        order.setShippingFee(shippingFee);
//        order.setTotalAmount(Math.max(totalAmount, 0)); // Sử dụng totalAmount từ frontend, đảm bảo không âm
//
//        // Gán voucher nếu có (chỉ lưu thông tin, không tính lại)
//        if (voucherId != null) {
//            Voucher voucher = voucherRepository.findById(voucherId)
//                .orElseThrow(() -> new RuntimeException("Voucher not found with ID: " + voucherId));
//            order.setVoucher(voucher);
//        }
//
//        // Lấy user từ userId
//        User user = userRepository.findByUserId(userId);
//        if (user == null) {
//            throw new RuntimeException("User not found with ID: " + userId);
//        }
//        order.setUser(user);
//
//        // Lưu đơn hàng vào database
//        Order savedOrder = orderRepository.save(order);
//        orders.add(savedOrder);
//
//        return orders;
//    }
//
//    // Hàm tính tổng giá sản phẩm từ ShoppingCartDTO (giữ nguyên để tương thích nếu cần)
//    private double calculateProductTotal(ShoppingCartDTO shoppingCart) {
//        double total = 0;
//        if (shoppingCart.getVariants() != null && shoppingCart.getQuantity() > 0) {
//            int totalVariants = shoppingCart.getVariants().size();
//            if (totalVariants > 0) {
//                double quantityPerVariant = (double) shoppingCart.getQuantity() / totalVariants;
//                total += shoppingCart.getVariants().stream()
//                        .filter(variant -> variant.getPrice() > 0)
//                        .mapToDouble(variant -> variant.getPrice() * quantityPerVariant)
//                        .sum();
//            }
//        }
//        return total;
//    }
//}
//
////package com.example.demo.service;
////
////import java.text.SimpleDateFormat;
////import java.util.ArrayList;
////import java.util.Date;
////import java.util.List;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import com.example.demo.dto.ShoppingCartDTO;
////import com.example.demo.entity.Order;
////import com.example.demo.entity.User;
////import com.example.demo.entity.Voucher;
////import com.example.demo.repository.OrderRepository;
////import com.example.demo.repository.UserRepository;
////import com.example.demo.repository.VoucherRepository;
////import com.example.demo.service.VoucherService; // Thêm import này
////
////@Service
////public class MultiOrderService {
////
////    @Autowired
////    private UserRepository userRepository;
////
////    @Autowired
////    private OrderRepository orderRepository;
////
////    @Autowired
////    private VoucherRepository voucherRepository;
////
////    @Autowired
////    private VoucherService voucherService; // Thêm dependency để kiểm tra voucher
////
////    // Lưu nhiều đơn hàng (tạo một đơn hàng tổng hợp từ danh sách sản phẩm)
////    public List<Order> saveMultiOrder(ShoppingCartDTO shoppingCartDTO, String shippingAddress, String phoneNumber, 
////                                     String paymentStatus, String content, double shippingFee, 
////                                     Integer voucherId, int userId, double totalAmount) { // Thêm totalAmount vào tham số
////        List<Order> orders = new ArrayList<>();
////
////        // Tạo một đơn hàng tổng hợp
////        Order order = new Order();
////        order.setOrderDate(new Date());
////        order.setStatus("Đặt Hàng");
////        order.setShippingAddress(shippingAddress);
////        order.setPhoneNumber(phoneNumber);
////        order.setPaymentStatus(paymentStatus);
////        order.setContent(content);
////        order.setShippingFee(shippingFee);
////        order.setTotalAmount(Math.max(totalAmount, 0)); // Sử dụng totalAmount từ frontend, đảm bảo không âm
////
////        // Kiểm tra và áp dụng voucher (nếu có)
////        if (voucherId != null) {
////            Voucher voucher = voucherRepository.findById(voucherId)
////                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại với ID: " + voucherId));
////
////            Date currentDate = new Date();
////            Date startDate = voucher.getStartDate();
////            Date lateDate = voucher.getLateDate();
////
////            // Kiểm tra chi tiết lý do voucher không hợp lệ
////            if (startDate == null || lateDate == null) {
////                throw new RuntimeException("Voucher không hợp lệ (ngày bắt đầu hoặc ngày kết thúc không được để trống).");
////            }
////
////            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
////            if (currentDate.before(startDate)) {
////                throw new RuntimeException("Không thể sử dụng vì chưa đến ngày: " + dateFormat.format(startDate));
////            }
////            if (currentDate.after(lateDate)) {
////                throw new RuntimeException("Không thể sử dụng vì đã hết hạn: " + dateFormat.format(lateDate));
////            }
////
////            // Nếu hợp lệ, áp dụng voucher (chỉ lưu thông tin, không tính lại trong service này)
////            order.setVoucher(voucher);
////        }
////
////        // Lấy user từ userId
////        User user = userRepository.findByUserId(userId);
////        if (user == null) {
////            throw new RuntimeException("User không tồn tại với ID: " + userId);
////        }
////        order.setUser(user);
////
////        // Lưu đơn hàng vào database
////        Order savedOrder = orderRepository.save(order);
////        orders.add(savedOrder);
////
////        return orders;
////    }
////
////    // Hàm tính tổng giá sản phẩm từ ShoppingCartDTO (giữ nguyên để tương thích nếu cần)
////    private double calculateProductTotal(ShoppingCartDTO shoppingCart) {
////        double total = 0;
////        if (shoppingCart.getVariants() != null && shoppingCart.getQuantity() > 0) {
////            int totalVariants = shoppingCart.getVariants().size();
////            if (totalVariants > 0) {
////                double quantityPerVariant = (double) shoppingCart.getQuantity() / totalVariants;
////                total += shoppingCart.getVariants().stream()
////                        .filter(variant -> variant.getPrice() > 0)
////                        .mapToDouble(variant -> variant.getPrice() * quantityPerVariant)
////                        .sum();
////            }
////        }
////        return total;
////    }
////}

package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ShoppingCartDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.entity.Voucher;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VoucherRepository;

@Service
public class MultiOrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    // Lưu nhiều đơn hàng (tạo một đơn hàng tổng hợp từ danh sách sản phẩm)
    public List<Order> saveMultiOrder(ShoppingCartDTO shoppingCartDTO, String shippingAddress, String phoneNumber, 
                                     String paymentStatus, String content, double shippingFee, 
                                     Integer voucherId, int userId, double totalAmount) { // totalAmount từ frontend sẽ bị bỏ qua
        List<Order> orders = new ArrayList<>();

        // Tạo một đơn hàng tổng hợp
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus("Đặt Hàng");
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setPaymentStatus(paymentStatus);
        order.setContent(content);
        order.setShippingFee(shippingFee);

        // Tính tổng tiền sản phẩm
        double productTotal = calculateProductTotal(shoppingCartDTO);
        double finalTotal = productTotal + shippingFee;

        // Gán và kiểm tra voucher nếu có
        if (voucherId != null) {
            Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Voucher not found with ID: " + voucherId));

            Date currentDate = new Date();
            Date startDate = voucher.getStartDate();
            Date lateDate = voucher.getLateDate();

            if (startDate == null || lateDate == null) {
                throw new RuntimeException("Voucher không hợp lệ (ngày bắt đầu hoặc ngày kết thúc không được để trống).");
            }

            if (currentDate.before(startDate)) {
                throw new RuntimeException("Không thể sử dụng vì chưa đến ngày: " + startDate);
            }
            if (currentDate.after(lateDate)) {
                throw new RuntimeException("Không thể sử dụng vì đã hết hạn: " + lateDate);
            }

            if (productTotal < voucher.getMinimumMoneyValue()) {
                throw new RuntimeException("Không thể sử dụng vì giá trị đơn hàng phải tối thiểu " + voucher.getMinimumMoneyValue() + " VNĐ.");
            }

            order.setVoucher(voucher);
            finalTotal = Math.max(finalTotal - voucher.getMaxDiscountValue(), 0); // Áp dụng giảm giá
        }

        order.setTotalAmount(finalTotal); // Gán totalAmount đã tính toán

        // Lấy user từ userId
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        order.setUser(user);

        // Lưu đơn hàng vào database
        Order savedOrder = orderRepository.save(order);
        orders.add(savedOrder);

        return orders;
    }

    // Hàm tính tổng giá sản phẩm từ ShoppingCartDTO
    private double calculateProductTotal(ShoppingCartDTO shoppingCart) {
        double total = 0;
        if (shoppingCart.getVariants() != null && shoppingCart.getQuantity() > 0) {
            int totalVariants = shoppingCart.getVariants().size();
            if (totalVariants > 0) {
                double quantityPerVariant = (double) shoppingCart.getQuantity() / totalVariants;
                total += shoppingCart.getVariants().stream()
                        .filter(variant -> variant.getPrice() > 0)
                        .mapToDouble(variant -> variant.getPrice() * quantityPerVariant)
                        .sum();
            }
        }
        return total;
    }
}