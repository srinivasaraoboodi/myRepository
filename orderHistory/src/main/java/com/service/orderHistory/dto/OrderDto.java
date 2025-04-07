package com.service.orderHistory.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {

    private Integer id;
    private LocalDate orderDate;
    private double totalAmount;
    private String status;
    private List<ProductDto> items;
}
