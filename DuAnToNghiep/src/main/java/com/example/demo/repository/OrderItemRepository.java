
package com.example.demo.repository;

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    // Tìm OrderItem theo orderId
    List<OrderItem> findByOrder_OrderId(int orderId);

    // Tìm OrderItem theo danh sách orderIds
    List<OrderItem> findByOrder_OrderIdIn(List<Integer> orderIds);

    // Tìm OrderItem theo Variant
    List<OrderItem> findByVariant(Variant variant);

    // Tìm OrderItem theo userId (thông qua Order và User)
    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE o.user.userId = :userId")
    List<OrderItem> findByUserId(@Param("userId") int userId);

    // Thêm phương thức mới: Tìm OrderItem theo variantId và danh sách trạng thái đơn hàng
    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE oi.variant.variantId = :variantId AND o.status IN (:statuses)")
    List<OrderItem> findByVariantIdAndOrderStatusIn(@Param("variantId") int variantId, @Param("statuses") List<String> statuses);
}