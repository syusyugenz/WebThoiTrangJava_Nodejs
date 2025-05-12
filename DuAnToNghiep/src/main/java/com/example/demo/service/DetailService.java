//package com.example.demo.service;
//
//import com.example.demo.dto.AttributeDTO;
//import com.example.demo.dto.AttributeOptionDTO;
//import com.example.demo.dto.CategoryDTO;
//import com.example.demo.dto.DetailDTO;
//import com.example.demo.dto.ProductDTO;
//import com.example.demo.dto.ReviewDTO;
//import com.example.demo.dto.VariantDTO;
//import com.example.demo.dto.WareHouseDTO;
//import com.example.demo.entity.Attribute;
//import com.example.demo.entity.AttributeOption;
//import com.example.demo.entity.OrderItem;
//import com.example.demo.entity.Product;
//import com.example.demo.entity.WareHouse;
//import com.example.demo.repository.AttributeOptionRepository;
//import com.example.demo.repository.AttributeRepository;
//import com.example.demo.repository.CategoryRepository;
//import com.example.demo.repository.OrderItemRepository;
//import com.example.demo.repository.ProductRepository;
//import com.example.demo.repository.VariantRepository;
//import com.example.demo.repository.WareHouseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class DetailService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private VariantRepository variantRepository;
//
//    @Autowired
//    private AttributeRepository attributeRepository;
//
//    @Autowired
//    private AttributeOptionRepository attributeOptionRepository;
//
//    @Autowired
//    private WareHouseRepository wareHouseRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private ReviewService reviewService;
//
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    @Autowired
//    private WareHouseService wareHouseService; // Thêm dependency để lấy quantitySold
//
//    private String convertCodeToReadableFormat(String code) {
//        if (code == null || code.isEmpty()) {
//            return "N/A";
//        }
//
//        List<String> pairs = Arrays.asList(code.split(";"));
//        List<String> readablePairs = pairs.stream().map(pair -> {
//            String[] ids = pair.split(":");
//            if (ids.length != 2) {
//                return "Invalid pair";
//            }
//
//            Integer attributeId = Integer.parseInt(ids[0]);
//            Integer attributeOptionId = Integer.parseInt(ids[1]);
//
//            Attribute attribute = attributeRepository.findById(attributeId)
//                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thuộc tính với ID: " + attributeId));
//            String attributeName = attribute.getName();
//
//            AttributeOption option = attributeOptionRepository.findById(attributeOptionId)
//                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn với ID: " + attributeOptionId));
//            String optionValue = option.getValue();
//
//            return attributeName + ":" + optionValue;
//        }).collect(Collectors.toList());
//
//        return String.join(", ", readablePairs);
//    }
//
//    public Optional<DetailDTO> getProductDetail(int productId) {
//        Optional<Product> productOptional = productRepository.findById(productId);
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//
//            // Lấy danh sách thông tin tồn kho từ WareHouseService
//            List<Map<String, Object>> stockInfo = wareHouseService.getStockInfoByProductId(productId);
//            // Tính tổng quantitySold từ tất cả biến thể
//            int totalQuantitySold = stockInfo.stream()
//                    .mapToInt(stock -> ((Number) stock.get("quantitySold")).intValue())
//                    .sum();
//
//            DetailDTO detailDTO = new DetailDTO();
//            detailDTO.setProductId(product.getProductId());
//            detailDTO.setProductName(product.getProductName());
//            detailDTO.setBrand(product.getBrand());
//            detailDTO.setDescription(product.getDescription());
//            detailDTO.setQuantitySold(totalQuantitySold); // Điền tổng quantitySold
//
//            List<VariantDTO> variants = variantRepository.findByProduct_ProductId(productId).stream()
//                .map(variant -> {
//                    VariantDTO dto = new VariantDTO(
//                        variant.getVariantId(),
//                        variant.getCode(),
//                        variant.getImage(),
//                        variant.getPrice(),
//                        List.of(new ProductDTO(
//                            variant.getProduct().getProductId(),
//                            variant.getProduct().getProductName(),
//                            variant.getProduct().getDescription(),
//                            variant.getProduct().getBrand(),
//                            null
//                        )),
//                        null,
//                        null
//                    );
//                    dto.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//                    return dto;
//                })
//                .collect(Collectors.toList());
//            detailDTO.setVariants(variants);
//
//            List<ReviewDTO> reviews = getReviewsForProduct(productId);
//            detailDTO.setReviews(reviews);
//
//            List<AttributeDTO> attributes = attributeRepository.findAll().stream().map(attribute -> {
//                AttributeDTO dto = new AttributeDTO();
//                dto.setAttributeId(attribute.getAttributeId());
//                dto.setName(attribute.getName());
//                return dto;
//            }).collect(Collectors.toList());
//            detailDTO.setAttributes(attributes);
//
//            List<AttributeOptionDTO> attributeOptions = attributeOptionRepository.findAll().stream().map(option -> {
//                AttributeOptionDTO dto = new AttributeOptionDTO();
//                dto.setAttributeOptionId(option.getAttributeOptionId());
//                dto.setValue(option.getValue());
//                return dto;
//            }).collect(Collectors.toList());
//            detailDTO.setAttributeOptions(attributeOptions);
//
//            List<WareHouseDTO> warehouses = wareHouseRepository.findAll().stream().map(warehouse -> {
//                WareHouseDTO dto = new WareHouseDTO();
//                dto.setWarehouseId(warehouse.getWarehouseId());
//                dto.setQuantity(warehouse.getQuantity());
//                dto.setPrice(warehouse.getPrice());
//                return dto;
//            }).collect(Collectors.toList());
//            detailDTO.setWarehouses(warehouses);
//
//            List<CategoryDTO> categories = categoryRepository.findAll().stream().map(category -> {
//                CategoryDTO dto = new CategoryDTO();
//                dto.setCategoryId(category.getCategoryId());
//                dto.setCategoryName(category.getCategoryName());
//                return dto;
//            }).collect(Collectors.toList());
//            detailDTO.setCategories(categories);
//
//            return Optional.of(detailDTO);
//        }
//        return Optional.empty();
//    }
//
//    private List<ReviewDTO> getReviewsForProduct(int productId) {
//        return reviewService.getReviewsByProductId(productId);
//    }
//
//    public List<VariantDTO> getAllVariants() {
//        return variantRepository.findAll().stream().map(variant -> {
//            VariantDTO dto = new VariantDTO(
//                variant.getVariantId(),
//                variant.getCode(),
//                variant.getImage(),
//                variant.getPrice(),
//                List.of(new ProductDTO(
//                    variant.getProduct().getProductId(),
//                    variant.getProduct().getProductName(),
//                    variant.getProduct().getDescription(),
//                    variant.getProduct().getBrand(),
//                    null
//                )),
//                null,
//                null
//            );
//            dto.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//            return dto;
//        }).collect(Collectors.toList());
//    }
//
//    public List<AttributeDTO> getAllAttributes() {
//        return attributeRepository.findAll().stream().map(attribute -> {
//            AttributeDTO dto = new AttributeDTO();
//            dto.setAttributeId(attribute.getAttributeId());
//            dto.setName(attribute.getName());
//            return dto;
//        }).collect(Collectors.toList());
//    }
//
//    public List<AttributeOptionDTO> getAllAttributeOptions() {
//        return attributeOptionRepository.findAll().stream().map(option -> {
//            AttributeOptionDTO dto = new AttributeOptionDTO();
//            dto.setAttributeOptionId(option.getAttributeOptionId());
//            dto.setValue(option.getValue());
//            return dto;
//        }).collect(Collectors.toList());
//    }
//
//    public List<WareHouseDTO> getAllWareHouses() {
//        return wareHouseRepository.findAll().stream().map(warehouse -> {
//            WareHouseDTO dto = new WareHouseDTO();
//            dto.setWarehouseId(warehouse.getWarehouseId());
//            dto.setQuantity(warehouse.getQuantity());
//            dto.setPrice(warehouse.getPrice());
//            return dto;
//        }).collect(Collectors.toList());
//    }
//
//    public List<CategoryDTO> getAllCategories() {
//        return categoryRepository.findAll().stream().map(category -> {
//            CategoryDTO dto = new CategoryDTO();
//            dto.setCategoryId(category.getCategoryId());
//            dto.setCategoryName(category.getCategoryName());
//            return dto;
//        }).collect(Collectors.toList());
//    }
//
//    public List<AttributeOptionDTO> getAttributeOptionsByAttributeId(int attributeId) {
//        List<AttributeOption> options = attributeOptionRepository.findByAttribute_AttributeId(attributeId);
//        return options.stream()
//                .map(option -> new AttributeOptionDTO(option.getAttributeOptionId(), option.getValue(), null))
//                .collect(Collectors.toList());
//    }
//
//    public Optional<DetailDTO> getProductSummary(int productId) {
//        Optional<Product> productOptional = productRepository.findById(productId);
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//
//            // Lấy danh sách thông tin tồn kho từ WareHouseService
//            List<Map<String, Object>> stockInfo = wareHouseService.getStockInfoByProductId(productId);
//            // Tính tổng quantitySold từ tất cả biến thể
//            int totalQuantitySold = stockInfo.stream()
//                    .mapToInt(stock -> ((Number) stock.get("quantitySold")).intValue())
//                    .sum();
//
//            DetailDTO detailDTO = new DetailDTO();
//            detailDTO.setProductId(product.getProductId());
//            detailDTO.setProductName(product.getProductName());
//            detailDTO.setBrand(product.getBrand());
//            detailDTO.setDescription(product.getDescription());
//            detailDTO.setQuantitySold(totalQuantitySold); // Điền tổng quantitySold
//
//            if (product.getCategory() != null) {
//                detailDTO.setCategories(List.of(new CategoryDTO(product.getCategory().getCategoryId(), product.getCategory().getCategoryName())));
//            }
//
//            List<VariantDTO> variants = variantRepository.findByProduct_ProductId(productId).stream()
//                .map(variant -> {
//                    VariantDTO dto = new VariantDTO(
//                        variant.getVariantId(),
//                        variant.getCode(),
//                        variant.getImage(),
//                        variant.getPrice(),
//                        List.of(new ProductDTO(
//                            variant.getProduct().getProductId(),
//                            variant.getProduct().getProductName(),
//                            variant.getProduct().getDescription(),
//                            variant.getProduct().getBrand(),
//                            null
//                        )),
//                        null,
//                        null
//                    );
//                    dto.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//                    return dto;
//                })
//                .collect(Collectors.toList());
//
//            if (!variants.isEmpty()) {
//                VariantDTO representativeVariant = variants.get(0);
//                detailDTO.setVariants(List.of(representativeVariant));
//            } else {
//                detailDTO.setVariants(List.of());
//            }
//
//            List<ReviewDTO> reviews = getReviewsForProduct(productId);
//            detailDTO.setReviews(reviews);
//
//            return Optional.of(detailDTO);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<Integer> getQuantityByVariantId(int variantId) {
//        Optional<Integer> quantityInStockOptional = wareHouseRepository.sumQuantityByVariantId(variantId);
//        int quantityInStock = quantityInStockOptional.orElse(0);
//
//        List<String> validStatuses = Arrays.asList("4", "5", "6", "7");
//        List<OrderItem> soldItems = orderItemRepository.findByVariantIdAndOrderStatusIn(variantId, validStatuses);
//        int quantitySold = soldItems.stream()
//                .mapToInt(OrderItem::getQuantity)
//                .sum();
//
//        int quantityRemaining = quantityInStock - quantitySold;
//        return Optional.of(Math.max(quantityRemaining, 0));
//    }
//
//    public List<ReviewDTO> getReviewsByProductId(int productId) {
//        return reviewService.getReviewsByProductId(productId);
//    }
//}

