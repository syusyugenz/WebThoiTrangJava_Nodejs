package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VariantRepository variantRepository;

    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderItemDTO getOrderItemById(int orderItemId) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderItemId);
        return optionalOrderItem.map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("OrderItem không tồn tại với ID: " + orderItemId));
    }

    public List<OrderItemDTO> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderId(orderId);
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderItemDTO> getOrderItemsByOrderIds(List<Integer> orderIds) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderIdIn(orderIds);
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderItemDTO> getOrderItemsByUserId(int userId) {
        List<OrderItem> orderItems = orderItemRepository.findByUserId(userId);
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO) {
        // Validation
        if (orderItemDTO == null) {
            throw new IllegalArgumentException("OrderItemDTO không được null");
        }
        if (orderItemDTO.getOrder() == null || orderItemDTO.getOrder().getOrderId() <= 0) {
            throw new IllegalArgumentException("Order ID không hợp lệ hoặc không được cung cấp");
        }
        if (orderItemDTO.getVariants() == null || orderItemDTO.getVariants().isEmpty()) {
            throw new IllegalArgumentException("Danh sách variants không được để trống");
        }
        if (orderItemDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        if (orderItemDTO.getPrice() < 0) {
            throw new IllegalArgumentException("Giá không được âm");
        }

        OrderItem orderItem;

        // Kiểm tra nếu cập nhật OrderItem hiện có
        if (orderItemDTO.getOrderItemId() > 0) {
            orderItem = orderItemRepository.findById(orderItemDTO.getOrderItemId())
                    .orElseThrow(() -> new RuntimeException("OrderItem không tồn tại với ID: " + orderItemDTO.getOrderItemId()));
        } else {
            orderItem = new OrderItem();
        }

        // Gán thông tin cơ bản
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());

        // Gán Order
        int orderId = orderItemDTO.getOrder().getOrderId();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order không tồn tại với ID: " + orderId));
        orderItem.setOrder(order);

        // Gán Variant
        int variantId = orderItemDTO.getVariants().get(0).getVariantId();
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant không tồn tại với ID: " + variantId));
        orderItem.setVariant(variant);

        // Lưu vào cơ sở dữ liệu
        try {
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            return convertToDTO(savedOrderItem);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lưu OrderItem: " + e.getMessage(), e);
        }
    }

    public List<OrderItemDTO> save2(OrderItemDTO orderItemDTO) {
        // Validation
        if (orderItemDTO == null) {
            throw new IllegalArgumentException("OrderItemDTO không được null");
        }
        if (orderItemDTO.getOrder() == null || orderItemDTO.getOrder().getOrderId() <= 0) {
            throw new IllegalArgumentException("Order ID không hợp lệ hoặc không được cung cấp");
        }
        if (orderItemDTO.getVariants() == null || orderItemDTO.getVariants().isEmpty()) {
            throw new IllegalArgumentException("Danh sách variants không được để trống");
        }
        if (orderItemDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        if (orderItemDTO.getPrice() < 0) {
            throw new IllegalArgumentException("Giá không được âm");
        }

        // Tìm Order
        Order order = orderRepository.findById(orderItemDTO.getOrder().getOrderId())
                .orElseThrow(() -> new RuntimeException("Order không tồn tại với ID: " + orderItemDTO.getOrder().getOrderId()));

        List<OrderItem> orderItems = new ArrayList<>();
        for (VariantDTO variantDTO : orderItemDTO.getVariants()) {
            OrderItem orderItem = new OrderItem();

            // Kiểm tra nếu cập nhật OrderItem hiện có
            if (orderItemDTO.getOrderItemId() > 0) {
                Optional<OrderItem> existingOrderItem = orderItemRepository.findById(orderItemDTO.getOrderItemId());
                orderItem = existingOrderItem.orElse(orderItem);
            }

            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setOrder(order);

            Variant variant = variantRepository.findById(variantDTO.getVariantId())
                    .orElseThrow(() -> new RuntimeException("Variant không tồn tại với ID: " + variantDTO.getVariantId()));
            orderItem.setVariant(variant);

            orderItems.add(orderItemRepository.save(orderItem));
        }

        // Ánh xạ kết quả
        List<OrderItemDTO> savedOrderItems = new ArrayList<>();
        if (!orderItems.isEmpty()) {
            OrderItem firstOrderItem = orderItems.get(0);
            List<VariantDTO> combinedVariants = new ArrayList<>();
            for (OrderItem item : orderItems) {
                Variant variant = item.getVariant();
                List<ProductDTO> productDTOs = new ArrayList<>();
                if (variant.getProduct() != null) {
                    Product product = variant.getProduct();
                    List<CategoryDTO> categoryDTOs = new ArrayList<>();
                    if (product.getCategory() != null) {
                        Category category = product.getCategory();
                        categoryDTOs.add(new CategoryDTO(category.getCategoryId(), category.getCategoryName()));
                    }
                    productDTOs.add(new ProductDTO(
                            product.getProductId(),
                            product.getProductName(),
                            product.getDescription(),
                            product.getBrand(),
                            categoryDTOs
                    ));
                }
                combinedVariants.add(new VariantDTO(
                        variant.getVariantId(),
                        variant.getCode(),
                        variant.getImage(),
                        variant.getPrice(),
                        productDTOs,
                        null,
                        null
                ));
            }

            OrderItemDTO combinedDTO = new OrderItemDTO(
                    firstOrderItem.getOrderItemId(),
                    firstOrderItem.getQuantity(),
                    firstOrderItem.getPrice(),
                    convertOrderToDTO(firstOrderItem.getOrder()),
                    combinedVariants
            );
            savedOrderItems.add(combinedDTO);
        }

        return savedOrderItems;
    }

    public void deleteOrderItem(int orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new RuntimeException("OrderItem không tồn tại với ID: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }

    public List<VariantDTO> getAllVariants() {
        List<Variant> variants = variantRepository.findAll();
        return variants.stream()
                .map(variant -> {
                    List<ProductDTO> productDTOs = new ArrayList<>();
                    if (variant.getProduct() != null) {
                        Product product = variant.getProduct();
                        List<CategoryDTO> categoryDTOs = new ArrayList<>();
                        if (product.getCategory() != null) {
                            Category category = product.getCategory();
                            categoryDTOs.add(new CategoryDTO(category.getCategoryId(), category.getCategoryName()));
                        }
                        productDTOs.add(new ProductDTO(
                                product.getProductId(),
                                product.getProductName(),
                                product.getDescription(),
                                product.getBrand(),
                                categoryDTOs
                        ));
                    }
                    return new VariantDTO(
                            variant.getVariantId(),
                            variant.getCode(),
                            variant.getImage(),
                            variant.getPrice(),
                            productDTOs,
                            null,
                            null
                    );
                })
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> {
                    UserRegisterDTO userRegisterDTO = null;
                    if (order.getUser() != null) {
                        User user = order.getUser();
                        userRegisterDTO = new UserRegisterDTO(
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
                    VoucherDTO voucherDTO = null;
                    if (order.getVoucher() != null) {
                        Voucher voucher = order.getVoucher();
                        voucherDTO = new VoucherDTO(
                                voucher.getVoucherId(),
                                voucher.getDiscountType(),
                                voucher.getMaxDiscountValue(),
                                voucher.getMinimumMoneyValue(),
                                voucher.getStartDate(),
                                voucher.getLateDate()
                        );
                    }
                    List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
                    if (order.getOrderItems() != null) {
                        orderItemDTOs = order.getOrderItems().stream()
                            .map(this::convertToDTO)
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
                            userRegisterDTO != null ? List.of(userRegisterDTO) : null,
                            new ShoppingCartDTO(0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                            orderItemDTOs,
                            order.getReceiverName() // Thêm receiverName
                    );
                })
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByUserId(int userId) {
        List<Order> orders = orderRepository.findByUser_UserId(userId);
        return orders.stream()
                .map(order -> {
                    UserRegisterDTO userRegisterDTO = null;
                    if (order.getUser() != null) {
                        User user = order.getUser();
                        userRegisterDTO = new UserRegisterDTO(
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
                    VoucherDTO voucherDTO = null;
                    if (order.getVoucher() != null) {
                        Voucher voucher = order.getVoucher();
                        voucherDTO = new VoucherDTO(
                                voucher.getVoucherId(),
                                voucher.getDiscountType(),
                                voucher.getMaxDiscountValue(),
                                voucher.getMinimumMoneyValue(),
                                voucher.getStartDate(),
                                voucher.getLateDate()
                        );
                    }
                    List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
                    if (order.getOrderItems() != null) {
                        orderItemDTOs = order.getOrderItems().stream()
                            .map(this::convertToDTO)
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
                            userRegisterDTO != null ? List.of(userRegisterDTO) : null,
                            new ShoppingCartDTO(0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                            orderItemDTOs,
                            order.getReceiverName() // Thêm receiverName
                    );
                })
                .collect(Collectors.toList());
    }

    protected OrderItemDTO convertToDTO(OrderItem orderItem) {
        OrderDTO orderDTO = null;
        if (orderItem.getOrder() != null) {
            Order order = orderItem.getOrder();
            UserRegisterDTO userRegisterDTO = null;
            if (order.getUser() != null) {
                User user = order.getUser();
                userRegisterDTO = new UserRegisterDTO(
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
            VoucherDTO voucherDTO = null;
            if (order.getVoucher() != null) {
                Voucher voucher = order.getVoucher();
                voucherDTO = new VoucherDTO(
                        voucher.getVoucherId(),
                        voucher.getDiscountType(),
                        voucher.getMaxDiscountValue(),
                        voucher.getMinimumMoneyValue(),
                        voucher.getStartDate(),
                        voucher.getLateDate()
                );
            }
            orderDTO = new OrderDTO(
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
                    userRegisterDTO != null ? List.of(userRegisterDTO) : null,
                    new ShoppingCartDTO(0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                    null,
                    order.getReceiverName() // Thêm receiverName
            );
        }

        List<VariantDTO> variantDTOs = new ArrayList<>();
        if (orderItem.getVariant() != null) {
            Variant variant = orderItem.getVariant();
            List<ProductDTO> productDTOs = new ArrayList<>();
            if (variant.getProduct() != null) {
                Product product = variant.getProduct();
                List<CategoryDTO> categoryDTOs = new ArrayList<>();
                if (product.getCategory() != null) {
                    Category category = product.getCategory();
                    categoryDTOs.add(new CategoryDTO(category.getCategoryId(), category.getCategoryName()));
                }
                productDTOs.add(new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getBrand(),
                        categoryDTOs
                ));
            }
            variantDTOs.add(new VariantDTO(
                    variant.getVariantId(),
                    variant.getCode(),
                    variant.getImage(),
                    variant.getPrice(),
                    productDTOs,
                    null,
                    null
            ));
        }

        return new OrderItemDTO(
                orderItem.getOrderItemId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderDTO,
                variantDTOs
        );
    }

    private OrderDTO convertOrderToDTO(Order order) {
        if (order == null) {
            return null;
        }
        UserRegisterDTO userRegisterDTO = null;
        if (order.getUser() != null) {
            User user = order.getUser();
            userRegisterDTO = new UserRegisterDTO(
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
        VoucherDTO voucherDTO = null;
        if (order.getVoucher() != null) {
            Voucher voucher = order.getVoucher();
            voucherDTO = new VoucherDTO(
                voucher.getVoucherId(),
                voucher.getDiscountType(),
                voucher.getMaxDiscountValue(),
                voucher.getMinimumMoneyValue(),
                voucher.getStartDate(),
                voucher.getLateDate()
            );
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
                userRegisterDTO != null ? List.of(userRegisterDTO) : null,
                new ShoppingCartDTO(0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                null,
                order.getReceiverName() // Thêm receiverName
        );
    }
}