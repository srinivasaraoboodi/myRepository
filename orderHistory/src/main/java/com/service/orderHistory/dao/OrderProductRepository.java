package com.service.orderHistory.dao;

import com.service.orderHistory.bean.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    @Query(value = "SELECT QUANTITY FROM ORDER_PRODUCT op WHERE op.orders_id=:orderId AND op.product_id=:productId", nativeQuery = true)
    Integer findQuantityByOrderAndProductId(Integer orderId, Integer productId);
}
