//
//package com.example.demo.service;
//
//import com.example.demo.dto.*;
//import com.example.demo.entity.*;
//import com.example.demo.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ReviewService {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private VariantRepository variantRepository;
//
//    @Autowired
//    private CloudinaryService cloudinaryService;
//
//    @Autowired
//    private OrderItemService orderItemService; // Thêm dependency
//
//    public List<ReviewDTO> getAllReviews() {
//        List<Review> reviews = reviewRepository.findAll();
//        return reviews.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public List<UserDisplayDTO> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(this::convertToUserDisplayDTO)
//                .collect(Collectors.toList());
//    }
//
//    public List<OrderItemDTO> getAllOrderItems() {
//        List<OrderItem> orderItems = orderItemRepository.findAll();
//        return orderItems.stream()
//                .map(orderItemService::convertToDTO) // Sử dụng OrderItemService
//                .collect(Collectors.toList());
//    }
//
//    public ReviewDTO addReview(ReviewDTO reviewDTO, MultipartFile[] images) throws IOException {
//        Review review = new Review();
//        review.setRating(reviewDTO.getRating());
//        review.setComment(reviewDTO.getComment());
//        review.setReviewDate(new Date());
//
//        if (images != null && images.length > 0) {
//            System.out.println("Processing " + images.length + " images");
//            for (int i = 0; i < Math.min(images.length, 3); i++) {
//                if (images[i] != null && !images[i].isEmpty()) {
//                    try {
//                        Map uploadResult = cloudinaryService.uploadFile(images[i]);
//                        String imageUrl = (String) uploadResult.get("url");
//                        System.out.println("Image " + (i + 1) + " uploaded: " + imageUrl);
//                        switch (i) {
//                            case 0: review.setImageUser1(imageUrl); break;
//                            case 1: review.setImageUser2(imageUrl); break;
//                            case 2: review.setImageUser3(imageUrl); break;
//                        }
//                    } catch (Exception e) {
//                        System.err.println("Failed to upload image " + (i + 1) + ": " + e.getMessage());
//                    }
//                }
//            }
//        }
//
//        User user = userRepository.findById(reviewDTO.getUser().getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found: " + reviewDTO.getUser().getUserId()));
//        OrderItem orderItem = orderItemRepository.findById(reviewDTO.getOrderItem().getOrderItemId())
//                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + reviewDTO.getOrderItem().getOrderItemId()));
//        review.setUser(user);
//        review.setOrderItem(orderItem);
//
//        Review savedReview = reviewRepository.save(review);
//        System.out.println("Saved review with ID: " + savedReview.getReviewId());
//        return convertToDTO(savedReview);
//    }
//
//    public ReviewDTO updateReview(int reviewId, ReviewDTO reviewDTO, MultipartFile[] images) throws IOException {
//        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
//        if (optionalReview.isPresent()) {
//            Review review = optionalReview.get();
//            review.setRating(reviewDTO.getRating());
//            review.setComment(reviewDTO.getComment());
//            review.setReviewDate(new Date());
//            System.out.println("Updating review ID: " + reviewId + ", reviewDate: " + review.getReviewDate());
//
//            // Lưu URL ảnh cũ để xóa trên Cloudinary nếu bị thay thế
//            String oldImage1 = review.getImageUser1();
//            String oldImage2 = review.getImageUser2();
//            String oldImage3 = review.getImageUser3();
//
//            // Khởi tạo các trường ảnh từ reviewDTO (tôn trọng null để xóa)
//            review.setImageUser1(reviewDTO.getImageUser1());
//            review.setImageUser2(reviewDTO.getImageUser2());
//            review.setImageUser3(reviewDTO.getImageUser3());
//
//            // Xử lý ảnh mới
//            if (images != null && images.length > 0) {
//                System.out.println("Processing " + images.length + " images for update");
//                int imageIndex = 0;
//
//                // Gán ảnh mới vào các vị trí trống (null) theo thứ tự imageUser1, imageUser2, imageUser3
//                String[] currentImages = { review.getImageUser1(), review.getImageUser2(), review.getImageUser3() };
//                for (int i = 0; i < 3 && imageIndex < images.length; i++) {
//                    if (currentImages[i] == null && images[imageIndex] != null && !images[imageIndex].isEmpty()) {
//                        try {
//                            Map uploadResult = cloudinaryService.uploadFile(images[imageIndex]);
//                            String imageUrl = (String) uploadResult.get("url");
//                            System.out.println("Image " + (i + 1) + " uploaded: " + imageUrl);
//                            switch (i) {
//                                case 0: review.setImageUser1(imageUrl); break;
//                                case 1: review.setImageUser2(imageUrl); break;
//                                case 2: review.setImageUser3(imageUrl); break;
//                            }
//                            imageIndex++;
//                        } catch (Exception e) {
//                            System.err.println("Failed to upload image " + (i + 1) + ": " + e.getMessage());
//                        }
//                    }
//                }
//                if (imageIndex < images.length) {
//                    System.err.println("Warning: More images provided than available slots");
//                }
//            }
//
//            // Xóa ảnh cũ trên Cloudinary nếu bị thay thế hoặc xóa
//            if (oldImage1 != null && !oldImage1.equals(review.getImageUser1())) {
//                try {
//                    cloudinaryService.deleteFile(extractPublicId(oldImage1));
//                    System.out.println("Deleted old imageUser1 from Cloudinary: " + oldImage1);
//                } catch (Exception e) {
//                    System.err.println("Failed to delete old imageUser1: " + e.getMessage());
//                }
//            }
//            if (oldImage2 != null && !oldImage2.equals(review.getImageUser2())) {
//                try {
//                    cloudinaryService.deleteFile(extractPublicId(oldImage2));
//                    System.out.println("Deleted old imageUser2 from Cloudinary: " + oldImage2);
//                } catch (Exception e) {
//                    System.err.println("Failed to delete old imageUser2: " + e.getMessage());
//                }
//            }
//            if (oldImage3 != null && !oldImage3.equals(review.getImageUser3())) {
//                try {
//                    cloudinaryService.deleteFile(extractPublicId(oldImage3));
//                    System.out.println("Deleted old imageUser3 from Cloudinary: " + oldImage3);
//                } catch (Exception e) {
//                    System.err.println("Failed to delete old imageUser3: " + e.getMessage());
//                }
//            }
//
//            User user = userRepository.findById(reviewDTO.getUser().getUserId())
//                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + reviewDTO.getUser().getUserId()));
//            OrderItem orderItem = orderItemRepository.findById(reviewDTO.getOrderItem().getOrderItemId())
//                    .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + reviewDTO.getOrderItem().getOrderItemId()));
//            review.setUser(user);
//            review.setOrderItem(orderItem);
//
//            Review updatedReview = reviewRepository.save(review);
//            System.out.println("Updated review with ID: " + updatedReview.getReviewId());
//            return convertToDTO(updatedReview);
//        } else {
//            System.out.println("Review not found: " + reviewId);
//            return null;
//        }
//    }
//
//    public ReviewDTO getReviewById(int reviewId) {
//        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
//        return optionalReview.map(this::convertToDTO).orElse(null);
//    }
//
//    public void deleteReview(int reviewId) {
//        reviewRepository.deleteById(reviewId);
//    }
//
//    private ReviewDTO convertToDTO(Review review) {
//        UserDisplayDTO userDTO = convertToUserDisplayDTO(review.getUser());
//        OrderItemDTO orderItemDTO = orderItemService.convertToDTO(review.getOrderItem()); // Sử dụng OrderItemService
//        return new ReviewDTO(
//                review.getReviewId(),
//                review.getRating(),
//                review.getComment(),
//                review.getImageUser1(),
//                review.getImageUser2(),
//                review.getImageUser3(),
//                review.getReviewDate(),
//                userDTO,
//                orderItemDTO
//        );
//    }
//
//    private UserDisplayDTO convertToUserDisplayDTO(User user) {
//        if (user == null) {
//            return null;
//        }
//        return new UserDisplayDTO(
//                user.getUserId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getFirstName(),
//                user.getLastName(),
//                user.getPhone(),
//                user.getPoint(),
//                user.getImage()
//        );
//    }
//
//    public boolean hasUserReviewed(int userId, int orderItemId) {
//        return reviewRepository.existsByUserUserIdAndOrderItemOrderItemId(userId, orderItemId);
//    }
//
//    public List<ReviewDTO> getReviewsByProductId(int productId) {
//        List<Variant> variants = variantRepository.findByProduct_ProductId(productId);
//        List<OrderItem> orderItems = new ArrayList<>();
//        for (Variant variant : variants) {
//            List<OrderItem> items = orderItemRepository.findByVariant(variant);
//            orderItems.addAll(items);
//        }
//        List<Review> reviews = reviewRepository.findByOrderItemIn(orderItems);
//        return reviews.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // Hàm hỗ trợ trích xuất public_id từ URL Cloudinary
//    private String extractPublicId(String imageUrl) {
//        if (imageUrl == null) return null;
//        String[] parts = imageUrl.split("/");
//        String fileName = parts[parts.length - 1];
//        return fileName.substring(0, fileName.lastIndexOf("."));
//    }
//}
package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private OrderItemService orderItemService;

    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDisplayDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDisplayDTO)
                .collect(Collectors.toList());
    }

    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(orderItemService::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO addReview(ReviewDTO reviewDTO, MultipartFile[] images) throws IOException {
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setReviewDate(new Date());

        if (images != null && images.length > 0) {
            System.out.println("Processing " + images.length + " images");
            for (int i = 0; i < Math.min(images.length, 3); i++) {
                if (images[i] != null && !images[i].isEmpty()) {
                    try {
                        Map uploadResult = cloudinaryService.uploadFile(images[i]);
                        String imageUrl = (String) uploadResult.get("url");
                        System.out.println("Image " + (i + 1) + " uploaded: " + imageUrl);
                        switch (i) {
                            case 0: review.setImageUser1(imageUrl); break;
                            case 1: review.setImageUser2(imageUrl); break;
                            case 2: review.setImageUser3(imageUrl); break;
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to upload image " + (i + 1) + ": " + e.getMessage());
                    }
                }
            }
        }

        User user = userRepository.findById(reviewDTO.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + reviewDTO.getUser().getUserId()));
        OrderItem orderItem = orderItemRepository.findById(reviewDTO.getOrderItem().getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + reviewDTO.getOrderItem().getOrderItemId()));
        review.setUser(user);
        review.setOrderItem(orderItem);

        Review savedReview = reviewRepository.save(review);
        System.out.println("Saved review with ID: " + savedReview.getReviewId());
        return convertToDTO(savedReview);
    }

    public ReviewDTO updateReview(int reviewId, ReviewDTO reviewDTO, MultipartFile[] images) throws IOException {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setRating(reviewDTO.getRating());
            review.setComment(reviewDTO.getComment());
            review.setReviewDate(new Date());
            System.out.println("Updating review ID: " + reviewId + ", reviewDate: " + review.getReviewDate());

            String oldImage1 = review.getImageUser1();
            String oldImage2 = review.getImageUser2();
            String oldImage3 = review.getImageUser3();

            review.setImageUser1(reviewDTO.getImageUser1());
            review.setImageUser2(reviewDTO.getImageUser2());
            review.setImageUser3(reviewDTO.getImageUser3());

            if (images != null && images.length > 0) {
                System.out.println("Processing " + images.length + " images for update");
                int imageIndex = 0;

                String[] currentImages = { review.getImageUser1(), review.getImageUser2(), review.getImageUser3() };
                for (int i = 0; i < 3 && imageIndex < images.length; i++) {
                    if (currentImages[i] == null && images[imageIndex] != null && !images[imageIndex].isEmpty()) {
                        try {
                            Map uploadResult = cloudinaryService.uploadFile(images[imageIndex]);
                            String imageUrl = (String) uploadResult.get("url");
                            System.out.println("Image " + (i + 1) + " uploaded: " + imageUrl);
                            switch (i) {
                                case 0: review.setImageUser1(imageUrl); break;
                                case 1: review.setImageUser2(imageUrl); break;
                                case 2: review.setImageUser3(imageUrl); break;
                            }
                            imageIndex++;
                        } catch (Exception e) {
                            System.err.println("Failed to upload image " + (i + 1) + ": " + e.getMessage());
                        }
                    }
                }
                if (imageIndex < images.length) {
                    System.err.println("Warning: More images provided than available slots");
                }
            }

            if (oldImage1 != null && !oldImage1.equals(review.getImageUser1())) {
                try {
                    cloudinaryService.deleteFile(extractPublicId(oldImage1));
                    System.out.println("Deleted old imageUser1 from Cloudinary: " + oldImage1);
                } catch (Exception e) {
                    System.err.println("Failed to delete old imageUser1: " + e.getMessage());
                }
            }
            if (oldImage2 != null && !oldImage2.equals(review.getImageUser2())) {
                try {
                    cloudinaryService.deleteFile(extractPublicId(oldImage2));
                    System.out.println("Deleted old imageUser2 from Cloudinary: " + oldImage2);
                } catch (Exception e) {
                    System.err.println("Failed to delete old imageUser2: " + e.getMessage());
                }
            }
            if (oldImage3 != null && !oldImage3.equals(review.getImageUser3())) {
                try {
                    cloudinaryService.deleteFile(extractPublicId(oldImage3));
                    System.out.println("Deleted old imageUser3 from Cloudinary: " + oldImage3);
                } catch (Exception e) {
                    System.err.println("Failed to delete old imageUser3: " + e.getMessage());
                }
            }

            User user = userRepository.findById(reviewDTO.getUser().getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + reviewDTO.getUser().getUserId()));
            OrderItem orderItem = orderItemRepository.findById(reviewDTO.getOrderItem().getOrderItemId())
                    .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + reviewDTO.getOrderItem().getOrderItemId()));
            review.setUser(user);
            review.setOrderItem(orderItem);

            Review updatedReview = reviewRepository.save(review);
            System.out.println("Updated review with ID: " + updatedReview.getReviewId());
            return convertToDTO(updatedReview);
        } else {
            System.out.println("Review not found: " + reviewId);
            return null;
        }
    }

    public ReviewDTO getReviewById(int reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        return optionalReview.map(this::convertToDTO).orElse(null);
    }

    public void deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private ReviewDTO convertToDTO(Review review) {
        UserDisplayDTO userDTO = convertToUserDisplayDTO(review.getUser());
        OrderItemDTO orderItemDTO = orderItemService.convertToDTO(review.getOrderItem());
        return new ReviewDTO(
                review.getReviewId(),
                review.getRating(),
                review.getComment(),
                review.getImageUser1(),
                review.getImageUser2(),
                review.getImageUser3(),
                review.getReviewDate(),
                userDTO,
                orderItemDTO
        );
    }

    private UserDisplayDTO convertToUserDisplayDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDisplayDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getPoint(),
                user.getImage()
        );
    }

    public boolean hasUserReviewed(int userId, int orderItemId) {
        return reviewRepository.existsByUserUserIdAndOrderItemOrderItemId(userId, orderItemId);
    }

    public List<ReviewDTO> getReviewsByProductId(int productId) {
        List<Variant> variants = variantRepository.findByProduct_ProductId(productId);
        List<OrderItem> orderItems = new ArrayList<>();
        for (Variant variant : variants) {
            List<OrderItem> items = orderItemRepository.findByVariant(variant);
            orderItems.addAll(items);
        }
        List<Review> reviews = reviewRepository.findByOrderItemIn(orderItems);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm phương thức mới để tính trung bình rating
    public Map<String, Object> calculateAverageRatingByProductId(int productId) {
        List<Review> reviews = getReviewsByProductId(productId).stream()
                .map(reviewDTO -> reviewRepository.findById(reviewDTO.getReviewId()).orElse(null))
                .filter(review -> review != null)
                .collect(Collectors.toList());

        if (reviews.isEmpty()) {
            return Map.of("average", 0.0, "count", 0);
        }

        double totalRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();
        double average = totalRating / reviews.size();
        // Làm tròn đến 0.5 gần nhất
        double roundedAverage = Math.round(average * 2) / 2.0;

        return Map.of(
                "average", roundedAverage,
                "count", reviews.size()
        );
    }

    private String extractPublicId(String imageUrl) {
        if (imageUrl == null) return null;
        String[] parts = imageUrl.split("/");
        String fileName = parts[parts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}