package com.ecommpet.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Description name is required")
        String description,
        @Positive(message = "Available quantity should be positive")
        double availableQuantity,
        @NotNull(message = "Price name is required")
        @Positive(message = "Price quantity should be positive")
        BigDecimal price,
        @NotNull(message = "Category id name is required")
        Integer categoryId
) {
}
