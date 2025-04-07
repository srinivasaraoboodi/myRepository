package com.service.orderHistory.controller;

import com.service.orderHistory.bean.Orders;
import com.service.orderHistory.dto.OrderDto;
import com.service.orderHistory.dto.ProductDto;
import com.service.orderHistory.service.OrderService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<String> createOrder(@Valid @RequestBody List<ProductDto> orderRequest) throws BadRequestException {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("orders/history")
    public List<OrderDto> getOrdersHistory(){

        return orderService.getOrdersHistory();
    }
}
