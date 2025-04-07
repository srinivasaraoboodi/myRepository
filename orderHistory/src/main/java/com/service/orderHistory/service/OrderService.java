package com.service.orderHistory.service;

import com.service.orderHistory.bean.Orders;
import com.service.orderHistory.dto.OrderDto;
import com.service.orderHistory.dto.ProductDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    public ResponseEntity<String> createOrder(List<ProductDto> orderRequest) throws BadRequestException;
    public List<OrderDto> getOrdersHistory();
}
