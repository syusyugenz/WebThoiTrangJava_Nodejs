//package com.example.demo.repository;
//
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.entity.WareHouse;
//
//@Repository
//public interface WareHouseRepository extends JpaRepository<WareHouse, Integer> {
//	List<WareHouse> findAll();
//	
//	// Thêm phương thức tìm kho hàng theo variantId
//    List<WareHouse> findByVariant_VariantId(int variantId);
//	List<WareHouse> findByVariant_Product_ProductId(int productId);
//}
 
package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.WareHouse;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Integer> {
    List<WareHouse> findAll();
    
    // Sửa phương thức: Trả về Optional<WareHouse> thay vì List<WareHouse>
    Optional<WareHouse> findByVariant_VariantId(int variantId);
    
    List<WareHouse> findByVariant_Product_ProductId(int productId);

    // Thêm phương thức: Tính tổng quantity của tất cả WareHouse cho một variantId
    @Query("SELECT SUM(wh.quantity) FROM WareHouse wh WHERE wh.variant.variantId = :variantId")
    Optional<Integer> sumQuantityByVariantId(@Param("variantId") int variantId);
    
    List<WareHouse> findAllByVariant_VariantId(int variantId);
    
    
}