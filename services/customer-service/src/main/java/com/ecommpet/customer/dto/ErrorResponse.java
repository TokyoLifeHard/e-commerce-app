package com.ecommpet.customer.dto;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
