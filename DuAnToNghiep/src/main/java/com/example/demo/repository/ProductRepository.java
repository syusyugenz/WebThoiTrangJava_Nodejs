//package com.example.demo.repository;
//
//import com.example.demo.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ProductRepository extends JpaRepository<Product, Integer> {
//    
//    // Tìm tất cả sản phẩm
//    List<Product> findAll();
//
//    // Tìm sản phẩm theo ID
//    Optional<Product> findById(Integer id);
//
//    // Tìm sản phẩm theo tên
//    List<Product> findByProductNameContainingIgnoreCase(String name);
//    
//    // Kiểm tra trùng tên sản phẩm trong cùng danh mục (không phân biệt hoa/thường)
//    Optional<Product> findByProductNameIgnoreCaseAndCategory_CategoryId(String productName, int categoryId);
//    
//    
//
//}

package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Tìm tất cả sản phẩm
    List<Product> findAll();

    // Tìm sản phẩm theo ID
    Optional<Product> findById(Integer id);

    // Tìm sản phẩm theo tên (phân trang)
    Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable pageable);

    // Kiểm tra trùng tên sản phẩm trong cùng danh mục
    Optional<Product> findByProductNameIgnoreCaseAndCategory_CategoryId(String productName, int categoryId);

    // Tìm sản phẩm theo danh mục (phân trang)
    Page<Product> findByCategory_CategoryNameIgnoreCase(String categoryName, Pageable pageable);

    // Tìm sản phẩm theo tên và danh mục (phân trang)
    Page<Product> findByProductNameContainingIgnoreCaseAndCategory_CategoryNameIgnoreCase(
            @Param("name") String name, @Param("categoryName") String categoryName, Pageable pageable);

    // Tìm sản phẩm theo khoảng giá của variants (phân trang)
    @Query("SELECT p FROM Product p JOIN p.variants v WHERE v.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByVariantsPriceBetween(
            @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

    // Tìm sản phẩm theo tên, danh mục và khoảng giá (phân trang)
    @Query("SELECT p FROM Product p JOIN p.variants v WHERE p.productName LIKE %:name% AND p.category.categoryName = :categoryName AND v.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByProductNameContainingIgnoreCaseAndCategory_CategoryNameIgnoreCaseAndVariantsPriceBetween(
            @Param("name") String name, @Param("categoryName") String categoryName,
            @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);
}