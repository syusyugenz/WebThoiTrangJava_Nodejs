package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Lấy tất cả danh mục dưới dạng CategoryDTO
     * @return Danh sách CategoryDTO
     */
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryDTO(category.getCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }

    /**
     * Thêm sản phẩm mới
     * @param productDTO DTO chứa thông tin sản phẩm
     * @return ProductDTO đã được lưu
     */
    public ProductDTO addProduct(ProductDTO productDTO) {
        return saveOrUpdateProduct(productDTO, null);
    }

    /**
     * Sửa sản phẩm
     * @param productId ID của sản phẩm cần sửa
     * @param productDTO DTO chứa thông tin mới
     * @return ProductDTO đã được cập nhật
     */
    public ProductDTO updateProduct(int productId, ProductDTO productDTO) {
        return saveOrUpdateProduct(productDTO, productId);
    }

    /**
     * Lưu hoặc cập nhật sản phẩm
     * @param productDTO DTO chứa thông tin sản phẩm
     * @param productId ID của sản phẩm (null nếu thêm mới)
     * @return ProductDTO đã được lưu hoặc cập nhật
     * @throws RuntimeException Nếu dữ liệu không hợp lệ hoặc trùng lặp
     */
    private ProductDTO saveOrUpdateProduct(ProductDTO productDTO, Integer productId) {
        Optional<Category> categoryOpt = categoryRepository.findById(productDTO.getCategories().get(0).getCategoryId());
        
        if (!categoryOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + productDTO.getCategories().get(0).getCategoryId());
        }

        Product product;
        if (productId != null) {
            product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));
        } else {
            product = new Product();

            Optional<Product> existingProduct = productRepository.findByProductNameIgnoreCaseAndCategory_CategoryId(
                    productDTO.getProductName(), categoryOpt.get().getCategoryId());
            if (existingProduct.isPresent()) {
                throw new RuntimeException("Sản phẩm với tên '" + productDTO.getProductName() + "' đã tồn tại trong danh mục này!");
            }
        }

        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setBrand(productDTO.getBrand());
        product.setCategory(categoryOpt.get());
        
        Product savedProduct = productRepository.save(product);
        
        return new ProductDTO(savedProduct.getProductId(), savedProduct.getProductName(), savedProduct.getDescription(), savedProduct.getBrand(), productDTO.getCategories());
    }

    /**
     * Lấy tất cả sản phẩm với phân trang, sắp xếp mới nhất trước
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Page<ProductDTO> Danh sách sản phẩm với thông tin phân trang
     */
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
            Sort.by("productId").descending()
        );
        return productRepository.findAll(sortedByIdDesc)
                .map(product -> new ProductDTO(
                    product.getProductId(),
                    product.getProductName(),
                    product.getDescription(),
                    product.getBrand(),
                    List.of(new CategoryDTO(product.getCategory().getCategoryId(), product.getCategory().getCategoryName()))
                ));
    }

    /**
     * Xóa sản phẩm
     * @param productId ID của sản phẩm cần xóa
     * @throws RuntimeException Nếu sản phẩm không tồn tại
     */
    public void deleteProduct(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));
        productRepository.delete(product);
    }
}