package com.example.demo.controller;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.dto.AttributeOptionDTO;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.DetailDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.dto.WareHouseDTO;
import com.example.demo.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/details")
public class DetailController {

    @Autowired
    private DetailService detailService;

    // Lấy thông tin chi tiết sản phẩm theo productId (bao gồm tất cả variants với readableCode)
    @GetMapping("/{productId}")
    public ResponseEntity<DetailDTO> getProductDetail(@PathVariable int productId) {
        Optional<DetailDTO> detailDTO = detailService.getProductDetail(productId);
        return detailDTO.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy thông tin tóm tắt sản phẩm theo productId (chỉ lấy 1 variant đại diện với readableCode)
    @GetMapping("/summary/{productId}")
    public ResponseEntity<DetailDTO> getProductSummary(@PathVariable int productId) {
        Optional<DetailDTO> detailDTO = detailService.getProductSummary(productId);
        return detailDTO.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy tất cả các thuộc tính (dùng để hiển thị danh sách thuộc tính trên giao diện)
    @GetMapping("/attributes")
    public ResponseEntity<List<AttributeDTO>> getAllAttributes() {
        List<AttributeDTO> attributes = detailService.getAllAttributes();
        return attributes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(attributes);
    }

    // Lấy tất cả các tùy chọn thuộc tính (dùng để hiển thị danh sách tùy chọn trên giao diện)
    @GetMapping("/attribute-options")
    public ResponseEntity<List<AttributeOptionDTO>> getAllAttributeOptions() {
        List<AttributeOptionDTO> options = detailService.getAllAttributeOptions();
        return options.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(options);
    }

    // Lấy tùy chọn thuộc tính theo attributeId (dùng để lọc tùy chọn theo thuộc tính cụ thể)
    @GetMapping("/attribute-options/{attributeId}")
    public ResponseEntity<List<AttributeOptionDTO>> getAttributeOptionsByAttributeId(@PathVariable int attributeId) {
        List<AttributeOptionDTO> options = detailService.getAttributeOptionsByAttributeId(attributeId);
        return options.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(options);
    }

    // Lấy tất cả các biến thể (bao gồm readableCode trong mỗi VariantDTO)
    @GetMapping("/variants")
    public ResponseEntity<List<VariantDTO>> getAllVariants() {
        List<VariantDTO> variants = detailService.getAllVariants();
        return variants.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(variants);
    }

    // Lấy tất cả kho hàng
    @GetMapping("/warehouses")
    public ResponseEntity<List<WareHouseDTO>> getAllWarehouses() {
        List<WareHouseDTO> warehouses = detailService.getAllWareHouses();
        return warehouses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(warehouses);
    }

    // Lấy tất cả danh mục
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = detailService.getAllCategories();
        return categories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categories);
    }

    // Lấy số lượng còn lại theo variantId (số lượng trong kho trừ số lượng đã bán)
    @GetMapping("/warehouses/quantity/{variantId}")
    public ResponseEntity<Integer> getQuantityByVariantId(@PathVariable int variantId) {
        Optional<Integer> quantity = detailService.getQuantityByVariantId(variantId);
        return quantity.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy danh sách đánh giá theo productId
    @GetMapping("/reviews/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable int productId) {
        List<ReviewDTO> reviews = detailService.getReviewsByProductId(productId);
        return reviews.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reviews);
    }
}