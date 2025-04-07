package com.service.orderHistory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {

    @NotNull(message="Product Id is mandatory")
    private Integer productId;
    private String name;

    @NotNull(message="Product quantity is mandatory")
    private Integer quantity;
}
