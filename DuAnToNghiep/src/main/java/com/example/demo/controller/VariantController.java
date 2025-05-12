package com.example.demo.controller;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.dto.AttributeOptionDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/variants")
public class VariantController {

    @Autowired
    private VariantService variantService;

    /**
     * Lấy tất cả sản phẩm
     * @return Danh sách ProductDTO
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = variantService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Lấy tất cả thuộc tính
     * @return Danh sách AttributeDTO
     */
    @GetMapping("/attributes")
    public ResponseEntity<List<AttributeDTO>> getAllAttributes() {
        List<AttributeDTO> attributes = variantService.getAllAttributes();
        return ResponseEntity.ok(attributes);
    }

    /**
     * Lấy tất cả tùy chọn thuộc tính
     * @return Danh sách AttributeOptionDTO
     */
    @GetMapping("/attribute-options")
    public ResponseEntity<List<AttributeOptionDTO>> getAllAttributeOptions() {
        List<AttributeOptionDTO> attributeOptions = variantService.getAllAttributeOptions();
        return ResponseEntity.ok(attributeOptions);
    }

    /**
     * Lấy tùy chọn thuộc tính theo attributeId
     * @param attributeId ID thuộc tính
     * @return Danh sách AttributeOptionDTO
     */
    @GetMapping("/attribute-options/{attributeId}")
    public ResponseEntity<List<AttributeOptionDTO>> getAttributeOptionsByAttributeId(@PathVariable Integer attributeId) {
        List<AttributeOptionDTO> options = variantService.getAttributeOptionsByAttributeId(attributeId);
        return ResponseEntity.ok(options);
    }

    /**
     * Thêm variant
     * @param variantDTO DTO chứa thông tin variant
     * @param imageFile File hình ảnh
     * @return Thông báo kết quả
     */
    @PostMapping
    public ResponseEntity<String> saveVariant(@RequestPart("variant") VariantDTO variantDTO,
                                              @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            variantService.saveVariant(variantDTO, imageFile);
            return ResponseEntity.ok("Thêm variant thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải ảnh lên Cloudinary");
        }
    }

    /**
     * Lấy tất cả variants với phân trang
     * @
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Danh sách variant với thông tin phân trang
     */
    @GetMapping
    public ResponseEntity<Page<VariantDTO>> getAllVariants(Pageable pageable) {
        Page<VariantDTO> variantPage = variantService.getAllVariants(pageable);
        if (variantPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(variantPage);
    }

    /**
     * Cập nhật variant
     * @param id ID của variant
     * @param variantDTO DTO chứa thông tin mới
     * @param imageFile File hình ảnh (có thể null)
     * @return Thông báo kết quả
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateVariant(@PathVariable Integer id,
                                                @RequestPart("variant") VariantDTO variantDTO,
                                                @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            variantService.updateVariant(id, variantDTO, imageFile);
            return ResponseEntity.ok("Cập nhật variant thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải ảnh lên Cloudinary");
        }
    }

    /**
     * Xóa variant
     * @param variantId ID của variant cần xóa
     * @return Thông báo kết quả
     */
    @DeleteMapping("/{variantId}")
    public ResponseEntity<String> deleteVariant(@PathVariable Integer variantId) {
        try {
            variantService.deleteVariant(variantId);
            return ResponseEntity.ok("Xóa variant thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa variant: " + e.getMessage());
        }
    }

    /**
     * Lấy biến thể theo productId
     * @param productId ID sản phẩm
     * @return Danh sách VariantDTO
     */
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<VariantDTO>> getVariantsByProductId(@PathVariable int productId) {
        try {
            List<VariantDTO> variants = variantService.getVariantsByProductId(productId);
            if (variants.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(variants);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}