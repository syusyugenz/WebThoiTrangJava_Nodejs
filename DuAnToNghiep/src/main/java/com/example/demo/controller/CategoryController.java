package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = {"*"})
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Thêm một danh mục mới
     * @param categoryDTO DTO chứa thông tin danh mục
     * @return CategoryDTO đã được tạo
     */
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO createdCategory = categoryService.addCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Lấy tất cả danh mục với phân trang
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Danh sách danh mục với thông tin phân trang
     */
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(Pageable pageable) {
        Page<CategoryDTO> categoryPage = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categoryPage);
    }

    /**
     * Cập nhật một danh mục
     * @param categoryId ID của danh mục
     * @param categoryDTO DTO chứa thông tin mới
     * @return CategoryDTO đã được cập nhật
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(
            @PathVariable int categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Danh mục không tồn tại")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Xóa một danh mục
     * @param categoryId ID của danh mục cần xóa
     * @return Thông báo kết quả
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("Xóa danh mục thành công!");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Danh mục không tồn tại")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}