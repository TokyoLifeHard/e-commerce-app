package com.ecommpet.customer.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class Address {
    private String town;
    private String street;
    private String buildingNumber;
    private String flour;
    private String flatNumber;
}
