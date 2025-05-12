package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Chuẩn hóa tên danh mục (loại bỏ khoảng trắng thừa)
     * @param name Tên danh mục
     * @return Tên đã chuẩn hóa
     */
    private String normalizeName(String name) {
        return (name != null) ? name.trim() : "";
    }

    /**
     * Thêm một danh mục mới
     * @param categoryDTO DTO chứa thông tin danh mục
     * @return CategoryDTO đã được lưu
     * @throws RuntimeException Nếu dữ liệu không hợp lệ hoặc tên danh mục đã tồn tại
     */
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        String normalizedName = normalizeName(categoryDTO.getCategoryName());
        if (normalizedName.isEmpty()) {
            throw new RuntimeException("Tên danh mục không được để trống!");
        }

        Optional<Category> existingCategory = categoryRepository.findByCategoryNameIgnoreCase(normalizedName);
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Danh mục '" + categoryDTO.getCategoryName() + "' đã tồn tại!");
        }

        Category category = new Category();
        category.setCategoryName(normalizedName);
        
        Category savedCategory = categoryRepository.save(category);
        
        categoryDTO.setCategoryId(savedCategory.getCategoryId());
        categoryDTO.setCategoryName(savedCategory.getCategoryName());
        return categoryDTO;
    }

    /**
     * Lấy tất cả danh mục với phân trang, sắp xếp mới nhất trước
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Page<CategoryDTO> Danh sách danh mục với thông tin phân trang
     */
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
            Sort.by("categoryId").descending()
        );
        return categoryRepository.findAll(sortedByIdDesc)
                .map(this::convertToDTO);
    }

    /**
     * Cập nhật một danh mục
     * @param categoryId ID của danh mục cần cập nhật
     * @param categoryDTO DTO chứa thông tin mới
     * @return CategoryDTO đã được cập nhật
     * @throws RuntimeException Nếu danh mục không tồn tại hoặc tên danh mục đã tồn tại
     */
    public CategoryDTO updateCategory(int categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại!"));

        String normalizedName = normalizeName(categoryDTO.getCategoryName());
        if (normalizedName.isEmpty()) {
            throw new RuntimeException("Tên danh mục không được để trống!");
        }

        Optional<Category> existingCategory = categoryRepository.findByCategoryNameIgnoreCase(normalizedName);
        if (existingCategory.isPresent() && existingCategory.get().getCategoryId() != categoryId) {
            throw new RuntimeException("Danh mục '" + categoryDTO.getCategoryName() + "' đã tồn tại!");
        }

        category.setCategoryName(normalizedName);
        
        Category updatedCategory = categoryRepository.save(category);
        
        return convertToDTO(updatedCategory);
    }

    /**
     * Xóa một danh mục theo ID
     * @param categoryId ID của danh mục cần xóa
     * @throws RuntimeException Nếu danh mục không tồn tại hoặc có dữ liệu liên quan
     */
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại!"));

        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new RuntimeException("Không thể xóa danh mục vì có dữ liệu liên quan: " + e.getMessage());
        }
    }

    /**
     * Chuyển đổi từ Category sang CategoryDTO
     * @param category Thực thể Category
     * @return CategoryDTO
     */
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }
}