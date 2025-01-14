package com.ecommpet.customer.dto;

import com.ecommpet.customer.entity.Address;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
         String id,

         @NotNull(message = "")
         String firstName,

         @NotNull(message = "")
         String lastName,

         String thirdName,
         @NotNull(message = "")

         String email,

         Address adress
) {
}
