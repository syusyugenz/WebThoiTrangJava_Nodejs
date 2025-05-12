package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = {"*"})
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Lấy tất cả danh mục
     * @return Danh sách CategoryDTO
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<CategoryDTO> categories = productService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Thêm sản phẩm
     * @param productDTO DTO chứa thông tin sản phẩm
     * @return ProductDTO đã được tạo
     */
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO savedProduct = productService.addProduct(productDTO);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Có lỗi xảy ra khi thêm sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy tất cả sản phẩm với phân trang
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Danh sách sản phẩm với thông tin phân trang
     */
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        try {
            Page<ProductDTO> productPage = productService.getAllProducts(pageable);
            return new ResponseEntity<>(productPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Sửa sản phẩm
     * @param productId ID của sản phẩm
     * @param productDTO DTO chứa thông tin mới
     * @return ProductDTO đã được cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int productId, 
                                           @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Có lỗi xảy ra khi sửa sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Xóa sản phẩm
     * @param productId ID của sản phẩm cần xóa
     * @return Thông báo kết quả
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int productId) {
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>("Xóa sản phẩm thành công", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Có lỗi xảy ra khi xóa sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}