package com.example.demo.service;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.dto.AttributeOptionDTO;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.DetailDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.dto.WareHouseDTO;
import com.example.demo.entity.Attribute;
import com.example.demo.entity.AttributeOption;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.WareHouse;
import com.example.demo.repository.AttributeOptionRepository;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.VariantRepository;
import com.example.demo.repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetailService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeOptionRepository attributeOptionRepository;

    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private WareHouseService wareHouseService;

    private String convertCodeToReadableFormat(String code) {
        if (code == null || code.isEmpty()) {
            return "N/A";
        }

        List<String> pairs = Arrays.asList(code.split(";"));
        List<String> readablePairs = pairs.stream().map(pair -> {
            String[] ids = pair.split(":");
            if (ids.length != 2) {
                return "Invalid pair";
            }

            Integer attributeId = Integer.parseInt(ids[0]);
            Integer attributeOptionId = Integer.parseInt(ids[1]);

            Attribute attribute = attributeRepository.findById(attributeId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thuộc tính với ID: " + attributeId));
            String attributeName = attribute.getName();

            AttributeOption option = attributeOptionRepository.findById(attributeOptionId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tùy chọn với ID: " + attributeOptionId));
            String optionValue = option.getValue();

            return attributeName + ":" + optionValue;
        }).collect(Collectors.toList());

        return String.join(", ", readablePairs);
    }

    public Optional<DetailDTO> getProductDetail(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            List<Map<String, Object>> stockInfo = wareHouseService.getStockInfoByProductId(productId);
            int totalQuantitySold = stockInfo.stream()
                    .mapToInt(stock -> ((Number) stock.get("quantitySold")).intValue())
                    .sum();

            DetailDTO detailDTO = new DetailDTO();
            detailDTO.setProductId(product.getProductId());
            detailDTO.setProductName(product.getProductName());
            detailDTO.setBrand(product.getBrand());
            detailDTO.setDescription(product.getDescription());
            detailDTO.setQuantitySold(totalQuantitySold);

            List<VariantDTO> variants = variantRepository.findByProduct_ProductId(productId).stream()
                .map(variant -> {
                    VariantDTO dto = new VariantDTO(
                        variant.getVariantId(),
                        variant.getCode(),
                        variant.getImage(),
                        variant.getPrice(),
                        List.of(new ProductDTO(
                            variant.getProduct().getProductId(),
                            variant.getProduct().getProductName(),
                            variant.getProduct().getDescription(),
                            variant.getProduct().getBrand(),
                            null
                        )),
                        null,
                        null
                    );
                    dto.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
                    return dto;
                })
                .collect(Collectors.toList());
            detailDTO.setVariants(variants);

            List<ReviewDTO> reviews = getReviewsForProduct(productId);
            detailDTO.setReviews(reviews);

            List<AttributeDTO> attributes = attributeRepository.findAll().stream().map(attribute -> {
                AttributeDTO dto = new AttributeDTO();
                dto.setAttributeId(attribute.getAttributeId());
                dto.setName(attribute.getName());
                return dto;
            }).collect(Collectors.toList());
            detailDTO.setAttributes(attributes);

            List<AttributeOptionDTO> attributeOptions = attributeOptionRepository.findAll().stream().map(option -> {
                AttributeOptionDTO dto = new AttributeOptionDTO();
                dto.setAttributeOptionId(option.getAttributeOptionId());
                dto.setValue(option.getValue());
                return dto;
            }).collect(Collectors.toList());
            detailDTO.setAttributeOptions(attributeOptions);

            List<WareHouseDTO> warehouses = wareHouseRepository.findAll().stream().map(warehouse -> {
                WareHouseDTO dto = new WareHouseDTO();
                dto.setWarehouseId(warehouse.getWarehouseId());
                dto.setQuantity(warehouse.getQuantity());
                dto.setPrice(warehouse.getPrice());
                return dto;
            }).collect(Collectors.toList());
            detailDTO.setWarehouses(warehouses);

            // Sửa: Chỉ lấy danh mục liên quan đến sản phẩm
            List<CategoryDTO> categories = product.getCategory() != null
                ? List.of(new CategoryDTO(product.getCategory().getCategoryId(), product.getCategory().getCategoryName()))
                : List.of();
            detailDTO.setCategories(categories);

            return Optional.of(detailDTO);
        }
        return Optional.empty();
    }

    private List<ReviewDTO> getReviewsForProduct(int productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    public List<VariantDTO> getAllVariants() {
        return variantRepository.findAll().stream().map(variant -> {
            VariantDTO dto = new VariantDTO(
                variant.getVariantId(),
                variant.getCode(),
                variant.getImage(),
                variant.getPrice(),
                List.of(new ProductDTO(
                    variant.getProduct().getProductId(),
                    variant.getProduct().getProductName(),
                    variant.getProduct().getDescription(),
                    variant.getProduct().getBrand(),
                    null
                )),
                null,
                null
            );
            dto.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
            return dto;
        }).collect(Collectors.toList());
    }

    public List<AttributeDTO> getAllAttributes() {
        return attributeRepository.findAll().stream().map(attribute -> {
            AttributeDTO dto = new AttributeDTO();
            dto.setAttributeId(attribute.getAttributeId());
            dto.setName(attribute.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<AttributeOptionDTO> getAllAttributeOptions() {
        return attributeOptionRepository.findAll().stream().map(option -> {
            AttributeOptionDTO dto = new AttributeOptionDTO();
            dto.setAttributeOptionId(option.getAttributeOptionId());
            dto.setValue(option.getValue());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<WareHouseDTO> getAllWareHouses() {
        return wareHouseRepository.findAll().stream().map(warehouse -> {
            WareHouseDTO dto = new WareHouseDTO();
            dto.setWarehouseId(warehouse.getWarehouseId());
            dto.setQuantity(warehouse.getQuantity());
            dto.setPrice(warehouse.getPrice());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryName(category.getCategoryName());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<AttributeOptionDTO> getAttributeOptionsByAttributeId(int attributeId) {
        List<AttributeOption> options = attributeOptionRepository.findByAttribute_AttributeId(attributeId);
        return options.stream()
                .map(option -> new AttributeOptionDTO(option.getAttributeOptionId(), option.getValue(), null))
                .collect(Collectors.toList());
    }

    public Optional<DetailDTO> getProductSummary(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            List<Map<String, Object>> stockInfo = wareHouseService.getStockInfoByProductId(productId);
            int totalQuantitySold = stockInfo.stream()
                    .mapToInt(stock -> ((Number) stock.get("quantitySold")).intValue())
                    .sum();

            DetailDTO detailDTO = new DetailDTO();
            detailDTO.setProductId(product.getProductId());
            detailDTO.setProductName(product.getProductName());
            detailDTO.setBrand(product.getBrand());
            detailDTO.setDescription(product.getDescription());
            detailDTO.setQuantitySold(totalQuantitySold);

            if (product.getCategory() != null) {
                detailDTO.setCategories(List.of(new CategoryDTO(product.getCategory().getCategoryId(), product.getCategory().getCategoryName())));
            }

            List<VariantDTO> variants = variantRepository.findByProduct_ProductId(productId).stream()
                .map(variant -> {
                    VariantDTO dto = new VariantDTO(
                        variant.getVariantId(),
                        variant.getCode(),
                        variant.getImage(),
                        variant.getPrice(),
                        List.of(new ProductDTO(
                            variant.getProduct().getProductId(),
                            variant.getProduct().getProductName(),
                            variant.getProduct().getDescription(),
                            variant.getProduct().getBrand(),
                            null
                        )),
                        null,
                        null
                    );
                    dto.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
                    return dto;
                })
                .collect(Collectors.toList());

            if (!variants.isEmpty()) {
                VariantDTO representativeVariant = variants.get(0);
                detailDTO.setVariants(List.of(representativeVariant));
            } else {
                detailDTO.setVariants(List.of());
            }

            List<ReviewDTO> reviews = getReviewsForProduct(productId);
            detailDTO.setReviews(reviews);

            return Optional.of(detailDTO);
        }
        return Optional.empty();
    }

    public Optional<Integer> getQuantityByVariantId(int variantId) {
        Optional<Integer> quantityInStockOptional = wareHouseRepository.sumQuantityByVariantId(variantId);
        int quantityInStock = quantityInStockOptional.orElse(0);

        List<String> validStatuses = Arrays.asList("4", "5", "6", "7");
        List<OrderItem> soldItems = orderItemRepository.findByVariantIdAndOrderStatusIn(variantId, validStatuses);
        int quantitySold = soldItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        int quantityRemaining = quantityInStock - quantitySold;
        return Optional.of(Math.max(quantityRemaining, 0));
    }

    public List<ReviewDTO> getReviewsByProductId(int productId) {
        return reviewService.getReviewsByProductId(productId);
    }
}