package com.example.demo.service;

import com.example.demo.dto.CardDTO;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.Variant;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ReviewService reviewService;

    private static final int NEW_PRODUCT_LIMIT = 10;

    /**
     * Tìm kiếm sản phẩm với các tiêu chí: tên, khoảng giá, danh mục
     * @param name Tên sản phẩm (tùy chọn)
     * @param priceRange Khoảng giá (ví dụ: "100000-200000", tùy chọn)
     * @param categoryName Tên danh mục (tùy chọn)
     * @param page Số trang (bắt đầu từ 1)
     * @param size Số sản phẩm mỗi trang
     * @return PagedResponseDTO<CardDTO> Danh sách sản phẩm kèm thông tin phân trang
     */
    public PagedResponseDTO<CardDTO> searchCards(String name, String priceRange, String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page bắt đầu từ 0 trong Spring Data
        Page<Product> productPage;

        // Chuẩn hóa tham số
        name = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        categoryName = (categoryName != null && !categoryName.trim().isEmpty()) ? categoryName.trim() : null;
        double minPrice = 0;
        double maxPrice = Double.MAX_VALUE;
        if (priceRange != null && !priceRange.trim().isEmpty()) {
            String[] range = priceRange.split("-");
            if (range.length == 2) {
                minPrice = Double.parseDouble(range[0]);
                maxPrice = Double.parseDouble(range[1]);
            }
        }

        // Xây dựng truy vấn
        if (name != null && categoryName != null && priceRange != null && !priceRange.trim().isEmpty()) {
            productPage = productRepository.findByProductNameContainingIgnoreCaseAndCategory_CategoryNameIgnoreCaseAndVariantsPriceBetween(
                    name, categoryName, minPrice, maxPrice, pageable);
        } else if (name != null && categoryName != null) {
            productPage = productRepository.findByProductNameContainingIgnoreCaseAndCategory_CategoryNameIgnoreCase(
                    name, categoryName, pageable);
        } else if (name != null && priceRange != null && !priceRange.trim().isEmpty()) {
            productPage = productRepository.findByProductNameContainingIgnoreCase(name, pageable);
            // Lọc thêm giá sau vì không có phương thức trực tiếp
        } else if (categoryName != null && priceRange != null && !priceRange.trim().isEmpty()) {
            productPage = productRepository.findByCategory_CategoryNameIgnoreCase(categoryName, pageable);
            // Lọc thêm giá sau
        } else if (name != null) {
            productPage = productRepository.findByProductNameContainingIgnoreCase(name, pageable);
        } else if (categoryName != null) {
            productPage = productRepository.findByCategory_CategoryNameIgnoreCase(categoryName, pageable);
        } else if (priceRange != null && !priceRange.trim().isEmpty()) {
            productPage = productRepository.findByVariantsPriceBetween(minPrice, maxPrice, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        List<Product> products = productPage.getContent();

        // Lọc thêm theo giá nếu cần
        if (priceRange != null && !priceRange.trim().isEmpty() && 
            (name != null || categoryName != null) && 
            !(name != null && categoryName != null)) {
            double finalMinPrice = minPrice;
            double finalMaxPrice = maxPrice;
            products = products.stream()
                    .filter(p -> variantRepository.findByProduct_ProductId(p.getProductId()).stream()
                            .anyMatch(v -> v.getPrice() >= finalMinPrice && v.getPrice() <= finalMaxPrice))
                    .collect(Collectors.toList());
        }

        // Xác định ngưỡng sản phẩm mới
        List<Integer> allProductIds = productRepository.findAll().stream()
                .map(Product::getProductId)
                .sorted()
                .collect(Collectors.toList());
        int totalProducts = allProductIds.size();
        int newThreshold = totalProducts > NEW_PRODUCT_LIMIT 
                ? allProductIds.get(totalProducts - NEW_PRODUCT_LIMIT) 
                : 0;

        // Chuyển đổi sang CardDTO
        List<CardDTO> cardDTOs = new ArrayList<>();
        for (Product product : products) {
            CardDTO cardDTO = createCardDTOFromProduct(product, newThreshold);
            cardDTOs.add(cardDTO);
        }

        // Trả về PagedResponseDTO với thông tin phân trang
        return new PagedResponseDTO<>(
                cardDTOs,
                productPage.getNumber() + 1, // currentPage bắt đầu từ 1 cho frontend
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    private CardDTO createCardDTOFromProduct(Product product, int newThreshold) {
        List<Variant> variants = variantRepository.findByProduct_ProductId(product.getProductId());
        List<VariantDTO> variantDTOs = new ArrayList<>();

        for (Variant variant : variants) {
            VariantDTO variantDTO = new VariantDTO(
                variant.getVariantId(),
                variant.getCode(),
                variant.getImage(),
                variant.getPrice(),
                null,
                null,
                null
            );
            variantDTOs.add(variantDTO);
        }

        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        if (product.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO(
                product.getCategory().getCategoryId(),
                product.getCategory().getCategoryName()
            );
            categoryDTOs.add(categoryDTO);
        }

        Map<String, Object> ratingData = reviewService.calculateAverageRatingByProductId(product.getProductId());
        double averageRating = (double) ratingData.get("average");

        CardDTO cardDTO = new CardDTO();
        cardDTO.setProductId(product.getProductId());
        cardDTO.setProductName(product.getProductName());
        cardDTO.setBrand(product.getBrand());
        cardDTO.setVariants(variantDTOs);
        cardDTO.setCategories(categoryDTOs);
        cardDTO.setNew(product.getProductId() >= newThreshold);
        cardDTO.setAverageRating(averageRating);

        return cardDTO;
    }

    public Map uploadImage(MultipartFile file) throws IOException {
        return cloudinaryService.uploadFile(file);
    }
}