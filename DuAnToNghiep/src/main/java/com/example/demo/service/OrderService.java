////package com.example.demo.service;
////
////import com.example.demo.dto.*;
////import com.example.demo.entity.*;
////import com.example.demo.repository.OrderRepository;
////import com.example.demo.repository.UserRepository;
////import com.example.demo.repository.VoucherRepository;
////import com.example.demo.util.VoucherMapper;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.PageRequest;
////import org.springframework.data.domain.Pageable;
////import org.springframework.mail.SimpleMailMessage;
////import org.springframework.mail.javamail.JavaMailSender;
////import org.springframework.stereotype.Service;
////
////import java.text.SimpleDateFormat;
////import java.util.ArrayList;
////import java.util.Date;
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Service
////public class OrderService {
////
////    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
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
////    private OrderItemService orderItemService;
////
////    @Autowired
////    private JavaMailSender mailSender;
////
////    public OrderRepository getOrderRepository() {
////        return orderRepository;
////    }
////
////    public int createOrderAndGetId(OrderDTO orderDTO) {
////        Order order = createOrderFromDTO(orderDTO);
////        Order savedOrder = orderRepository.save(order);
////        return savedOrder.getOrderId();
////    }
////
////    public Order saveOrder(OrderDTO orderDTO) {
////        Order order = createOrderFromDTO(orderDTO);
////        return orderRepository.save(order);
////    }
////
////    private Order createOrderFromDTO(OrderDTO orderDTO) {
////        Order order = new Order();
////        order.setOrderDate(orderDTO.getOrderDate());
////        order.setStatus("1");
////        order.setReceiverName(orderDTO.getReceiverName());
////        order.setPhoneNumber(orderDTO.getPhoneNumber());
////        order.setShippingAddress(orderDTO.getShippingAddress());
////
////        String paymentTypeId = orderDTO.getPaymentStatus();
////        if ("2".equals(paymentTypeId)) {
////            order.setPaymentStatus("1"); // Thanh toán khi nhận hàng
////        } else if ("1".equals(paymentTypeId)) {
////            order.setPaymentStatus("2"); // VNPay
////        } else {
////            throw new RuntimeException("Invalid payment type");
////        }
////        order.setContent(orderDTO.getContent());
////        order.setShippingFee(orderDTO.getShippingFee());
////
////        double totalAmount = orderDTO.getTotalAmount();
////
////        if (orderDTO.getVoucher() != null) {
////            VoucherDTO voucherDTO = orderDTO.getVoucher();
////            Voucher voucher = voucherRepository.findById(voucherDTO.getVoucherId())
////                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại với ID: " + voucherDTO.getVoucherId()));
////
////            Date currentDate = new Date();
////            Date startDate = voucher.getStartDate();
////            Date lateDate = voucher.getLateDate();
////
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
////            double productTotal = calculateProductTotal(orderDTO.getShoppingCart());
////            if (productTotal < voucher.getMinimumMoneyValue()) {
////                throw new RuntimeException("Không thể sử dụng vì giá trị đơn hàng phải tối thiểu " + voucher.getMinimumMoneyValue() + " VNĐ.");
////            }
////
////            order.setVoucher(voucher);
////        }
////
////        order.setTotalAmount(Math.max(totalAmount, 0));
////
////        if (orderDTO.getUsers() == null || orderDTO.getUsers().isEmpty()) {
////            throw new RuntimeException("No users provided");
////        }
////        UserRegisterDTO userDto = orderDTO.getUsers().get(0);
////        User user = userRepository.findByUserId(userDto.getUserId());
////        if (user == null) {
////            throw new RuntimeException("User không tồn tại");
////        }
////        order.setUser(user);
////
////        return order;
////    }
////
////    private double calculateProductTotal(ShoppingCartDTO shoppingCart) {
////        if (shoppingCart == null || shoppingCart.getVariants() == null) {
////            return 0;
////        }
////        double total = shoppingCart.getVariants().stream()
////                .filter(variant -> variant.getPrice() > 0)
////                .mapToDouble(VariantDTO::getPrice)
////                .sum();
////        return total * shoppingCart.getQuantity();
////    }
////
////    public List<UserRegisterDTO> getAllUsersAsDTO() {
////        List<User> users = userRepository.findAll();
////        return users.stream()
////                .map(this::convertToDTO)
////                .collect(Collectors.toList());
////    }
////
////    private UserRegisterDTO convertToDTO(User user) {
////        return new UserRegisterDTO(
////            user.getUserId(),
////            user.getUsername(),
////            user.getPassword(),
////            user.getEmail(),
////            user.getRole(),
////            user.getFirstName(),
////            user.getLastName(),
////            user.getPhone(),
////            user.getPoint()
////        );
////    }
////
////    public List<OrderDTO> getAllOrdersAsDTO(int page, int size) {
////        Pageable pageable = PageRequest.of(page - 1, size);
////        Page<Order> orderPage = orderRepository.findAll(pageable);
////        List<Order> orders = orderPage.getContent();
////
////        return orders.stream()
////                .map(this::convertToDTO)
////                .collect(Collectors.toList());
////    }
////
////    public OrderDTO convertToDTO(Order order) {
////        List<UserRegisterDTO> users = new ArrayList<>();
////        if (order.getUser() != null) {
////            users.add(convertToUserRegisterDTO(order.getUser()));
////        }
////
////        ShoppingCartDTO shoppingCart = new ShoppingCartDTO(
////            0, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
////        );
////
////        VoucherDTO voucherDTO = null;
////        if (order.getVoucher() != null) {
////            voucherDTO = VoucherMapper.toDto(order.getVoucher());
////        }
////
////        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
////        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
////            orderItemDTOs = order.getOrderItems().stream()
////                .map(orderItem -> orderItemService.convertToDTO(orderItem))
////                .collect(Collectors.toList());
////        }
////
////        return new OrderDTO(
////            order.getOrderId(),
////            order.getOrderDate(),
////            order.getStatus(),
////            order.getShippingAddress(),
////            order.getPhoneNumber(),
////            order.getPaymentStatus(),
////            order.getContent(),
////            order.getShippingFee(),
////            order.getTotalAmount(),
////            voucherDTO,
////            users,
////            shoppingCart,
////            orderItemDTOs,
////            order.getReceiverName()
////        );
////    }
////
////    private UserRegisterDTO convertToUserRegisterDTO(User user) {
////        return new UserRegisterDTO(
////            user.getUserId(),
////            user.getUsername(),
////            null,
////            user.getEmail(),
////            user.getRole(),
////            user.getFirstName(),
////            user.getLastName(),
////            user.getPhone(),
////            user.getPoint()
////        );
////    }
////
////    public Order getOrderById(int orderId) {
////        return orderRepository.findById(orderId)
////                .orElseThrow(() -> new RuntimeException("Order not found"));
////    }
////
////    public Order cancelOrder(int orderId) {
////        Order order = getOrderById(orderId);
////        if ("2".equals(order.getStatus())) {
////            throw new RuntimeException("Cannot cancel an already canceled order");
////        }
////        if ("6".equals(order.getStatus())) {
////            throw new RuntimeException("Cannot cancel a delivered order");
////        }
////        order.setStatus("2");
////        return orderRepository.save(order);
////    }
////
////    public Order userCancelOrder(int orderId) {
////        Order order = getOrderById(orderId);
////        if ("2".equals(order.getStatus())) {
////            throw new RuntimeException("Không thể hủy đơn hàng đã bị hủy");
////        }
////        if (!("1".equals(order.getStatus()) || "3".equals(order.getStatus()))) {
////            throw new RuntimeException("Không thể hủy đơn hàng: Đơn hàng đã được xác nhận hoặc xử lý");
////        }
////
////        Date orderDate = order.getOrderDate();
////        Date currentDate = new Date();
////        long timeDifference = currentDate.getTime() - orderDate.getTime();
////        long oneHourInMs = 1 * 60 * 60 * 1000; // 1 tiếng
////
////        if (timeDifference > oneHourInMs) {
////            throw new RuntimeException("Không thể hủy đơn hàng: Thời gian hủy (1 giờ) đã hết");
////        }
////
////        if ("3".equals(order.getStatus()) && order.getUser() != null && order.getUser().getEmail() != null) {
////            sendCancellationEmail(order.getUser().getEmail(), order.getOrderId());
////        }
////
////        order.setStatus("2");
////        return orderRepository.save(order);
////    }
////
////    public Order confirmOrder(int orderId) {
////        Order order = getById(orderId);
////        if ("1".equals(order.getStatus()) || "3".equals(order.getStatus())) {
////            order.setStatus("4");
////            return orderRepository.save(order);
////        }
////        throw new RuntimeException("Cannot confirm order: Invalid status");
////    }
////
////    public Order shippingOrder(int orderId) {
////        Order order = getById(orderId);
////        if ("4".equals(order.getStatus())) {
////            order.setStatus("5");
////            return orderRepository.save(order);
////        }
////        throw new RuntimeException("Cannot update to shipping: Order not confirmed");
////    }
////
////    public Order deliveredOrder(int orderId) {
////        Order order = getById(orderId);
////        if ("5".equals(order.getStatus())) {
////            order.setStatus("6");
////            return orderRepository.save(order);
////        }
////        throw new RuntimeException("Cannot update to delivered: Order not in shipping");
////    }
////
////    public Order receiveOrder(int orderId) {
////        Order order = getById(orderId);
////        if ("6".equals(order.getStatus())) {
////            order.setStatus("7");
////            return orderRepository.save(order);
////        }
////        throw new RuntimeException("Cannot confirm receipt: Order not delivered");
////    }
////
////    public Order adminCancelOrder(int orderId) {
////        Order order = getOrderById(orderId);
////        if ("2".equals(order.getStatus())) {
////            throw new RuntimeException("Không thể hủy đơn hàng đã bị hủy");
////        }
////        if ("5".equals(order.getStatus())) {
////            throw new RuntimeException("Không thể hủy đơn hàng đang vận chuyển");
////        }
////        if ("6".equals(order.getStatus())) {
////            throw new RuntimeException("Không thể hủy đơn hàng đã giao");
////        }
////        if ("7".equals(order.getStatus())) {
////            throw new RuntimeException("Không thể hủy đơn hàng đã nhận");
////        }
////
////        if ("3".equals(order.getStatus()) && order.getUser() != null && order.getUser().getEmail() != null) {
////            sendCancellationEmail(order.getUser().getEmail(), order.getOrderId());
////        }
////
////        order.setStatus("2");
////        return orderRepository.save(order);
////    }
////
////    private void sendCancellationEmail(String to, int orderId) {
////        try {
////            SimpleMailMessage message = new SimpleMailMessage();
////            message.setTo(to);
////            message.setSubject("Đơn Hàng Của Bạn Đã Bị Hủy");
////            message.setText(
////                "Kính gửi Quý khách,\n\n" +
////                "Đơn hàng #" + orderId + " của bạn đã bị hủy.\n" +
////                "Vui lòng liên hệ admin qua số điện thoại: 0932805261 để nhận lại số tiền được hoàn trả khi đã thanh toán trước.\n\n" +
////                "Trân trọng,\n" +
////                "Đội ngũ hỗ trợ"
////            );
////            mailSender.send(message);
////            logger.info("Email thông báo hủy đơn hàng #{} đã được gửi đến {}", orderId, to);
////        } catch (Exception e) {
////            logger.error("Lỗi khi gửi email thông báo hủy đơn hàng #{}: {}", orderId, e.getMessage());
////            throw new RuntimeException("Không thể gửi email thông báo hủy đơn hàng: " + e.getMessage());
////        }
////    }
////
////    public List<OrderDTO> getOrdersByUserId(Integer userId, int page, int size) {
////        List<Order> allOrders = orderRepository.findByUser_UserId(userId);
////
////        int start = (page - 1) * size;
////        int end = Math.min(start + size, allOrders.size());
////
////        if (start >= allOrders.size()) {
////            return new ArrayList<>();
////        }
////
////        List<Order> orders = allOrders.subList(start, end);
////        return orders.stream()
////                .map(this::convertToDTO)
////                .collect(Collectors.toList());
////    }
////
////    private Order getById(int orderId) {
////        return orderRepository.findById(orderId)
////                .orElseThrow(() -> new RuntimeException("Order not found"));
////    }
////
////    public List<VoucherDTO> getValidVouchers() {
////        List<Voucher> allVouchers = voucherRepository.findAll();
////        Date currentDate = new Date();
////
////        return allVouchers.stream()
////                .filter(voucher -> {
////                    Date startDate = voucher.getStartDate();
////                    Date lateDate = voucher.getLateDate();
////                    return startDate != null && lateDate != null &&
////                           !currentDate.before(startDate) && !currentDate.after(lateDate);
////                })
////                .map(VoucherMapper::toDto)
////                .collect(Collectors.toList());
////    }
////
////    public long getTotalOrdersByUserId(Integer userId) {
////        return orderRepository.findByUser_UserId(userId).size();
////    }
////
////    public Order handleVNPayCallback(int orderId, String vnpResponseCode) {
////        Order order = getOrderById(orderId);
////        if (!"2".equals(order.getPaymentStatus())) {
////            throw new RuntimeException("Order is not in VNPay payment mode");
////        }
////
////        if ("3".equals(order.getStatus())) {
////            if ("00".equals(vnpResponseCode)) {
////                return order;
////            } else {
////                throw new RuntimeException("Cannot update order: Already processed as paid");
////            }
////        }
////
////        if ("00".equals(vnpResponseCode)) {
////            order.setStatus("3");
////        } else {
////            order.setStatus("2");
////        }
////
////        return orderRepository.save(order);
////    }
////
////    public static String mapPaymentStatus(String paymentStatus) {
////        switch (paymentStatus) {
////            case "1": return "Thanh toán khi nhận hàng";
////            case "2": return "VNPay";
////            default: return paymentStatus;
////        }
////    }
////}
//
//package com.example.demo.service;
//
//import com.example.demo.dto.*;
//import com.example.demo.entity.*;
//import com.example.demo.repository.OrderRepository;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.VoucherRepository;
//import com.example.demo.util.VoucherMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderService {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
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
//    @Autowired
//    private OrderItemService orderItemService;
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public OrderRepository getOrderRepository() {
//        return orderRepository;
//    }
//
//    public int createOrderAndGetId(OrderDTO orderDTO) {
//        Order order = createOrderFromDTO(orderDTO);
//        Order savedOrder = orderRepository.save(order);
//        return savedOrder.getOrderId();
//    }
//
//    public Order saveOrder(OrderDTO orderDTO) {
//        Order order = createOrderFromDTO(orderDTO);
//        return orderRepository.save(order);
//    }
//
//    private Order createOrderFromDTO(OrderDTO orderDTO) {
//        Order order = new Order();
//        order.setOrderDate(orderDTO.getOrderDate());
//        order.setStatus("1");
//        order.setReceiverName(orderDTO.getReceiverName());
//        order.setPhoneNumber(orderDTO.getPhoneNumber());
//        order.setShippingAddress(orderDTO.getShippingAddress());
//
//        String paymentTypeId = orderDTO.getPaymentStatus();
//        if ("2".equals(paymentTypeId)) {
//            order.setPaymentStatus("1"); // Thanh toán khi nhận hàng
//        } else if ("1".equals(paymentTypeId)) {
//            order.setPaymentStatus("2"); // VNPay
//        } else {
//            throw new RuntimeException("Invalid payment type");
//        }
//        order.setContent(orderDTO.getContent());
//        order.setShippingFee(orderDTO.getShippingFee());
//
//        double totalAmount = orderDTO.getTotalAmount();
//
//        if (orderDTO.getVoucher() != null) {
//            VoucherDTO voucherDTO = orderDTO.getVoucher();
//            Voucher voucher = voucherRepository.findById(voucherDTO.getVoucherId())
//                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại với ID: " + voucherDTO.getVoucherId()));
//
//            Date currentDate = new Date();
//            Date startDate = voucher.getStartDate();
//            Date lateDate = voucher.getLateDate();
//
//            if (startDate == null || lateDate == null) {
//                throw new RuntimeException("Voucher không hợp lệ (ngày bắt đầu hoặc ngày kết thúc không được để trống).");
//            }
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            if (currentDate.before(startDate)) {
//                throw new RuntimeException("Không thể sử dụng vì chưa đến ngày: " + dateFormat.format(startDate));
//            }
//            if (currentDate.after(lateDate)) {
//                throw new RuntimeException("Không thể sử dụng vì đã hết hạn: " + dateFormat.format(lateDate));
//            }
//
//            double productTotal = calculateProductTotal(orderDTO.getShoppingCart());
//            if (productTotal < voucher.getMinimumMoneyValue()) {
//                throw new RuntimeException("Không thể sử dụng vì giá trị đơn hàng phải tối thiểu " + voucher.getMinimumMoneyValue() + " VNĐ.");
//            }
//
//            order.setVoucher(voucher);
//        }
//
//        order.setTotalAmount(Math.max(totalAmount, 0));
//
//        if (orderDTO.getUsers() == null || orderDTO.getUsers().isEmpty()) {
//            throw new RuntimeException("No users provided");
//        }
//        UserRegisterDTO userDto = orderDTO.getUsers().get(0);
//        User user = userRepository.findByUserId(userDto.getUserId());
//        if (user == null) {
//            throw new RuntimeException("User không tồn tại");
//        }
//        order.setUser(user);
//
//        return order;
//    }
//
//    private double calculateProductTotal(ShoppingCartDTO shoppingCart) {
//        if (shoppingCart == null || shoppingCart.getVariants() == null) {
//            return 0;
//        }
//        double total = shoppingCart.getVariants().stream()
//                .filter(variant -> variant.getPrice() > 0)
//                .mapToDouble(VariantDTO::getPrice)
//                .sum();
//        return total * shoppingCart.getQuantity();
//    }
//
//    public List<UserRegisterDTO> getAllUsersAsDTO() {
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private UserRegisterDTO convertToDTO(User user) {
//        return new UserRegisterDTO(
//            user.getUserId(),
//            user.getUsername(),
//            user.getPassword(),
//            user.getEmail(),
//            user.getRole(),
//            user.getFirstName(),
//            user.getLastName(),
//            user.getPhone(),
//            user.getPoint()
//        );
//    }
//
//    public Page<OrderDTO> getAllOrdersAsDTO(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());
//        Page<Order> orderPage = orderRepository.findAll(pageable);
//        return orderPage.map(this::convertToDTO);
//    }
//
//    public OrderDTO convertToDTO(Order order) {
//        List<UserRegisterDTO> users = new ArrayList<>();
//        if (order.getUser() != null) {
//            users.add(convertToUserRegisterDTO(order.getUser()));
//        }
//
//        ShoppingCartDTO shoppingCart = new ShoppingCartDTO(
//            0, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
//        );
//
//        VoucherDTO voucherDTO = null;
//        if (order.getVoucher() != null) {
//            voucherDTO = VoucherMapper.toDto(order.getVoucher());
//        }
//
//        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
//        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
//            orderItemDTOs = order.getOrderItems().stream()
//                .map(orderItem -> orderItemService.convertToDTO(orderItem))
//                .collect(Collectors.toList());
//        }
//
//        return new OrderDTO(
//            order.getOrderId(),
//            order.getOrderDate(),
//            order.getStatus(),
//            order.getShippingAddress(),
//            order.getPhoneNumber(),
//            order.getPaymentStatus(),
//            order.getContent(),
//            order.getShippingFee(),
//            order.getTotalAmount(),
//            voucherDTO,
//            users,
//            shoppingCart,
//            orderItemDTOs,
//            order.getReceiverName()
//        );
//    }
//
//    private UserRegisterDTO convertToUserRegisterDTO(User user) {
//        return new UserRegisterDTO(
//            user.getUserId(),
//            user.getUsername(),
//            null,
//            user.getEmail(),
//            user.getRole(),
//            user.getFirstName(),
//            user.getLastName(),
//            user.getPhone(),
//            user.getPoint()
//        );
//    }
//
//    public Order getOrderById(int orderId) {
//        return orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//    }
//
//    public Order cancelOrder(int orderId) {
//        Order order = getOrderById(orderId);
//        if ("2".equals(order.getStatus())) {
//            throw new RuntimeException("Cannot cancel an already canceled order");
//        }
//        if ("6".equals(order.getStatus())) {
//            throw new RuntimeException("Cannot cancel a delivered order");
//        }
//        order.setStatus("2");
//        return orderRepository.save(order);
//    }
//
//    public Order userCancelOrder(int orderId) {
//        Order order = getOrderById(orderId);
//        if ("2".equals(order.getStatus())) {
//            throw new RuntimeException("Không thể hủy đơn hàng đã bị hủy");
//        }
//        if (!("1".equals(order.getStatus()) || "3".equals(order.getStatus()))) {
//            throw new RuntimeException("Không thể hủy đơn hàng: Đơn hàng đã được xác nhận hoặc xử lý");
//        }
//
//        Date orderDate = order.getOrderDate();
//        Date currentDate = new Date();
//        long timeDifference = currentDate.getTime() - orderDate.getTime();
//        long oneHourInMs = 1 * 60 * 60 * 1000; // 1 tiếng
//
//        if (timeDifference > oneHourInMs) {
//            throw new RuntimeException("Không thể hủy đơn hàng: Thời gian hủy (1 giờ) đã hết");
//        }
//
//        if ("3".equals(order.getStatus()) && order.getUser() != null && order.getUser().getEmail() != null) {
//            sendCancellationEmail(order.getUser().getEmail(), order.getOrderId());
//        }
//
//        order.setStatus("2");
//        return orderRepository.save(order);
//    }
//
//    public Order confirmOrder(int orderId) {
//        Order order = getById(orderId);
//        if ("1".equals(order.getStatus()) || "3".equals(order.getStatus())) {
//            order.setStatus("4");
//            return orderRepository.save(order);
//        }
//        throw new RuntimeException("Cannot confirm order: Invalid status");
//    }
//
//    public Order shippingOrder(int orderId) {
//        Order order = getById(orderId);
//        if ("4".equals(order.getStatus())) {
//            order.setStatus("5");
//            return orderRepository.save(order);
//        }
//        throw new RuntimeException("Cannot update to shipping: Order not confirmed");
//    }
//
//    public Order deliveredOrder(int orderId) {
//        Order order = getById(orderId);
//        if ("5".equals(order.getStatus())) {
//            order.setStatus("6");
//            return orderRepository.save(order);
//        }
//        throw new RuntimeException("Cannot update to delivered: Order not in shipping");
//    }
//
//    public Order receiveOrder(int orderId) {
//        Order order = getById(orderId);
//        if ("6".equals(order.getStatus())) {
//            order.setStatus("7");
//            return orderRepository.save(order);
//        }
//        throw new RuntimeException("Cannot confirm receipt: Order not delivered");
//    }
//
//    public Order adminCancelOrder(int orderId) {
//        Order order = getOrderById(orderId);
//        if ("2".equals(order.getStatus())) {
//            throw new RuntimeException("Không thể hủy đơn hàng đã bị hủy");
//        }
//        if ("5".equals(order.getStatus())) {
//            throw new RuntimeException("Không thể hủy đơn hàng đang vận chuyển");
//        }
//        if ("6".equals(order.getStatus())) {
//            throw new RuntimeException("Không thể hủy đơn hàng đã giao");
//        }
//        if ("7".equals(order.getStatus())) {
//            throw new RuntimeException("Không thể hủy đơn hàng đã nhận");
//        }
//
//        if ("3".equals(order.getStatus()) && order.getUser() != null && order.getUser().getEmail() != null) {
//            sendCancellationEmail(order.getUser().getEmail(), order.getOrderId());
//        }
//
//        order.setStatus("2");
//        return orderRepository.save(order);
//    }
//
//    private void sendCancellationEmail(String to, int orderId) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(to);
//            message.setSubject("Đơn Hàng Của Bạn Đã Bị Hủy");
//            message.setText(
//                "Kính gửi Quý khách,\n\n" +
//                "Đơn hàng #" + orderId + " của bạn đã bị hủy.\n" +
//                "Vui lòng liên hệ admin qua số điện thoại: 0932805261 để nhận lại số tiền được hoàn trả khi đã thanh toán trước.\n\n" +
//                "Trân trọng,\n" +
//                "Đội ngũ hỗ trợ"
//            );
//            mailSender.send(message);
//            logger.info("Email thông báo hủy đơn hàng #{} đã được gửi đến {}", orderId, to);
//        } catch (Exception e) {
//            logger.error("Lỗi khi gửi email thông báo hủy đơn hàng #{}: {}", orderId, e.getMessage());
//            throw new RuntimeException("Không thể gửi email thông báo hủy đơn hàng: " + e.getMessage());
//        }
//    }
//
//    public Page<OrderDTO> getOrdersByUserId(Integer userId, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());
//        Page<Order> orderPage = orderRepository.findByUser_UserId(userId, pageable);
//        return orderPage.map(this::convertToDTO);
//    }
//
//    private Order getById(int orderId) {
//        return orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//    }
//
//    public List<VoucherDTO> getValidVouchers() {
//        List<Voucher> allVouchers = voucherRepository.findAll();
//        Date currentDate = new Date();
//
//        return allVouchers.stream()
//                .filter(voucher -> {
//                    Date startDate = voucher.getStartDate();
//                    Date lateDate = voucher.getLateDate();
//                    return startDate != null && lateDate != null &&
//                           !currentDate.before(startDate) && !currentDate.after(lateDate);
//                })
//                .map(VoucherMapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public long getTotalOrdersByUserId(Integer userId) {
//        return orderRepository.countByUser_UserId(userId);
//    }
//
//    public Order handleVNPayCallback(int orderId, String vnpResponseCode) {
//        Order order = getOrderById(orderId);
//        if (!"2".equals(order.getPaymentStatus())) {
//            throw new RuntimeException("Order is not in VNPay payment mode");
//        }
//
//        if ("3".equals(order.getStatus())) {
//            if ("00".equals(vnpResponseCode)) {
//                return order;
//            } else {
//                throw new RuntimeException("Cannot update order: Already processed as paid");
//            }
//        }
//
//        if ("00".equals(vnpResponseCode)) {
//            order.setStatus("3");
//        } else {
//            order.setStatus("2");
//        }
//
//        return orderRepository.save(order);
//    }
//
//    public static String mapPaymentStatus(String paymentStatus) {
//        switch (paymentStatus) {
//            case "1": return "Thanh toán khi nhận hàng";
//            case "2": return "VNPay";
//            default: return paymentStatus;
//        }
//    }
//}

package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.util.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private JavaMailSender mailSender;

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public int createOrderAndGetId(OrderDTO orderDTO) {
        Order order = createOrderFromDTO(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getOrderId();
    }

    public Order saveOrder(OrderDTO orderDTO) {
        Order order = createOrderFromDTO(orderDTO);
        return orderRepository.save(order);
    }

    private Order createOrderFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus("1");
        order.setReceiverName(orderDTO.getReceiverName());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setShippingAddress(orderDTO.getShippingAddress());

        String paymentTypeId = orderDTO.getPaymentStatus();
        if ("2".equals(paymentTypeId)) {
            order.setPaymentStatus("1"); // Thanh toán khi nhận hàng
        } else if ("1".equals(paymentTypeId)) {
            order.setPaymentStatus("2"); // VNPay
        } else {
            throw new RuntimeException("Invalid payment type");
        }
        order.setContent(orderDTO.getContent());
        order.setShippingFee(orderDTO.getShippingFee());

        double totalAmount = orderDTO.getTotalAmount();
        double productTotal = calculateProductTotal(orderDTO.getShoppingCart());

        if (orderDTO.getVoucher() != null) {
            VoucherDTO voucherDTO = orderDTO.getVoucher();
            Voucher voucher = voucherRepository.findById(voucherDTO.getVoucherId())
                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại với ID: " + voucherDTO.getVoucherId()));

            Date currentDate = new Date();
            Date startDate = voucher.getStartDate();
            Date lateDate = voucher.getLateDate();

            if (startDate == null || lateDate == null) {
                throw new RuntimeException("Voucher không hợp lệ (ngày bắt đầu hoặc ngày kết thúc không được để trống).");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (currentDate.before(startDate)) {
                throw new RuntimeException("Không thể sử dụng vì chưa đến ngày: " + dateFormat.format(startDate));
            }
            if (currentDate.after(lateDate)) {
                throw new RuntimeException("Không thể sử dụng vì đã hết hạn: " + dateFormat.format(lateDate));
            }

            if (productTotal < voucher.getMinimumMoneyValue()) {
                throw new RuntimeException("Không thể sử dụng vì giá trị đơn hàng phải tối thiểu " + voucher.getMinimumMoneyValue() + " VNĐ.");
            }

            order.setVoucher(voucher);
            totalAmount = Math.max(totalAmount - voucher.getMaxDiscountValue(), 0); // Áp dụng giảm giá vào totalAmount
        }

        order.setTotalAmount(totalAmount); // Sử dụng totalAmount đã trừ giảm giá

        if (orderDTO.getUsers() == null || orderDTO.getUsers().isEmpty()) {
            throw new RuntimeException("No users provided");
        }
        UserRegisterDTO userDto = orderDTO.getUsers().get(0);
        User user = userRepository.findByUserId(userDto.getUserId());
        if (user == null) {
            throw new RuntimeException("User không tồn tại");
        }
        order.setUser(user);

        return order;
    }

    private double calculateProductTotal(ShoppingCartDTO shoppingCart) {
        if (shoppingCart == null || shoppingCart.getVariants() == null) {
            return 0;
        }
        return shoppingCart.getVariants().stream()
                .filter(variant -> variant.getPrice() > 0)
                .mapToDouble(variant -> variant.getPrice() * shoppingCart.getQuantity()) // Giả định quantity áp dụng cho tất cả variants
                .sum();
    }

    public List<UserRegisterDTO> getAllUsersAsDTO() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserRegisterDTO convertToDTO(User user) {
        return new UserRegisterDTO(
            user.getUserId(),
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            user.getRole(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getPoint()
        );
    }

    public Page<OrderDTO> getAllOrdersAsDTO(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(this::convertToDTO);
    }

    public OrderDTO convertToDTO(Order order) {
        List<UserRegisterDTO> users = new ArrayList<>();
        if (order.getUser() != null) {
            users.add(convertToUserRegisterDTO(order.getUser()));
        }

        ShoppingCartDTO shoppingCart = new ShoppingCartDTO(
            0, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );

        VoucherDTO voucherDTO = null;
        if (order.getVoucher() != null) {
            voucherDTO = VoucherMapper.toDto(order.getVoucher());
        }

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            orderItemDTOs = order.getOrderItems().stream()
                .map(orderItem -> orderItemService.convertToDTO(orderItem))
                .collect(Collectors.toList());
        }

        return new OrderDTO(
            order.getOrderId(),
            order.getOrderDate(),
            order.getStatus(),
            order.getShippingAddress(),
            order.getPhoneNumber(),
            order.getPaymentStatus(),
            order.getContent(),
            order.getShippingFee(),
            order.getTotalAmount(),
            voucherDTO,
            users,
            shoppingCart,
            orderItemDTOs,
            order.getReceiverName()
        );
    }

    private UserRegisterDTO convertToUserRegisterDTO(User user) {
        return new UserRegisterDTO(
            user.getUserId(),
            user.getUsername(),
            null,
            user.getEmail(),
            user.getRole(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getPoint()
        );
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order cancelOrder(int orderId) {
        Order order = getOrderById(orderId);
        if ("2".equals(order.getStatus())) {
            throw new RuntimeException("Cannot cancel an already canceled order");
        }
        if ("6".equals(order.getStatus())) {
            throw new RuntimeException("Cannot cancel a delivered order");
        }
        order.setStatus("2");
        return orderRepository.save(order);
    }

    public Order userCancelOrder(int orderId) {
        Order order = getOrderById(orderId);
        if ("2".equals(order.getStatus())) {
            throw new RuntimeException("Không thể hủy đơn hàng đã bị hủy");
        }
        if (!("1".equals(order.getStatus()) || "3".equals(order.getStatus()))) {
            throw new RuntimeException("Không thể hủy đơn hàng: Đơn hàng đã được xác nhận hoặc xử lý");
        }

        Date orderDate = order.getOrderDate();
        Date currentDate = new Date();
        long timeDifference = currentDate.getTime() - orderDate.getTime();
        long oneHourInMs = 1 * 60 * 60 * 1000; // 1 tiếng

        if (timeDifference > oneHourInMs) {
            throw new RuntimeException("Không thể hủy đơn hàng: Thời gian hủy (1 giờ) đã hết");
        }

        if ("3".equals(order.getStatus()) && order.getUser() != null && order.getUser().getEmail() != null) {
            sendCancellationEmail(order.getUser().getEmail(), order.getOrderId());
        }

        order.setStatus("2");
        return orderRepository.save(order);
    }

    public Order confirmOrder(int orderId) {
        Order order = getById(orderId);
        if ("1".equals(order.getStatus()) || "3".equals(order.getStatus())) {
            order.setStatus("4");
            return orderRepository.save(order);
        }
        throw new RuntimeException("Cannot confirm order: Invalid status");
    }

    public Order shippingOrder(int orderId) {
        Order order = getById(orderId);
        if ("4".equals(order.getStatus())) {
            order.setStatus("5");
            return orderRepository.save(order);
        }
        throw new RuntimeException("Cannot update to shipping: Order not confirmed");
    }

    public Order deliveredOrder(int orderId) {
        Order order = getById(orderId);
        if ("5".equals(order.getStatus())) {
            order.setStatus("6");
            return orderRepository.save(order);
        }
        throw new RuntimeException("Cannot update to delivered: Order not in shipping");
    }

    public Order receiveOrder(int orderId) {
        Order order = getById(orderId);
        if ("6".equals(order.getStatus())) {
            order.setStatus("7");
            return orderRepository.save(order);
        }
        throw new RuntimeException("Cannot confirm receipt: Order not delivered");
    }

    public Order adminCancelOrder(int orderId) {
        Order order = getOrderById(orderId);
        if ("2".equals(order.getStatus())) {
            throw new RuntimeException("Không thể hủy đơn hàng đã bị hủy");
        }
        if ("5".equals(order.getStatus())) {
            throw new RuntimeException("Không thể hủy đơn hàng đang vận chuyển");
        }
        if ("6".equals(order.getStatus())) {
            throw new RuntimeException("Không thể hủy đơn hàng đã giao");
        }
        if ("7".equals(order.getStatus())) {
            throw new RuntimeException("Không thể hủy đơn hàng đã nhận");
        }

        if ("3".equals(order.getStatus()) && order.getUser() != null && order.getUser().getEmail() != null) {
            sendCancellationEmail(order.getUser().getEmail(), order.getOrderId());
        }

        order.setStatus("2");
        return orderRepository.save(order);
    }

    private void sendCancellationEmail(String to, int orderId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Đơn Hàng Của Bạn Đã Bị Hủy");
            message.setText(
                "Kính gửi Quý khách,\n\n" +
                "Đơn hàng #" + orderId + " của bạn đã bị hủy.\n" +
                "Vui lòng liên hệ admin qua số điện thoại: 0932805261 để nhận lại số tiền được hoàn trả khi đã thanh toán trước.\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ hỗ trợ"
            );
            mailSender.send(message);
            logger.info("Email thông báo hủy đơn hàng #{} đã được gửi đến {}", orderId, to);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi email thông báo hủy đơn hàng #{}: {}", orderId, e.getMessage());
            throw new RuntimeException("Không thể gửi email thông báo hủy đơn hàng: " + e.getMessage());
        }
    }

    public Page<OrderDTO> getOrdersByUserId(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());
        Page<Order> orderPage = orderRepository.findByUser_UserId(userId, pageable);
        return orderPage.map(this::convertToDTO);
    }

    private Order getById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<VoucherDTO> getValidVouchers() {
        List<Voucher> allVouchers = voucherRepository.findAll();
        Date currentDate = new Date();

        return allVouchers.stream()
                .filter(voucher -> {
                    Date startDate = voucher.getStartDate();
                    Date lateDate = voucher.getLateDate();
                    return startDate != null && lateDate != null &&
                           !currentDate.before(startDate) && !currentDate.after(lateDate);
                })
                .map(VoucherMapper::toDto)
                .collect(Collectors.toList());
    }

    public long getTotalOrdersByUserId(Integer userId) {
        return orderRepository.countByUser_UserId(userId);
    }

    public Order handleVNPayCallback(int orderId, String vnpResponseCode) {
        Order order = getOrderById(orderId);
        if (!"2".equals(order.getPaymentStatus())) {
            throw new RuntimeException("Order is not in VNPay payment mode");
        }

        if ("3".equals(order.getStatus())) {
            if ("00".equals(vnpResponseCode)) {
                return order;
            } else {
                throw new RuntimeException("Cannot update order: Already processed as paid");
            }
        }

        if ("00".equals(vnpResponseCode)) {
            order.setStatus("3");
        } else {
            order.setStatus("2");
        }

        return orderRepository.save(order);
    }

    public static String mapPaymentStatus(String paymentStatus) {
        switch (paymentStatus) {
            case "1": return "Thanh toán khi nhận hàng";
            case "2": return "VNPay";
            default: return paymentStatus;
        }
    }
}